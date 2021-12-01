<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- <%@taglib prefix="spring" uri="http://www.springframework.org/tags"%> --%>
<%@page import="com.vo.CoordinateVO"%>
<%@page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
	<head>
		
	<meta charset="UTF-8">
	<title>Graphics Test</title>
	<link rel="stylesheet" href="/np/view/graphics_view/styles.css">
	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
	<!-- <script src="/np/view/graphics_view/animation.js"></script> -->
	<script type="text/javascript">
/* 		function test(name){
	          addr = $("#"+name).closest("div").find(".placeaddr").text(); 
	         
	         x = $("#"+name).closest("div").find(".x").text();
	         alert(x)
	         setMap(addr)
	         $.ajax({
	              url:"/uu.mc",
	              type:"get",
	              data:{"X":x},
	              success:function(data){
	                 
	                 $("#test").empty();
	                 $("#test").append(data)
	              }
	           })
	     } */
	     
	
	    
	   

	
	
		document.addEventListener( 'DOMContentLoaded', function(){
		/**@type {HTMLCanvasElement} */
		
		     function codi(x,y) {
			     const canvas = document.getElementById('canvas');
			 	 const ctx = canvas.getContext('2d');
			 		/* ctx.lineWidth = 10;
					ctx.lineCap = 'round';
					ctx.lineJoin = 'bevel';
					ctx.beginPath();
					ctx.moveTo(x,y);
					ctx.lineTo(x,y);
					//ctx.lineTo(100,450);
					ctx.stroke();*/
					ctx.beginPath();
					ctx.fillStyle = 'red';
					ctx.rect(x,y,10,10);
					ctx.fill();
					}
		
		
		function getData() {
	 	         $.ajax({
		            url:'uu.mc',
		            success:function(data){
		            	console.log("data뻥션들어왔니?")
		               console.log(data);
		               $(data).each(function(idx,item){
		                codi(item.X,item.Y);
		                //codi(item.Y);
		                
		                
		               });
		            }
		         });
		      };
		      
		  var data = getData();
		  $(document).ready(function(){
				setInterval(function(){
					getData();
				}, 50);
			});
			

		
			}); 
		

	
		
		
	</script>
	</head>
	<body>
	<% ArrayList<CoordinateVO> coordlist = (ArrayList<CoordinateVO>) request.getAttribute("coordlist"); %>
		<canvas id="canvas" width="800" height="500">
			does it work??
		</canvas>
		
		
		<!-- rendering logic -->
		<!-- <script src="./index.js"></script> -->
		<!-- <script src="./firstdraw.js"></script> -->
         <div>
		<%        	                       
           for(int i = 0; i < coordlist.size() ; i++) { 
           CoordinateVO coord = coordlist.get(i);
         %>
		<%-- <div class="coord_x" ><%= coord.getX() %></div> --%>
		<a id="x"></a>
		<!-- <button class="coordbtn" type="button" id="coordbtn1" onclick="test('coordbtn')">개객기</button> -->
		</div>
		<%
           }
		%>
		
	</body>
</html>