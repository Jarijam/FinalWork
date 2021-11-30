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
	     
	     function getData() {
	         $.ajax({
	            url:'uu.mc',
	            success:function(data){
	            	console.log("data뻥션들어왔니?")
	               console.log(data);
	               $(data).each(function(idx,item){
	                  console.log(item.X+": XVAl");
	                  $('#X').html(item.X);
	               });
	            }
	         });
	               return data;
	      };
	    /*   $(document).ready(function(){
	         setInterval(function(){
	            getData();
	         }, 1000);
	      });  */

		
		var data = getData();
		console.log(data);
	
	
		document.addEventListener( 'DOMContentLoaded', function(){
		/**@type {HTMLCanvasElement} */
		
		const canvas = document.getElementById('canvas');
		const ctx = canvas.getContext('2d');

		
		// fill style
		ctx.fillStyle = 'red';
		// starting point
		let x = 0;
		let y = 200;
		let z = data;
		console.log(z+"값넘어가요?");

			
		//create a draw function to draw each frame
		function draw() {
			//if 'x' is greater than 500px, stop animation
			if(x>500) {
				return;
			}
			if(y>500) {
				return;
			}
			/* ctx.clearRect(0,0,canvas.width,canvas.height); */
			ctx.fillRect(x,y,50,50);
			//increase 'x' value by '5px'
			x += 10;
			/* y += 10; */
			//register 'draw()' call before next paint
			requestAnimationFrame(draw);
		}

		requestAnimationFrame(draw);
		
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