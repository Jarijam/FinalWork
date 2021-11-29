package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.frame.Service;
import com.vo.DataVO;
import com.vo.LedVO;

@Controller
public class MainController {
	MyMqtt_Pub_client client;
	
	
	private Logger data_log = Logger.getLogger("data");
	
	
	@Resource(name="datavice")
	Service<String, DataVO> service;
	
	
	public MainController() {
		client = new MyMqtt_Pub_client();
		
	}
	
	
	@RequestMapping("/main.mc")
	public ModelAndView main(HttpServletRequest request) {
	      String LED = request.getParameter("LED");
	      System.out.println("LED Status: "+LED);
	      ModelAndView mv = new ModelAndView();
	      HttpSession session = request.getSession();
	      session.setAttribute("status", LED);
	      mv.setViewName("led_check");
	      client.send("led","led_"+LED);
	      if(LED==null) {
	    	 return mv;
	     }else if(LED.equals("on")) {
				try {
					FcmUtil.sendServer(LED);
				} catch (Exception e) {
					e.printStackTrace();				
					}
			} 
	      return mv;
	}
	
	@RequestMapping("/data.mc")
	@ResponseBody
	public void data(HttpServletRequest request) throws IOException{
		String btn = request.getParameter("btn");
		System.out.println("Button Status : "+btn);
		String temp = request.getParameter("temp");
		double f_temp = Double.parseDouble(temp);
		System.out.println("Temp Status : "+temp+"ºC");
		data_log.debug(f_temp+" : "+btn);
		try {
			DataVO datainfo = new DataVO(btn,temp);
			service.modify(datainfo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(btn!=null && temp!=null) {
			if(btn.equals(1+"")) { 
				try {
					FcmUtil_btn.sendServer(btn);
					} catch (Exception e) {
						e.printStackTrace(); 
						}
					}else if(f_temp >= 25) { 
						try {
							FcmUtil_temp.sendServer(temp);
							} catch (Exception e) {
								e.printStackTrace(); 
								}
							}
			}
	}
	@RequestMapping("/dataajax.mc")
	@ResponseBody
	public void uu(HttpServletResponse response) throws IOException {
		response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		JSONArray ja = new JSONArray();
		try {
		ArrayList<DataVO> datalist = service.get();
		DataVO datainfo = datalist.get(0);
		
			JSONObject jo = new JSONObject();
			jo.put("btn", datainfo.getBtn());
			jo.put("temp", datainfo.getTemp());
			ja.add(jo);
		}catch (Exception e) {
			e.printStackTrace();
		}
		out.print(ja.toJSONString());
		out.close();
		
	}
}

