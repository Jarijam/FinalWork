package basic;
//아두이노에서 전송하는 데이터를 읽는 쓰레드

import java.io.IOException;
import java.io.InputStream;

public class SerialReaderThread implements Runnable {
	InputStream in;
	
	public SerialReaderThread(InputStream in) {
		super();
		this.in = in;
	}
	@Override
	public void run() {
		byte[] buffer = new byte[1024];
		int len=-1;
		try {
			while((len = in.read(buffer))>-1) {
				System.out.print(new String(buffer,0,len));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
