from locust import HttpLocust, TaskSet, task
import json

#Correr con locust -f locustfile.py --host=http://localhost:9000

#1) shops city = algo
#2) post found prices
#3) post shops

#1)+2)xn
#1)+3)+2)xn
from pymongo import MongoClient

def crear_falsos_shops():
    client = MongoClient()
    db = client['test']
    coll = db['shops']

    bera = coll.insert_one({"latitude" : 1.2, "longitude" : 30.8, "address" : "Av Bergara 2323", "location" : "Berazategui", "name" : "Los 12 hermanos" })
    bernal = coll.insert_one({"latitude" : 1.7, "longitude" : 78.5, "address" : "Calle falsa 123", "location" : "Bernal", "name" : "Coto" })
    lanus = coll.insert_one({ "latitude" : 3.3, "longitude" : 324.8, "address" : "Av. Corrientes 1233", "location" : "Lanus", "name" : "Econo" })

    #bera_id = str(bera.inserted_id)
    #bernal_id = str(bernal.inserted_id)
    #lanus_id = str(lanus.inserted_id)


import random

def user1(l):
    location = random.choice(["Berazategui","Lanus","Bernal"])
    shops_items = json.loads(shops(l,location).content)['items']
    if len(shops_items) == 0:
        crear_falsos_shops()
    shop_id = json.loads(shops(l,location).content)['items'][0]["id"]
    crear_found_price(l,shop_id)


def user2(l):
    location = crear_shop(l)
    crear_found_price(l,location)

def crear_found_price(l,shop_id):


    part_ids = ["abc0", "def0", "hij0", "klm0", "opeq0", "rst0", "uvw0", "xyz0", "1230", "4560", "7890", "10110", 
                  "abcdef9", "hijklm9", "opeqrst9", "uvwxyz9", "123456z", "7891011z", "abc1z", "def1z", "hij1z", 
                  "klm1z", "opeq1z", "rst1z", "uvw1z", "xyz1z", "123a7", "456a7", "789a7", "1011a7" , "abcdef17", 
                  "hijklm17", "opeqrst17", "uvwxyz1", "123456a", "7891011a", "abcdef12", "hijklm12", "opeqrst12", 
                  "uvwxyz12", "123456ab", "7891011ab"]
    product_id = random.choice(part_ids) + "-" + random.choice(part_ids) + "-" + random.choice(part_ids)

    price = [9.1,9.5,9.9,9.6,9.4,9.7,9.81,78.5,142.2,123.1,987.3,14.2,99.8,45.6,13.8,188.7,223.8,324.8,30.8,223.7,989.8,62.1,74.8,256.1,67.8]
    datetime = ['28/05/2016', '03/06/2016', '23/03/2016', '28/02/2016', '16/12/2016', '03/08/2016', '14/02/2016']

    l.client.post('/api/v1/found-prices',
        {'product_id': random.choice(product_id),
        'price':random.choice(price), 
        'datetime': random.choice(datetime),
        'shop_id': shop_id})


def crear_shop(l):
    lats = [0.1,0.5,2.9,0.6,2.4,0.7,2.1,3.5,2.2,0.2,3.3,1.2,0.8,1.6,1.8,1.7,2.8,3.8,3.8,2.7,9.8,6.1,7.8,2.1,6.8]
    longs = [9.1,9.5,9.9,9.6,9.4,9.7,9.81,78.5,142.2,123.1,987.3,14.2,99.8,45.6,13.8,188.7,223.8,324.8,30.8,223.7,989.8,62.1,74.8,256.1,67.8]
    address = ['Calle falsa 123', 'Suipacha 3666', 'Nosedonde 1234', 'UnLugar 4567', 'Av. Corrientes 1233', 'Guido 1298', 'Pringles 9876', 'Av Bergara 2323']
    location = ['Manantiales Chicos', 'Mendoza', 'General Alvear', 'Departamento de Cafayate', 'Cafayate','Gobernador Benegas', 'Azcuenaga', 'Olaeta', 'Los Condores', 'De la Garma', 'La Calera', 'Lartigau', 'Arroyo Cabral', 'Gobernador Gregores', 'Santa Regina', 'Amadores', 'Guerrico', 'San Carlos Sur', 'Alto de la Piedra', 'Puerto Ruiz', 'El Pueblito']    
    name = ['Los chinos', 'Los chinos II', 'Lo de irene', 'Carrefour', 'Coto', 'Walmart', 'Econo', 'Los 12 hermanos']

    shop = l.client.post('/api/v1/shops',
        {'latitude': random.choice(lats),
        'longitude': random.choice(longs),
        'address':random.choice(address), 
        'name': random.choice(name),
        'location': random.choice(location)})
    return shop.headers['location'].split('\\')[4]

def shops(l,location=None):
    if location == None:
        l.client.get("/api/v1/shops")
    else:
        return l.client.get("/api/v1/shops?location="+location)


def shopsConOffsetYLimit(l):
    limit = random.choice([1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,150,200])
    offset = random.choice([1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,150,200])
    l.client.get("/api/v1/shops?offset="+str(offset)+"&limit="+str(limit))

def found_prices(l):
    l.client.get("/api/v1/found-prices")

class UserBehavior(TaskSet):
    #tasks = {crear_shop:8,shops:1,shopsConOffsetYLimit:5,found_prices:2}
    tasks = {user1:7,user2:3}

class WebsiteUser(HttpLocust):
    task_set = UserBehavior
    min_wait=5000
    max_wait=9000
