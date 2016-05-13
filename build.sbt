import com.typesafe.sbt.packager.docker._
import AssemblyKeys._

name := """play-scala-2.4"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

enablePlugins(JavaAppPackaging)

enablePlugins(DockerPlugin)

dockerCommands := Seq( 
  Cmd("FROM", "beevelop/java:latest"),
  Cmd("WORKDIR","/opt/docker"),
  Cmd("ADD","opt /opt"),
  Cmd("RUN","[\"chown\", \"-R\", \"daemon:daemon\", \".\"]"),
  Cmd("USER","daemon"),
  Cmd("ENTRYPOINT","[\"bin/play-scala-2-4\", \"-Dconfig.resource=application.docker.conf\", \"-Dlogger.resource=logback.xml\"]"),
  Cmd("CMD", "/usr/sbin/init")
)

scalaVersion := "2.11.8"
autoScalaLibrary := false

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  specs2 % Test,
  "org.mongodb" %% "casbah" % "3.1.1"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
resolvers += "fwbrasil.net" at "http://repo1.maven.org/maven2"

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator

fork in run := true

//Variables de heroku
herokuAppName in Compile := "aqueous-dusk-80720"
herokuJdkVersion in Compile := "1.8"

assemblySettings

mainClass in assembly := Some("play.core.server.NettyServer")

fullClasspath in assembly += Attributed.blank(PlayKeys.playPackageAssets.value)

libraryDependencies ~= { _ map {
  case m if m.organization == "com.typesafe.play" =>
    m.exclude("commons-logging", "commons-logging").
      exclude("com.typesafe.play", "sbt-link")
  case m => m
}}