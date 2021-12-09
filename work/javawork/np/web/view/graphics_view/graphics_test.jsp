<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<link rel="stylesheet" href="/np/view/graphics_view/styles.css">	
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
	<script type="text/javascript">
		document.addEventListener( 'DOMContentLoaded', function(){
		/**@type {HTMLCanvasElement} */		
		     function codi(x,y,x1,y1) {
			     const canvas = document.getElementById('canvas');
			 	 const ctx = canvas.getContext('2d');
			 	    ctx.beginPath();
					ctx.fillStyle = 'rgba(251,192,45,0.5)';
					if (x<100) {
					ctx.rect(x,y,20,20);						
					}
					ctx.fill();				
			 	 	ctx.lineWidth = 1;
					ctx.lineCap = 'round';
					ctx.lineJoin = 'bevel';
					ctx.beginPath();
					ctx.moveTo(x,y);
					ctx.lineTo(x1,y1);
					ctx.stroke();					
					}		
		function getData() {
	 	         $.ajax({
		            url:'crddata.mc',
		            success:function(data){
		            	console.log("data뻥션들어왔니?")
		               console.log(data);	        
		               for (var i=0; i < data.length-1; i++) {
		            	   codi(data[i].X,data[i].Y,data[i+1].X,data[i+1].Y);
		               }
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
		<canvas id="canvas" width="500" height="500">
			does it work??
		</canvas>	
		
