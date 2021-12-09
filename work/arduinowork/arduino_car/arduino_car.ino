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

#define servo_motor 2  // 서보모터 Signal 핀, 아두이노 우노 보드 디지털 3번 핀에 연결

int motorA_vector = 1;  // 모터의 회전방향이 반대일 시 0을 1로, 1을 0으로 바꿔주면 모터의 회전방향이 바뀜.
int motorB_vector = 1;  // 모터의 회전방향이 반대일 시 0을 1로, 1을 0으로 바꿔주면 모터의 회전방향이 바뀜.
int motor_speed = 150;  // 모터 스피드 0 ~ 255
void setup()  // 초기화
{
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

  pinMode(5, INPUT);
  
  LKservo.attach(servo_motor);  // 서보모터 핀 설정
  LKservo.write(1);  // 서보모터 초기값 90도 설정
}
void loop()  // 무한루프
{

  
//    LKservo.write(90);
  
  Serial.println(val);
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

int read_ultrasonic1(void)  // 초음파 센서 값 읽어오는 함수
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

int read_ultrasonic2(void)  // 초음파 센서 값 읽어오는 함수
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

void motorA_con(int M1)  // 모터A 컨트롤 함수
{
  if (M1 > 0)  // M1이 양수일 때, 모터A 정회전
  {
    digitalWrite(M_IN1, motorA_vector);  // IN1번에 HIGH(motorA_vector가 0이면 LOW)
    digitalWrite(M_IN2, !motorA_vector);  // IN2번에 LOW(motorA_vector가 0이면 HIGH)
  }
  else if (M1 < 0)  // M1이 음수일 대, 모터A 역회전
  {
    digitalWrite(M_IN1, !motorA_vector);  // IN1번에 LOW(motorA_vector가 0이면 HIGH)
    digitalWrite(M_IN2, motorA_vector);  // IN2번에 HIGH(motorA_vector가 0이면 LOW)
  }
  else  // 모터A 정지
  {
    digitalWrite(M_IN1, LOW);  // IN1번에 LOW
    digitalWrite(M_IN2, LOW);  // IN2번에 LOW
  }
  analogWrite(EA, abs(M1));  // M1의 절대값으로 모터A 속도 제어
}

void motorB_con(int M2)  // 모터B 컨트롤 함수
{
  if (M2 > 0)  // M2가 양수일 때, 모터B 정회전
  {
    digitalWrite(M_IN3, motorB_vector);  // IN3번에 HIGH(motorB_vector가 0이면 LOW)
    digitalWrite(M_IN4, !motorB_vector);  // IN4번에 LOW(motorB_vector가 0이면 HIGH)
  }
  else if (M2 < 0)  // M2가 음수일 때, 모터B 역회전
  {
    digitalWrite(M_IN3, !motorB_vector);  // IN3번에 LOW(motorB_vector가 0이면 HIGH)
    digitalWrite(M_IN4, motorB_vector);  // IN4번에 HIGH(motorB_vector가 0이면 LOW)
  }
  else  // 모터B 정지
  {
    digitalWrite(M_IN3, LOW);  // IN3번에 LOW
    digitalWrite(M_IN4, LOW);  // IN4번에 LOW
  }
  analogWrite(EB, abs(M2));  // M2의 절대값으로 모터B 속도 제어
}
//오른직진 1 오른후진2 왼직진3 왼후진4
void go() {
  digitalWrite(M_IN1, 1); digitalWrite(M_IN2, 0); delay(3);
  digitalWrite(M_IN3, 1); digitalWrite(M_IN4, 0); delay(3);
  analogWrite(EA,170);analogWrite(EB,170); delay(30);
}
//void go_straight(){}
void brake() {
  digitalWrite(M_IN1, 0); digitalWrite(M_IN2, 0); delay(3);
  digitalWrite(M_IN3, 0); digitalWrite(M_IN4, 0); delay(3);
}
void left() {
  digitalWrite(M_IN1, 1); digitalWrite(M_IN2, 0); delay(3);
  digitalWrite(M_IN3, 1); digitalWrite(M_IN4, 0); delay(3);
  analogWrite(EA,200);analogWrite(EB,50); delay(30);
}
void right(){
  digitalWrite(M_IN1, 1); digitalWrite(M_IN2, 0); delay(3);
  digitalWrite(M_IN3, 1); digitalWrite(M_IN4, 0); delay(3);
  analogWrite(EA,50);analogWrite(EB,200); delay(30);;
}
void spin_left() {
  digitalWrite(M_IN1, 1); digitalWrite(M_IN2, 0); delay(3);
  digitalWrite(M_IN3, 0); digitalWrite(M_IN4, 1); delay(3);
  analogWrite(EA,200);analogWrite(EB,200); delay(30);;
}
void spin_right() {
  digitalWrite(M_IN1, 0); digitalWrite(M_IN2, 1); delay(3);
  digitalWrite(M_IN3, 1); digitalWrite(M_IN4, 0); delay(3);
  analogWrite(EA,200);analogWrite(EB,200); delay(30);;
}
void back() {
  digitalWrite(M_IN1, 0); digitalWrite(M_IN2, 1); delay(3);
  digitalWrite(M_IN3, 0); digitalWrite(M_IN4, 1); delay(3);
  analogWrite(EA,150);analogWrite(EB,150); delay(30);
}

void mode1() {
  int crash_m = analogRead(A5);
  int crash_l = analogRead(A4);
  int crash_r = analogRead(A3);
//  Serial.println(crash_r);
  if (crash_l <= 10 | crash_r <= 10 | crash_m <=10) {
    spin_left();
    delay(1000);
  }else if (read_ultrasonic2() < 7) {
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
