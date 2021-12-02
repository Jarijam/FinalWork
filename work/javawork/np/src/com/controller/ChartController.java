package com.controller;

import java.io.IOException;
import java.io.PrintWriter;

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
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ChartController {
		
	public ChartController() {
	
	}
	
	@RequestMapping("/test01.mc")
	public ModelAndView test01() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("chart01"); 
		mv.setViewName("sample_graph/testchartview");
		return mv;
	}
	
	@RequestMapping("/chart01.mc")
	@ResponseBody
	public void chart(HttpServletResponse response) throws IOException, RserveException, REXPMismatchException {
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		RConnection rconn = new RConnection("192.168.0.158");
		rconn.setStringEncoding("utf8");

		rconn.eval("source('C:/logs/test1.R',encoding='UTF-8')");
		// R의 계산 결과를 리스트로 리턴 받음
		RList list = rconn.eval("a3()").asList();

		// 리스트의 첫 번째 요소를 double 배열로 리턴
		double[] n1 = list.at(0).asDoubles();
		double[] n2 = list.at(1).asDoubles();
		double[] n3 = list.at(2).asDoubles();		
//		  {
//	         "time": [8,9,10,11],
//	         "data": [
//	               {"name":"temp","data":[9,20,10,11,22]},
//	               {"name":"humi","data":[9,20,10,11,22]}
//	                 ]
//	      }				
		JSONObject jo = new JSONObject();
		JSONArray tdata = new JSONArray();
		for(double num:n1) {
			tdata.add(num);
		}
		jo.put("time",tdata);			
		JSONObject jo2 = new JSONObject();		
		JSONArray ja2 = new JSONArray();
		JSONObject jj = new JSONObject();
		jj.put("name", "temp");
		JSONArray tdata2 = new JSONArray();
		for(double num:n2) {
			tdata2.add(num);
		}
		jj.put("data", tdata2);
		ja2.add(jj);
		JSONObject jj2 = new JSONObject();
		jj2.put("name", "humi");
		JSONArray tdata3 = new JSONArray();
		for(double num:n3) {
			tdata3.add(num);
		}
		jj2.put("data", tdata3);
		ja2.add(jj2);	
		jo.put("data", ja2);	
		
		out.print(jo.toJSONString());
		out.close();
		rconn.close();
	}
}
