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
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.frame.Service;
import com.vo.CoordinateVO;
import com.vo.UserVO;

@Controller
public class MainController {
	
		private Logger data_log = 
				Logger.getLogger("data");
		
		MyMqtt_Pub_client client;
		
		 @Resource(name="cdservice")
		   Service<String, CoordinateVO> cdservice;
		   @Resource(name="userservice")
		   Service<String, UserVO> userservice;
		
		public MainController() {
			client = new MyMqtt_Pub_client();
			
		}
		
		@RequestMapping("/uadd.mc")
		public ModelAndView add(ModelAndView mv) {
			mv.setViewName("register");
			return mv;
		}
		
		
		@RequestMapping("/uaddimpl.mc")
		public String addimpl(UserVO user) {
			try {
				userservice.register(user);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "redirect:main.mc";
		}
		
		@RequestMapping("/update.mc")
		public ModelAndView update(ModelAndView mv,String id) {
			UserVO dbuser = null;
			try {
				System.out.println("update모델엔뷰 try왔니?");
				
				dbuser = userservice.get(id);
				
				mv.addObject("uuser", dbuser);
				mv.addObject("center","update");
				mv.setViewName("main");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return mv;
		}
		@RequestMapping("/uupdateimpl.mc")
		public String updateimpl(UserVO user) {
			try {
				userservice.modify(user);
			} catch (Exception e) {
				e.printStackTrace();
			}
//			String id = user.getId();
			return "redirect:main.mc";
		}
		
		@RequestMapping("/udel.mc")
		public String delete(String id) {
			try {
				userservice.remove(id);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "redirect:ulist.mc";
		}
		
		@RequestMapping("/login.mc")
		public ModelAndView login() {
			ModelAndView mv = new ModelAndView();
			mv.addObject("center", "login");
			mv.setViewName("login");
			return mv;
		}
	
	  
	
	   @RequestMapping("/main.mc")
	   public ModelAndView main() {
			ModelAndView mv = new ModelAndView();
			mv.setViewName("login");
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
					server = new Socket("192.168.0.29", 12346);
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
	    
		@RequestMapping("/loginimpl.mc")
		public ModelAndView loginimpl(
				HttpServletRequest request) {
			String id = request.getParameter("id");
			String pwd = request.getParameter("pwd");
			ModelAndView mv = new ModelAndView();
			
			UserVO dbuser = null;
			try {
				dbuser = userservice.get(id);
				if(dbuser.getPwd().equals(pwd)) {
					mv.addObject("center", "recentdata");
					HttpSession session 
					= request.getSession();
					session.setAttribute("loginid", id);
				}else {
					mv.addObject("center", "fail");
				}
			} catch (Exception e) {
				mv.addObject("center", "fail");
				e.printStackTrace();
			}
			
			
			mv.setViewName("main");
			return mv;
		}
		@RequestMapping("/test01.mc")
		public ModelAndView test01() {
			ModelAndView mv = new ModelAndView();  
			mv.addObject("center","test_view/testview");
	    	mv.setViewName("main");
			return mv;
			}
		@RequestMapping("/test02.mc")
		public ModelAndView test02() {
			ModelAndView mv = new ModelAndView();  		
			mv.addObject("center","test_view/testview2");
	    	mv.setViewName("main");
			return mv;
			}    
	}
	
	  
	 
	  
	

