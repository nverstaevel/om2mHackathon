# SMART Digital Twin API Documentation

*********************
* HTTP Request

Field | Value
------------ | -------------
URL | ~/in-cse/in-name/DigitalTwin
?op           | GET, SET_OPEN, SET_CLOSE, SET_LIGHT
?room         | Room number [0...400].
?device       | { window, door, light}
?value        | New value
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
URL example| ~/in-cse/in-name/DigitalTwin?op=GET&room=203&device=window
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
        "con": "{\"room\":\"203\", \"device\":\"window\", \"state\":\"CLOSE\"}"
        }
    }
```
*********************

## Change the current state of a Window or a Door 
* HTTP Request
 
Field | Value
------------ | -------------
URL example| ~/in-cse/in-name/DigitalTwin?op=SET_CLOSE&room=203&device=window
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
        "con": "{\"room\":\"203\", \"device\":\"window\", \"state\":\"CLOSE\"}"
    }
}
```
*********************


## Change the current state of a Light
* HTTP Request
 
Field | Value
------------ | -------------
URL example| ~/in-cse/in-name/DigitalTwin?op=SET_LIGHT&room=203&device=light&value=50.0
op      | SET_CLOSE, SET_OPEN
value   | Intensity between 0.0 and 100.0
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
        "con": "{\"room\":\"203\", \"device\":\"light\", \"state\":\"50.0\"}"
    }
}
```

