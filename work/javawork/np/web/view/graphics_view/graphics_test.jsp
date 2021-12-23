<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<link rel="stylesheet" href="/np/view/graphics_view/styles.css">	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
	<script type="text/javascript">
		document.addEventListener( 'DOMContentLoaded', function(){
		/** @type { HTMLCanvasElement } */		
		     function codi(data) {
			     const canvas = document.getElementById('canvas');
			 	 const ctx = canvas.getContext('2d');
			 	 
			 
			 	 
			 	
				
			 	 		//구역표시 이전 초기화.
			 	         ctx.beginPath();
			 	   		ctx.fillStyle = '#eee';
			 	   		ctx.rect(0,0,600,600);
			 	  	 	ctx.fill();
			 	  		ctx.closePath();
			 	  		//새로운 구역 값 들어올 시 영역 표시
				 	    ctx.beginPath();
						ctx.fillStyle = '#FB0606';
						ctx.rect(data[0].X,data[0].Y,300,200);
						if(data[0].X == 0 & data[0].Y == 0) {
							$('#location').html('1구역')
						} else if(data[0].X == 300 & data[0].Y == 0) {
							$('#location').html('2구역')
						} else if(data[0].X == 0 & data[0].Y == 200) {
							$('#location').html('3구역')
						} else if(data[0].X == 300 & data[0].Y == 200) {
							$('#location').html('4구역')
						} else if(data[0].X == 0 & data[0].Y == 400) {
							$('#location').html('5구역')
						} else if(data[0].X == 300 & data[0].Y == 400) {
							$('#location').html('6구역')
						} 
						ctx.fill();						
						ctx.closePath();
			 	    	//영역 구별 수월함을 위한 직선 그리기.
				 	    ctx.beginPath();
				 	    ctx.moveTo(300,0);
				 	    ctx.lineTo(300,600);
				 	   	ctx.moveTo(0,200);
				 	    ctx.lineTo(600,200);
				 	   	ctx.moveTo(0,400);
				 	    ctx.lineTo(600,400);
				 	    ctx.lineWidth = 3;
				 	    ctx.stroke();
				 	    ctx.closePath();
				 	    img.src = url;
						
											
			 	    
			 	    
						
					}		
		function getData() {
	 	         $.ajax({
		            url:'crddata.mc',
		            success:function(data){
		         
		            	codi(data);
		            }
		         });
		      };
		      function getSensor() {
	               $.ajax({
	                  url:'recentsensor.mc',
	                  success:function(sensor){
	                   
	                     $('#areatemp').html(sensor.temp+"°C");
	                     $('#areagas').html(sensor.gas+"ppm");
	                     if( sensor.flame == 1){
	                    	 $('#areaflame').html("WARNING!!!!!")
	                     }else if(sensor.flame == 0){
	                    	 $('#areaflame').html("SAFE")
	                     }
	                  }
	               });
	            };
		      var sensor = getSensor();
			  var data = getData();
			  $(document).ready(function(){
					 setInterval(function(){
					 getData();
					 getSensor();
					}, 1000);
				});		
			}); 
		
		//=============================time========================
		 function time(){
			  var time= new Date(); //시간받기위해서 new date
			      document.getElementById("now").innerHTML="2021년 12월 17일 "+time.getHours()+"시 "+time.getMinutes()+"분 "+time.getSeconds()+"초";
			     setInterval("time()",1000);     //1초 지난후 time()실행
			  }
		     
	</script>
	<style>
	
		.areahead{
			text-align: center;
			border-radius: 20px;
			background-color: #FFCD42;
			margin: 60px auto;
			width: 400px;
			
		}
		#areatemp{
		font-family: 'Titillium Web', sans-serif;
		font-size: 30px;
		height: 100px;
		border: 2px solid red;
		border-radius: 10px;
		border-color: #D1D4D8;
		background-color: #F5DCAA;
		padding-left: 40px;
		padding-right: 40px;
		margin-left: 5px;
		}
		
		#areagas{
		font-family: 'Titillium Web', sans-serif;
		font-size: 30px;
		height: 100px;
		border: 2px solid red;
		border-radius: 10px;
		border-color: #D1D4D8;
		background-color: #F5DCAA;
		margin-left: 150px;
		padding-left: 40px;
		padding-right: 40px;
		}
		
		#areaflame{
		font-family: 'Titillium Web', sans-serif;
		font-size: 30px;
		height: 100px;
		border: 2px solid red;
		border-radius: 10px;
		border-color: #D1D4D8;
		background-color: #F5DCAA;
		margin-left: 120px;
		padding-left: 40px;
		padding-right: 40px;
		}
		
		.testbox{
		  padding-left: 30px;
		  margin-left: 130px;
		 /* text-align: center;  */
		
		}
		
		.list-group{
		/* margin: 0 auto; */
		margin-left: 200px;
		font-size: 30px;
		font-family: 'Titillium Web', sans-serif; 'Dongle', sans-serif;
		width: 600px; 
		}
		
		#now{
		text-align: center;
		}
		
		#location {
		height: 100px;
		border: 2px solid red;
		border-radius: 10px;
		border-color: #D1D4D8;
		background-color: #E0E2E5;
		margin-left: 100px;
		padding-left: 40px;
		padding-right: 40px;
		}
		
		#t{
		height: 100px;
		border: 2px solid red;
		border-radius: 10px;
		border-color: #D1D4D8;
		background-color: #E0E2E5;
		margin-left: 60px;
		padding-left: 40px;
		padding-right: 40px;
		}
		
		p {
		text-align: center;
		}
		
		img{
		margin-left: 50px;
		}
		 #test001{
			float: left;
		} 
		 .test002{
			/* float: left; */
		}  
		
	</style>
	<div >
		<h1 class = "areahead">Current Status</h1>
	</div>		
	<div >
		<div>
			<div>
				<div class= "testbox" >
				   <!--  <h2  id="now"></h2> -->
				    <!-- <h2>Location : <span id="location"></span></h2> -->
					<!-- <h2>Admin : hello</h2>
					<h2>Machine : FITO-01</h2>
					<h2>Connection : ON</h2> -->
					<div id="test001">
					  <ul class="list-group" id="myList" >
					    <li class="list-group-item" id="now"></li>
					    <li class="list-group-item"><img src="/np/img/square.png">&nbsp;Location : <span id="location"></span></li>
					    <li class="list-group-item"><img src="/np/img/bluesquare.png">&nbsp;Admin : &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id="t">Park</span></li>
					    <li class="list-group-item"><img src="/np/img/redsquare.png">&nbsp;Machine :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id="t">FITO-01</span></li>
					    <li class="list-group-item"><img src="/np/img/yellowsquare.png">&nbsp;Connection : &nbsp;<span id="t">ON</span></li>
					    <li class="list-group-item"><p class="t2">Sensor</p></li>
					    <li class="list-group-item"><img src="/np/img/purplesquare.png">&nbsp;Temperature :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span id="areatemp"></span></li>
					    <li class="list-group-item"><img src="/np/img/limesquare.png">&nbsp;Gas : &nbsp;<span id="areagas"></span></li>
					    <li class="list-group-item"><img src="/np/img/pinksquare.png">&nbsp;Flame : &nbsp;<span id="areaflame"></span></li>					    
 				  	</ul>
 				  	</div>
					<div id = "canvas_body" class="test002">
						<canvas id="canvas" width="600" height= "600"  ></canvas>
							<a href = "crddelte.mc" >DB삭제</a>
					</div>								
 				  	
				</div>
			</div>
		</div>

		
	</div>
	


	