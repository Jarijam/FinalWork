package final_latte;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class SendHttp {
	public SendHttp() {
		super();
		
	}
	
	class SendThread extends Thread{

		String flame;
		String temp;
		String gas;
		String crash;
		
		String urlstr = "http://192.168.0.29/np/data.mc";
		
		URL url = null;
		HttpURLConnection con = null;

		BufferedReader br = null;
		
		public SendThread() {
			
		}
		
		public SendThread(String flame, String temp,String gas, String crash) {
			this.flame = flame;
			this.temp = temp;
			this.gas = gas;
			this.crash = crash;
		}
		
		@Override
		public void run() {
			//request $ response
			try {
				url = new URL(urlstr + "?flame="+flame + "&temp="+temp + "&gas="+gas + "&crash="+crash);
				System.out.println(url);
				con = (HttpURLConnection) url.openConnection();
				con.setReadTimeout(10000);
				con.setRequestMethod("POST");

				br = new BufferedReader(
						new InputStreamReader(
								con.getInputStream()));

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
	
	public void sendData(String flame,String temp,String gas, String crash) {
		SendThread st = new SendThread(flame,temp,gas,crash);
		st.start();
	}

}
