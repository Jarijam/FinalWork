package serial_rxtx_exam01;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ButtonTestControlServer {
	private ServerSocket server;
	public void connect() {
		try {
			server = new ServerSocket(12345);
			Thread t1 = new Thread(new Runnable() {			
				@Override
				public void run() {
					while(true) {
						try {
							Socket client = server.accept();
							String ip = client.getInetAddress().getHostName();
							System.out.println(ip+"사용자님 접속!");
							new ButtonReceiverThread(client).start();
						}catch(IOException e) {
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
		new ButtonTestControlServer().connect();
	}
}
