<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <style>
			@import url('https://fonts.googleapis.com/css2?family=Dongle:wght@300&display=swap');
			@import url('https://fonts.googleapis.com/css2?family=Dongle:wght@300&family=Titillium+Web&display=swap');
    	.kr-font{
    		font-family: 'Dongle', sans-serif;
    		font-size: 30px;
    	}
    	.eng-font{
    		font-family: 'Titillium Web', sans-serif;
    		font-size: 30px;
    	}
    	h3{
    		font-family: 'Titillium Web', sans-serif;
    		font-size: 30px;
    		color: white;
    	
    	}
    	img{
    		size: 30px;
    	}
    	.cbox{
			background-color: white;
			border-radius: 40px;
			margin: 30px;
		}
		.cbox-body {
			text-decoration: underline;
		}
		.tbox{
			background-color: white;
			margin: 30px;
			height: 150px;
		
			
		}
		.tbox-head {
			
		}
		.tbox-body {
			margin: auto;
			height: 200px;
			
		}
    
    </style>
	<script>
	function getData() {
		$.ajax({
			url:'gas.mc',
			success:function(data){
				console.log(data);
				$(data).each(function(idx,item){
					console.log(item.date+": date");
					console.log(item.gas+": gas");
					$('#date').html(item.date);
					$('#gas').html(item.gas);
				});
			}
		});
	};
	$(document).ready(function(){
			getData();
		setInterval(function(){
			getData();
		},5000);
	});
	
	function getData2() {
		$.ajax({
			url:'temp.mc',
			success:function(data){
				console.log(data);
				$(data).each(function(idx,item){
					console.log(item.date+": date");
					console.log(item.temp+": temp");
					$('#date2').html(item.date);
					$('#temp').html(item.temp);
				});
			}
		});
	};
	$(document).ready(function(){
			getData2();
		setInterval(function(){
			getData2();
		},5000);
	});
	
	
	function getData3() {
		$.ajax({
			url:'flame.mc',
			success:function(data){
				console.log(data);
				$(data).each(function(idx,item){
					console.log(item.date+": date");
					console.log(item.flame+": flame");
					$('#date3').html(item.date);
					$('#flame').html(item.flame);
				});
			}
		});
	};
	$(document).ready(function(){
			getData3();
		setInterval(function(){
			getData3();
		},5000);
	});
	


    </script>
    
	<div class="row">
				    <div class="col-sm-3">
					    <div class="tbox"  >
						    <h3 class="tbox-head" style="background-color: blue;"  >Flame &nbsp;&nbsp;
						    <img src="/np/img/graph.png" width="30px" height="30px" ></h3>
						      <img src="/np/img/flame.png" width="60px" height="60px" >
							    <div class="tbox-body">
							   		 <a class="kr-font">불꽃 감지 횟수</a>
									 <a id="flame"></a>
								</div>
						</div>
				    </div>
				    <div class="col-sm-3" >
					 <div class="tbox">
						<h3 class="tbox-head" style="background-color: #E39600;" >Gas &nbsp;&nbsp;
						<img src="/np/img/graph2.png" width="30px" height="30px" ></h3>
							<img src="/np/img/gas.png" width="60px" height="60px" >
							  <div class="tbox-body">
								  <a class="kr-font">기준치 초과 횟수</a>
								  <a id="gas"></a>
						 	  </div>
						</div>
					</div>
				    <div class="col-sm-3" >
				     <div class="tbox" >
						<h3 class="tbox-head" style="background-color: #03C65A;" >Temp &nbsp;&nbsp;
						<img src="/np/img/graph.png" width="30px" height="30px" ></h3>
							<img src="/np/img/temp.png" width="60px" height="60px" >
							 <div class="tbox-body">
								<a class="kr-font">기준치 초과 횟수</a>
							    <a id="temp"></a>
							 </div>
						</div>
				    </div>
				    <div class="col-sm-3">
					  <div class="tbox"  >
					    <h3 class="tbox-head" style="background-color: #ED1C24;" >Crash &nbsp;&nbsp;
					    <img src="/np/img/graph2.png" width="30px" height="30px" ></h3>
					    	<img src="/np/img/crash.png" width="60px" height="60px" >
						     <div class="tbox-body">
							 	<a class="kr-font">기준치 초과 횟수</a>
							    <a id=></a>
						    </div>
				   	  </div>
            	 </div>
            </div>