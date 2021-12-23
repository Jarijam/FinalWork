package final_latte;

 
import java.io.BufferedInputStream;

import java.io.IOException;

import java.io.InputStream;

import java.io.OutputStream;

import java.net.ServerSocket;

import java.net.Socket;

import gnu.io.CommPort;

import gnu.io.CommPortIdentifier;

import gnu.io.SerialPort;

import gnu.io.SerialPortEvent;

import gnu.io.SerialPortEventListener;

 

public class SendAndReceiveSerial implements SerialPortEventListener {

 

	private BufferedInputStream bin;
	private InputStream in;
	private OutputStream out;
	private SerialPort serialPort;
	private CommPortIdentifier portIdentifier;
	private CommPort commPort;

	private ServerSocket server1;

	SendHttp http;
	
	public SendAndReceiveSerial() {
		super();
	}
	
	//웹 요청 받으면 아두이노 장치와 통신하는 server
	public void connectweb() {
		try {
			server1 = new ServerSocket(12346);
			System.out.println("server1체크");
			Thread t1 = new Thread(new Runnable() {

				@Override
				public void run() {
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
						http = new SendHttp();
						http.sendData(ssarr[1], ssarr[2], ssarr[3], ssarr[4]);
						}	
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

	public static void main(String[] args) {
		SendAndReceiveSerial ss = new SendAndReceiveSerial("COM9", true);
		ss.connectweb();
	}
}