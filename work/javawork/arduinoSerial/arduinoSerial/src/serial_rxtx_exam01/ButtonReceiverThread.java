package serial_rxtx_exam01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import android.control.SerialArduinoLEDControl;

public class ButtonReceiverThread extends Thread{
	Socket client;
	BufferedReader clientIn;
	PrintWriter clientOut;	
	ButtonSerailArduinoLEDControl serialObj;
	OutputStream serialOut;
	public ButtonReceiverThread(Socket client) {
		super();
		this.client = client;
		//초기화
		//클라이언트가 보내오는 메시지를 읽기 위한 스트림생성
		try {
			clientIn = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
			//클라이언트에게 메시지를
			clientOut = new PrintWriter(this.client.getOutputStream(), true);
			//아두이노와 시리얼통신을 하기 위한 객체
			serialObj = new ButtonSerailArduinoLEDControl();
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
				if(msg.equals("led_on")) {
					serialOut.write('o');
				}else {
					serialOut.write('f');
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
}
