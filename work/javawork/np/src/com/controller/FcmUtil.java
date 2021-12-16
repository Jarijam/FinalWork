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
public class FcmUtil {
	public final static String API_KEY = "AAAA1VVZLSw:APA91bFeZYPNf8ZCUYBVRcpM_XzeiDDR8k1hWujBXSPhalQcC_BknrVB3aHg_ijA5ryBSlk4-mwvjvBIu68nmkmc2-9LpuvADYX_2fxNPZZ8w5wCxtlGggj87B-Sg3z_94n0ayPj7Whx"; //서버키 값
	public final static String URL = "https://fcm.googleapis.com/fcm/send";

	public static void sendServer(String flame) throws Exception{
		URL url = new URL(URL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Authorization", "key=" + API_KEY);

		conn.setDoOutput(true);

		JSONObject notification = new JSONObject();
		notification.put("title", "화재 발생!!!");
		notification.put("body", "긴급상황 알림!!! \n 화재발생!!!!");
		notification.put("contents", "18층 사무실 화재 발생 건물 밖으로 피신");
		
		JSONObject body = new JSONObject();
		body.put("notification", notification);
		body.put("to", "fUgb9-D3SlO3X3P9-1XgLV:APA91bEPOnZ_d62DGfewfOJug0_EjvCCLfLnfxAZRCxvDzErinXGKHa3QKgtZ5DsAV_GH72iLxS-DtjbJLH7_Zsgj3BhnKf9vMbB0aTpoapCUfSPqYRNvf7Ajk3shxamFtbDKxH79oA8"); // 토큰값

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
