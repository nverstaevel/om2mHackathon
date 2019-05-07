// Version 1.0

var express = require('express');
var bodyParser = require('body-parser');
var request = require('request');
var app = express();


var monitorIP = "127.0.0.1";
var monitorPort = 9999;

var sensorThreshold = 0;

var sensorToMonitor = "LuminositySensor";
var actuatorToTrigger = "LedActuator";
var isLedOn = false;

app.use(bodyParser.json());

// start http server
app.listen(monitorPort, function () {
	console.log("Listening on: " + monitorIP + ":" + monitorPort);
});


// handle received http messages
app.post('/', function (req, res) {
	var  vrq  = req.body["m2m:sgn"]["m2m:vrq"];
	if  (!vrq) {
		var sensorValue = req.body["m2m:sgn"]["m2m:nev"]["m2m:rep"]["m2m:cin"].con;
		console.log("Receieved sensor value : " + sensorValue);

		if(sensorValue>sensorThreshold && isLedOn ){
			console.log("High luminosity => Switch Off the led");
			createCIN("[switchOff]");
			isLedOn=false;
		}else if(sensorValue<=sensorThreshold && !isLedOn){
			console.log("Low luminosity => Switch On the led");
			createCIN("[switchOn]")
			isLedOn=true;
		}else{
			console.log("Nothing to do");
		}
	}
	res.sendStatus(200);	
});

createAE();
function createAE(){
	var options = {
		uri: "http://127.0.0.1:8080/~/in-cse/in-name/",
		method: "POST",
		headers: {
			"X-M2M-Origin": "admin:admin",
			"Content-Type": "application/json;ty=2"
		},
		json: { 
			"m2m:ae":{
				"rn": sensorToMonitor + "Monitor",			
				"api":"org.demo.app",
				"rr":"true",
				"poa":["http://"+ monitorIP + ":" + monitorPort]
			}
		}
	};

	request(options, function (err, resp, body) {
		if(err){
			console.log("AE Creation error : " + err);
		} else {
			console.log("AE Creation :" + resp.statusCode);
			createSUB();
		}
	});
}


function createSUB(){
	var options = {
		uri: "http://127.0.0.1:8080/~/in-cse/in-name/" + sensorToMonitor + "/DATA",
		method: "POST",
		headers: {
			"X-M2M-Origin": "admin:admin",
			"Content-Type": "application/json;ty=23"
		},
		json: {
			"m2m:sub": {
				"rn": "SUB_" + sensorToMonitor + "Monitor",
				"nu": ["in-name/"+ sensorToMonitor + "Monitor"],
				"nct": 2,
				"enc": {
					"net": 3
				}
			}
		}
	};

	request(options, function (err, resp, body) {
		if(err){
			console.log("SUB Creation error : " + err);
		}else{
			console.log("SUB Creation : " + resp.statusCode);
		}
	});
}

function createCIN(commandName){
	var options = {
		uri: "http://127.0.0.1:8080/~/in-cse/in-name/" + actuatorToTrigger + "/COMMAND",
		method: "POST",
		headers: {
			"X-M2M-Origin": "admin:admin",
			"Content-Type": "application/json;ty=4"
		},
		json: {
			"m2m:cin":{
					"con": commandName
				}
			}
	};

	request(options, function (err, resp, body) {
		if(err){
			console.log("CIN Creation error : " + err);
		}else{
			console.log("CIN Creation : " + resp.statusCode);
		}
	});
}


