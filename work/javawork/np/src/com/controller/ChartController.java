	package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.RList;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.frame.Service;
//import com.vo.Product;

@Controller
public class ChartController {

	
//	@Resource(name="pbiz")
//	Service<Integer,Product> biz;
	
	public ChartController() {

	}
	
	@RequestMapping("/rcharttemp.mc")
	@ResponseBody
	public void ruu(HttpServletResponse response) throws IOException, RserveException, REXPMismatchException {
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		RConnection rconn = new RConnection("192.168.0.158");
		rconn.setStringEncoding("utf8");

		rconn.eval("source('C:/logs/final_test.R',encoding='UTF-8')");
		// R의 계산 결과를 리스트로 리턴 받음(소스를 로딩하고 함수를 호출하는 과정, 어레이리스트아님)
		RList list = rconn.eval("a3()").asList();
//		RList list = rconn.eval("a3()").asList();
//		RList list = new RList();
//		list.add(rconn.eval("a3()"));
//		list.add(rconn.eval("a4()"));
//		System.out.println(list);
		
		
		
		

		// 리스트의 첫 번째 요소를 double 배열로 리턴
		double[] n1 = list.at(0).asDoubles();
		double[] n2 = list.at(1).asDoubles();
		double[] n3 = list.at(2).asDoubles();
		
//		{
//			"time":[8,9,10,11],
//			"data":[
//					{"name":"temp","data":[9,20,10,11,22]},
//					{"name":"humi","data":[9,20,10,11,22]}
//			     ]
//		}
		
//		ArrayList<ArrayList<String>> jarrs = new ArrayList<ArrayList<String>>();
//		ArrayList<String> jarr = new ArrayList<>();
//		long sec = 116760960;
//		for (double num2:n1) {
//			sec+=1;
//			jarr.add(sec+","+num2);
//		}
////		for (double num2:n3) {
////			jarr.add((long) num2);
////		}
//		jarrs.add(jarr);
//		out.print(jarrs);
		
		//JSONObject jo = new JSONObject();
		JSONArray tdatas = new JSONArray();
		long sec = 116760960;
		for(double num:n3) {
			JSONArray tdata = new JSONArray();
				sec+=1;
				tdata.add(sec);
				tdata.add(num);
				tdatas.add(tdata);
		}
		//jo.put("",tdata);
		out.print(tdatas);
		
		//123
		
//		JSONObject jo2 = new JSONObject();
//		
//		JSONArray ja2 = new JSONArray();
//		JSONObject jj = new JSONObject();
//		jj.put("name", "gas");
//		JSONArray tdata2 = new JSONArray();
//		for(double num:n2) {
//			tdata2.add(num);
//		}
////		jj.put("data", tdata2);
//		jj.put("", tdata2);
//		ja2.add(jj);
//		JSONObject jj2 = new JSONObject();
//		jj2.put("name", "temp");
//		JSONArray tdata3 = new JSONArray();
//		for(double num:n3) {
//			tdata3.add(num);
//		}
//		jj2.put("data", tdata3);
//		ja2.add(jj2);
		
		//jo.put("data", ja2);
		
		
		//out.print(jo.toJSONString());
		
		out.close();
		rconn.close();
	}

	
//	@RequestMapping("/pchart.mc")
//	@ResponseBody
//	public void uu(HttpServletResponse response) throws IOException {
//		response.setContentType("text/json;charset=UTF-8");
//		PrintWriter out = response.getWriter();
//		ArrayList<Product> list = null;
//		try {
//			list = biz.get();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		JSONArray ja = new JSONArray();
//		for(Product u:list) {
//			JSONArray jo = new JSONArray();
//			jo.add(u.getName());
//			jo.add(u.getPrice());
//			ja.add(jo);
//		}
//		out.print(ja.toJSONString());
//		out.close();
//	}
//	@RequestMapping("/tchart.mc")
//	@ResponseBody
//	public void tuu(HttpServletResponse response) throws IOException {
//		response.setContentType("text/json;charset=UTF-8");
//		PrintWriter out = response.getWriter();
//		ArrayList<Product> list = null;
//		try {
//			list = biz.get();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		JSONArray ja = new JSONArray();
//		for(Product u:list) {
//			JSONObject jo = new JSONObject();
//			
//			jo.put("price", u.getPrice());
//			ja.add(jo);
//		}
//		out.print(ja.toJSONString());
//		out.close();
//	}
}





