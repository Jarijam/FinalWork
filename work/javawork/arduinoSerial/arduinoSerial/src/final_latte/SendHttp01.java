package final_latte;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class SendHttp01 {
	public SendHttp01() {
		super();
		
	}
	
	class SendThread extends Thread{

		String X;
		String Y;
		
		//String urlstr = "http://192.168.0.158/semi/iot2.mc";
		String urlstr = "http://192.168.0.29/np/data.mc";
		
		URL url = null;
		HttpURLConnection con = null;

		BufferedReader br = null;
		
		public SendThread() {
			
		}
		
		public SendThread(String X, String Y) {
			this.X = X;
			this.Y = Y;
			
		}
		
		@Override
		public void run() {
			//request $ response
			try {
//				url = new URL(urlstr + "?crash="+crash + "&gas="+gas+ "&flame="+flame+ "&temp="+temp);
				url = new URL(urlstr + "?X="+X + "&Y="+Y);
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
	
	
	
	public void sendData(String X, String Y) {
		SendThread st = new SendThread(X,Y);
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
