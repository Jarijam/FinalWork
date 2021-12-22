package final_latte;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class SendHttp02 {
	public SendHttp02() {
		super();
		
	}
	
	class SendThread extends Thread{

		String flame;
		String temp;
//		String btn;
		String gas;
		String crash;
//		String dis;
		
		//String urlstr = "http://192.168.0.158/semi/iot2.mc";
		String urlstr = "http://192.168.0.29/np/data.mc";
		
		URL url = null;
		HttpURLConnection con = null;

		BufferedReader br = null;
		
		public SendThread() {
			
		}
		
		public SendThread(String flame, String temp,String gas, String crash) {
//			this.btn = btn;
			this.flame = flame;
			this.temp = temp;
			this.gas = gas;
			this.crash = crash;
//			this.dis = dis;
		}
		
		@Override
		public void run() {
			//request $ response
			try {
//				url = new URL(urlstr + "?crash="+crash + "&gas="+gas+ "&flame="+flame+ "&temp="+temp);
				url = new URL(urlstr + "?flame="+flame + "&temp="+temp + "&gas="+gas + "&crash="+crash);
				System.out.println(url);
				con = (HttpURLConnection) url.openConnection();
				con.setReadTimeout(10000);
				con.setRequestMethod("POST");
				//con.getInputStream();
				System.out.println("작동여부체크");

				br = new BufferedReader(
						new InputStreamReader(
								con.getInputStream()));

//				String str = "";
//				str = br.readLine();
//				System.out.println(str);
//				while ((str = br.readLine()) != null) {
//					if(str.equals("")) {
//						continue;
//					}
//					System.out.println(str.trim());
//				}

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
		System.out.println("센드데이터체크");
	
	}
	
//	public String sendData2(double temp) {
//		SendThread st = new SendThread(temp);
//		st.start();
//		return null;
//	}
	
//	public static void main(String[] args) {
//		SendHttp02 shttp = new SendHttp02();
//		System.out.println("메인 실행이 되어야하나;");
//		shttp.sendData(21.0);
//	}

}
