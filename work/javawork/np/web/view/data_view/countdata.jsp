<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
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
			height: 300px;
		
			
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
	
	var imgArray = new Array();
	imgArray[0] = "warning.png";
	imgArray[1] = "default.png";
	
	
	function getData3() {
		
		var imgArray = new Array();
		imgArray[0] = "warning.png";
		imgArray[1] = "default.png";
		
		var defalutimg = document.getElementById('warning');
		
		$.ajax({
			url:'flame.mc',
			success:function(data){
				console.log(data);
				
				$(data).each(function(idx,item){
					console.log(item.date+": date");
					console.log(item.flame+": flame");
					if( item.flame >= 65){
						$('#flame').html(item.flame+"<br/><img class='warning' src='/np/img/warning.png'>");	
					}else if ( item.flame < 65){
						$('#flame').html(item.flame+"<br/><img class='default' src='/np/img/default.png'>");
					}
					
					
				/* 	if ( item.flame > 30 ){ 
						 $('#flame').document.write("<img src='/np/img/warning.png'>");
						
					}else if ( item.flame < 30){
						 $('#flame').document.write("<img src='/np/img/default.png'>");
					} */
					
					
					
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
	// ================================== TEMP GAGE ====================================
		function display4(d){
			var gaugeOptions = {
				    chart: {
				        type: 'solidgauge'
				    },

				    title: null,

				    pane: {
				        center: ['50%', '85%'],
				        size: '140%',
				        startAngle: -90,
				        endAngle: 90,
				        background: {
				            backgroundColor:
				                Highcharts.defaultOptions.legend.backgroundColor || '#EEE',
				            innerRadius: '60%',
				            outerRadius: '100%',
				            shape: 'arc'
				        }
				    },

				    exporting: {
				        enabled: false
				    },

				    tooltip: {
				        enabled: false
				    },

				    // the value axis
				    yAxis: {
				        stops: [
				            [0.1, '#55BF3B'], // green
				            [0.5, '#DDDF0D'], // yellow
				            [0.9, '#DF5353'] // red
				        ],
				        lineWidth: 0,
				        tickWidth: 0,
				        minorTickInterval: null,
				        tickAmount: 2,
				        title: {
				            y: -70
				        },
				        labels: {
				            y: 16
				        }
				    },

				    plotOptions: {
				        solidgauge: {
				            dataLabels: {
				                y: 5,
				                borderWidth: 0,
				                useHTML: true
				            }
				        }
				    }
				};

				// The speed gauge
				var chartSpeed = Highcharts.chart('container-speed', Highcharts.merge(gaugeOptions, {
				    yAxis: {
				        min: 0,
				        max: 50,
				        title: {
				            text: 'Temperature'
				        }
				    },

				    credits: {
				        enabled: false
				    },

				    series: [{
				        name: 'Speed',
				        data: d.temp,
				        dataLabels: {
				            format:
				                '<div style="text-align:center">' +
				                '<span style="font-size:25px">{y}</span><br/>' +
				                '<span style="font-size:12px;opacity:0.4">°C</span>' +
				                '</div>'
				        },
				        tooltip: {
				            valueSuffix: ' °C'
				        }
				    }]

				}));

	}
			
		
		function getData4(){
			$.ajax({
				url:'tempgage.mc',
				success:function(d){
					display4(d);
				}
			});
		};
		
		
		$(document).ready(function(){
			getData4();
		setInterval(function(){
			getData4();
		},5000);
	});
		// ================================== GAS GAGE ====================================
		function display5(d){
			var gaugeOptions = {
				    chart: {
				        type: 'solidgauge'
				    },

				    title: null,

				    pane: {
				        center: ['50%', '85%'],
				        size: '140%',
				        startAngle: -90,
				        endAngle: 90,
				        background: {
				            backgroundColor:
				                Highcharts.defaultOptions.legend.backgroundColor || '#EEE',
				            innerRadius: '60%',
				            outerRadius: '100%',
				            shape: 'arc'
				        }
				    },

				    exporting: {
				        enabled: false
				    },

				    tooltip: {
				        enabled: false
				    },

				    // the value axis
				    yAxis: {
				        stops: [
				            [0.1, '#55BF3B'], // green
				            [0.5, '#DDDF0D'], // yellow
				            [0.9, '#DF5353'] // red
				        ],
				        lineWidth: 0,
				        tickWidth: 0,
				        minorTickInterval: null,
				        tickAmount: 2,
				        title: {
				            y: -70
				        },
				        labels: {
				            y: 16
				        }
				    },

				    plotOptions: {
				        solidgauge: {
				            dataLabels: {
				                y: 5,
				                borderWidth: 0,
				                useHTML: true
				            }
				        }
				    }
				};

				// The speed gauge
				var chartSpeed = Highcharts.chart('container-gas', Highcharts.merge(gaugeOptions, {
				    yAxis: {
				        min: 0,
				        max: 800,
				        title: {
				            text: 'Gas'
				        }
				    },

				    credits: {
				        enabled: false
				    },

				    series: [{
				        name: 'Speed',
				        data: d.gas,
				        dataLabels: {
				            format:
				                '<div style="text-align:center">' +
				                '<span style="font-size:25px">{y}</span><br/>' +
				                '<span style="font-size:12px;opacity:0.4">ppm</span>' +
				                '</div>'
				        },
				        tooltip: {
				            valueSuffix: ' ppm'
				        }
				    }]

				}));

	}
			
		
		function getData5(){
			$.ajax({
				url:'gasgage.mc',
				success:function(d){
					display5(d);
				}
			});
		};
		
		
		$(document).ready(function(){
			getData5();
		setInterval(function(){
			getData5();
		},5000);
	});
	
    </script>
    
	<div class="row">
				    <div class="col-sm-3">
					    <div class="tbox"  >
						    <h3 class="tbox-head" style="background-color: blue;"  >Flame &nbsp;&nbsp;
						    <img src="/np/img/graph.png" width="30px" height="30px" ></h3>
						      <!-- <img src="/np/img/warning.png" width="100px" height="100px"  id="warning"><br/>
						      <img src="/np/img/default.png" width="100px" height="100px" id="default"> -->
							    <div class="tbox-body">
							   		 <a class="kr-font">기준치 초과 횟수</a>
									 <a id="flame"></a>
								</div>
						</div>
				    </div>
			   <div class="col-sm-3" >
					 <div class="tbox">
						<h3 class="tbox-head" style="background-color: #E39600;" >Gas &nbsp;&nbsp;
						<img src="/np/img/graph2.png" width="30px" height="30px" ></h3>
							<div class="tbox-body">
							  <a class="kr-font">기준치 초과 횟수</a>
							  <a id="gas"></a>
						    <div class="highcharts-figure">
							    <div id="container-gas" class="chart-container"></div>
							</div>
						 </div>
					</div>
				</div>
				    <div class="col-sm-3" >
				     <div class="tbox" >
						<h3 class="tbox-head" style="background-color: #03C65A;" >Temp &nbsp;&nbsp;
						<img src="/np/img/temp.png" width="30px" height="30px" ></h3>
						   <div class="tbox-body">
							  <a class="kr-font">기준치 초과 횟수</a>
							  <a id="temp"></a>
						   <div class="highcharts-figure">
							    <div id="container-speed" class="chart-container"></div>
							</div>
					    </div>
					</div>
			    </div>
			    
				    <div class="col-sm-3">
					  <div class="tbox"  >
					    <h3 class="tbox-head" style="background-color: #ED1C24;" >Crash &nbsp;&nbsp;
					    <img src="/np/img/graph2.png" width="30px" height="30px" ></h3>
						     <div class="tbox-body">
							 	<a class="kr-font">기준치 초과 횟수</a><br/>
							 	<img src="/np/img/crash.png" width="60px" height="60px" >
						    </div>
					    	
				   	  </div>
            	 </div>
            </div>