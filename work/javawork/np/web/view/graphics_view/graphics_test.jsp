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
						ctx.fillStyle = '#03A87C';
						ctx.rect(data[0].X,data[0].Y,300,200);
						if(data[0].X == 0 & data[0].Y == 0) {
							$('#location').html('정원이형집')
						} else if(data[0].X == 300 & data[0].Y == 0) {
							$('#location').html('해규집')
						} else if(data[0].X == 0 & data[0].Y == 200) {
							$('#location').html('승훈이집')
						} else if(data[0].X == 300 & data[0].Y == 200) {
							$('#location').html('준범이집')
						} else if(data[0].X == 0 & data[0].Y == 400) {
							$('#location').html('강남역')
						} else if(data[0].X == 300 & data[0].Y == 400) {
							$('#location').html('멀티캠퍼스')
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
					
						
											
			 	    
			 	    
						
					}		
		function getData() {
	 	         $.ajax({
		            url:'crddata.mc',
		            success:function(data){
		           /* for (var i=0; i < data.length; i++) {
		            	   codi(data[i].X,data[i].Y);
		            	   console.log(data[i]);
		               } */  
		            	/* codi(data[1].X,data[1].Y); */
		            	codi(data);
		               
		            }
		         });
		      };
		      function getSensor() {
	               $.ajax({
	                  url:'recentsensor.mc',
	                  success:function(sensor){
	                   
	                     $('#areatemp').html(sensor.temp);
	                     $('#areagas').html(sensor.gas);
	                  }
	               });
	            };
		      var sensor = getSensor();
			  var data = getData();
			  $(document).ready(function(){
					 setInterval(function(){
					 getData();
					 getSensor();
					}, 500);
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
		
		}
		
		#areagas{
		font-family: 'Titillium Web', sans-serif;
		font-size: 30px;
		}
		.testbox{
		text-align: center;
		}
		
	</style>
	<div >
		<h1 class = "areahead">Area info</h1>
	</div>
	
	
	<div >
		<div class="col-sm-6" style="float: left; border: 2px solid red; height: 600px;">
			<div>
				<div class= "testbox" >
				    <h2  id="now"></h2>
				    <h2>Location : <span id="location"></span></h2>
					<h2>Admin : hello</h2>
					<h2>Machine : FITO-01</h2>
					<h2>Connection : ON</h2>
				</div>
				<div class = "testbox" style=" border: 2px solid red;">
					<h2>현재온도 <span id="areatemp"></span></h2><br/>
					
					<h2>현재가스 <span id="areagas"></span></h2>
					
				</div>
			</div>
		</div>
		<div >
			<div id = "canvas_body" style = "margin-left : 200px;">
				<canvas id="canvas" width="600" height= "600"  ></canvas>
					<a href = "crddelte.mc" >DB삭제</a>
				
				
			</div>
			
			
		</div>
		
		
	</div>
	

	