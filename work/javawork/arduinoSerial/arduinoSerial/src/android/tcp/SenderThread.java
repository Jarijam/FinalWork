package android.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SenderThread extends Thread{
	Socket client;
	BufferedReader clientIn;//클라이언트가 보내는 메시지를 읽는 스트림
	PrintWriter clientOut;//클라이언트에게 메시지를 보내는 스트림
	InputStream serialIn;
	//쓰레드가 생성될 때, 클라이언트객체와 아두이노 시리얼포트로 출력할 수 있는 스트림
	//객체를 받아 처리
	public SenderThread(Socket client, InputStream serialIn) {
		super();
		this.client = client;
		this.serialIn = serialIn;
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
		byte[] buffer = new byte[1024];
		int len = -1;
		try {
			while((len = serialIn.read(buffer))>-1) {
				String mydata = new String(buffer,0,len);
				System.out.print(mydata);
				if(mydata.trim().length()>0) {
					clientOut.println(mydata);
				}
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
