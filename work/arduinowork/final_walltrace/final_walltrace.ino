#include "Wire.h"
#include "I2Cdev.h"
#include "MPU9250.h"
MPU9250 accelgyro;

I2Cdev I2C_M;
uint8_t buffer_m[6];
int16_t ax, ay, az;
int16_t gx, gy, gz;
int16_t mx, my, mz;
float heading;
float tiltheading;
float Axyz[3];
float Gxyz[3];
float Mxyz[3];
float Yaw_angle;
unsigned long t_now, t_prev;
unsigned long int D_min = 10;
unsigned long int D, Angle;

#define sample_num_mdate 5000
volatile float mx_sample[3];
volatile float my_sample[3];
volatile float mz_sample[3];
static float mx_centre = 0;
static float my_centre = 0;
static float mz_centre = 0;
volatile int mx_max = 0;
volatile int my_max = 0;
volatile int mz_max = 0;
volatile int mx_min = 0;
volatile int my_min = 0;
volatile int mz_min = 0;
float temperature;
float pressure;
float atm;
float altitude;


#include <SoftwareSerial.h>     //블루투스 통신을 하기위하여 아두이노에서 기본 제공해주는 SoftwareSerial.h를 사용하겠다는 선언
SoftwareSerial BTSerial(10, 1); // HC - 06 통신을 위한 TX, RX의 PIN번호를 입력 합니다.(TX = 10 , RX = 1)

#include "Servo.h"  // 서보모터를 사용하기 위한 헤더파일 호출
Servo LKservo;  // 서보모터 객체 선언

#define EA 3  // 모터드라이버 EA 핀, 아두이노 우노 보드 디지털 3번 핀에 연결
#define EB 11  // 모터드라이버 EB 핀, 아두이노 우노 보드 디지털 11번 핀에 연결
#define M_IN1 4  // 모터드라이버 IN1 핀, 아두이노 우노 보드 디지털 4번 핀에 연결
#define M_IN2 5  // 모터드라이버 IN2 핀, 아두이노 우노 보드 디지털 5번 핀에 연결
#define M_IN3 13  // 모터드라이버 IN3 핀, 아두이노 우노 보드 디지털 13번 핀에 연결
#define M_IN4 12  // 모터드라이버 IN4 핀, 아두이노 우노 보드 디지털 12번 핀에 연결

#define echo1 6  // 초음파 센서 에코(Echo) 핀, 아두이노 우노 보드의 디지털 6번 핀 연결
#define trig1 7  // 초음파 센서 트리거(Trigger) 핀, 아두이노 우노 보드의 디지털 7번 핀 연결

#define echo2 8  // 초음파 센서 에코(Echo) 핀, 아두이노 우노 보드의 디지털 8번 핀 연결
#define trig2 9  // 초음파 센서 트리거(Trigger) 핀, 아두이노 우노 보드의 디지털 9번 핀 연결

#define servo_motor 2  // 서보모터 Signal 핀, 아두이노 우노 보드 디지털 2번 핀에 연결

#define TEMP A2
#define GAS A0
#define FIRE A3
int fireState = 0;
int crash = 0;

int motorA_vector = 1;  // 모터의 회전방향이 반대일 시 0을 1로, 1을 0으로 바꿔주면 모터의 회전방향이 바뀜.
int motorB_vector = 1;  // 모터의 회전방향이 반대일 시 0을 1로, 1을 0으로 바꿔주면 모터의 회전방향이 바뀜.
int motor_speed = 150;  // 모터 스피드 0 ~ 255



void setup()  // 초기화
{ 
  Wire.begin();
  Serial.println("Initializing I2C devices...");
  accelgyro.initialize();
  Serial.println("Testing device connections...");
  Serial.println(accelgyro.testConnection() ? "MPU9250 connection successful" : "MPU9250 connection failed");
  delay(1000);
  Serial.println(" ");
  
  BTSerial.begin(9600);
  Serial.begin(9600);
  
  pinMode(EA, OUTPUT);  // EA와 연결된 핀 출력 설정
  pinMode(M_IN1, OUTPUT);  // IN1과 연결된 핀 출력 설정 오른쪽 직진
  pinMode(M_IN2, OUTPUT);  // IN2와 연결된 핀 출력 설정 오른쪽 후진
  pinMode(EB, OUTPUT);  // EA와 연결된 핀 출력 설정
  pinMode(M_IN3, OUTPUT);  // IN3과 연결된 핀 출력 설정 왼쪽 직진
  pinMode(M_IN4, OUTPUT);  // IN4와 연결된 핀 출력 설정 왼쪽 후진

  pinMode(trig1, OUTPUT);  // trig1 출력 설정
  pinMode(echo1, INPUT);  // echo2 입력설정
  pinMode(trig2, OUTPUT);  // trig1 출력 설정
  pinMode(echo2, INPUT);  // echo2 입력설정

  pinMode(TEMP, INPUT);
  pinMode(GAS, INPUT);
  pinMode(FIRE, INPUT);
  
//  pinMode(5, INPUT);
  
  LKservo.attach(servo_motor);  // 서보모터 핀 설정
//  LKservo.write(1);  // 서보모터 초기값 90도 설정

  Axyz[0]=Axyz[1]=0;

}

char data;

void loop()  // 무한루프
{
//crash = analogRead(A1);
//Serial.println(crash);
mode2();
//unsigned Spin_angle;
//for(Yaw_angle=0, t_prev=millis(); (Spin_angle - 23)>abs(Yaw_angle); ) {
//  getYaw_angle();
//  Serial.println(getYaw_angle());  
//}


//if(read_ultrasonic2()<10) {
//if(read_ultrasonic2()<10 || analogRead(A1)<10) {
//  LKservo.write(1);
//  delay(500);
//  LKservo.write(100);
//}
//Serial.println(read_ultrasonic1());
//Serial.println(getYaw_angle());
//mode2();
//mode3();
//if(Serial.available()>0) {
//  data = Serial.read();
//  if(data=='Y') {
//    mode2();
//    LKservo.write(1);
//  }else if (data=='Z') {
//    mode3();
////    LKservo.write(1);
//  }
//}
//LKservo.write(20);
//delay(200);
//LKservo.write(80);
//delay(1000);
//LKservo.write(1);
//delay(1000);
  //sensing();
// for (int i=40;i<150;i+=10) {LKservo.write(i); delay(200);
// } 
//if(Serial.available()>0) {
//    data = Serial.read();
//  if(data=='x') {
////    mode1();
////    Serial.println("x들어옴");
//    LKservo.write(90);
//  }else if(data=='y') {
////    mode2();
////    Serial.println("y들어옴");
//    LKservo.write(180);
//  }else if(data=='z') {
//    mode3();
////    Serial.println("z들어옴");
////    LKservo.write(1);
//  }
//}
 
//    LKservo.write(90);
 
 
}

void sensing() {
  String spc = " "; 
 unsigned int temp_val;
 String fstate;
 String temp;
 String gas;
 
 temp_val = analogRead(TEMP);
 temp = (temp_val*500/1024);

 fireState = digitalRead(FIRE);
 if(fireState == LOW) {
    fstate = "0";
 }else if(fireState == HIGH) {
    fstate = "1";
 }

 gas = analogRead(GAS);

 crash = analogRead(A1);

  getAccel_Data();
  getGyro_Data();
  getCompassDate_calibrated(); // compass data has been calibrated here
  getHeading(); //before we use this function we should run 'getCompassDate_calibrated()' frist, so that we can get calibrated data ,then we can get correct angle .
  getTiltHeading();
//Serial.println("Acceleration(g) of X,Y,Z:");
//Serial.println(Axyz[0]+spc+Axyz[1]);
String cmm = ",";
//Serial.println(cmm+(Axyz[0]-0.015)*10+cmm+(Axyz[1]+0.1)*10);
Serial.println(cmm+(Axyz[0])*100+cmm+(Axyz[1])*100);
//Serial.print(",");
//Serial.print(Axyz[1]);
//Serial.print(",");
//Serial.println(Axyz[2]);
//  Serial.print(Mxyz[0]);
//  Serial.print(",");
//  Serial.print(Mxyz[1]);
//  Serial.println(",");
 //String C = "C";
// Serial.println(C+spc+Mxyz[0]+spc+Mxyz[1]);
// Mxyz[0] = 0;
// Mxyz[1] = 0;
// Serial.println("S"+spc+fstate+spc+temp+spc+gas+spc+crash);
 delay(1000);
}


int read_ultrasonic1(void)  // 초음파 센서1 값 읽어오는 함수
{
  float return_time, distance;  // 시간 값과 거리 값을 저장하는 변수를 만든다.
  // 초음파 센서는 초음파를 발사 후 돌아오는 시간을 역산하여 거리 값으로 계산한다.
  digitalWrite(trig1, HIGH);  // 초음파 발사
  delay(5);  // 0.05초간 지연
  digitalWrite(trig1, LOW);  // 초음파 발사 정지
  return_time = pulseIn(echo1, HIGH);  // 돌아오는 시간
  distance = ((float)(340 * return_time) / 10000) / 2;  // 시간을 거리로 계산
  return distance;  // 거리 값 리턴
}

int read_ultrasonic2(void)  // 초음파 센서2 값 읽어오는 함수
{
  float return_time, distance;  // 시간 값과 거리 값을 저장하는 변수를 만든다.
  // 초음파 센서는 초음파를 발사 후 돌아오는 시간을 역산하여 거리 값으로 계산한다.
  digitalWrite(trig2, HIGH);  // 초음파 발사
  delay(5);  // 0.05초간 지연
  digitalWrite(trig2, LOW);  // 초음파 발사 정지
  return_time = pulseIn(echo2, HIGH);  // 돌아오는 시간
  distance = ((float)(340 * return_time) / 10000) / 2;  // 시간을 거리로 계산
  return distance;  // 거리 값 리턴
}

void find_wall(){
    for (int i=40;i<150;i+=10) {LKservo.write(i); delay(200); 
                             D=read_ultrasonic1(); delay(3); if (D<D_min&&D!=0) {D_min=D; Angle=i;} delay(10);}
    if (Angle>=70&&Angle<90) {right(); delay(Angle*2+100); }
    else if(Angle<=110&&Angle>90) {left(); delay(460-Angle*2); }
    else if (Angle>110) {spin_left(Angle-88);}
    else if (Angle<70) {spin_right(92-Angle);}
    else {brake(); delay(100); }
    LKservo.write(90); brake(); delay(1000); Yaw_angle=0;}


//오른직진 1 오른후진2 왼직진3 왼후진4
void go() {
  digitalWrite(M_IN1, 1); digitalWrite(M_IN2, 0); delay(3);
  digitalWrite(M_IN3, 0); digitalWrite(M_IN4, 1); delay(3);
  analogWrite(EA,255);analogWrite(EB,255); delay(30);
//  Serial.println("go");
}
void go_slow() {
  digitalWrite(M_IN1, 1); digitalWrite(M_IN2, 0); delay(3);
  digitalWrite(M_IN3, 0); digitalWrite(M_IN4, 1); delay(3);
  analogWrite(EA,255);analogWrite(EB,255); delay(30);
//  Serial.println("go_slow");
}
//void go_straight(){}
void brake() {
  digitalWrite(M_IN1, 0); digitalWrite(M_IN2, 0); delay(3);
  digitalWrite(M_IN3, 0); digitalWrite(M_IN4, 0); delay(3);
  analogWrite(EA,0);analogWrite(EB,0); delay(30);
}
void left() {
  digitalWrite(M_IN1, 1); digitalWrite(M_IN2, 0); delay(3);
  digitalWrite(M_IN3, 1); digitalWrite(M_IN4, 0); delay(3);
  analogWrite(EA,255);analogWrite(EB,150); delay(30);
}
void right(){
  digitalWrite(M_IN1, 1); digitalWrite(M_IN2, 0); delay(3);
  digitalWrite(M_IN3, 1); digitalWrite(M_IN4, 0); delay(3);
  analogWrite(EA,150);analogWrite(EB,255); delay(30);
}
//void left_back() {
//  digitalWrite(M_IN1, 0); digitalWrite(M_IN2, 1); delay(3);
//  digitalWrite(M_IN3, 0); digitalWrite(M_IN4, 1); delay(3);
//  analogWrite(EA,200);analogWrite(EB,50); delay(30);
//}
//void right_back(){
//  digitalWrite(M_IN1, 0); digitalWrite(M_IN2, 1); delay(3);
//  digitalWrite(M_IN3, 0); digitalWrite(M_IN4, 1); delay(3);
//  analogWrite(EA,50);analogWrite(EB,200); delay(30);
//}
void spin_left() {
  digitalWrite(M_IN1, 1); digitalWrite(M_IN2, 0); delay(3);
  digitalWrite(M_IN3, 1); digitalWrite(M_IN4, 0); delay(3);
  analogWrite(EA,255);analogWrite(EB,255); delay(30);;
}
void spin_right() {
  digitalWrite(M_IN1, 0); digitalWrite(M_IN2, 1); delay(3);
  digitalWrite(M_IN3, 0); digitalWrite(M_IN4, 1); delay(3);
  analogWrite(EA,255);analogWrite(EB,255); delay(30);;
}
void spin_left(unsigned int Spin_angle) {//우직,좌후
  digitalWrite(M_IN1, 1); digitalWrite(M_IN2, 0); delay(3); 
  digitalWrite(M_IN3, 1); digitalWrite(M_IN4, 0); delay(3);
  analogWrite(EA,255);analogWrite(EB,255); 
   for(Yaw_angle=0, t_prev=millis(); (Spin_angle - 23)>abs(Yaw_angle); ) getYaw_angle();
  analogWrite(EA,0);analogWrite(EB,0); 
  delay(30);
}
void spin_right(unsigned int Spin_angle) {
  digitalWrite(M_IN1, 0); digitalWrite(M_IN2, 1); delay(3);
  digitalWrite(M_IN3, 0); digitalWrite(M_IN4, 1); delay(3);
  analogWrite(EA,255);analogWrite(EB,255); 
  for(Yaw_angle=0, t_prev=millis(); (Spin_angle - 23)>abs(Yaw_angle); ) getYaw_angle();
  analogWrite(EA,0);analogWrite(EB,0);
  delay(30);
}
void back() {
  digitalWrite(M_IN1, 0); digitalWrite(M_IN2, 1); delay(3);
  digitalWrite(M_IN3, 1); digitalWrite(M_IN4, 0); delay(3);
  analogWrite(EA,255);analogWrite(EB,255); delay(30);
}

void mode1() {
//  int crash= analogRead(A1);
//  Serial.println(crash_r);
  
  if (read_ultrasonic2() < 7 | crash <= 10) {
    back();
    delay(500);
    spin_left();
    delay(500);
  }
  else {
      if (read_ultrasonic1() < 18)  // 만약 측정한 거리값이 18보다 작으면 자동차 우회전
    {
      left();
      delay(280);  // 0.28초간 지연
    }
    else  // 측정한 거리값이 16보다 작지 않으면 자동차 직진
    {
       right();
    }
  }
}
//void mode2() {
//  //물체 탐지.
////  int crash = analogRead(A1);
// 
//  int distance = 0;
//  int angle = 0;
//  for (int i=0 ; i<180 ; i+=10) {
//    LKservo.write(i);
//    //distance 최대값 설정 및 그때 각도 설정
//    if (read_ultrasonic1()>distance) {
//      if(read_ultrasonic1()>500) {
//        distance = 500;
//      }else {
//       distance = read_ultrasonic1(); 
//      }
//      angle = i;
//    }
//    delay(100);
//  }
//
//  if (angle>=80 & angle<=100) {
//    go();
//    delay(800);
//  } else if (angle<80){
//    spin_right();
//    delay(800);
//  } else if (angle >100) {
//    spin_left();
//    delay(800);
//  }
//
//  if (read_ultrasonic2() <5 | crash <= 10 ) {
//    brake();
//    delay(50);
//    back();
//    delay(300);
//   } else if ( angle<80) {
//    spin_right();
//    delay(300);
//   } else if ( angle>100) {
//    spin_left();
//    delay(300);
//   } else {
//    go();
//    delay(500);
//   }
//
//   LKservo.write(1);
//}

void mode2() {
   if(read_ultrasonic2() <9 || analogRead(A1) <8){
    back();
    delay(200);
    brake();
    delay(200);
    //서보모터가 도는 부분
    for (int i=40;i<150;i+=10) {
      LKservo.write(i); delay(200); 
       D=read_ultrasonic1(); 
//       Serial.println(read_ultrasonic1());
       delay(3); 
       
       if (D<D_min&&D!=0) {
        D_min=D; Angle=i;
//        Serial.write(D_min);
        } 
        delay(10);
        }
//       Serial.println(Angle);
      if (Angle>=70&&Angle<90) {spin_left(); delay(Angle*2+100); }
      else if(Angle<=110&&Angle>90) {spin_right(); delay(460-Angle*2); }
//      else if (Angle>110) {spin_right(Angle-88);}
//      else if (Angle<70) {spin_left(92-Angle);}
//    else {brake(); delay(100); }

   }else {
    go_slow();
   }
}

void mode3() {
if (BTSerial.available()) 
  {
    char cmd = (char) BTSerial.read();  //BTSerial.read()를 사용할 때마다 사용한 것을 지워지므로 char타입 변수에 저장합니다.
    Serial.println(cmd);
    
    if ( cmd == 'F')      // rc카가 전진 합니다.
    {
         go();
    } 
    else if (cmd ==  'B')     // rc카가 후진 합니다.
    {    
          back();
    } 
    else if (cmd == 'S')    // rc카가 멈춤니다.
    {    
          brake();
    }
    else if (cmd=='L') 
    {
          spin_left();
    }
    else if (cmd=='R')
    {
          spin_right();
    }
  }
}

void speedtest() {
//   Serial.println(val);
  delay(1000);
  digitalWrite(EA, HIGH);
  digitalWrite(EB, HIGH);
  go();
  delay(1000);
  back();
  delay(1000);
  left();
  delay(1000);
  right();
  delay(1000);
  spin_left();
  delay(1500);
  spin_right();
  delay(1500);
  brake();
  delay(1000);
}

void getHeading(void)
{
heading = 180 * atan2(Mxyz[1], Mxyz[0]) / PI;
if (heading < 0) heading += 360;
}
void getTiltHeading(void)
{
float pitch = asin(-Axyz[0]);
float roll = asin(Axyz[1] / cos(pitch));
float xh = Mxyz[0] * cos(pitch) + Mxyz[2] * sin(pitch);
float yh = Mxyz[0] * sin(roll) * sin(pitch) + Mxyz[1] * cos(roll) - Mxyz[2] * sin(roll) * cos(pitch);
float zh = -Mxyz[0] * cos(roll) * sin(pitch) + Mxyz[1] * sin(roll) + Mxyz[2] * cos(roll) * cos(pitch);
tiltheading = 180 * atan2(yh, xh) / PI;
if (yh < 0) tiltheading += 360;
}
void Mxyz_init_calibrated ()
{
Serial.println(F("Before using 9DOF,we need to calibrate the compass frist,It will takes about 2 minutes."));
Serial.print(" ");
Serial.println(F("During calibratting ,you should rotate and turn the 9DOF all the time within 2 minutes."));
Serial.print(" ");
Serial.println(F("If you are ready ,please sent a command data 'ready' to start sample and calibrate."));
while (!Serial.find("ready"));
Serial.println(" ");
Serial.println("ready");
Serial.println("Sample starting......");
Serial.println("waiting ......");
get_calibration_Data ();
Serial.println(" ");
Serial.println("compass calibration parameter ");
Serial.print(mx_centre);
Serial.print(" ");
Serial.print(my_centre);
Serial.print(" ");
Serial.println(mz_centre);
Serial.println(" ");
}
void get_calibration_Data ()
{
for (int i = 0; i < sample_num_mdate; i++)
{
get_one_sample_date_mxyz();
/*
 Serial.print(mx_sample[2]);
 Serial.print(" ");
 Serial.print(my_sample[2]); //you can see the sample data here .
 Serial.print(" ");
 Serial.println(mz_sample[2]);
 */
if (mx_sample[2] >= mx_sample[1])mx_sample[1] = mx_sample[2];
if (my_sample[2] >= my_sample[1])my_sample[1] = my_sample[2]; //find max value
if (mz_sample[2] >= mz_sample[1])mz_sample[1] = mz_sample[2];
if (mx_sample[2] <= mx_sample[0])mx_sample[0] = mx_sample[2];
if (my_sample[2] <= my_sample[0])my_sample[0] = my_sample[2]; //find min value
if (mz_sample[2] <= mz_sample[0])mz_sample[0] = mz_sample[2];
}
mx_max = mx_sample[1];
my_max = my_sample[1];
mz_max = mz_sample[1];
mx_min = mx_sample[0];
my_min = my_sample[0];
mz_min = mz_sample[0];
mx_centre = (mx_max + mx_min) / 2;
my_centre = (my_max + my_min) / 2;
mz_centre = (mz_max + mz_min) / 2;
}
void get_one_sample_date_mxyz()
{
getCompass_Data();
mx_sample[2] = Mxyz[0];
my_sample[2] = Mxyz[1];
mz_sample[2] = Mxyz[2];
}
void getAccel_Data(void)
{
accelgyro.getMotion9(&ax, &ay, &az, &gx, &gy, &gz, &mx, &my, &mz);
Axyz[0] = (double) ax / 16384;
Axyz[1] = (double) ay / 16384;
Axyz[2] = (double) az / 16384;
}
void getGyro_Data(void)
{
accelgyro.getMotion9(&ax, &ay, &az, &gx, &gy, &gz, &mx, &my, &mz);
Gxyz[0] = (double) gx * 250 / 32768;
Gxyz[1] = (double) gy * 250 / 32768;
Gxyz[2] = (double) gz * 250 / 32768;
}
void getCompass_Data(void)
{
I2C_M.writeByte(MPU9150_RA_MAG_ADDRESS, 0x0A, 0x01); //enable the magnetometer
delay(10);
I2C_M.readBytes(MPU9150_RA_MAG_ADDRESS, MPU9150_RA_MAG_XOUT_L, 6, buffer_m);
mx = ((int16_t)(buffer_m[1]) << 8) | buffer_m[0] ;
my = ((int16_t)(buffer_m[3]) << 8) | buffer_m[2] ;
mz = ((int16_t)(buffer_m[5]) << 8) | buffer_m[4] ;
Mxyz[0] = (double) mx * 1200 / 4096;
Mxyz[1] = (double) my * 1200 / 4096;
Mxyz[2] = (double) mz * 1200 / 4096;
}
void getCompassDate_calibrated ()
{
getCompass_Data();
Mxyz[0] = Mxyz[0] - mx_centre;
Mxyz[1] = Mxyz[1] - my_centre;
Mxyz[2] = Mxyz[2] - mz_centre;
}

double getYaw_angle(void) {
  getGyro_Data();
  t_now = millis();
  if(t_prev) Yaw_angle = Yaw_angle + Gxyz[2] * (t_now-t_prev) / 1000.;
  t_prev = t_now;
  return Yaw_angle;
}
//void motorA_con(int M1)  // 모터A 컨트롤 함수
//{
//  if (M1 > 0)  // M1이 양수일 때, 모터A 정회전
//  {
//    digitalWrite(M_IN1, motorA_vector);  // IN1번에 HIGH(motorA_vector가 0이면 LOW)
//    digitalWrite(M_IN2, !motorA_vector);  // IN2번에 LOW(motorA_vector가 0이면 HIGH)
//  }
//  else if (M1 < 0)  // M1이 음수일 대, 모터A 역회전
//  {
//    digitalWrite(M_IN1, !motorA_vector);  // IN1번에 LOW(motorA_vector가 0이면 HIGH)
//    digitalWrite(M_IN2, motorA_vector);  // IN2번에 HIGH(motorA_vector가 0이면 LOW)
//  }
//  else  // 모터A 정지
//  {
//    digitalWrite(M_IN1, LOW);  // IN1번에 LOW
//    digitalWrite(M_IN2, LOW);  // IN2번에 LOW
//  }
//  analogWrite(EA, abs(M1));  // M1의 절대값으로 모터A 속도 제어
//}
//
//void motorB_con(int M2)  // 모터B 컨트롤 함수
//{
//  if (M2 > 0)  // M2가 양수일 때, 모터B 정회전
//  {
//    digitalWrite(M_IN3, motorB_vector);  // IN3번에 HIGH(motorB_vector가 0이면 LOW)
//    digitalWrite(M_IN4, !motorB_vector);  // IN4번에 LOW(motorB_vector가 0이면 HIGH)
//  }
//  else if (M2 < 0)  // M2가 음수일 때, 모터B 역회전
//  {
//    digitalWrite(M_IN3, !motorB_vector);  // IN3번에 LOW(motorB_vector가 0이면 HIGH)
//    digitalWrite(M_IN4, motorB_vector);  // IN4번에 HIGH(motorB_vector가 0이면 LOW)
//  }
//  else  // 모터B 정지
//  {
//    digitalWrite(M_IN3, LOW);  // IN3번에 LOW
//    digitalWrite(M_IN4, LOW);  // IN4번에 LOW
//  }
//  analogWrite(EB, abs(M2));  // M2의 절대값으로 모터B 속도 제어
//}
//  // DC모터 정회전
//  digitalWrite(EA, HIGH);  // 모터구동 ON
//  digitalWrite(M_IN1, motorA_vector);  // IN1에 HIGH(or LOW)
//  digitalWrite(M_IN2, !motorA_vector);  // IN2에 LOW(or HIGH)
//  digitalWrite(EB, HIGH);  // 모터구동 ON
//  digitalWrite(M_IN3, motorB_vector);  // IN1에 HIGH(or LOW)
//  digitalWrite(M_IN4, !motorB_vector);  // IN2에 LOW(or HIGH)
//  delay(5000);  // 5초간 지연
  // DC모터 정지
//  digitalWrite(EA, LOW);  // 모터구동 OFF
//  digitalWrite(M_IN1, LOW);  // IN1에 LOW
//  digitalWrite(M_IN2, LOW);  // IN2에 LOW
//  digitalWrite(EB, LOW);  // 모터구동 OFF
//  digitalWrite(M_IN3, LOW);  // IN1에 LOW
//  digitalWrite(M_IN4, LOW);  // IN2에 LOW
//  delay(5000);  // 5초간 지연
//  // DC모터 역회전
//  digitalWrite(EA, HIGH);  // 모토구동 ON
//  digitalWrite(M_IN1, !motorA_vector);  // IN1에 LOW(or HIGH)
//  digitalWrite(M_IN2, motorA_vector);  // IN2에 HIGH(or LOW)
//  digitalWrite(EB, HIGH);  // 모토구동 ON
//  digitalWrite(M_IN3, !motorB_vector);  // IN1에 LOW(or HIGH)
//  digitalWrite(M_IN4, motorB_vector);  // IN2에 HIGH(or LOW)
//  delay(5000);  // 5초간 지연
// 기기 제자리 회전
//  digitalWrite(EA, HIGH);  // 모터구동 ON
//  digitalWrite(M_IN1, motorA_vector);  // IN1에 HIGH(or LOW)
//  digitalWrite(M_IN2, !motorA_vector);  // IN2에 LOW(or HIGH)
//  digitalWrite(EB, HIGH);  // 모터구동 ON
//  digitalWrite(M_IN3, !motorB_vector);  // IN1에 HIGH(or LOW)
//  digitalWrite(M_IN4, motorB_vector);  // IN2에 LOW(or HIGH)
//  delay(5000);  // 5초간 지연
