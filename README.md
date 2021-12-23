# Fire IT Patrol Project
<img src="img\Logo.png" style="zoom: 80%;" />



## 목차

- 개요
- Web
- DB
- R
- Arduino
- Android



## 개요
<img src="img\title.png"/>
- 고층아파트, 대형병원, 마트 등 어느 곳에서든 우리는 화재의 위험 속에서 살아가고 있다. 
  현대화 사회에서 화재의 위험 요인이 많아지면서 화재가 발생하였을 때 가장 중요한 점은 초기인지(신고), 초기 진압, 인명 대피 등이 있다. 
  이에 따라 이번 프로젝트를 통해 IOT를 접목하고 화재를 초기에 감지 하여 피해를 사전에 방지 하고자 한다.
  
  
  
### 프로젝트 목적
-  부재중인 시설 또는 사람의 시선이 닿지 않는곳에서 발생할 수 있는 화재를 사전에 감지하고자 한다.
-  사전 감지를 통하여 화재 골든타임을 놓치지 않으며 피해를 감소 시키고자 한다.



### 프로젝트 구성
#### Web
- 자이로 센서 값을 TCP/IP 통신으로 Main Server에 전송후  순찰 장비의 위치 및 이동경로를 Canvas 로 표현
- 불꽃 감지 센서,가스 센서, 온도 센서, 충돌 센서 값을 TCP/IP 통신으로 MainServer에 전송후 MyBatis로 DB저장 또는 Log 데이터 축적
- 저장된 데이터를 R을 활용, 차트 api를 활용해 시각화



#### Mobile
- Flame Sensor, Gas Sensor, Temperature Sensor 의 값이 일정 수준 도달 했을때 FCM을 활용해 관리자 App으로 푸시알림 발송
- Bluetooth 통신으로 순찰 장비 무선 조종



#### IOT
- 화재감지 센서를 활용한 화재 감지
- 가스 감지 센서를 활용한 가스 수치 확인
- 온도 감지 센서를 활용한 온도 측정
- 초음파센서를 활용해 장애물 회피 또는 구조물 tracing
- 와이파이 및 블루투스 모듈을 활용해 메인서버와 통신



### 프로젝트 멤버

![``](img/member.png)




### 환경 및 기술 스택
![``](img/tool.PNG)



### 시스템 구성도
![``](img/system.PNG)



-----

## Web

![``](img/member_login.PNG)

<h4>로그인 페이지 </h4>
- DB에 저장된 데이터를 통하여 로그인이 가능

![``](img/member_signup.PNG)

<h4>회원 가입</h4>
- DB에 데이터를 저장하며 회원가입 가능

![``](img/member_delete.PNG)

<h4>회원 수정</h4>
- 회원 수정 기능(본인의 아이디가 고정으로 입력되어 있도록 설정)

<h4>회원 탈퇴</h4>
- 회원 탈퇴 기능(로그인 되어 있는 아이디를 삭제하도록 설정) 



#### ■ Arduino 센서값  Http 통신으로 Main Server로 가져오기

<img src="img\console.png"/>



####  Data Controller (메인 서버)

https://github.com/Jarijam/FinalWork/blob/8bda858031d70f3f7f9070bbaeb52c3ee591279c/work/javawork/np/src/com/controller/DataController.java#L49-L71

####  Send Data (Latte panda 에서 메인서버로 HTTP 통신으로 센서값 보내기)

```java
package final_test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class SendHttp {
	public SendHttp() {
		super();
		
	}
	
	class SendThread extends Thread{

		String flame;
		String gas;
		String temp;
		String crash;
		
		
		String urlstr = "http://192.168.0.29/np/data.mc";
		URL url = null;
		HttpURLConnection con = null;

		BufferedReader br = null;
		
		public SendThread() {
			
		}
		
		public SendThread(String btn,String gas,String flame,String dis, String temp) {
		
			
			this.flame = flame;
			this.temp = temp;
            this.gas = gas;
			this.temp = crash;
		}
		
		@Override
		public void run() {
			//request $ response
			try {
				
				url = new URL(urlstr + "?btn="+btn + "&gas="+gas+ "&flame="+flame+ "&dis="+dis+ "&temp="+temp);
				con = (HttpURLConnection) url.openConnection();
				con.setReadTimeout(10000);
				con.setRequestMethod("POST");
				//con.getInputStream();

				br = new BufferedReader(
						new InputStreamReader(
								con.getInputStream()));

				String str = "";


			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				con.disconnect();
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
		}
	}
	public void sendData(String btn,String gas,String flame,String dis, String temp) {
		SendThread st = new SendThread(btn,gas,flame,dis,temp);
		st.start();
	
	}
}

```



#### ■ 불꽃 감지시 이미지 변경 및 CCTV

<img src="img\sequrity.png"/>

<img src="img\warning.png"/>


``` java
<script>
	function getData3() {
		
		$.ajax({
			url:'flame.mc',
			success:function(data){			
				$(data).each(function(idx,item){
					console.log(item.flame);
					if( item.flame == 1){
						$('#flame').html("<br/><img class='warning' src='/np/img/warning3.png'>");
					}else if ( item.flame == 0){
						$('#flame').html("<br/><img class='default' src='/np/img/security2.png'>");
					}
				});
			}
		});
	};
	$(document).ready(function(){
			getData3();
		setInterval(function(){
			getData3();
		},5000);
	});

 <div class="row">
				    <div class="col-sm-4" >
					    <div class="tbox"  >
						    <h5 class="tbox-head" style="background-color: blue;"  >Flame Detection&nbsp;&nbsp;
						    </h5>
							    <div class="tbox-body">
									 <a id="flame"></a> 
								</div>
						</div>
				    </div>
				    <div class="col-sm-5" ><br/>
   			 			<div class="cbox">
						    <h3 class="cbox-body">CCTV</h3>
						    <div id = "cam" style="text-align: center;" >
						    <iframe src="https://www.youtube.com/embed/7EndDbSl-0k/7EndDbSl-0k?autoplay=1&mute=1&amp;playlist=7EndDbSl-0k&amp;loop=1" frameborder="0" width="90%" height="350px" scrolling="no" style="margin: auto;  align-content: center;"></iframe>
						    </div>
					    </div>
 					</div>
            </div>
```



#### ■ 가스, 온도값 데이터 시각화 (R, HighChart 활용)

* 로그파일 저장을 위한 Log4j 설정

```xml
log4j.logger.data = DEBUG, console, data

# data
log4j.appender.data.Threadhold=DEBUG
log4j.appender.data = org.apache.log4j.FileAppender
log4j.appender.data.layout = org.apache.log4j.PatternLayout 
log4j.appender.data.layout.ConversionPattern = %d{YYYY,MM,dd,HH,mm,ss},%m%n
log4j.appender.data.File = c:/logs/finaldata.log
```

* Http 통신을 통해 받은 센서값을 로그 파일로 저장하는 controller

```java
private Logger data_log = 
				Logger.getLogger("data");
				
 @RequestMapping("/data.mc")
		@ResponseBody
		public void data(HttpServletRequest request) throws Exception {
	    	String flame = request.getParameter("flame");
	    	String temp = request.getParameter("temp");
			String gas = request.getParameter("gas");
	    	String crash = request.getParameter("crash");
	    	
			int f_flame = Integer.parseInt(flame);
			System.out.println("불꽃감지센서 : "+flame+" 온도 : "+temp+" 가스 : "
                               +gas+" 충돌감지 : "+crash);
			
			data_log.debug(flame+","+temp+","+gas+","+crash);
			
			if(f_flame > 0) {
				try {
					FcmUtil.sendServer(flame);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
```

* 저장된 로그 파일 

<img src="img\logcapture.PNG"/>



* 웹의 highcharts 가 필요한 data를 요청하는 javascript

```javascript
<script>
function display(d){
			Highcharts.getJSON(
			  'https://cdn.jsdelivr.net/gh/highcharts/highcharts@v7.0.0/samples/data/usdeur.json',
			  function(data) {

				  var chart2 = new Highcharts.chart('container_gas', {
			      chart: {
			        zoomType: 'x'
			      },
			      title: {
			        text: 'Gas over time'
			      },
			      subtitle: {
			        text: document.ontouchstart === undefined ?
			          'Click and drag to zoom in' : 'Pinch the chart to zoom in'
			      },
			      xAxis: {
			        type: 'datetime'
			      },
			      yAxis: {
			        title: {
			          text: 'Gas'
			        }
			      },
			      legend: {
			        enabled: false
			      },
			      plotOptions: {
			        area: {
			          fillColor: {
			            linearGradient: {
			              x1: 0,
			              y1: 0,
			              x2: 0,
			              y2: 1
			            },
			            stops: [
			              [0, Highcharts.getOptions().colors[6]],
			              [1, Highcharts.color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
			            ]
			          },
			          marker: {
			            radius: 2
			          },
			          lineWidth: 1,
			          states: {
			            hover: {
			              lineWidth: 1
			            }
			          },
			          threshold: null
			        }
			      },

			      series: [{
			        type: 'area',
			        name: 'Gas',
			        data: d
			      }]
			    });
			  });
}
function getdata(){
	$.ajax({
		url:'rchartgas.mc',
		success:function(d){
			display(d);
		}
	});
};

$(document).ready(function(){
		getdata(); 
	setInterval(function(){
		getdata();
	},5000);
});
</script>
```

- R Studio를 활용해 log파일 가공

<img src="img\R.png"/>

- 요청을 받는 controller

  https://github.com/Jarijam/FinalWork/blob/50f8ad60edd3bf3d3aef119943b26625ada100b9/work/javawork/np/src/com/controller/ChartController.java#L1-L154



- 결과화면

<img src="img\data.png"/>

<img src="img\data2.png"/>



#### ■ 구역별 데이터 시각화 (Canvas를 활용해 IoT장비 위치추적)

- Canvas를 활용하기 위한 좌표값 받아오기

https://github.com/Jarijam/FinalWork/blob/8bda858031d70f3f7f9070bbaeb52c3ee591279c/work/javawork/np/src/com/controller/DataController.java#L115-L136

- Canvas 띄우기

https://github.com/Jarijam/FinalWork/blob/8bda858031d70f3f7f9070bbaeb52c3ee591279c/work/javawork/np/web/view/graphics_view/graphics_test.jsp#L6-L69

- 온도값, 가스값, 현재시간

  ```java
  	      function getSensor() {
  	               $.ajax({
  	                  url:'recentsensor.mc',
  	                  success:function(sensor){
  	                   
  	                     $('#areatemp').html(sensor.temp+"°C");
  	                     $('#areagas').html(sensor.gas+"ppm");
  	                     if( sensor.flame == 1){
  	                    	 $('#areaflame').html("WARNING!!!!!")
  	                     }else if(sensor.flame == 0){
  	                    	 $('#areaflame').html("SAFE")
  	                     }
  	                  }
  	               });
  	            };
  		      var sensor = getSensor();
  			  var data = getData();
  			  $(document).ready(function(){
  					 setInterval(function(){
  					 getData();
  					 getSensor();
  					}, 1000);
  				});		
  			}); 
  		
  		//=============================time========================
  		 function time(){
  			  var time= new Date(); //시간받기위해서 new date
  			      document.getElementById("now").innerHTML="2021년 12월 17일 "+time.getHours()+"시 "+time.getMinutes()+"분 	     "+time.getSeconds()+"초";
  			     setInterval("time()",1000);     //1초 지난후 time()실행
  			  }
  ```

- 결과화면

<img src="img\canvas.png"/>








-----

## DB

오라클 데이터베이스 세팅 및 구축

![``](img/oracle.PNG)

![``](img/oracle2.PNG)


이클립스의 데이터베이스 세팅(XML)

![``](img/oracle3.PNG)

데이터를 입력하거나 조회 요청이 왔을 때, 입력된 데이터나 조회하는 조건을 VO에 담아서 DAO에 요청하면 DAO는 저장소(DB)로부터 데이터를 입력하거나 조회한 후 그 결과를 돌려주게 하기 위한 작업

![``](img/oracle4.PNG)
![``](img/oracle5.PNG)
![``](img/oracle6.PNG)

controller

![``](img/oracle7.PNG)

mapper

![``](img/oracle8.PNG)

![``](img/oracle9.PNG)
-----

## R


------

## Arduino

* 필요 라이브러리, 헤더파일 호출 및 핀 입출력 지정

```c++
#include <SoftwareSerial.h>     //블루투스 통신을 위한 헤더파일 호출
SoftwareSerial BTSerial(10, 1); // HC - 06 통신을 위한 TX, RX의 
								// PIN번호를 입력(TX = 10 , RX = 1)

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
```

* 가스, 충돌감지, 불꽃감지, 온도 센싱 함수 설정

```c++
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
```

* 초음파 센서 1,2 거리 측정 함수 

```c++
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
```

* 모터 방향 및 속도 설정 함수

```c++
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
```

* mode 1 : 월 트레이서 모드 함수

```C++
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
```

* mode 2 : 장애물 탐지 및 우회 모드 함수

```c++
void mode2() {
   unsigned long int D, Angle;
   unsigned long int D_min = 10000;
   if(read_ultrasonic2() <8 || analogRead(A1) <8){
    back();
    delay(500);
    brake();
    delay(200);
    //장애물 탐지 및 서보모터 각도(장애물의 위치)저장
    for (int i=40;i<150;i+=10) {
      LKservo.write(i); delay(200); 
       D=read_ultrasonic1(); 
       delay(3); 
       
       if (D<D_min&&D!=0) {
        D_min=D; Angle=i;
        } 
        delay(10);
        }
     //장애물 위치에 따라 회전방향 설정
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
```

* mode 3 : 안드로이드 앱을 통한 무선 조종 모드 함수

```C++
void mode3() {
if (BTSerial.available()) 
  {
    char cmd = (char) BTSerial.read();  
    Serial.println(cmd);
    
    if ( cmd == 'F')     	 // rc카 전진
    {
         go();
    } 
    else if (cmd ==  'B')     // rc카 후진
    {    
          back();
    } 
    else if (cmd == 'S')     // rc카 정지
    {    
          brake();
    }
    else if (cmd=='L')		 // rc카 좌회전
    {
          spin_left();
    }
    else if (cmd=='R') 		// rc카 우회전
    {
          spin_right();
    }
  }
}
```




------

## Android 

<img src="img\app_logo.png" style="zoom: 80%;" />

### APP 목차

- 개요
- 환경 및 기술 스택
- 구성 및 기능




## 1. 개요

산업현장에서 운용중인 무인탐지설비를 효율적으로 관리 하기위하여 편의성과 가시성에 초점을 맞추어 제작.

- 데이터 값을 한눈에 볼 수 있도록 UI구성
- 변화된 데이터 값에 따라 변화 하는 이미지로 쉽게 상황 판단 가능
- 비상 상황시 필요한 기능 구현





## 2. 환경 및 기술 스택

- 언어 : JAVA
- 개발 도구 : Android Studio 4.2.0
- 기술 스택 : FireBase, GoogleMap API
- 협업 도구 : Zoom , Allo, Google Docs, GItHub
- 통신 환경 : Bluetooth, Http






## 3. 구성 및 기능


### 3.1 구성

#### Console

![``](img/console1.PNG)

- FCM으로 비상 상황 전파 및 상단바 알림으로 상황 알림
- 긴급 통화 기능

![``](img/console2.PNG)

- HTTP 통신으로 받아온 센서 데이터값을 실시간으로 표시
- Google Map을 사용하여 현 관리자 위치 표시
- 현재 데이터값을 상황에 따라 캡처 하여  저장후 갤러리에서 관리 



#### Controller

![``](img/controller.PNG)

- IOT장비와 블루투스 연결을 통한 제어
- 필요시 무선으로 장비를 조정하여 관리 
- 장비에 부착된 카메라를 이용하여 현장의 상황 판단





#### web view

![``](img/webview.PNG)



- web에서 관리하는 차트들을 app에서도 동시에 관리 





### 3.2 기능 상세

#### FCM

``` java
<service
    android:name=".MyFirebaseMessagingService"
    android:enabled="true"
    android:exported="true"
    android:stopWithTask="false">
   <intent-filter>
     <action android:name="com.google.firebase.MESSAGING_EVENT" />
   </intent-filter>
</service>	
```

- FCM 사용을 위한 Manifest 초기 등록



``` java
notificationManager = NotificationManagerCompat.from(ConsoleActivity.this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }
        Intent intent2 = new Intent(ConsoleActivity.this, ConsoleActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(ConsoleActivity.this, 101, intent2, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ConsoleActivity.this, "channel")
                .setSmallIcon(R.drawable.fireman)
                .setContentTitle(title)
                .setContentText(body)
                .setContentText(contents)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{1000, 1000});
        notificationManager.notify(0, mBuilder.build());
```

- 상단바로 알림을 받기 위하여 notification을 작성



``` java
<activity
  android:name=".ReceivedActivity"
  android:label="비상상황 발생"
  android:theme="@style/Theme.AppCompat.Light.Dialog"
  android:windowSoftInputMode="stateAlwaysHidden" />
```

- 비상상황 알림을 다이얼로그로 전파 하기 위한 설정



```java
 regId = "fUgb9-D3SlO3X3P9-1XgLV:APA91bEPOnZ_d62DGfewfOJug0_EjvCCLfLnfxAZRCxvDzErinXGKHa3QKgtZ5DsAV_GH72iLxS-DtjbJLH7_Zsgj3BhnKf9vMbB0aTpoapCUfSPqYRNvf7Ajk3shxamFtbDKxH79oA8";
 regId2 = "c1UI3eBjTtupybel2GeJFT:APA91bEcRXROZl-lGzDXls5WHbru8fuPw92lVt5V4SQ_W7YO-MSYpwrpVw3KcWQAiVCV1WA1CY8M7mUe4hs62LYkmUDxaSlFJG8ds2xxUS3QR3x_3tZwreOTyUfBWvKsLfh2GKcvlvcg";

public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "key = AAAA1VVZLSw:APA91bFeZYPNf8ZCUYBVRcpM_XzeiDDR8k1hWujBXSPhalQcC_BknrVB3aHg_ijA5ryBSlk4-mwvjvBIu68nmkmc2-9LpuvADYX_2fxNPZZ8w5wCxtlGggj87B-Sg3z_94n0ayPj7Whx");
                return headers;
            }

```
-  FCM서버키 및 관리자 기기 고유 토큰 등록




```java
JSONObject requestData = new JSONObject();
     try{
         requestData.put("priority", "high");

         JSONObject dataobj = new JSONObject();
         dataobj.put("contents",input);
         requestData.put("data",dataobj);

         JSONArray idarray = new JSONArray();
         idarray.put(0, regId);
         idarray.put(1, regId2);

         requestData.put("registration_ids", idarray);
     } catch (Exception e) {
         e.printStackTrace();
     }
```

```java
JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                "https://fcm.googleapis.com/fcm/send",
                requestData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listener.onRequestCompleted();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onRequestWithError(error);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return  params;
            }
```

- 긴급상황을 FCM을 이용하여 다른 관리자 기기로 전파 할 수 있도록 전송기능 설정


#### 데이터 받아오기

<img src="img\http1.PNG" style="zoom: 85%;" /> <img src="img\http2.PNG" alt="http2" style="zoom: 85%;" />

- IOT장비에서 측정중인 센서값을 받아오기 위해 클래스 생성



<img src="img\http3.PNG"/>

- JSON형식으로 수집한 데이터를 각각의 뷰로 지정해주고 값에 따라 이미지를 변화 할 수 있게 설정 





#### Google Map

<img src="img\mapapi.PNG"/>

- Google Maps platform에서 API키 발급 및 SDK등록



<img src="img\mapmanifest.PNG"/>

- Manifest에 발급받은 API키를 등록



```java
 public void onMapReady(GoogleMap googleMap) {
                Log.d("Map", "지도 준비됨.");
                map = googleMap;
                LatLng latLng = new LatLng(37.50958477466319, 127.05552514757225);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
                if (ActivityCompat.checkSelfPermission(getApplicationContext(),android.Manifest.permission.ACCESS_FINE_LOCATION)                 != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext()
                , android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
                }
                map.setMyLocationEnabled(true);
```

- 지도가 처음 실행되고 보여지는 위치를 좌표로 지정



```java
AndPermission.with(this)
                .runtime()
                .permission(
                        Permission.ACCESS_FINE_LOCATION,
                        Permission.ACCESS_COARSE_LOCATION)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        showToast("허용");
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        showToast("거부");
                    }
                })
                .start();
```

- 위치권한은 위험 권한이므로 코드상에서 권한을 승인 하여준다



<img src="img\location.PNG"/>

- 현재위치를 찾고 지도에 현위치를 표시



<img src="img\map.png" style="zoom: 30%;" />



#### Bluetooth

```java
 btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!btAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
        btArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        deviceAddressArray = new ArrayList<>();
        blue_name.setAdapter(btArrayAdapter);
        blue_name.setOnItemClickListener(new myOnItemClickListener());
```

```java
public void onClickButtonPaired(View view){
        btArrayAdapter.clear();
        if(deviceAddressArray!=null && !deviceAddressArray.isEmpty()){ deviceAddressArray.clear(); }
        pairedDevices = btAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                btArrayAdapter.add(deviceName);
                deviceAddressArray.add(deviceHardwareAddress);
            }
        }
    }
```

- 디바이스에 기존에 페어링 되어있는 블루투스 기기 목록을 불러와서 리스뷰에 출력



<img src="img\connectbluetooth.PNG"/>

<img src="img\bluetooth_soket.PNG"/>

- 선택된 기기의 이름과 address를 가져오도록 설정

- 선택된 기기와의 소켓을 만들고 연결을 시도

- 연결이 정상적으로 완료되면 thread를 통하여 통신을 시작

  

<img src="img\bluetooth_connect.PNG"/>

- Bluetooth연결을 위한 connectedThread 클래스 생성



<img src="img\sendbluetooth.PNG"/>

- 블루투스로 문자열을 IOT기기에 전송하여 기기를 제어



#### Web View

```java
WebSettings webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); // 컨텐츠 사이즈 맞추기
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 브라우저 캐시 허용
        web.setWebViewClient(new WebViewClient());
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setSaveFormData(false);
        web.loadUrl("http://192.168.0.29:80/np/recentdata.mc");
```

- Web view를 실행 시키기 위한 설정


