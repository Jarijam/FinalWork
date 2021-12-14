<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   

	<script>
	function display2(d){
		Highcharts.getJSON(
		  'https://cdn.jsdelivr.net/gh/highcharts/highcharts@v7.0.0/samples/data/usdeur.json',
		  function(data) {

			  var chart1 = new Highcharts.chart('container_temp', {
		      chart: {
		        zoomType: 'x'
		      },
		      title: {
		        text: 'Temperature over time'
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
		          text: 'Temperature'
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
		              [0, Highcharts.getOptions().colors[2]],
		              [1, Highcharts.color(Highcharts.getOptions().colors[9]).setOpacity(0).get('rgba')]
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
		        name: 'Temperature',
		        data: d
		      }]
		    });
		  });
		}
		function getdata2(){
		$.ajax({
			url:'rcharttemp.mc',
			success:function(d){
				display2(d);
			}
		});
		};
		
		$(document).ready(function(){
			getdata2();
		setInterval(function(){
			getdata2();
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
	
    </script>
    
	<div class="row">
			<div class="col-sm-4" >
				     <div class="tbox">
						<h5 class="tbox-head" style="background-color: #03C65A;" >Temp Info &nbsp;&nbsp;
						 <!-- <img src="/np/img/graph.png" width="30px" height="30px" > --> </h5>
						   <div class="tbox-body">
							<!--   <a class="kr-font">기준치 초과 횟수</a>
							  <a id="temp"></a> -->
						   <div class="highcharts-figure">
							    <div id="container-speed" class="chart-container"></div>
							</div>
					    </div>
					</div>
				</div>
			<div class="col-sm-8" style="margin-top: 10px;"><br/>
			   <div id="container_temp"></div>
  			</div>
  		
  			
	</div>
	
	
			
           