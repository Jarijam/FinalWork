package client_http01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import android.control.SerialArduinoLEDControl;
import event.SerialListener;

public class send_http {	
	class SendThread extends Thread{
		int btn;
		String urlstr = "http://192.168.0.7/hp/btn.mc";
		URL url = null;
		HttpURLConnection con = null;
		BufferedReader br = null;
		
		public SendThread() {		
		
		}		
		public SendThread(int btn) {
			this.btn = btn;
		}
		
		@Override
		public void run() {
			try {
				url = new URL(urlstr + "?btn="+btn);
				con = (HttpURLConnection) url.openConnection();
				con.setReadTimeout(5000);
				con.setRequestMethod("POST");
				con.getInputStream();
				br = new BufferedReader(
						new InputStreamReader(
								con.getInputStream()));
				String str = "";			
				while ((str = br.readLine()) != null) {
					if(str.equals("")) {
						continue;
					}
					System.out.println(str.trim());
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				con.disconnect();
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public void sendData(String btn) {
		SendThread st = new SendThread();
		st.start();
	}
	
	public static void main (String[] args) {		
		while(true) {
			
			send_http shttp = new send_http();
			//shttp.sendData();
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}