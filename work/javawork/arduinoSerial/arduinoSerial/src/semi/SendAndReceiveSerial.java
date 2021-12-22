package semi;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class SendAndReceiveSerial implements SerialPortEventListener, MqttCallback {

	private BufferedInputStream bin;
	private InputStream in;
	private OutputStream out;
	private SerialPort serialPort;
	private CommPortIdentifier portIdentifier;
	private CommPort commPort;
	private MqttClient mqttclient;
	private MqttConnectOptions mqttOption;
	
	SendHttp02 http;
	
	public SendAndReceiveSerial(String portName, boolean mode) {
		try {
			if (mode == true) {
				portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
				System.out.printf("Port Connect : %s\n", portName);
				connectSerial();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void connectSerial() throws Exception {
		if (portIdentifier.isCurrentlyOwned()) {
			System.out.println("Error: Port is currently in use");
		} else {
			commPort = portIdentifier.open(this.getClass().getName(), 3000);
			if (commPort instanceof SerialPort) {
				serialPort = (SerialPort) commPort;
				serialPort.addEventListener(this);
				serialPort.notifyOnDataAvailable(true);
				serialPort.setSerialPortParams(9600, // 통신속도
						SerialPort.DATABITS_8, // 데이터 비트
						SerialPort.STOPBITS_1, // stop 비트
						SerialPort.PARITY_NONE); // 패리티
				in = serialPort.getInputStream();
				bin = new BufferedInputStream(in);
				out = serialPort.getOutputStream();
			} else {
				System.out.println("Error: Only serial ports are handled by this example.");
			}
		}
	}
	
	// Asynchronized Receive Data
	// --------------------------------------------------------
	public void serialEvent(SerialPortEvent event) {
		switch (event.getEventType()) {
		case SerialPortEvent.BI:
		case SerialPortEvent.OE:
		case SerialPortEvent.FE:
		case SerialPortEvent.PE:
		case SerialPortEvent.CD:
		case SerialPortEvent.CTS:
		case SerialPortEvent.DSR:
		case SerialPortEvent.RI:
		case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
			break;
		case SerialPortEvent.DATA_AVAILABLE:
			byte[] readBuffer = new byte[1024];

			try {
				while (bin.available() > 4) {
					int numBytes = bin.read(readBuffer);
				}
				String ss = new String(readBuffer);
				
				String[] ssarr = ss.split(" ");
				String ss1 = ssarr[0];
				String ss2 = ssarr[1];
				if(ss1!=null && ss2!=null) {
					http = new SendHttp02();
					System.out.println(ss1+","+ss2);
					http.sendData(ss1,ss2);
					
					System.out.println("Receive Low Data:" + ss1+","+ss2 + "||");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
	}
	
	public void close() throws IOException {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (in != null) {
			in.close();
		}
		if (out != null) {
			out.close();
		}
		if (commPort != null) {
			commPort.close();
		}

	}
	
	public SendAndReceiveSerial init(String server, String clientId) {
		mqttOption = new MqttConnectOptions();
		mqttOption.setCleanSession(true);
		mqttOption.setKeepAliveInterval(30);
		try {
			mqttclient = new MqttClient(server,  clientId);
			mqttclient.setCallback(this);
			mqttclient.connect(mqttOption);
		} catch (MqttException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	@Override
	public void connectionLost(Throwable arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		System.out.println("===============메시지 도착===========");
		System.out.println(message+","+"topic: "+topic+", id: "+message.getId()+", Payload: "+
																		new String(message.getPayload()));
		String msg = new String(message.getPayload());
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					if(msg.equals("led_on")) {
						out.write('o');
					}else if(msg.equals("led_off")){
						out.write('f');
					}
				}catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
	//구독신청하기
		public boolean subscribe(String topic) {
			try {
			if(topic!=null) {
					mqttclient.subscribe(topic, 0); 
				}
			} catch (MqttException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}

	public static void main(String[] args) {
		SendAndReceiveSerial ss = new SendAndReceiveSerial("COM8", true);
		ss.init("tcp://192.168.0.7:1883", "myid").subscribe("led");
	}
}
