	package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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

@Controller
public class ChartController {

	

	
	public ChartController() {

	}
	
	@RequestMapping("/rcharttemp.mc")
	@ResponseBody
	public void ruu_temp(HttpServletResponse response) throws IOException, RserveException, REXPMismatchException {
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		RConnection rconn = new RConnection("192.168.0.29");
		rconn.setStringEncoding("utf8");

		rconn.eval("source('C:/logs/final_test.R',encoding='UTF-8')");
		// R의 계산 결과를 리스트로 리턴 받음(소스를 로딩하고 함수를 호출하는 과정, 어레이리스트아님)
		RList list = rconn.eval("a4()").asList();


		// 리스트의 첫 번째 요소를 double 배열로 리턴
		double[] n1 = list.at(0).asDoubles();
		double[] n2 = list.at(1).asDoubles();
		double[] n3 = list.at(2).asDoubles();
		double[] n8 = list.at(7).asDoubles();

		
		Date date = new Date();
		//미국과 시차 고려한 우리 로그 시작 시간 설정
		long datetime = date.parse ( "DEC 04, 2021 19:34:29");
		
		JSONArray tdatas = new JSONArray();
		for(double num:n8) {
			JSONArray tdata = new JSONArray();
				datetime+=1000;
				tdata.add(datetime);
				tdata.add(num);
				tdatas.add(tdata);
		}
		out.print(tdatas);
		out.close();
		rconn.close();
	}
	
	@RequestMapping("/rchartgas.mc")
	@ResponseBody
	public void ruu_gas(HttpServletResponse response) throws IOException, RserveException, REXPMismatchException {
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		RConnection rconn = new RConnection("192.168.0.29");
		rconn.setStringEncoding("utf8");

		rconn.eval("source('C:/logs/final_test.R',encoding='UTF-8')");
		// R의 계산 결과를 리스트로 리턴 받음(소스를 로딩하고 함수를 호출하는 과정, 어레이리스트아님)
		RList list = rconn.eval("a4()").asList();


		// 리스트의 첫 번째 요소를 double 배열로 리턴
		double[] n1 = list.at(0).asDoubles();
		double[] n2 = list.at(1).asDoubles();
		double[] n3 = list.at(2).asDoubles();
		double[] n7 = list.at(6).asDoubles();

		
		Date date = new Date();
		//미국과 시차 고려한 우리 로그 시작 시간 설정
		long datetime = date.parse ( "DEC 04, 2021 19:34:29");
		
		JSONArray tdatas = new JSONArray();
		for(double num:n7) {
			JSONArray tdata = new JSONArray();
				datetime+=1000;
				tdata.add(datetime);
				tdata.add(num);
				tdatas.add(tdata);
		}
		out.print(tdatas);
		out.close();
		rconn.close();
	}
		
	@RequestMapping("/rchart_tot.mc")
	@ResponseBody
	public void ruu_tot(HttpServletResponse response) throws IOException, RserveException, REXPMismatchException {
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		RConnection rconn = new RConnection("192.168.0.29");
		rconn.setStringEncoding("utf8");

		rconn.eval("source('C:/logs/final_test.R',encoding='UTF-8')");
		// R의 계산 결과를 리스트로 리턴 받음(소스를 로딩하고 함수를 호출하는 과정, 어레이리스트아님)
		RList list = rconn.eval("a3()").asList();


		// 리스트의 첫 번째 요소를 double 배열로 리턴
		double[] n1 = list.at(0).asDoubles();
		double[] n2 = list.at(1).asDoubles();
		double[] n3 = list.at(2).asDoubles();
		
		
		JSONObject jo = new JSONObject();
		JSONArray tdata = new JSONArray();
		for(double num:n1) {
			tdata.add(num);
		}
		jo.put("time",tdata);
		
		JSONArray tdata2 = new JSONArray();
		for(double num:n2) {
			tdata2.add(num);
		}
		jo.put("temp", tdata2);
		
		JSONArray tdata3 = new JSONArray();
		for(double num:n3) {
			tdata3.add(num);
		}
		jo.put("gas", tdata3);
		
		
		out.print(jo.toJSONString());
		out.close();
		rconn.close();
	}

	@RequestMapping("/tempgage.mc")
	@ResponseBody
	public void tempgage(HttpServletResponse response) throws IOException, RserveException, REXPMismatchException {
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		RConnection rconn = new RConnection("192.168.0.29");
		rconn.setStringEncoding("utf8");

		rconn.eval("source('C:/logs/final_test.R',encoding='UTF-8')");
		RList list = rconn.eval("b2()").asList();

		int [] n1 = list.at(0).asIntegers();
		
		JSONObject jo = new JSONObject();
		JSONArray tdata = new JSONArray();
		for(int num:n1) {
			tdata.add(num);
		}
		jo.put("temp",tdata);
		
		out.print(jo.toJSONString());
		out.close();
		rconn.close();
	}
	
	@RequestMapping("/gasgage.mc")
	@ResponseBody
	public void gasgage(HttpServletResponse response) throws IOException, RserveException, REXPMismatchException {
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		RConnection rconn = new RConnection("192.168.0.29");
		rconn.setStringEncoding("utf8");

		rconn.eval("source('C:/logs/final_test.R',encoding='UTF-8')");
		RList list = rconn.eval("b1()").asList();

		int [] n1 = list.at(0).asIntegers();
		
		JSONObject jo = new JSONObject();
		JSONArray tdata = new JSONArray();
		for(int num:n1) {
			tdata.add(num);
		}
		jo.put("gas",tdata);
		
		out.print(jo.toJSONString());
		out.close();
		rconn.close();
	}
	
	@RequestMapping("/areadata.mc")
	@ResponseBody
	public void areadata(HttpServletResponse response) throws IOException, RserveException, REXPMismatchException {
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		RConnection rconn = new RConnection("192.168.0.29");
		rconn.setStringEncoding("utf8");

		rconn.eval("source('C:/logs/final_test.R',encoding='UTF-8')");
		// R의 계산 결과를 리스트로 리턴 받음(소스를 로딩하고 함수를 호출하는 과정, 어레이리스트아님)
		RList list = rconn.eval("b3()").asList();
		RList list2 = rconn.eval("b4()").asList();


		// 리스트의 첫 번째 요소를 double 배열로 리턴
		double[] n1 = list.at(0).asDoubles();
		double[] n2 = list.at(1).asDoubles();
		double[] n3 = list.at(2).asDoubles();
		
		double[] nn1 = list2.at(0).asDoubles();
		double[] nn2 = list2.at(1).asDoubles();
		double[] nn3 = list2.at(2).asDoubles();

		if(n1 == nn1) {
			
		}
		
		JSONObject jo = new JSONObject();
		JSONArray tdata = new JSONArray();
		for(double num:n1) {
			tdata.add(num);
		}
		jo.put("time",tdata);
		
		JSONArray tdata2 = new JSONArray();
		for(double num:n2) {
			tdata2.add(num);
		}
		jo.put("temp", tdata2);
		
		JSONArray tdata3 = new JSONArray();
		for(double num:n3) {
			tdata3.add(num);
		}
		jo.put("gas", tdata3);
		
		
		out.print(jo.toJSONString());
		out.close();
		rconn.close();
	}
	
	
}
