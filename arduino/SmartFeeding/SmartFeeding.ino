#include <KNoTThing.h>
#include <Servo.h>

#define thingName "SmartFeeding-testing"

#define infraredSensorId 3
#define ultraSensorId 5
#define servoMotorId 1

#define infraredSensorName "infraredSensor"
#define infraredSensorPin 4

#define ultraSensorName "ultraSensor"
#define ultraSensorTrigPin 2
#define ultraSensorEchoPin 3

#define relaySensorPin 5

#define servoMotorName "servoMotor"
int servoMotorPin = A0;

Servo servo;
int servo_value = 0;
bool gateOpened = false;

KNoTThing thing;

int infraredRead(uint8_t *val) {
    uint8_t value = digitalRead(infraredSensorPin);
    *val = value;
    return 0;
}

int infraredWrite(uint8_t *val) {
    return 0;
}

int ultraRead(int32_t *val, int32_t *multiplier) {
//    *val = digitalRead(ultraSensorPin);
    
    // Clears the trigPin
    digitalWrite(ultraSensorTrigPin, LOW);
    delayMicroseconds(2);
    
    // Sets the trigPin on HIGH state for 10 micro seconds
    digitalWrite(ultraSensorTrigPin, HIGH);
    delayMicroseconds(10);
    digitalWrite(ultraSensorTrigPin, LOW);
    
    // Reads the echoPin, returns the sound wave travel time in microseconds
    long duration = pulseIn(ultraSensorEchoPin, HIGH);
    // Calculating the distance
    int32_t distance = (int32_t)(duration * 0.034/2);

    *multiplier = 1;
    *val = distance;

    return 0;
}

int ultraWrite(uint8_t *val) {
    return 0;
}

int servoRead(int32_t *val, int32_t *multiplier) {
    *val = servo_value;
    return 0;
}

int servoWrite(int32_t *val) {
    servo_value = *val;
    return 0;
}

void setup() {
  Serial.begin(115200);
  
  pinMode(infraredSensorPin, INPUT);
  pinMode(ultraSensorTrigPin, OUTPUT);
  pinMode(ultraSensorEchoPin, INPUT);
  pinMode(relaySensorPin, OUTPUT);

  digitalWrite(relaySensorPin, HIGH);
  delay(100);

  servo.attach(servoMotorPin);
  servo.write(180);
  
  delay(500);
  digitalWrite(relaySensorPin, LOW);
  thing.init(thingName);

  thing.registerBoolData(infraredSensorName, infraredSensorId, KNOT_TYPE_ID_SWITCH, KNOT_UNIT_NOT_APPLICABLE, infraredRead, infraredWrite);
  thing.registerIntData(ultraSensorName, ultraSensorId, KNOT_TYPE_ID_DISTANCE, KNOT_UNIT_DISTANCE_M, ultraRead, ultraWrite);
//  thing.registerIntData(servoMotorName, servoMotorId, KNOT_TYPE_ID_TIME, KNOT_UNIT_TIME_MS, servoRead, servoWrite);

  thing.registerDefaultConfig(infraredSensorId, KNOT_EVT_FLAG_TIME, 5, 0, 0, 0, 0);
  thing.registerDefaultConfig(ultraSensorId, KNOT_EVT_FLAG_TIME, 1, 0, 0, 0, 0);
//  thing.registerDefaultConfig(servoMotorId, KNOT_EVT_FLAG_TIME, 1, 0, 0, 0, 0);
}

long curr_time = 0;

void loop() {
  thing.run();

  if (servo_value > 0) {
    digitalWrite(relaySensorPin, HIGH);
    
    if (!gateOpened) {
//        digitalWrite(relaySensorPin, HIGH);
      delay(500);

      gateOpened = true;
      servo.write(100);
      delay(500);
//        digitalWrite(relaySensorPin, LOW);
      Serial.println("gonna open");
    }
    
    if ((millis() - curr_time > servo_value) && gateOpened) {
        delay(500);

        Serial.println("gonna close");
        servo_value = 0;
        servo.write(180);
        delay(500);
        gateOpened = false;
    }
    
    digitalWrite(relaySensorPin, LOW);
  } else {
      curr_time = millis();
  }
}
