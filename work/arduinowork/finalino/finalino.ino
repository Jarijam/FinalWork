char data;
int val;
int i;

unsigned long int Distance;


#include <Servo.h>
Servo servo;

int gasPin = A0;
int firePin = A5;
int fireState = 0;

#define Temp A3
#define Flame A1
#define Echo 13
#define Trig 12
#define BTN A2
void setup() {
  Serial.begin(9600);
  pinMode(BTN, INPUT);
  
  pinMode(Echo,INPUT);
  pinMode(Trig,OUTPUT);

  servo.attach(8);
  servo.write(170);

  pinMode(gasPin, INPUT);
  
  pinMode(Flame, INPUT);

  pinMode(Temp, INPUT);
}

unsigned long int Distance_sensing(){
    digitalWrite(Trig,HIGH);
    delayMicroseconds(10);
    digitalWrite(Trig,LOW);
//    Serial.write(pulseIn(Echo,1,5000)*340/1000/2);
    int dis = pulseIn(Echo,HIGH)*17/1000;
//    if ((pulseIn(Echo,HIGH)*17/1000)<15000){
//      return pulseIn(Echo,HIGH)*17/1000;  
//    }else {
//      return 1500;
//    }

    if (dis<=1500) {
      dis = dis;
    }else {
      dis = 1501;
    }
    return dis;
  }

void loop() {
  String spc = " ";
  String fstate;
  unsigned int temp_val;
  String temp;
  
  temp_val = analogRead(Temp);
  temp = (temp_val*500/1224);
  
  val = digitalRead(BTN);
//  Serial.print("btn:");
//  Serial.print(val+spc);

//  Serial.print("gas:");
//  Serial.print(analogRead(gasPin)+spc);

//  for (i=10;i<150;i+=1) {
//    servo.write(i);
//    delay(20); 
//  }
  fireState = digitalRead(Flame);
  if(fireState == LOW) {
//    Serial.print("fire undetected");
//    Serial.print("0"+spc);
  fstate = "0";
  }else if(fireState == HIGH) {
//    Serial.print("FIRE DETECTED!!!! SOS!!!");
//    Serial.print("1"+spc);
  fstate= "1";
  }
  
  if(Serial.available()>0) {
    data = Serial.read();
    if(data='o'){
//     Serial.println("on");
    }else if(data='f') {
//     Serial.println("off");
    }
  }
  
 Distance = Distance_sensing();
// Serial.print("distance:");
// Serial.println(Distance+spc);
 Serial.println(val+spc+analogRead(gasPin)+spc+fstate+spc+temp+spc+Distance);
 delay(500);
}
