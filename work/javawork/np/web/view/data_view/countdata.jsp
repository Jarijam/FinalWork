<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <style>
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
			border-radius: 40px;
			margin: 30px;
			height: 300px;
			
			
		}
		.tbox-body {
			text-decoration: underline;
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
		setInterval(function(){
			getData2();
		},5000);
	});
	
	


    </script>
    
	<div class="row">
				    <div class="col-sm-4">
					    <div class="tbox" style="background: #84A4E5;" >
					    <h3 class="tbox-body" >test1</h3>
						</div>
				    </div>
				    <div class="col-sm-4" >
					    <div class="tbox" style="background: #E5722C;">
						 <h3 class="tbox-body">가스 test</h3>
						 <a>12월 </a><span id = "date" ></span><span>일</span><br/>
						 <a>금일 기준치 초과 횟수 </a><span id = "gas" ></span><br/>
						</div>
					</div>
				    <div class="col-sm-4" >
				    	 <div class="tbox" style="background: #24CE96;">
						 <h3 class="tbox-body">온도 test</h3>
						 <a>12월 </a><span id = "date2" ></span><span>일</span><br/>
						 <a>금일 기준치 초과 횟수 </a><span id = "temp" ></span><br/>
						</div>
				    </div>
  	</div>