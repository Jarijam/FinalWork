package android.tcp;

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
	OutputStream serialOut;
	//쓰레드가 생성될 때, 클라이언트객체와 아두이노 시리얼포트로 출력할 수 있는 스트림
	//객체를 받아 처리
	public ReceiverThread(Socket client, OutputStream serialOut) {
		super();
		this.client = client;
		this.serialOut = serialOut;
		//초기화
		//클라이언트가 보내오는 메시지를 읽기 위한 스트림생성
		try {
			clientIn = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
			//클라이언트에게 메시지를
			clientOut = new PrintWriter(this.client.getOutputStream(), true);
			//아두이노와 시리얼통신을 하기 위한 객체
			
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
