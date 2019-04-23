# SMART Digital Twin API Documentation

*********************
* HTTP Request

Field | Value
------------ | -------------
URL | http://localhost:8080/~/in-cse/in-name/DigitalTwin
?op           | GET, SET_OPEN, SET_CLOSE, SET_LIGHT
?room         | Room number [0...400].
?device       | { window, door, light, movement}
?hex          | HEX code withouth the #
?intensity    | Intensity [0...100]
Method        | POST
Header        | { "X-M2M-Origin": "admin:admin",  "Accept": "application/json"} 
Body          | (empty)

* HTTP responses

Status : 200 Ok | 400 Bad Request
*********************
*********************

## Get the current state of a device 
* HTTP Request
 
Field | Value
------------ | -------------
URL example| http://localhost:8080/~/in-cse/in-name/DigitalTwin?op=GET&room=6.203&device=window
Method | POST
Header |  { "X-M2M-Origin": "admin:admin",  "Accept": "application/json"} 
Body | (empty)

* HTTP response

Status : 200 Ok
Body   :
```json
    {    
    "m2m:cin": {
        "rn": "cin_76678820",
        "ty": 4,
        "ri": "/in-cse/cin-76678820",
        "pi": "/in-cse/cnt-98842656",
        "ct": "20190418T132948",
        "lt": "20190418T132948",
        "st": 0,
        "cnf": "text/plain:0",
        "cs": 50,
        "con": "{\"room\":\"6.203\", \"device\":\"window\", \"state\":\"CLOSE\"}"
        }
    }
```
*********************

## Get the current state of a parameter 
* HTTP Request
 
Field | Value
------------ | -------------
URL example| http://localhost:8080/~/in-cse/in-name/DigitalTwin?op=GET&param=TimeOfDay
?op    | GET
?param | TimeOfDay
Method | POST
Header |  { "X-M2M-Origin": "admin:admin",  "Accept": "application/json"} 
Body | (empty)

* HTTP response

Status : 200 Ok
Body   :
```json
    {
    "m2m:cin": {
        "rn": "cin_824262239",
        "ty": 4,
        "ri": "/in-cse/cin-824262239",
        "pi": "/in-cse/cnt-609135732",
        "ct": "20190423T183731",
        "lt": "20190423T183731",
        "st": 0,
        "cnf": "text/plain:0",
        "cs": 35,
        "con": "{\"param\":\"TimeOfDay\", \"value\":\"13\"}"
    }
}
```
*********************

## Change the current state of a Window or a Door 
* HTTP Request
 
Field | Value
------------ | -------------
URL example| http://localhost:8080/~/in-cse/in-name/DigitalTwin?op=SET_CLOSE&room=203&device=window
op      | SET_CLOSE, SET_OPEN
Method | POST
Header |  { "X-M2M-Origin": "admin:admin",  "Accept": "application/json"} 
Body | (empty)

* HTTP response

Status : 200 Ok
Body   :
```json
    {
    "m2m:cin": {
        "rn": "cin_591265056",
        "ty": 4,
        "ri": "/in-cse/cin-591265056",
        "pi": "/in-cse/cnt-98842656",
        "ct": "20190418T135929",
        "lt": "20190418T135929",
        "st": 0,
        "cnf": "text/plain:0",
        "cs": 50,
        "con": "{\"room\":\"6.203\", \"device\":\"window\", \"state\":\"CLOSE\"}"
    }
}
```
*********************

## Change the current state of a Movement sensor 
* HTTP Request
 
Field | Value
------------ | -------------
URL example| http://localhost:8080/~/in-cse/in-name/DigitalTwin?op=SET_ON&room=6.203&device=movement
op      | SET_ON, SET_OFF
Method | POST
Header |  { "X-M2M-Origin": "admin:admin",  "Accept": "application/json"} 
Body | (empty)

* HTTP response

Status : 200 Ok
Body   :
```json
    {
    "m2m:cin": {
        "rn": "cin_591265056",
        "ty": 4,
        "ri": "/in-cse/cin-591265056",
        "pi": "/in-cse/cnt-98842656",
        "ct": "20190418T135929",
        "lt": "20190418T135929",
        "st": 0,
        "cnf": "text/plain:0",
        "cs": 50,
        "con": "{\"room\":\"6.203\", \"device\":\"window\", \"state\":\"ON\"}"
    }
}
```
*********************


## Change the current state of a Light
* HTTP Request
 
Field | Value
------------ | -------------
URL example| http://localhost:8080/~/in-cse/in-name/DigitalTwin?op=SET_LIGHT&room=6.203&device=light&hex=4286f4&intensity=21.2
op      | SET_CLOSE, SET_OPEN
?hex          | HEX code withouth the #
?intensity    | Intensity [0...100]
Method | POST
Header |  { "X-M2M-Origin": "admin:admin",  "Accept": "application/json"} 
Body | (empty)

* HTTP response

Status : 200 Ok
Body   :
```json
    {
    "m2m:cin": {
        "rn": "cin_44510965",
        "ty": 4,
        "ri": "/in-cse/cin-44510965",
        "pi": "/in-cse/cnt-591405752",
        "ct": "20190418T140145",
        "lt": "20190418T140145",
        "st": 0,
        "cnf": "text/plain:0",
        "cs": 48,
        "con": "{\"room\":\"6.203\", \"device\":\"light\", \"hex\":\"#4286f4\", \"intensity\":\"21.2\"}"
    }
}
```

