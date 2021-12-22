package android.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

//안드로이드(클라이언트)의 요청을 받으면 아두이노 장치와 통신하기 위한 시리얼 객체를 제어하는 서버
public class AndroidLEDControlServer {
	private ServerSocket server;
	SerialArduinoLEDControl serialObj;
	//서버객체가 생성되고 실행될 때, 아두이노 포트를 열고 통신할 준비를 함
	public AndroidLEDControlServer() {
		//필요하면 클라이언트가 접속하고 accept하고 작업
		//아두이노와 시리얼통신을 하기 위한 객체
		serialObj = new SerialArduinoLEDControl();
		serialObj.connect("COM8");
	}
	public void connect() {
		try {
			server = new ServerSocket(12345);
			//클라이언트의 접속을 기다리는 쓰레드
			Thread t1 = new Thread(new Runnable() {			
				@Override
				public void run() {
					while(true) {
						try {
							Socket client = server.accept();
							String ip = client.getInetAddress().getHostName();
							System.out.println(ip+"사용자 접속!");
							//시리얼통신을 위한 객체생성
							//클라이언트의 메시지를 읽는 쓰레드
							//아두이노와 시리얼통신을 할 수 있도록 입력스트림,
							//출력스트림을 쓰레드로 넘겨줌
							InputStream serialIn = serialObj.getInput();
							OutputStream serialOut = serialObj.getOutput();
							new ReceiverThread(client, serialOut).start();
							new SenderThread(client, serialIn).start();
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
	
	public static void main(String[] args) {
		new AndroidLEDControlServer().connect();
	}
}
