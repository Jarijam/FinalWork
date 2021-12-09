char data;
int val;

#define LED 8
#define BTN 7
//int potPin = 5;
#define TEMP A5
long value = 0;
int temp = 0;
#include <LiquidCrystal.h>
LiquidCrystal lcd(3,4,10,11,12,13);

void setup() {
  Serial.begin(9600);
  lcd.begin(16,2);
  lcd.clear();
  pinMode(7, INPUT);
  pinMode(8, OUTPUT);
}

void loop() {
  String space = " ";
  value = analogRead(TEMP);
  temp = (value*500/1024);
  val = digitalRead(BTN);
  Serial.print(temp+space);  
  Serial.println(val);
  delay(1000);
  if(Serial.available()>0) {
    data = Serial.read();
    if(data=='o'){
      digitalWrite(LED,HIGH);
        lcd.setCursor(0,0);
        lcd.print("LED_ON!!^^");
//      Serial.println("on");
    }else if(data=='f') {
      digitalWrite(LED,LOW);
        lcd.setCursor(0,0);
        lcd.print("LED_OFF T^T");
      //Serial.println("off");
      
    }
  }
//  lcd.setCursor(0,0);
//  lcd.print("Hello World");
  lcd.setCursor(0,1);
  lcd.print(val);
  //  if(digitalRead(BUTTON) == HIGH) {
//    digitalWrite(LED, HIGH);
//  }else {
//    digitalWrite(LED, LOW);
//  }
}
