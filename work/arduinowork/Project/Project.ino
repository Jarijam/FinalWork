#include <Servo.h> 
#include "MPU6050.h"

#define L_M_back 3        // (IN1)
#define L_M_go 5           // (IN2)
#define R_M_go 6           // (IN3)
#define R_M_back 11       // (IN4)
#define Echo 13
#define Trig 12
#define Speed 120
#define slowspeed 80

MPU6050 accelgyro(0x68);
Servo servo;
int16_t ax, ay, az,gx, gy, gz;
float Axyz[3], Gxyz[3], Yaw_angle;
unsigned long t_now,t_prev,i;
char mode,spin1,spin2,modes,modew;
unsigned long int Distance,DistanceL,DistanceR,D,Angle;
unsigned long int D_min=10000;
bool FrontSensorL,FrontSensorR,Line,LineSensorR,LineSensorL;

void getGyro_Data(void) {
    accelgyro.getMotion6(&ax, &ay, &az, &gx, &gy, &gz);
    Gxyz[0] = (double) gx / 131;//* 250 / 32768;
    Gxyz[1] = (double) gy / 131;//* 250 / 32768;
    Gxyz[2] = (double) gz / 131;//* 250 / 32768;
    }

void getYaw_angle(void) {
  getGyro_Data();
  t_now = millis();
  if(t_prev) Yaw_angle = Yaw_angle + Gxyz[2] * (t_now-t_prev) / 1000.;
  t_prev = t_now;
}

void setup() {
  pinMode(L_M_back, OUTPUT);
  pinMode(L_M_go, OUTPUT);
  pinMode(R_M_back, OUTPUT);
  pinMode(R_M_go, OUTPUT);
  pinMode(Echo,INPUT);
  pinMode(Trig,OUTPUT);
  servo.attach(7);
  servo.write(170);
  Wire.begin();
  accelgyro.initialize();
}
  void go(){
  digitalWrite(R_M_go, 1); digitalWrite(L_M_go, 1); delay(3);
  analogWrite(R_M_go,70);analogWrite(L_M_go,70); delay(30);
  }
  void go_straight(){
  getYaw_angle();
  if(Yaw_angle > 1) right();
  else if(Yaw_angle <-1) left();
  else {digitalWrite(R_M_go, 1); digitalWrite(L_M_go, 1); delay(5);
    analogWrite(R_M_go,100);analogWrite(L_M_go,100); delay(30);}
  }
  void brake(){
  analogWrite(R_M_go,0);analogWrite(R_M_back,0);
  analogWrite(L_M_go,0);analogWrite(L_M_back,0);
  }
  void left(){
  digitalWrite(R_M_go, 1); digitalWrite(L_M_go, 1); delay(3);
  analogWrite(R_M_go,101); analogWrite(L_M_go,41); delay(30);
  }
  void right(){
  digitalWrite(R_M_go, 1); digitalWrite(L_M_go, 1); delay(3);
  analogWrite(R_M_go,41);  analogWrite(L_M_go,111); delay(30);
  }
  void spin_left(unsigned int Spin_angle) {
  analogWrite(R_M_go,120); analogWrite(L_M_back,120);
  for(Yaw_angle=0, t_prev=millis(); (Spin_angle - 23)>abs(Yaw_angle); ) getYaw_angle();
  digitalWrite(R_M_go,0); digitalWrite(L_M_back,0);
  }
  void spin_right(unsigned int Spin_angle) {
  analogWrite(R_M_back,120); analogWrite(L_M_go,120);
  for(Yaw_angle=0, t_prev=millis(); (Spin_angle - 23)>abs(Yaw_angle); ) getYaw_angle();
  digitalWrite(R_M_back,0); digitalWrite(L_M_go,0);
  }
  void back(){
  digitalWrite(R_M_back, 1); digitalWrite(L_M_back, 1); delay(3);
  analogWrite(R_M_back,70); analogWrite(L_M_back,70); delay(30);
  }
  void slow_go() {
  getYaw_angle();
  if(Yaw_angle > 1) slow_right();
  else if(Yaw_angle <-1) slow_left();
  else {digitalWrite(R_M_go, 1); digitalWrite(L_M_go, 1); delay(3);
  analogWrite(R_M_go, slowspeed); analogWrite(L_M_go, slowspeed); delay(30);}
  }
  void slow_left() {
  digitalWrite(R_M_go,1); analogWrite(L_M_go,0); delay(3);
  analogWrite(R_M_go,slowspeed); analogWrite(L_M_go,0); delay(30);
  }
  void slow_right() {
  analogWrite(R_M_go,0); digitalWrite(L_M_go,1); delay(7);
  analogWrite(R_M_go,0); analogWrite(L_M_go,80); delay(30);
  }
  void slight_left() {
  digitalWrite(R_M_go,1); digitalWrite(L_M_go,1); delay(3);
  analogWrite(R_M_go,120); analogWrite(L_M_go,70); delay(30);
  }
  void slight_right() {
  digitalWrite(R_M_go,1); digitalWrite(L_M_go,1); delay(3);
  analogWrite(R_M_go,70); analogWrite(L_M_go,120); delay(30);
  }
  void go_left(){
  digitalWrite(R_M_go,1); digitalWrite(L_M_go,1); delay(5);
  analogWrite(R_M_go,125); analogWrite(L_M_go,40); delay(30);
  }

  unsigned long int Distance_sensing(){
    digitalWrite(Trig,1);
    delayMicroseconds(10);
    digitalWrite(Trig,LOW);
    Serial.write(pulseIn(Echo,1,500)*340/1000/2);
    return pulseIn(Echo,1,5000)*340/1000/2;
  }
  
// void LineTrace(){
//       if(LineSensorL==1 && LineSensorR==1) go();
//  else if(LineSensorL==1 && LineSensorR==0) left();
//  else if(LineSensorL==0 && LineSensorR==1) right();
//  else if(LineSensorL==0 && LineSensorR==0) {if (Distance<300 && Distance>10) {mode=1; brake(); delay(300);}
//                                             else if(Distance<10||Distance>300) {back(); delay(300);}}
//  else go();
//  }

void WallSense(){Distance=Distance_sensing();  
  delay(3);
  if (spin1<3&&spin2==0) {if(Distance) {if(Distance<133) slight_right();
                                        else if(Distance>139) slight_left();
                                        else go();}
                          else if (Distance<5||Distance>150) {go_left();}
                          if (FrontSensorL==0 || FrontSensorR==0) {brake(); delay(400); spin_right(80); brake(); delay(400); spin1++;}}
                          //벽 따라서 가다가 전면부 적외선 센서로 전방 벽 발견시 행동. +회전 한 횟수 카운트
 
  else if (spin1>=3&&modew==0){ 
        if(Distance) {if(Distance<133) slight_right();
                      else if(Distance>139) slight_left();
                      else go();
                      if (FrontSensorL==0 || FrontSensorR==0) {brake(); delay(400); spin_right(80); brake(); delay(400); Distance=Distance_sensing(); delay(3); spin1++;}}
        else if (Distance<3||Distance>400) {brake(); delay(400); servo.write(10); brake(); delay(400); modew=1;}}
 
  else if(spin1>=3&&modew==1) {if(Distance) {if (FrontSensorL==0 || FrontSensorR==0) {brake(); delay(400); spin_left(70); brake(); delay(400); spin2++;}
                                             if (Distance<150) slight_left();
                                             else if(Distance>156) slight_right();
                                             else go(); }
                                else if (Distance<5||Distance>300) {brake(); delay(500); mode=2;}
                                     } } 

void find_wall(){
    for (i=40;i<150;i+=10) {servo.write(i); delay(200); 
                             D=Distance_sensing(); delay(3); if (D<D_min&&D!=0) {D_min=D; Angle=i;} delay(10);}
    if (Angle>=70&&Angle<90) {slow_right(); delay(Angle*2+100); }
    else if(Angle<=110&&Angle>90) {slow_left(); delay(460-Angle*2); }
    else if (Angle>110) {spin_left(Angle-88);}
    else if (Angle<70) {spin_right(92-Angle);}
    else {brake(); delay(100); }
    servo.write(90); brake(); delay(1000); Yaw_angle=0; mode=3; modes=0;}

void Slalom(){
    if(modes==0){
   if (Distance>90 || Distance<10) go_straight();
   else {brake(); delay(500); servo.write(10); spin_left(70); brake(); delay(500); Yaw_angle=0; modes=1; }}                                     
  else if (modes==1){  
    if(Distance<130 && Distance>10) slow_left();
    else if(Distance>130||Distance==0) slow_right();
    if (LineSensorL==1||LineSensorR==1) {Line=1;}
    if (LineSensorL==0&&LineSensorR==0&&Line==1) {Line=0; brake(); delay(300); servo.write(190); modes=2; go_straight(); delay(200); }}
  else if (modes==2){  
    if(Distance<130 && Distance>10) slow_right();
    else if(Distance>130||Distance==0) slow_left();
    if (LineSensorL==1||LineSensorR==1) {Line=1;}
    if (LineSensorL==0&&LineSensorR==0&&Line==1) {Line=0; brake(); delay(300); servo.write(10); modes=3; go_straight(); delay(300);}}
  else if (modes==3){
    if(Distance<130 && Distance>10) slow_left();
    else if(Distance>130||Distance==0) slow_right();
    if (LineSensorL==1||LineSensorR==1) {Line=1;}
    if (LineSensorL==0&&LineSensorR==0&&Line==1) {Line=0; brake(); delay(300); mode=4; }}
}

void loop() {
  FrontSensorL=digitalRead(16);
  FrontSensorR=digitalRead(17);  
  Distance=Distance_sensing(); 
  LineSensorR=digitalRead(14);
  LineSensorL=digitalRead(15);
if (mode==0) LineTrace();
else if (mode==1) WallSense();
else if (mode==2) find_wall();
else if (mode==3) Slalom();
else brake(); }
