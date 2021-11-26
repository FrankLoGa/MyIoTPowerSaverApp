#include <Math.h> 
#include <analogWrite.h>
#include <Ubidots.h> 
#include <Wire.h>
#include <Adafruit_INA219.h>

#define UBIDOTS_TOKEN "BBFF-98RGN2fkfJN0z1be5wDiZzpUaiKhQN" // Put here your Ubidots TOKEN 
#define WIFI_SSID "INFINITUMDB9D_2.4"  // Put here your Wi-Fi SSID
#define WIFI_PASS "N8qQJxx6cj"  // Put here your Wi-Fi password
/* Estudiantes usar wifimanager para que el cliente escoja el WIFI*/

#define VARIABLE_LABEL_curr "current" // Variable Temperatura 
#define VARIABLE_LABEL_presnc "presence" // Variable Humedad 
#define VARIABLE_LABEL_nlight "luminosity" // Variable Humedad 
#define VARIABLE_LABEL_led "lightlevel" // Variable led
#define DEVICE_LABEL "esp32" // Nombre del dispositivo a crear 

Ubidots ubidots(UBIDOTS_TOKEN, UBI_HTTP);
Adafruit_INA219 ina219;

#define PIR_SENSOR_PIN 35

#define LEDS_PIN 17
#define PIR_LEDS_PIN 7                                                        

#define PHOTORESISTOR_PIN 39

int light_val = 0;
int v_light_val = 0;
int n_light = 0;
int natural_light = 0;
int presence = LOW;
int presenceFlag = LOW;

float current = 0;


void setup() {

  Serial.begin(9600);

  ubidots.wifiConnect(WIFI_SSID, WIFI_PASS);
  // ubidots.setDebug(true); // use this for printing debug messages

  Serial.print("Conectado al WIFi: ");
  Serial.println(WIFI_SSID);

  ina219.begin();

  pinMode(LEDS_PIN , OUTPUT);  //definir pin como salida

  pinMode(PIR_SENSOR_PIN, INPUT); // declaramos los pines de entrada y salida 
  pinMode(PIR_LEDS_PIN, OUTPUT);


}

void loop(){


  presenceFlag = digitalRead(PIR_SENSOR_PIN);

  current = (ina219.getCurrent_mA()-2.5);

  
  Serial.print("Corriente: ");
  Serial.println(current);


  if(presenceFlag == HIGH){

    n_light = analogRead(PHOTORESISTOR_PIN);
    natural_light = map(n_light,0,4095,100,0);

    light_val = (int)1.7*n_light;
    v_light_val = map(light_val,0,4095,0,255);
    analogWrite(LEDS_PIN,v_light_val);
    delay(50);
    presence = HIGH;
  }else {
    analogWrite(LEDS_PIN,0);
    presence = LOW;
  }

  ubidots.add(VARIABLE_LABEL_curr,current); //corriente en la variable 
  Serial.println(current);

  ubidots.add(VARIABLE_LABEL_presnc, presence); // presencia en la variable 
  Serial.println(presence);

  ubidots.add(VARIABLE_LABEL_nlight, natural_light); // led en la variable 
  Serial.println(natural_light);

  ubidots.add(VARIABLE_LABEL_led, v_light_val); // led en la variable 
  Serial.println(v_light_val);

  // bool bufferSent = false;
  ubidots.send(DEVICE_LABEL); // Will send data to a device label that matches the device Id

  // if (bufferSent) {
  // // Do something if values were sent properly 
  // Serial.println("Values sent by the device");
  // }
  delay(1000);
}
