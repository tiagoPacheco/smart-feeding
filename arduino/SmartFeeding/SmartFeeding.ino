#include <Servo.h>

#include <KNoTThing.h> 

#define thingName "SmartFeeding"

#define infraredSensorId 1
#define ultraSensorId 2
#define servoMotorId 3

#define infraredSensorName "infraredSensor"
#define infraredSensorPin 4

#define ultraSensorName "ultraSensor"
#define ultraSensorTrigPin 2
#define ultraSensorEchoPin 3

#define servoMotorName "servoMotor"
int servoMotorPin = A0;

Servo servo;

KNoTThing thing;

int infraredRead(uint8_t *val) {
    uint8_t value = digitalRead(infraredSensorPin);
    *val = value;
    return 0;
}

int infraredWrite(uint8_t *val) {
    digitalWrite(infraredSensorPin, *val);
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
    *val = servo.read();
    return 0;
}

int servoWrite(int32_t *val) {
    Serial.println("testing the foca");
    servo.write(180);
    delay(*val);
    servo.write(0);
    return 0;
}

void setup() {
  Serial.begin(115200);
  
  pinMode(infraredSensorPin, INPUT);
  pinMode(ultraSensorTrigPin, OUTPUT);
  pinMode(ultraSensorEchoPin, INPUT);

  servo.attach(servoMotorPin);
  servo.write(0);
  
  thing.init(thingName);
  
  thing.registerBoolData(infraredSensorName, infraredSensorId, KNOT_TYPE_ID_SWITCH, KNOT_UNIT_NOT_APPLICABLE, infraredRead, infraredWrite);
  thing.registerDefaultConfig(infraredSensorId, KNOT_EVT_FLAG_TIME, 10, 0, 0, 0, 0);

  thing.registerIntData(ultraSensorName, ultraSensorId, KNOT_TYPE_ID_DISTANCE, KNOT_UNIT_DISTANCE_M, ultraRead, ultraWrite);
  thing.registerDefaultConfig(ultraSensorId, KNOT_EVT_FLAG_TIME, 20, 0, 0, 0, 0);

  thing.registerIntData(servoMotorName, servoMotorId, KNOT_TYPE_ID_TIME, KNOT_UNIT_TIME_MS, servoRead, servoWrite);
  thing.registerDefaultConfig(servoMotorId, KNOT_EVT_FLAG_TIME, 10, 0, 0, 0, 0);
}

void loop() {
  thing.run();
}
