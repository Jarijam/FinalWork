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
	size: 30px;
}

.cbox {
	background-color: white;
	border-radius: 40px;
	margin: 30px;
}

.cbox-body {
	text-decoration: underline;
}

.tbox {
	background-color: white;
	border-radius: 40px;
	margin: 30px;
	height: 400px;
}

.tbox-head {
	border-radius: 40px;
	
}

.tbox-body {
	margin: auto;
	height: 300px;
}


</style>
	<script>
	
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
				        max: 900,
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
				                '<span style="font-size:60px">{y}</span><br/>' +
				                '<span style="font-size:20px;opacity:0.4">ppm</span>' +
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
		function display(d){
			Highcharts.getJSON(
			  'https://cdn.jsdelivr.net/gh/highcharts/highcharts@v7.0.0/samples/data/usdeur.json',
			  function(data) {

				  var chart2 = new Highcharts.chart('container_gas', {
			      chart: {
			        zoomType: 'x'
			      },
			      title: {
			        text: 'Gas over time'
			      },
			      subtitle: {
			        text: document.ontouchstart === undefined ?
			          'Click and drag to zoom in' : 'Pinch the chart to zoom in'
			      },
			      xAxis: {
			        type: 'datetime'
			      },
			      yAxis: {
			        title: {
			          text: 'Gas'
			        }
			      },
			      legend: {
			        enabled: false
			      },
			      plotOptions: {
			        area: {
			          fillColor: {
			            linearGradient: {
			              x1: 0,
			              y1: 0,
			              x2: 0,
			              y2: 1
			            },
			            stops: [
			              [0, Highcharts.getOptions().colors[6]],
			              [1, Highcharts.color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
			            ]
			          },
			          marker: {
			            radius: 2
			          },
			          lineWidth: 1,
			          states: {
			            hover: {
			              lineWidth: 1
			            }
			          },
			          threshold: null
			        }
			      },

			      series: [{
			        type: 'area',
			        name: 'Gas',
			        data: d
			      }]
			    });
			  });
		}
		function getdata(){
			$.ajax({
				url:'rchartgas.mc',
				success:function(d){
					display(d);
				}
			});
		};
		
		$(document).ready(function(){
				getdata(); 
			setInterval(function(){
				getdata();
			},5000);
		});
	
	
    </script>
    
	 <div class="row">
			   <div class="col-sm-4" >
					 <div class="tbox" >
						<h5 class="tbox-head" style="background-color: #E39600;" >Gas Info&nbsp;&nbsp;
						 <!-- <img src="/np/img/graph2.png" width="30px" height="30px" > --></h5>
							<div class="tbox-body">
							 <!--  <a class="kr-font">????????? ?????? ??????</a>
							  <a id="gas"></a> -->
						    <div class="highcharts-figure">
							    <div id="container-gas" class="chart-container"></div>
							</div>
						 </div>
					</div>
				</div>
           <div class="col-sm-8" style="margin-top: 10px;"><br/>
   			 <div id="container_gas"></div>
 		 </div>
 		
      </div> 
        
        
        <script>
           var mapContainer = document.getElementById('map'), // ????????? ????????????
           mapOption = { 
        	    
               center: new kakao.maps.LatLng(37.50972007166255, 127.05555398447125), // ????????? ????????????
               level: 10 // ????????? ?????? ??????
           }; 

       var map = new kakao.maps.Map(mapContainer, mapOption); // ????????? ???????????????

       // ????????? ????????? ??????????????? 
       var marker = new kakao.maps.Marker({
           map: map, 
           position: new kakao.maps.LatLng(37.50972007166255, 127.05555398447125)
       });

       // ????????? ??????????????? ????????? ????????? ?????????
       // ????????? ??????????????? ????????? ?????? ???????????? ???????????? ???????????? ???????????? ???????????? ????????? ??? ?????? ?????????
       // ????????? ????????? ???????????? ???????????? ???????????? 
       var content = '<div class="wrap">' + 
                   '    <div class="info">' + 
                   '        <div class="title">' + 
                   '            ???????????????' + 
                   '            <div class="close" onclick="closeOverlay()" title="??????"></div>' + 
                   '        </div>' + 
                   '        <div class="body">' + 
                   '            <div class="img">' +
                   '                <img src="/np/img/mclogo.jpg" width="73" height="70">' +
                   '           </div>' + 
                   '            <div class="desc">' + 
                   '                <div class="ellipsis">?????? ????????? ????????? 534 SAC????????? 6???</div>' + 
                   '                <div class="jibun ellipsis">(???) 06166 </div>' + 
                   '                <div><a href="https://www.kakaocorp.com/main" target="_blank" class="link">????????????</a></div>' + 
                   '            </div>' + 
                   '        </div>' + 
                   '    </div>' +    
                   '</div>';

       // ?????? ?????? ???????????????????????? ???????????????
       // ????????? ???????????? ????????? ??????????????? ?????????????????? CSS??? ????????? ????????? ??????????????????
       var overlay = new kakao.maps.CustomOverlay({
           content: content,
           map: map,
           position: marker.getPosition()       
       });

       // ????????? ???????????? ??? ????????? ??????????????? ???????????????
       kakao.maps.event.addListener(marker, 'click', function() {
           overlay.setMap(map);
       });

       // ????????? ??????????????? ?????? ?????? ???????????? ??????????????? 
       function closeOverlay() {
           overlay.setMap(null);     
       }
           </script>  