<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
    <style>
@import
	url('https://fonts.googleapis.com/css2?family=Dongle:wght@300&display=swap')
	;

@import
	url('https://fonts.googleapis.com/css2?family=Dongle:wght@300&family=Titillium+Web&display=swap')
	;

.kr-font {
	font-family: 'Dongle', sans-serif;
	font-size: 30px;
}

.eng-font {
	font-family: 'Titillium Web', sans-serif;
	font-size: 30px;
}



img {
	/* size: 30px; */
}

.cbox {
	background-color: white;
	border-radius: 40px;
	margin: 30px;
}

.cbox-body {
	text-decoration: underline;
	text-align: center;
}

.tbox {
	background-color: white;
	margin: 30px;
	height: 190px;
}

.tbox-head {
	text-align: center;
	margin-top: 30px;
}

.tbox-body {
	margin: auto;
	height: 300px;
	text-align: center;
}

.wrap {
	position: absolute;
	left: 0;
	bottom: 40px;
	width: 288px;
	height: 132px;
	margin-left: -144px;
	text-align: left;
	overflow: hidden;
	font-size: 12px;
	font-family: 'Malgun Gothic', dotum, '돋움', sans-serif;
	line-height: 1.5;
}

.wrap * {
	padding: 0;
	margin: 0;
}

.wrap .info {
	width: 286px;
	height: 120px;
	border-radius: 5px;
	border-bottom: 2px solid #ccc;
	border-right: 1px solid #ccc;
	overflow: hidden;
	background: #fff;
}

.wrap .info:nth-child(1) {
	border: 0;
	box-shadow: 0px 1px 2px #888;
}

.info .title {
	padding: 5px 0 0 10px;
	height: 30px;
	background: #eee;
	border-bottom: 1px solid #ddd;
	font-size: 18px;
	font-weight: bold;
}

.info .close {
	position: absolute;
	top: 10px;
	right: 10px;
	color: #888;
	width: 17px;
	height: 17px;
	background:
		url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/overlay_close.png');
}

.info .close:hover {
	cursor: pointer;
}

.info .body {
	position: relative;
	overflow: hidden;
}

.info .desc {
	position: relative;
	margin: 13px 0 0 90px;
	height: 75px;
}

.desc .ellipsis {
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}

.desc .jibun {
	font-size: 11px;
	color: #888;
	margin-top: -2px;
}

.info .img {
	position: absolute;
	top: 6px;
	left: 5px;
	width: 73px;
	height: 71px;
	border: 1px solid #ddd;
	color: #888;
	overflow: hidden;
}

.info:after {
	content: '';
	position: absolute;
	margin-left: -12px;
	left: 50%;
	bottom: 0;
	width: 22px;
	height: 12px;
	background:
		url('https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png')
}

.info .link {
	color: #5085BB;
}
</style>
	<script>
	function getData() {
		$.ajax({
			url:'gas.mc',
			success:function(data){
				
				$(data).each(function(idx,item){
					
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
				
				$(data).each(function(idx,item){
					
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
				
				
				$(data).each(function(idx,item){
					
					if( item.flame >= 65){
						/* $('#flame').html(item.flame+"<br/><img class='warning' src='/np/img/warning.png'>"); */
						$('#flame').html("<br/><img class='warning' src='/np/img/warning.png'>");
					}else if ( item.flame < 65){
						/* $('#flame').html(item.flame+"<br/><img class='default' src='/np/img/default.png'>"); */
						$('#flame').html("<br/><img class='default' src='/np/img/security.png'>");
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
	
		
// =================================== kakao map =====================================
	
	
    </script>
    
	 <div class="row">
	    <div class="col-sm-6">
					  <div class="tbox"  >
						     <div class="tbox-body">
							 	<div id="map" style="width:100%;height:200px;"></div>
						    </div>
					    	
				   	  </div>
            	 </div>
				    <div class="col-sm-2">
					    <div class="tbox"  >
						    <h5 class="tbox-head" style="background-color: blue;"  >Flame Detection&nbsp;&nbsp;
						    <!-- <img src="/np/img/graph.png" width="30px" height="30px" > --></h5>
						     <!--  <img src="/np/img/warning.png" width="100px" height="100px"  id="warning"><br/>
						      <img src="/np/img/default.png" width="100px" height="100px" id="default"> -->
							    <div class="tbox-body">
							   		<!-- <  <a class="kr-font">기준치 초과 횟수</a> -->
									 <a id="flame"></a> 
								</div>
						</div>
				    </div>
			   <div class="col-sm-2" >
				<!-- 	 <div class="tbox">
						<h5 class="tbox-head" style="background-color: #E39600;" >Gas Info&nbsp;&nbsp;
						 <img src="/np/img/graph2.png" width="30px" height="30px" ></h5>
							<div class="tbox-body">
							  <a class="kr-font">기준치 초과 횟수</a>
							  <a id="gas"></a>
						    <div class="highcharts-figure">
							    <div id="container-gas" class="chart-container"></div>
							</div>
						 </div>
					</div> -->
				</div>
				<!--     <div class="col-sm-2" >
				     <div class="tbox" >
						<h5 class="tbox-head" style="background-color: #03C65A;" >Temp Info &nbsp;&nbsp;
						 <img src="/np/img/graph.png" width="30px" height="30px" > </h5>
						   <div class="tbox-body">
							  <a class="kr-font">기준치 초과 횟수</a>
							  <a id="temp"></a>
						   <div class="highcharts-figure">
							    <div id="container-speed" class="chart-container"></div>
							</div>
					    </div>
					</div>
			    </div> -->
			    
				 
            	 
            </div> 
           <script>
           var mapContainer = document.getElementById('map'), // 지도의 중심좌표
           mapOption = { 
        	    
               center: new kakao.maps.LatLng(37.50972007166255, 127.05555398447125), // 지도의 중심좌표
               level: 2 // 지도의 확대 레벨
           }; 

       var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

       // 지도에 마커를 표시합니다 
       var marker = new kakao.maps.Marker({
           map: map, 
           position: new kakao.maps.LatLng(37.50972007166255, 127.05555398447125)
       });

       // 커스텀 오버레이에 표시할 컨텐츠 입니다
       // 커스텀 오버레이는 아래와 같이 사용자가 자유롭게 컨텐츠를 구성하고 이벤트를 제어할 수 있기 때문에
       // 별도의 이벤트 메소드를 제공하지 않습니다 
       var content = '<div class="wrap">' + 
                   '    <div class="info">' + 
                   '        <div class="title">' + 
                   '            캠퍼스세븐' + 
                   '            <div class="close" onclick="closeOverlay()" title="닫기"></div>' + 
                   '        </div>' + 
                   '        <div class="body">' + 
                   '            <div class="img">' +
                   '                <img src="/np/img/mclogo.jpg" width="73" height="70">' +
                   '           </div>' + 
                   '            <div class="desc">' + 
                   '                <div class="ellipsis">서울 강남구 삼성로 534 SAC아트홀 6층</div>' + 
                   '                <div class="jibun ellipsis">(우) 06166 </div>' + 
                   '                <div><a href="https://www.kakaocorp.com/main" target="_blank" class="link">홈페이지</a></div>' + 
                   '            </div>' + 
                   '        </div>' + 
                   '    </div>' +    
                   '</div>';

       // 마커 위에 커스텀오버레이를 표시합니다
       // 마커를 중심으로 커스텀 오버레이를 표시하기위해 CSS를 이용해 위치를 설정했습니다
       var overlay = new kakao.maps.CustomOverlay({
           content: content,
           map: map,
           position: marker.getPosition()       
       });

       // 마커를 클릭했을 때 커스텀 오버레이를 표시합니다
       kakao.maps.event.addListener(marker, 'click', function() {
           overlay.setMap(map);
       });

       // 커스텀 오버레이를 닫기 위해 호출되는 함수입니다 
       function closeOverlay() {
           overlay.setMap(null);     
       }
           </script>