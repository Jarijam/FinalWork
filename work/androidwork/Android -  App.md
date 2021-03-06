# Android  -  App

### 목차

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

![``](C:\Users\a\Desktop\파이널프로젝트\console1.PNG)

- FCM으로 비상 상황 전파 및 상단바 알림으로 상황 알림
- 긴급 통화 기능

![](C:\Users\a\Desktop\파이널프로젝트\console2.PNG)

- HTTP 통신으로 받아온 센서 데이터값을 실시간으로 표시
- Google Map을 사용하여 현 관리자 위치 표시
- 현재 데이터값을 상황에 따라 캡처 하여  저장후 갤러리에서 관리 



#### Controller

![](C:\Users\a\Desktop\파이널프로젝트\controller.PNG)

- IOT장비와 블루투스 연결을 통한 제어
- 필요시 무선으로 장비를 조정하여 관리 
- 장비에 부착된 카메라를 이용하여 현장의 상황 판단





#### web view

![](C:\Users\a\Desktop\파이널프로젝트\webview.PNG)

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

<img src="C:\Users\a\Desktop\http1.PNG" style="zoom: 85%;" /><img src="C:\Users\a\Desktop\http2.PNG" alt="http2" style="zoom: 89%;" />

- IOT장비에서 측정중인 센서값을 받아오기 위해 클래스 생성



![](C:\Users\a\Desktop\http3.PNG)

- JSON형식으로 수집한 데이터를 각각의 뷰로 지정해주고 값에 따라 이미지를 변화 할 수 있게 설정 





#### Google Map

![](C:\Users\a\Desktop\mapapi.PNG)

- Google Maps platform에서 API키 발급 및 SDK등록



![](C:\Users\a\Documents\GitHub\FinalWork\img\mapmanifest.PNG)

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



![](C:\Users\a\Documents\GitHub\FinalWork\img\location.PNG)

- 현재위치를 찾고 지도에 현위치를 표시



<img src="C:\Users\a\Documents\GitHub\FinalWork\img\map.png" style="zoom: 33%;" />



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



![](C:\Users\a\Documents\GitHub\FinalWork\img\connectbluetooth.PNG)

![](C:\Users\a\Documents\GitHub\FinalWork\img\bluetooth_soket.PNG)

- 선택된 기기의 이름과 address를 가져오도록 설정

- 선택된 기기와의 소켓을 만들고 연결을 시도

- 연결이 정상적으로 완료되면 thread를 통하여 통신을 시작

  

![](C:\Users\a\Documents\GitHub\FinalWork\img\bluetooth_connect.PNG)

- Bluetooth연결을 위한 connectedThread 클래스 생성



![](C:\Users\a\Documents\GitHub\FinalWork\img\sendbluetooth.PNG)

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



