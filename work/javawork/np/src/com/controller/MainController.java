package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.frame.Service;
import com.vo.CoordinateVO;

@Controller
public class MainController {
	MyMqtt_Pub_client client;
	
	public MainController() {
		client = new MyMqtt_Pub_client();
		
	}
	
	@Resource(name="cdservice")
	Service<String, CoordinateVO> cdservice;
	
	@RequestMapping("/main.mc")
	public ModelAndView main() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("main");
		return mv;
	}
	
	@RequestMapping(value = "/getX", method = RequestMethod.GET,
			produces = "application/text;charset=utf-8")
	public @ResponseBody String coords(String x) {
		System.out.println("ajax 통신: "+x);
		return x;
		
	}
	
	  @RequestMapping("/graphics.mc") 
	   public ModelAndView coords() throws Exception { 
	      ModelAndView mv =  new ModelAndView(); 
	      List<CoordinateVO> coordlist = cdservice.get(); 
	     mv.addObject("coordlist", coordlist);
	     mv.setViewName("graphics_view/graphics_test");
	     
	     return mv; 
	   
	   }
	  
	  @RequestMapping("/uu.mc")
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
	  
	 
	  
	
}

