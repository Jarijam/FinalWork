package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
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
	    public ModelAndView recentdata() {
	    	ModelAndView mv = new ModelAndView();
	    	mv.addObject("center","recentdata");
	    	mv.setViewName("main");
	    	return mv;
	    }
	    
	}
	
	  
	 
	  
	

