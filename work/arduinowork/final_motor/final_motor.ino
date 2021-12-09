#define EA 3  // 모터드라이버 EA 핀, 아두이노 우노 보드 디지털 3번 핀에 연결
#define EB 11  // 모터드라이버 EA 핀, 아두이노 우노 보드 디지털 3번 핀에 연결
#define M_IN1 4  // 모터드라이버 IN1 핀, 아두이노 우노 보드 디지털 4번 핀에 연결
#define M_IN2 5  // 모터드라이버 IN2 핀, 아두이노 우노 보드 디지털 5번 핀에 연결
#define M_IN3 13  // 모터드라이버 IN3 핀, 아두이노 우노 보드 디지털 5번 핀에 연결
#define M_IN4 12  // 모터드라이버 IN4 핀, 아두이노 우노 보드 디지털 5번 핀에 연결

int motorA_vector = 1;  // 모터의 회전방향이 반대일 시 0을 1로, 1을 0으로 바꿔주면 모터의 회전방향이 바뀜.
int motorB_vector = 1;  // 모터의 회전방향이 반대일 시 0을 1로, 1을 0으로 바꿔주면 모터의 회전방향이 바뀜.
int motor_speed = 150;  // 모터 스피드 0 ~ 255

void setup()  // 초기화
{
  pinMode(EA1, OUTPUT);  // EA와 연결된 핀 출력 설정
  pinMode(M_IN1, OUTPUT);  // IN1과 연결된 핀 출력 설정 오른쪽 위
  pinMode(M_IN2, OUTPUT);  // IN2와 연결된 핀 출력 설정 오른쪽 아래
  pinMode(EA2, OUTPUT);  // EA와 연결된 핀 출력 설정
  pinMode(M_IN3, OUTPUT);  // IN1과 연결된 핀 출력 설정 왼쪽 위
  pinMode(M_IN4, OUTPUT);  // IN2와 연결된 핀 출력 설정 왼쪽 아래
}
void loop()  // 무한루프
{
  // DC모터 정회전
  digitalWrite(EA1, HIGH);  // 모터구동 ON
  digitalWrite(M_IN1, motorA_vector);  // IN1에 HIGH(or LOW)
  digitalWrite(M_IN2, !motorA_vector);  // IN2에 LOW(or HIGH)
  digitalWrite(EA2, HIGH);  // 모터구동 ON
  digitalWrite(M_IN3, motorA_vector);  // IN1에 HIGH(or LOW)
  digitalWrite(M_IN4, !motorA_vector);  // IN2에 LOW(or HIGH)
  delay(5000);  // 5초간 지연
  // DC모터 정지
//  digitalWrite(EA1, LOW);  // 모터구동 OFF
//  digitalWrite(M_IN1, LOW);  // IN1에 LOW
//  digitalWrite(M_IN2, LOW);  // IN2에 LOW
//  digitalWrite(EA2, LOW);  // 모터구동 OFF
//  digitalWrite(M_IN3, LOW);  // IN1에 LOW
//  digitalWrite(M_IN4, LOW);  // IN2에 LOW
//  delay(5000);  // 5초간 지연
//  // DC모터 역회전
//  digitalWrite(EA1, HIGH);  // 모토구동 ON
//  digitalWrite(M_IN1, !motorA_vector);  // IN1에 LOW(or HIGH)
//  digitalWrite(M_IN2, motorA_vector);  // IN2에 HIGH(or LOW)
//  digitalWrite(EA2, HIGH);  // 모토구동 ON
//  digitalWrite(M_IN3, !motorA_vector);  // IN1에 LOW(or HIGH)
//  digitalWrite(M_IN4, motorA_vector);  // IN2에 HIGH(or LOW)
//  delay(5000);  // 5초간 지연
// 기기 제자리 회전
//  digitalWrite(EA, HIGH);  // 모터구동 ON
//  digitalWrite(M_IN1, motorA_vector);  // IN1에 HIGH(or LOW)
//  digitalWrite(M_IN2, !motorA_vector);  // IN2에 LOW(or HIGH)
//  digitalWrite(EB, HIGH);  // 모터구동 ON
//  digitalWrite(M_IN3, !motorB_vector);  // IN1에 HIGH(or LOW)
//  digitalWrite(M_IN4, motorB_vector);  // IN2에 LOW(or HIGH)
//  delay(5000);  // 5초간 지연
}
