package com.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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
	
		private Logger data_log = 
				Logger.getLogger("data");
		
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
		
	
	
	   @RequestMapping("/graphics.mc") 
	   public ModelAndView coords() throws Exception { 
		  	ModelAndView mv =  new ModelAndView(); 
	      	List<CoordinateVO> coordlist = cdservice.get(); 
		    mv.addObject("coordlist", coordlist);
		    mv.addObject("center","graphics_view/graphics_test");
		    mv.setViewName("main");
	     
	     	return mv; 
	   
	   		}
	    
	    @RequestMapping("/rcharttemppage.mc")
		public ModelAndView rchart_temp() {
			ModelAndView mv = new ModelAndView();
			mv.addObject("rchart", "rcharttemp"); 
			mv.setViewName("chart_view/recentdata"); 
			return mv;
			}
	    
	    @RequestMapping("/rchartgaspage.mc")
		public ModelAndView rchart_gas() {
			ModelAndView mv = new ModelAndView();
			mv.addObject("rchart", "rchartgas"); 
			mv.setViewName("chart_view/recentdata"); 
			return mv;
			}
	    
	    @RequestMapping("/rchartpage_tot.mc")
		public ModelAndView rchart_tot() {
			ModelAndView mv = new ModelAndView();
			mv.addObject("rchart", "rcharttot"); 
			mv.setViewName("chart_view/recentdata"); 
			return mv;
			}
	    
	    @RequestMapping("/recentdata.mc")
	    public ModelAndView recentdata(HttpServletRequest request) {
	    	
	    	String MODE = request.getParameter("MODE");
	    		
		      System.out.println("MODE Status: "+MODE);
	    	ModelAndView mv = new ModelAndView();
	    	mv.addObject("center","recentdata");
	    	mv.setViewName("main");
	    	
	    		Socket server = null;
				BufferedReader in = null;
				PrintWriter out = null;
				try {
					server = new Socket("192.168.0.158", 12346);
					System.out.println("서버에 접속 성공");
					//네트워크를 통해서 입출력을 하기 위한 IO스트림객체를 생성
					in = new BufferedReader(new InputStreamReader(server.getInputStream()));
					out = new PrintWriter(server.getOutputStream(),true);
					
					//=====================<<< 통신 시작>>>====================
					//1. 클라이언트 <- 서버
//					String msg = in.readLine();
//					System.out.println("서버가 보내 온 메시지 =>>>"+msg);
					
					//2. 클라이언트 -> 서버
					out.println(MODE);
					//out.flush();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
	    	return mv;
	    }
	    
	}
	
	  
	 
	  
	

