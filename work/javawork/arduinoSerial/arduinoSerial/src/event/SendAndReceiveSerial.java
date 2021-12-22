package event;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

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
	
	SendHttp02 http;
	LEDControlClient led;
//	public SendAndReceiveSerial() {
//		http = new SendHttp02();
//	}
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
			commPort = portIdentifier.open(this.getClass().getName(), 5000);
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
			byte[] readBuffer = new byte[128];

			try {

				while (bin.available() > 0) {
					int numBytes = bin.read(readBuffer);
				}

				String ss = new String(readBuffer);
				http = new SendHttp02();
				http.sendData(Double.parseDouble(ss));
				System.out.println("Receive Low Data:" + ss + "||");

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

//	public void sendIoT(String cmd) {
//		Thread t1 = new Thread(new SendIoT(cmd));
//		t1.start();
//	}

//	class SendIoT implements Runnable {
//
//		String cmd;

//		public SendIoT(String cmd) {
//			this.cmd = cmd;
//		}

//		@Override
//		public void run() {
//			byte[] datas = cmd.getBytes();
//			try {
//				out.write(datas);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}

//	}

	public static void main(String[] args) {
		SendAndReceiveSerial ss = new SendAndReceiveSerial("COM8", true);
		Scanner sc = new Scanner(System.in);
//		while (true) {
//			System.out.println("Input cmd");
//			String cmd = sc.nextLine();
//			if (cmd.equals("s")) {
//				ss.sendIoT(cmd);
//			} else if (cmd.equals("t")) {
//				ss.sendIoT(cmd);
//			} else if (cmd.equals("q")) {
//				break;
//			}
//		}
//		sc.close();
//		try {
//			ss.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

}
