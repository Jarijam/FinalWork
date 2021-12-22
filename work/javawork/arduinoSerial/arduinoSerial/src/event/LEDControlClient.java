package event;

import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import android.control.SerialArduinoLEDControl;

public class LEDControlClient implements MqttCallback{
	private MqttClient mqttclient;
	private MqttConnectOptions mqttOptions;
	//메시지가 전송되어 messageArrived메소드가 호출되면 데이터를 아두이노로 전송
	SerialArduinoLEDControl serialObj;
	OutputStream serialOut;
	public LEDControlClient() {
		//MQTT객체가 생설될 때 시리얼 통신을 위한 준비
		serialObj = new SerialArduinoLEDControl();
		serialObj.connect("COM8");
		serialOut = serialObj.getOutput();
	}
	public LEDControlClient init(String server, String clientId) {
		//mqtt클라이언트가 연결하기 위해 필요한 정보 설정
		mqttOptions = new MqttConnectOptions();
		mqttOptions.setCleanSession(true);
		mqttOptions.setKeepAliveInterval(30);
		try {
			//broker에 subscribe하기 위한 클라이언트객체를 생성
			mqttclient = new MqttClient(server, clientId);
			//클라이언트객체에 콜백을 셋팅 - subscribe하면서 적절한 시점에 메소드가 호출될 수 있다.
			mqttclient.setCallback(this);
			//mqttclient가 broker에 연결할 수 있도록 설정
			mqttclient.connect(mqttOptions);
		} catch (MqttException e) {
			e.printStackTrace();
		}
		return this;
	}
	//구독신청하기
	public boolean subscribe(String topic) {
		try {
			if(topic!=null) {
				mqttclient.subscribe(topic, 0);//topic, Qos는 메시지를 전달하고 관리하는 방법(품질)
			}
		}catch(MqttException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public static void main(String[] args) {
		LEDControlClient client = new LEDControlClient();	
		//MqttClient객체가 broker에 연결되고 구독신청
		client.init("tcp://192.168.0.7:1883", "myid").subscribe("led");
	}
	//커넥션이 종료되면 호출 - 통신오류로 연결이 끊어진 경우 호출
	@Override
	public void connectionLost(Throwable arg0) {
		
	}
	//메시지 배달이 완료되면 호출
	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		
	}
	//메시지가 도착하면 호출되는 메소드 - topic(broker구독신청한 topic명), MqttMessage는 메시지
	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		System.out.println("===메시지도착===");
		System.out.println(message+","+"topic:"+topic+",id:"+message.getId()+",Payload:"+new String(message.getPayload()));
		String msg = new String(message.getPayload());
		new Thread(new Runnable() {	
			@Override
			public void run() {
				try {
					if(msg.equals("led_on")) {					
						serialOut.write('o');
					}else {
						serialOut.write('f');
					}
				}catch (IOException e) {
						e.printStackTrace();
					}
			}
		}).start();
	}
}
