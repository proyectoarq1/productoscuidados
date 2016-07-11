package filtersA

import play.api.Logger
import play.api.mvc._
import play.api.mvc.Results.TooManyRequest
import scala.concurrent.Future
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.ConcurrentLinkedQueue
import play.api.Play.current
import scala.concurrent.Promise
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import javax.inject.Inject
import play.api.http.HttpFilters

class MyFilters @Inject() (

  filterConcurrent: ConcurrentRequestsLimiterFilter
) extends HttpFilters {

  val filters = Seq(filterConcurrent)
}

/**
 * This filter limits the concurrently processed requests (configurable via
 * "maxProcessedRequests", default 100) and queues requests exceeding this limit.
 * The maximum number of requests to queue can be configured as well
 * (via `maxQueuedRequests`, default 1000), so that requests that would grow the
 * queue over this limit are rejected with status 429 ("Too many requests").
 */

object Stats {
  type FilterFun = RequestHeader => Future[Result]
  val active = new AtomicInteger(0)
  val queue = new ConcurrentLinkedQueue[(Promise[Result], FilterFun, RequestHeader)]()
  
}
class ConcurrentRequestsLimiterFilter extends Filter {
  private val stat = Stats
  //private type FilterFun = RequestHeader => Future[Result]
  
  //private val active = new AtomicInteger(0)
  //private val queue = new ConcurrentLinkedQueue[(Promise[Result], FilterFun, RequestHeader)]()
  
  private lazy val maxProcessedRequests = current.configuration.getInt("maxProcessedRequests").getOrElse(200)
  private lazy val maxQueuedRequests = current.configuration.getInt("maxQueuedRequests").getOrElse(150)
  
  def apply(next: stat.FilterFun)(request: RequestHeader): Future[Result] = {
    Logger.debug(s"Comparing active=${stat.active.get} with maxProcessedRequests=$maxProcessedRequests")
    Logger.debug(s"Comparing queue=${stat.queue.size()} with maxQueuedRequests=$maxQueuedRequests")
    if(stat.active.get >= maxProcessedRequests) {
      if(stat.queue.size() > maxQueuedRequests) {
        Logger.debug("Rejecting request with status 429 because of too many queued requests.")
        Future.successful(TooManyRequest)
      } else {
        Logger.debug(s"Delaying request (${request.queryString}) because there are already more than $maxProcessedRequests requests processed.")
        val p = Promise[Result]()
        stat.queue.offer((p, next, request))
        p.future
      }
    } else {
      processRequest(next, request)
    }
  }

  /**
   * Process the given request header with the provided next filter function.
   * The active counter is updated at start/end accordingly.
   *
   * When the request processing is completed, a possibly queued request will be taken from the queue
   * and will be processed as well.
   */
  private def processRequest(next: stat.FilterFun, request: RequestHeader): Future[Result] = {
    val a = stat.active.incrementAndGet()
    
    Logger.debug(s"Processing request (${request.queryString}) with incremented active=$a")
    val result = next(request)
    
    result.onComplete { _ =>

      stat.active.decrementAndGet()
    
      Option(stat.queue.poll()).foreach { case (p, next, request) =>
        process(p, next, request)
      }
    }
    
    result
  }

  private def process(p: Promise[Result], next: stat.FilterFun, request: RequestHeader): Unit = {
    Logger.debug("Completing a queued request...")
    val res = processRequest(next, request)
    p.completeWith(res)
  }

}

