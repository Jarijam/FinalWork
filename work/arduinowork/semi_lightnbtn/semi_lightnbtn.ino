char data;
int val;

#define LED 8
#define BTN 7
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
  val = digitalRead(BTN);
  Serial.println(val);
  delay(500);
  if(Serial.available()>0) {
    data = Serial.read();
    if(data='o'){
      digitalWrite(LED,HIGH);
//      Serial.println("on");
    }else if(data='f') {
      digitalWrite(LED,LOW);
      //Serial.println("off");
      
    }
  }
  
//  if(digitalRead(BUTTON) == HIGH) {
//    digitalWrite(LED, HIGH);
//  }else {
//    digitalWrite(LED, LOW);
//  }
 
  lcd.setCursor(0,0);
  lcd.print("Hello World");
  lcd.setCursor(0,1);
  lcd.print("HIIIIIIIIIIII");
}
