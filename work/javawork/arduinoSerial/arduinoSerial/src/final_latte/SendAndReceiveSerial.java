package final_latte;

 

import java.io.BufferedInputStream;

import java.io.IOException;

import java.io.InputStream;

import java.io.OutputStream;

import java.net.ServerSocket;

import java.net.Socket;

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

	

	private ServerSocket server1;

	private ServerSocket server2;

	

	//안드로이드 요청 받으면 아두이노 장치와 통신하는 server

	public void connect() {

		try {

			server2 = new ServerSocket(12345);

			Thread t2 = new Thread(new Runnable() {

				@Override

				public void run() {

					while(true) {

					try {

						Socket 	client = server2.accept();

						String ip = client.getInetAddress().getHostName();

						System.out.println(ip+"사용자 접속");

						//클라이언트의 메시지를 읽는 쓰레드

						new ReceiverThread2(client, out).start();

					} catch (IOException e) {

						e.printStackTrace();

						}

					}

				}

			});

			t2.start();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	

	//웹 요청 받으면 아두이노 장치와 통신하는 server

		public void connectweb() {

			System.out.println("커넥션웹체크");

			try {

				server1 = new ServerSocket(12346);
				System.out.println("server1체크");
				Thread t1 = new Thread(new Runnable() {

					@Override

					public void run() {
						System.out.println("run체크");
						while(true) {

						try {

							Socket 	client = server1.accept();
							
							String ip = client.getInetAddress().getHostName();
							
							System.out.println(ip+"사용자 접속");

							//클라이언트의 메시지를 읽는 쓰레드

							new ReceiverThread(client, out).start();

						} catch (IOException e) {

							e.printStackTrace();

							}

						}

					}

				});

				t1.start();

			} catch (IOException e) {

				e.printStackTrace();

			}

		}

	

	

	public OutputStream getOutput() {

		System.out.println("out2값?:"+out);

		return out;

	}

//	public OutputStream getOutput() {

//		return out;

//	}

	SendHttp01 http01;

	SendHttp02 http02;

	

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

 

	public SendAndReceiveSerial() {

		super();

		

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

				

//				serialPort2 = (SerialPort) commPort;

//				serialPort2.setSerialPortParams(9600,

//						SerialPort.DATABITS_8,

//						SerialPort.STOPBITS_1,

//						SerialPort.PARITY_NONE);

//				System.out.println("out2 들어왔닝?");

//				out2 = serialPort2.getOutputStream();

				

				

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

			byte[] readBuffer = new byte[256];

 

			try {

				while (bin.available() > 10) {

					int numBytes = bin.read(readBuffer);
					System.out.println(numBytes+"bytes");

				}
				String ss = new String(readBuffer).trim();
				String[] ssarr = ss.split(",");
				
				if(ssarr.length>1) {
					if(ssarr[1]!=null) {
						http02 = new SendHttp02();
						http02.sendData(ssarr[1], ssarr[2], ssarr[3], ssarr[4]);
					}
					



//					if(ssarr[0].equals("C")) {
//
//						if(ssarr[1]!=null & ssarr[2]!=null) {
//
//							http01 = new SendHttp01();
//
//							System.out.println(ssarr[1]+","+ssarr[2]);
//
//							http01.sendData(ssarr[1],ssarr[2]);
//
//						}
//
//						
//
//						
//
//					}else if(ssarr[0].equals("S")) {
//
//						if(ssarr[1]!=null & ssarr[2]!=null & ssarr[3]!=null & ssarr[4]!=null) {
//
////							System.out.println(ssarr[3]);
//
////						if(ss1!=null & ss2!=null & ss3!=null) {
//
//							http02 = new SendHttp02();
//
//							System.out.println(ssarr[1]+","+ssarr[2]+","+ssarr[3]+","+ssarr[4]);
//
//							http02.sendData(ssarr[1],ssarr[2],ssarr[3],ssarr[4]);
//
//						}

					}

//					System.out.println("Receive Low Data:" + ss1+","+ss2+","+ss3+","+ss4 + "||");
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

	

	//MQTT (LED정보 mqtt로 받아오기)==========================================

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

		SendAndReceiveSerial ss = new SendAndReceiveSerial("COM9", true);

		ss.init("tcp://192.168.0.158:1883", "myid").subscribe("led");
		System.out.println("메인실행체크");
//		new AndroidLEDControlServer().connect();

//		ss.connect();

		ss.connectweb();

		

	}

 

	

 

	

}