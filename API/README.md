# SMART Digital Twin API Documentation

# Table of Content
* [General principle](https://github.com/Eldey/om2mHackathon/tree/master/API#general-principle)
* [GET](https://github.com/Eldey/om2mHackathon/tree/master/API#get)
  * [Get the current state of all device and parameters](https://github.com/Eldey/om2mHackathon/tree/master/API#get-the-current-state-of-all-device)
  * [Get a specific device](https://github.com/Eldey/om2mHackathon/tree/master/API#get-the-current-state-of-a-device)
  * [Get a parameter](https://github.com/Eldey/om2mHackathon/tree/master/API#get-the-current-state-of-a-parameter)
* [SET](https://github.com/Eldey/om2mHackathon/tree/master/API#get)
  * [Set a Window or a Door](https://github.com/Eldey/om2mHackathon/tree/master/API#change-the-current-state-of-a-window-or-a-door) 
  * [Set a movement sensor](https://github.com/Eldey/om2mHackathon/tree/master/API#change-the-current-state-of-a-movement-sensor)
  * [Set a light](https://github.com/Eldey/om2mHackathon/tree/master/API#change-the-current-state-of-a-light)
* [Parameters](https://github.com/Eldey/om2mHackathon/tree/master/API#parameters)
  * [Controlling the time](https://github.com/Eldey/om2mHackathon/tree/master/API#controlling-the-time-of-day)
    * [Set to a specific time](https://github.com/Eldey/om2mHackathon/tree/master/API#controlling-the-time-of-day)
    * [Start auto-increment](https://github.com/Eldey/om2mHackathon/tree/master/API#start-auto-increment) 
    * [Stop auto-increment](https://github.com/Eldey/om2mHackathon/tree/master/API#stop-auto-increment)
    * [Change the increment value](https://github.com/Eldey/om2mHackathon/tree/master/API#change-increment)
*********************
## General Principle

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
## GET
### Get the current state of all device and parameters
* HTTP Request
 
Field | Value
------------ | -------------
URL example| http://localhost:8080/~/in-cse/in-name/DigitalTwin?op=GET_ALL
Method | POST
Header |  { "X-M2M-Origin": "admin:admin",  "Accept": "application/json"} 
Body | (empty)

* HTTP response

Status : 200 Ok
Body   :
```json
    A JSON with all rooms and devices.
```
*********************


### Get the current state of a device 
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
        "con": "{\"room\":\"6.203\", \"device\":\"window\", \"state\":\"CLOSED\"}"
        }
    }
```
*********************

### Get the current state of a parameter 
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
## SET
### Change the current state of a Window or a Door 
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
        "con": "{\"room\":\"6.203\", \"device\":\"window\", \"state\":\"CLOSED\"}"
    }
}
```
*********************

### Change the current state of a Movement sensor 
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


### Change the current state of a Light
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

*********************

## Parameters
### Controlling the time of Day

#### Set to a specific time
* HTTP Request
 
Field | Value
------------ | -------------
URL example| http://localhost:8080/~/in-cse/in-name/DigitalTwin?op=SET&param=TimeOfDay&value=0
op      | SET
?param          | Time of the day [0...1440] where 0 is 0:00 and 1440 is 23:59.
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
        "con": "{\"id\":\"TimeOfDay\", \"value\":\"0\"}"
    }
}
```

#### Start auto increment 
* HTTP Request
 
Field | Value
------------ | -------------
URL example| http://localhost:8080/~/in-cse/in-name/DigitalTwin?op=SET_AUTO&param=TimeOfDay&value=150
op      | SET_AUTO_ON
?param | Time in ms between increments
Method | POST
Header |  { "X-M2M-Origin": "admin:admin",  "Accept": "application/json"} 
Body | (empty)

* HTTP response

Status : 200 Ok
Body   :
```

```

#### Stop auto increment 
* HTTP Request
 
Field | Value
------------ | -------------
URL example| http://localhost:8080/~/in-cse/in-name/DigitalTwin?op=SET_STOP&param=TimeOfDay
op      | SET_AUTO_OFF
Method | POST
Header |  { "X-M2M-Origin": "admin:admin",  "Accept": "application/json"} 
Body | (empty)

* HTTP response

Status : 200 Ok
Body   :
```

```

#### Change increment 
* HTTP Request
 
Field | Value
------------ | -------------
URL example| http://localhost:8080/~/in-cse/in-name/DigitalTwin?op=SET_INCR&param=TimeOfDay&value=2
op      | SET_INCR
?value   | Integer
Method | POST
Header |  { "X-M2M-Origin": "admin:admin",  "Accept": "application/json"} 
Body | (empty)

* HTTP response

Status : 200 Ok
Body   :
```

```

