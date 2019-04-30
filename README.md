# om2mHackathon
This is the respository of the om2mHackathon @Wollongong.

This hackathon aims to imagine new applications that a smart city
could offer its citizens, its elected officials or its technical and
administrative services in a multi-domain vision based on the
oneM2M standard.

The two day event will involve a day of developers tutorials followed
by the hackathon. In entering in this hackathon, participants will be
challenged to come up with innovative ideas, work with others to
learn new technologies, present your ideas and compete for prizes.

EVENT COST: FREE

VENUE: SMART BUILDING 6, FOYER

WEDNESDAY 8 MAY - 9.30AM - 5.00PM 

THURSDSAY 9 MAY - 8.30AM - 5.00PM

Hosted by the SMART Infrastructure Facility

TO REGISTER VISIT:
https://bit.ly/2IdpyZ8

This hackathon is supported by funding from ETSI, SMART, and LAAS-CNRS.

## Content of your Kit:

* HiLetgo ESP8266 NodeMCU : [Documentation](https://www.nodemcu.com/index_en.html) 
* Elegoo 37-in-1 Sensor Kit V2.0 : [Documentation & Code examples](https://www.elegoo.com/tutorial/Elegoo%2037%20Sensor%20Kit%20Tutorial%20for%20UNO%20R3%20and%20Mega%202560%20V2.0.0.2019.03.04.zip)
* Breadboard
* 120pcs Dupont Wire (40 Male to Female, 40 Male to Male, 40 Female to Female)
* USB Cable 

Some more leds are available on request.

### Programming with NodeMCU


1 - Get and install the Arduino IDE : https://www.arduino.cc/en/main/software

2 - Launch the Arduino IDE

3 - Under File -> Preferences -> Additional Boards Manager URLs, adds http://arduino.esp8266.com/stable/package_esp8266com_index.json and validate.

4 - Select the board: Tools -> Board -> NodeMCU 1.0 (ESP-12E Module)

### Simulation: Digital Twin of the SMART Infrastructure Buidling.

The simulation has two components:
* A middleware based on oneM2M managing the simulation and the sensors
* A 3D visualisation of the SMART Infrastructure Building 

#### Quick start
#####  Requirements

```powershell
JAVA Runtime Environment (JRE) 8 (not compatible with JAVA 11)
```
##### Launching the simulation on Windows

1. Download the middleware: [Download](https://github.com/Eldey/om2mHackathon/blob/master/IPE/x86_64.zip)
2. Extract the file content of the file x86_64.zip 
3. Execute the file start.bat

![](https://github.com/Eldey/om2mHackathon/blob/master/img/middleware_laumched.PNG)

#### Launching the simulation on Linux

```powershell
Unzip the file x86_64.zip 
Launch file start.sh
```
