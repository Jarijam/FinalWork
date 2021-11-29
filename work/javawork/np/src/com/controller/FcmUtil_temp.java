package com.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.annotation.Resource;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.frame.Service;

@Controller
public class FcmUtil_temp {
	public final static String API_KEY = "AAAAj786B0A:APA91bEswaGH2EkUHyoeZimlPM6T70o39tf-uIEO93Q2KKB43UlbE3LVaugXvU9Fdb2gj3siFR218BSvdyFIPQN73_pmm_oynYPGbUFRZpbN-kFrrnVXGAdFesdvhk5-IfMz-r9aK_mw"; //서버키 값
	public final static String URL = "https://fcm.googleapis.com/fcm/send";

	public static void sendServer(String temp) throws Exception{
		URL url = new URL(URL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Authorization", "key=" + API_KEY);

		conn.setDoOutput(true);

		JSONObject notification = new JSONObject();
		notification.put("title", "온도 상태 변동");
		notification.put("body", "온도값 : "+temp);
		
		JSONObject sdata = new JSONObject();
		sdata.put("c1", temp+"");
		
		JSONObject body = new JSONObject();
		body.put("notification", notification);
		body.put("data", sdata);
		body.put("to","sendToActivity2");
		body.put("to", "cw3Pu9h7Q3eTygl_PtxbQ3:APA91bH-X4SYrm3eXICSqTSuuhciDYAkhVJB4byBOqhUWLwnouEdpE20SzmJmUuBVne4VAHLWz3KRb9DwVm9tW106yiloYUJt_TZQ35EziSC1iWbtOdBIL2XE2aE9ozDNI_M5XOW6b74"); // 토큰값

		OutputStream os = conn.getOutputStream();

		os.write(body.toJSONString().getBytes("UTF-8"));
		os.flush();
		os.close();

		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

	}

}
