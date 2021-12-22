package event;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendHttp02 {

	
	class SendThread extends Thread{

		double temp;
		
		String urlstr = "http://192.168.0.7/hp/btn.mc";
		URL url = null;
		HttpURLConnection con = null;
		BufferedReader br = null;
		public SendThread() {
			
		}	
		public SendThread(double temp) {
			this.temp = temp;
		}	
		@Override
		public void run() {
			try {
				url = new URL(urlstr + "?temp="+temp);
				con = (HttpURLConnection) url.openConnection();
				con.setReadTimeout(5000);
				con.setRequestMethod("POST");
				con.getInputStream();
				br = new BufferedReader(
						new InputStreamReader(
								con.getInputStream()));
				String str = "";
				//str = br.readLine();
				//System.out.println(str);
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
	public void sendData(double temp) {
		SendThread st = new SendThread(temp);
		st.start();
	}
	
	public static void main(String[] args) {
		SendHttp02 shttp = new SendHttp02();
		shttp.sendData(18.0);
	}

}
