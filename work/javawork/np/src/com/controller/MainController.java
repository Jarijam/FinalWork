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
	     mv.setViewName("graphics_view/graphics_test");
	     
	     return mv; 
	   
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
	    @RequestMapping("/data.mc")
		@ResponseBody
		public void data(HttpServletRequest request) throws Exception {
			String btn = request.getParameter("btn");
			String gas = request.getParameter("gas");
			String flame = request.getParameter("flame");
			String dis = request.getParameter("dis");
			String temp = request.getParameter("temp");
			
			
//			System.out.println(btn+","+gas+","+flame+","+dis);
			
			data_log.debug(btn+","+gas+","+flame+","+dis+","+temp);

				if(btn.equals(1+"")) {
					cdservice.remove(btn);
				}
		}
	    
	    
	    @RequestMapping("/rcharttemppage.mc")
		public ModelAndView rchart_temp() {
			ModelAndView mv = new ModelAndView();
			//mv.addObject("center2", "rcharttemp"); 
			mv.setViewName("chart_view/rcharttemp"); 
			return mv;
			}
	    
	    @RequestMapping("/rchartgaspage.mc")
		public ModelAndView rchart_gas() {
			ModelAndView mv = new ModelAndView();
			//mv.addObject("center", "rchartgas"); 
			mv.setViewName("chart_view/rchartgas"); 
			return mv;
			}
	    
	    @RequestMapping("/rchartpage_tot.mc")
		public ModelAndView rchart_tot() {
			ModelAndView mv = new ModelAndView();
			//mv.addObject("center", "rchart"); 
			mv.setViewName("chart_view/rcharttot"); 
			return mv;
			}
	}
	
	  
	 
	  
	


