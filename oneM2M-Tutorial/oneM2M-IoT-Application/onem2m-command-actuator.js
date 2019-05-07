// Version 1.0

var request = require('request');

function createCIN(aeName, commandValue){
	var options = {
		uri: "http://127.0.0.1:8080/~/in-cse/in-name/"+ aeName +"/COMMAND",
		method: "POST",
		headers: {
			"X-M2M-Origin": "admin:admin",
			"Content-Type": "application/json;ty=4"
		},
		json: {
			"m2m:cin":{
					"con": commandValue
				}
			}
	};

	request(options, function (err, resp, body) {
        console.log("Sending http request ...");
        if(err){
			console.log(resp);
		} else {
            console.log("Received response : " + resp.statusCode);
		}
	});
}
if (process.argv.length != 4) {
    console.log("Incorrect invocation !");
    console.log("To trigger a command on an actuator > node onem2m-command-actuator.js %ActuatorName% %CommandName%");
} else {
    createCIN(process.argv[2], process.argv[3]);
}
