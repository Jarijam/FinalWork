<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- <script>
function display3(d){
	var chart3 = new Highcharts.chart('container_tot', {
	    chart: {
	        zoomType: 'xy'
	    },
	    title: {
	        text: 'Sensored Average Data',
	        align: 'left'
	    },
	    subtitle: {
	        text: '',
	        align: 'left'
	    },
	    xAxis: [{
	        categories: d.time,
	        crosshair: true
	    }],
	    yAxis: [{ // Primary yAxis
	        labels: {
	            format: '{value}°C',
	            style: {
	                color: Highcharts.getOptions().colors[1]
	            }
	        },
	        title: {
	            text: 'Temperature',
	            style: {
	                color: Highcharts.getOptions().colors[1]
	            }
	        },
	        opposite: true

	    }, { // Secondary yAxis
	        gridLineWidth: 0,
	        title: {
	            text: 'Gas',
	            style: {
	                color: Highcharts.getOptions().colors[0]
	            }
	        },
	        labels: {
	            format: '{value} ppm',
	            style: {
	                color: Highcharts.getOptions().colors[0]
	            }
	        }

	    }, { // Tertiary yAxis
	        gridLineWidth: 0,
	        title: {
	            text: 'Sea-Level Pressure',
	            style: {
	                color: Highcharts.getOptions().colors[1]
	            }
	        },
	        labels: {
	            format: '{value} mb',
	            style: {
	                color: Highcharts.getOptions().colors[1]
	            }
	        },
	        opposite: true
	    }],
	    tooltip: {
	        shared: true
	    },
	    legend: {
	        layout: 'vertical',
	        align: 'left',
	        x: 80,
	        verticalAlign: 'top',
	        y: 55,
	        floating: true,
	        backgroundColor:
	            Highcharts.defaultOptions.legend.backgroundColor || // theme
	            'rgba(255,255,255,0.25)'
	    },
	    series: [{
	        name: 'Gas',
	        type: 'column',
	        yAxis: 1,
	        data: d.gas,
	        tooltip: {
	            valueSuffix: ' mm'
	        }

	    }, 
	/*     {
	        name: 'Sea-Level Pressure',
	        type: 'spline',
	        yAxis: 2,
	        data: [1016, 1016, 1015.9, 1015.5, 1012.3, 1009.5, 1009.6, 1010.2, 1013.1, 1016.9, 1018.2, 1016.7],
	        marker: {
	            enabled: false
	        },
	        dashStyle: 'shortdot',
	        tooltip: {
	            valueSuffix: ' mb'
	        }

	    },  */
	    
	    {
	        name: 'Temperature',
	        type: 'spline',
	        data: d.temp,
	        tooltip: {
	            valueSuffix: ' °C'
	        }
	    }],
	    responsive: {
	        rules: [{
	            condition: {
	                maxWidth: 500
	            },
	            chartOptions: {
	                legend: {
	                    floating: false,
	                    layout: 'horizontal',
	                    align: 'center',
	                    verticalAlign: 'bottom',
	                    x: 0,
	                    y: 0
	                },
	                yAxis: [{
	                    labels: {
	                        align: 'right',
	                        x: 0,
	                        y: -6
	                    },
	                    showLastLabel: false
	                }, {
	                    labels: {
	                        align: 'left',
	                        x: 0,
	                        y: -6
	                    },
	                    showLastLabel: false
	                }, {
	                    visible: false
	                }]
	            }
	        }]
	    }
	});
};



function getdata3(){
	$.ajax({
		url:'rchart_tot.mc',
		success:function(d){
			display3(d);
		}
	});
};



$(document).ready(function(){
		getdata3(); 
	setInterval(function(){
		getdata3();
	},5000);
});


</script> -->
</head>
<body>
<div class="cbox">
    <h3 class="cbox-body">CCTV</h3>
    <div id = "cam" style="text-align: center;" >
    <iframe src='http://192.168.0.16:81/stream' frameborder='0' width='90%' height='420px' scrolling='no' style='margin: auto;  border: solid red 2px; align-content: center;'></iframe>
    </div>
  </div>
