	package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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
import com.vo.CoordinateVO;

@Controller
public class DataController {
	
	
		private Logger data_log = 
				Logger.getLogger("data");
		
		private Logger coord_log = 
				Logger.getLogger("coord");
		
		
		@Resource(name="cdservice")
		Service<String, CoordinateVO> cdservice;
		
		
		public DataController() {
	
		}
	

	    @RequestMapping("/data.mc")
		@ResponseBody
		public void data(HttpServletRequest request) throws Exception {
//			String btn = request.getParameter("btn");
			String gas = request.getParameter("gas");
			String flame = request.getParameter("flame");
//			String dis = request.getParameter("dis");
			String temp = request.getParameter("temp");
	    	String crash = request.getParameter("crash");
	    	
			int f_flame = Integer.parseInt(flame);
			
			
		//	System.out.println(btn+","+gas+","+flame+","+dis);
			
			data_log.debug(flame+","+temp+","+gas+","+crash);
			if(f_flame > 0) {
				try {
					FcmUtil.sendServer(flame);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
//			if(btn.equals(1+"")) {
//			cdservice.remove(btn);
//		}
		}
	    @RequestMapping("/data2.mc")
		@ResponseBody
		public void data2(HttpServletRequest request)  {
			
			String X = request.getParameter("X");
			String Y = request.getParameter("Y");
			/*
			 * int X1 = Integer.parseInt(X); int Y1 = Integer.parseInt(Y);
			 */
			System.out.println(X+","+Y);
			
			coord_log.debug(X+","+Y);
			CoordinateVO coord1 = new CoordinateVO(X, Y);
			try {
				cdservice.modify(coord1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
				
			
//			if(btn.equals(1+"")) {
//			cdservice.remove(btn);
//		}
		}
	    
	    
	    
	    @RequestMapping("/androidpower.mc")
		@ResponseBody
		public void androidpower(HttpServletRequest request) throws Exception {
			String power = request.getParameter("pow_txt");
			System.out.println(power);
				
		}
	    
	    @RequestMapping("/androidmode.mc")
		@ResponseBody
		public void androidmode(HttpServletRequest request) throws Exception {
			String mode = request.getParameter("con_txt");
			System.out.println(mode);
			
		}
    
    	@RequestMapping("/crddata.mc")
  		@ResponseBody
  		public void uu(HttpServletResponse response) throws IOException {
  			response.setContentType("text/json;charset=UTF-8");
  			PrintWriter out = response.getWriter();
  			ArrayList<CoordinateVO> list = null;
  			try {
  				list = cdservice.get();
  			} catch (Exception e) {
  				e.printStackTrace();
  			}
  			JSONArray ja = new JSONArray();
  			for(CoordinateVO u:list) {
  				JSONObject jo = new JSONObject();
  				jo.put("X", u.getX()+"");
  				jo.put("Y", u.getY()+"");
  				ja.add(jo);
  			}
  			out.print(ja.toJSONString());
  			out.close();
  		}
    	
    	@RequestMapping("/crddelte.mc")
    	public String delete(String coordinate) {
    		try {
    			cdservice.remove(coordinate);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    		return "redirect:graphics.mc";
    	}
    
    
    //test용 (sendhttp)
    	@RequestMapping("/iot1.mc")
		@ResponseBody
		public void iotdata(HttpServletRequest request, CoordinateVO coord) throws Exception {
			String temp = request.getParameter("temp");
			String humi = request.getParameter("humi");
			int f_temp = Integer.parseInt(temp);
			int f_humi = Integer.parseInt(humi);
			System.out.println(f_temp+" : "+f_humi);
			CoordinateVO coord1 = new CoordinateVO(temp, humi);
			
			cdservice.register(coord1);
			//data_log.debug(f_temp+" : "+f_humi);
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
		@RequestMapping("/flame.mc")
		@ResponseBody
		public void flame(HttpServletResponse response) throws IOException, RserveException, REXPMismatchException {
			response.setContentType("text/json;charset=UTF-8");
			PrintWriter out = response.getWriter();
			RConnection rconn = new RConnection("192.168.0.29");
			rconn.setStringEncoding("utf8");
	
			rconn.eval("source('C:/logs/final_test.R',encoding='UTF-8')");
			// R의 계산 결과를 리스트로 리턴 받음(소스를 로딩하고 함수를 호출하는 과정, 어레이리스트아님)
			RList list = rconn.eval("a8()").asList();
	
	
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
			jo.put("flame", tdata2);

			
			
			
			
			out.print(jo.toJSONString());
			out.close();
			rconn.close();
		}
		@RequestMapping("/recentsensor.mc")
	      @ResponseBody
	      public void recent(HttpServletResponse response) throws IOException, RserveException, REXPMismatchException {
	         response.setContentType("text/json;charset=UTF-8");
	         PrintWriter out = response.getWriter();
	         RConnection rconn = new RConnection("192.168.0.29");
	         rconn.setStringEncoding("utf8");
	   
	         rconn.eval("source('C:/logs/final_test.R',encoding='UTF-8')");
	         // R의 계산 결과를 리스트로 리턴 받음(소스를 로딩하고 함수를 호출하는 과정, 어레이리스트아님)
	         RList list = rconn.eval("b5()").asList();
	   
	         // 리스트의 첫 번째 요소를 double 배열로 리턴
	         double[] n1 = list.at(0).asDoubles();
	         double[] n2 = list.at(1).asDoubles();
	         double[] n3 = list.at(2).asDoubles();
	         double[] n4 = list.at(3).asDoubles();
	         
	         JSONObject jo = new JSONObject();
	         JSONArray tdata = new JSONArray();
	         for(double num:n3) {
	            tdata.add(num);
	         }
	         jo.put("gas",tdata);
	         
	         JSONArray tdata2 = new JSONArray();
	         for(double num:n4) {
	            tdata2.add(num);
	         }
	         jo.put("temp", tdata2);

	         out.print(jo.toJSONString());
	         out.close();
	         rconn.close();
	      }

		
		@RequestMapping("/androidtemp.mc")
		@ResponseBody
		public void androidtemp(HttpServletResponse response) throws IOException, RserveException, REXPMismatchException {
			response.setContentType("text/json;charset=UTF-8");
			PrintWriter out = response.getWriter();
			RConnection rconn = new RConnection("192.168.0.29");
			rconn.setStringEncoding("utf8");
	
			rconn.eval("source('C:/logs/final_test.R',encoding='UTF-8')");

			RList list = rconn.eval("b1()").asList();
			
			
			int [] n1 = list.at(0).asIntegers();
			int [] n2 = list.at(1).asIntegers();
			int [] n3 = list.at(2).asIntegers();
			
			
			int gas = n1[0];
			int temp = n2[0];
			int flame = n3[0];		
			
			
			out.print(gas+","+temp+","+flame);
			
			out.close();
			rconn.close();
		}
		
//		@RequestMapping("/data2.mc")
//		@ResponseBody
//		public void data2(HttpServletRequest request) throws Exception {
////			Double X = Double.parseDouble(request.getParameter("X"));
//			String X = request.getParameter("X");
//			String Y = request.getParameter("Y");
//	    	
//			System.out.println(X+","+Y);
//			coord_log.debug(X+","+Y);
//		}
		
	}
