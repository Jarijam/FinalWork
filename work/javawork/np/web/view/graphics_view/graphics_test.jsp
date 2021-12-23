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
			 	    /* console.log(data); */
					/* ctx.moveTo(x,y); */
					/* ctx.fillStyle = 'rgba(251,192,45,0.5)'; */
					/* ctx.rect(data[0].X,data[0].Y,100,100); */
			 	    /* console.log(data[0].X,data[0].Y); */
			 	 	
				
						console.log(data.length);
						console.log(data[0].X,data[0].Y);
				 	    ctx.beginPath();
						ctx.fillStyle = '#03A87C';
						ctx.rect(data[0].X,data[0].Y,300,200);
						ctx.fill();
						ctx.closePath();
					
			 	    
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
					/* ctx.beginPath();
					ctx.fillStyle= '#03A87C';
					ctx.moveTo(0,0);
					ctx.lineTo(200,100);
					ctx.lineTo(200,200);
					ctx.lineTo(100,200);
					ctx.lineTo(100,100);
					ctx.fill();  */
					
					/* ctx.rect(300,300,50,50); */
					/* ctx.beginPath();
					ctx.rect(300,300,20,20); */
					/* if (x<100) {
					ctx.rect(x,y,20,20);						
					} */
					/* ctx.fill();	 */			
			 	 	/* ctx.lineWidth = 1;
					ctx.lineCap = 'round';
					ctx.lineJoin = 'bevel'; */
					/* ctx.lineTo(x1,y1); */
					/* ctx.stroke();	 */				
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
			
			</canvas>
		</div>
		<a href = "crddelte.mc">DB삭제1</a>
		<div id="test"></div>

</body>	