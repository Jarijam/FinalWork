#include <SoftwareSerial.h>     //블루투스 통신을 위한 헤더파일 호출
SoftwareSerial BTSerial(10, 1); // HC - 06 통신을 위한 TX, RX의 PIN번호를 입력(TX = 10 , RX = 1)

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

#define GAS A0
#define TEMP A2
#define FIRE A3

void setup()  // 초기화
{ 

  BTSerial.begin(9600);
  Serial.begin(9600);
  
  pinMode(EA, OUTPUT);  // EA와 연결된 핀 출력 설정
  pinMode(M_IN1, OUTPUT);  // IN1과 연결된 핀 출력 설정 오른 바퀴 직진
  pinMode(M_IN2, OUTPUT);  // IN2와 연결된 핀 출력 설정 오른 바퀴 후진
  pinMode(EB, OUTPUT);  // EA와 연결된 핀 출력 설정
  pinMode(M_IN3, OUTPUT);  // IN3과 연결된 핀 출력 설정 왼 바퀴 후진
  pinMode(M_IN4, OUTPUT);  // IN4와 연결된 핀 출력 설정 왼 바퀴 직진

  pinMode(trig1, OUTPUT);  // trig1 출력 설정
  pinMode(echo1, INPUT);  // echo2 입력설정
  pinMode(trig2, OUTPUT);  // trig1 출력 설정
  pinMode(echo2, INPUT);  // echo2 입력설정

  pinMode(TEMP, INPUT);
  pinMode(GAS, INPUT);
  pinMode(FIRE, INPUT);
  
  
  
  LKservo.attach(servo_motor);  // 서보모터 핀 설정

}

void loop()  // 무한루프
{
  
  sensing();
  
  char data;
  if(Serial.available()>0) {
    data = Serial.read();
    if(data=='X') {
      mode1();
    }else if(data=='Y') {
      mode2();
    }else if(data=='Z') {
      mode3();
    }
  }
  delay(800);
}

void sensing() {
   
   int fireState = 0;
   int crash = 0;
   unsigned int temp_val;
   String fstate;
   String temp;
   String gas;
 
  temp_val = analogRead(TEMP);
  temp = (temp_val*600/1024);

  fireState = digitalRead(FIRE);
  if(fireState == LOW) {
    fstate = "0";
  }else if(fireState == HIGH) {
    fstate = "1";
  }

 gas = analogRead(GAS);
 
  if(analogRead(A1)==0 || analogRead(A1)<3){
    crash=1;
  }else {
    crash=0;
  }
  String cmm = ",";
  Serial.println(cmm+fstate+cmm+temp+cmm+gas+cmm+crash);
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

//오른직진 1 오른후진2 왼직진3 왼후진4
void go() {
  digitalWrite(M_IN1, 1); digitalWrite(M_IN2, 0); delay(3);
  digitalWrite(M_IN3, 0); digitalWrite(M_IN4, 1); delay(3);
  analogWrite(EA,255);analogWrite(EB,255); delay(30);
}
void go_slow() {
  digitalWrite(M_IN1, 1); digitalWrite(M_IN2, 0); delay(3);
  digitalWrite(M_IN3, 0); digitalWrite(M_IN4, 1); delay(3);
  analogWrite(EA,255);analogWrite(EB,255); delay(30);
}
void brake() {
  digitalWrite(M_IN1, 0); digitalWrite(M_IN2, 0); delay(3);
  digitalWrite(M_IN3, 0); digitalWrite(M_IN4, 0); delay(3);
  analogWrite(EA,0);analogWrite(EB,0); delay(30);
}
void left() {
  digitalWrite(M_IN1, 1); digitalWrite(M_IN2, 0); delay(3);
  digitalWrite(M_IN3, 1); digitalWrite(M_IN4, 0); delay(3);
  analogWrite(EA,200);analogWrite(EB,80); delay(30);
}
void right(){
  digitalWrite(M_IN1, 1); digitalWrite(M_IN2, 0); delay(3);
  digitalWrite(M_IN3, 0); digitalWrite(M_IN4, 1); delay(3);
  analogWrite(EA,80);analogWrite(EB,200); delay(30);
}
void slight_right(){
  digitalWrite(M_IN1, 1); digitalWrite(M_IN2, 0); delay(3);
  digitalWrite(M_IN3, 0); digitalWrite(M_IN4, 1); delay(3);
  analogWrite(EA,130);analogWrite(EB,200); delay(30);
}
void spin_left() {
  digitalWrite(M_IN1, 1); digitalWrite(M_IN2, 0); delay(3);
  digitalWrite(M_IN3, 1); digitalWrite(M_IN4, 0); delay(3);
  analogWrite(EA,200);analogWrite(EB,200); delay(30);;
}
void spin_right() {
  digitalWrite(M_IN1, 0); digitalWrite(M_IN2, 1); delay(3);
  digitalWrite(M_IN3, 0); digitalWrite(M_IN4, 1); delay(3);
  analogWrite(EA,200);analogWrite(EB,200); delay(30);;
}
void back() {
  digitalWrite(M_IN1, 0); digitalWrite(M_IN2, 1); delay(3);
  digitalWrite(M_IN3, 1); digitalWrite(M_IN4, 0); delay(3);
  analogWrite(EA,255);analogWrite(EB,255); delay(30);
}

void mode1() {
  LKservo.write(170);  
      if (read_ultrasonic1() < 18)  // 만약 측정한 거리값이 18보다 작으면 자동차 우회전
    { 
       if (read_ultrasonic2() < 7 || analogRead(A1) <= 10) {
      back();
      delay(500);
      spin_left();
      delay(500);
      }else {
        spin_right();
        delay(280);  // 0.28초간 지연  
      }
    }
    else  // 측정한 거리값이 16보다 작지 않으면 자동차 직진
    {
       go();
    }
  
}
void mode2() {
   unsigned long int D, Angle;
   unsigned long int D_min = 10000;
   if(read_ultrasonic2() <8 || analogRead(A1) <8){
    back();
    delay(500);
    brake();
    delay(200);
    //서보모터가 도는 부분
    for (int i=40;i<150;i+=10) {
      LKservo.write(i); delay(200); 
       D=read_ultrasonic1(); 
       delay(3); 
       
       if (D<D_min&&D!=0) {
        D_min=D; Angle=i;
        } 
        delay(10);
        }
      if (Angle>=80&&Angle<100) {back; delay(500); }
      else if(Angle<=100) {
        spin_right(); delay(1000);
      }else if(Angle>80) {
        spin_left(); delay(1000); 
      }
   }else if(read_ultrasonic2() >10 && analogRead(A1)>20) {
    go_slow();
   } else {
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
