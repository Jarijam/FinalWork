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
//import com.vo.Product;

@Controller
public class DataController {

	
//	@Resource(name="pbiz")
//	Service<Integer,Product> biz;
	
	public DataController() {

	}
	
	@RequestMapping("/gas.mc")
	@ResponseBody
	public void gas(HttpServletResponse response) throws IOException, RserveException, REXPMismatchException {
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		RConnection rconn = new RConnection("192.168.0.29");
		rconn.setStringEncoding("utf8");

		rconn.eval("source('C:/logs/final_test.R',encoding='UTF-8')");
		// R의 계산 결과를 리스트로 리턴 받음(소스를 로딩하고 함수를 호출하는 과정, 어레이리스트아님)
		RList list = rconn.eval("a6()").asList();


		// 리스트의 첫 번째 요소를 double 배열로 리턴
		double[] n1 = list.at(0).asDoubles();
		double[] n2 = list.at(1).asDoubles();
		
		
		JSONObject jo = new JSONObject();
		JSONArray tdata = new JSONArray();
		for(double num:n1) {
			tdata.add(num);
		}
		jo.put("date",tdata);
		
		JSONArray tdata2 = new JSONArray();
		for(double num:n2) {
			tdata2.add(num);
		}
		jo.put("gas", tdata2);
		
		
		
		out.print(jo.toJSONString());
		out.close();
		rconn.close();
	}
	@RequestMapping("/temp.mc")
	@ResponseBody
	public void temp(HttpServletResponse response) throws IOException, RserveException, REXPMismatchException {
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		RConnection rconn = new RConnection("192.168.0.29");
		rconn.setStringEncoding("utf8");

		rconn.eval("source('C:/logs/final_test.R',encoding='UTF-8')");
		// R의 계산 결과를 리스트로 리턴 받음(소스를 로딩하고 함수를 호출하는 과정, 어레이리스트아님)
		RList list = rconn.eval("a7()").asList();


		// 리스트의 첫 번째 요소를 double 배열로 리턴
		double[] n1 = list.at(0).asDoubles();
		double[] n2 = list.at(1).asDoubles();
		
		
		JSONObject jo = new JSONObject();
		JSONArray tdata = new JSONArray();
		for(double num:n1) {
			tdata.add(num);
		}
		jo.put("date",tdata);
		
		JSONArray tdata2 = new JSONArray();
		for(double num:n2) {
			tdata2.add(num);
		}
		jo.put("temp", tdata2);
		
		
		
		out.print(jo.toJSONString());
		out.close();
		rconn.close();
	}
	
	
	
}