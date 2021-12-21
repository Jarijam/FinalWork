
## Android  -  Native App

<img src="img\Logo.png" style="zoom: 80%;" />

### 목차

- 개요
- 환경 및 기술 스택
- 구성 및 기능

---



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



----



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
  - 



