package android.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ReceiverThread extends Thread{
	Socket client;
	BufferedReader clientIn;//클라이언트가 보내는 메시지를 읽는 스트림
	PrintWriter clientOut;//클라이언트에게 메시지를 보내는 스트림
	SerialArduinoLEDControl serialObj;//시리얼통신을 위한 객체
	OutputStream serialOut;//시리얼통신에서 아두이노로 데이터를 내보내기 위한 스트림
	public ReceiverThread(Socket client) {
		super();
		this.client = client;
		//초기화
		//클라이언트가 보내오는 메시지를 읽기 위한 스트림생성
		try {
			clientIn = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
			//클라이언트에게 메시지를
			clientOut = new PrintWriter(this.client.getOutputStream(), true);
			//아두이노와 시리얼통신을 하기 위한 객체
			serialObj = new SerialArduinoLEDControl();
			serialObj.connect("COM8");
			serialOut = serialObj.getOutput();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void run() {
		//클라이언트의 메시지를 받아서 아두이노로 전송
		while(true) {
			try {
				String msg = clientIn.readLine();
				System.out.println("클라이언트가 보낸 메시지:"+msg);
				if(msg.equals("led1_on")) {
					serialOut.write('a');
				}
				if(msg.equals("led2_on")) {
					serialOut.write('b');
				}
				if(msg.equals("led3_on")) {
					serialOut.write('c');
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
}
