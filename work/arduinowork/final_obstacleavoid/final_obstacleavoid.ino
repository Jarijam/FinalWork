#include "Servo.h"  // 서보모터를 사용하기 위한 헤더파일 호출
Servo LKservo;  // 서보모터 객체 선언

#define EA 3  // 모터드라이버 EA 핀, 아두이노 우노 보드 디지털 3번 핀에 연결
#define EB 11  // 모터드라이버 EA 핀, 아두이노 우노 보드 디지털 3번 핀에 연결
#define M_IN1 4  // 모터드라이버 IN1 핀, 아두이노 우노 보드 디지털 4번 핀에 연결
#define M_IN2 5  // 모터드라이버 IN2 핀, 아두이노 우노 보드 디지털 5번 핀에 연결
#define M_IN3 13  // 모터드라이버 IN3 핀, 아두이노 우노 보드 디지털 5번 핀에 연결
#define M_IN4 12  // 모터드라이버 IN4 핀, 아두이노 우노 보드 디지털 5번 핀에 연결

#define echo 6  // 초음파 센서 에코(Echo) 핀, 아두이노 우노 보드의 디지털 6번 핀 연결
#define trig 7  // 초음파 센서 트리거(Trigger) 핀, 아두이노 우노 보드의 디지털 7번 핀 연결

#define servo_motor 2  // 서보모터 Signal 핀, 아두이노 우노 보드 디지털 3번 핀에 연결

int motorA_vector = 1;  // 모터의 회전방향이 반대일 시 0을 1로, 1을 0으로 바꿔주면 모터의 회전방향이 바뀜.
int motorB_vector = 1;  // 모터의 회전방향이 반대일 시 0을 1로, 1을 0으로 바꿔주면 모터의 회전방향이 바뀜.
int motor_speed = 200;  // 모터 스피드 0 ~ 255
void setup()  // 초기화
{
  pinMode(EA, OUTPUT);  // EA와 연결된 핀 출력 설정
  pinMode(M_IN1, OUTPUT);  // IN1과 연결된 핀 출력 설정 오른쪽 위
  pinMode(M_IN2, OUTPUT);  // IN2와 연결된 핀 출력 설정 오른쪽 아래
  pinMode(EB, OUTPUT);  // EA와 연결된 핀 출력 설정
  pinMode(M_IN3, OUTPUT);  // IN1과 연결된 핀 출력 설정 왼쪽 위
  pinMode(M_IN4, OUTPUT);  // IN2와 연결된 핀 출력 설정 왼쪽 아래

  pinMode(trig, OUTPUT);  // trig 출력 설정
  pinMode(echo, INPUT);  // echo 입력설정

  LKservo.attach(servo_motor);  // 서보모터 핀 설정
  LKservo.write(65);  // 서보모터 초기값 90도 설정
}
void loop()  // 무한루프
{
    if (read_ultrasonic() < 18)  // 만약 측정한 거리값이 18보다 작으면 자동차 우회전
  {
    motorA_con( motor_speed );  // 모터A 정방향
    motorB_con( -(motor_speed) );  // 모터B 역방향
    delay(280);  // 0.28초간 지연
  }
  else  // 측정한 거리값이 16보다 작지 않으면 자동차 직진
  {
    motorA_con( motor_speed );  // 모터A 정방향
    motorB_con( motor_speed );  // 모터B 정방향
  }
}

int read_ultrasonic(void)  // 초음파 센서 값 읽어오는 함수
{
  float return_time, howlong;  // 시간 값과 거리 값을 저장하는 변수를 만든다.
  // 초음파 센서는 초음파를 발사 후 돌아오는 시간을 역산하여 거리 값으로 계산한다.
  digitalWrite(trig, HIGH);  // 초음파 발사
  delay(5);  // 0.05초간 지연
  digitalWrite(trig, LOW);  // 초음파 발사 정지
  return_time = pulseIn(echo, HIGH);  // 돌아오는 시간
  howlong = ((float)(340 * return_time) / 10000) / 2;  // 시간을 거리로 계산
  Serial.println(howlong);
  return howlong;  // 거리 값 리턴
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
