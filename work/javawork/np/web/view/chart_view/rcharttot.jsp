<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/highcharts-3d.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<script src="https://code.highcharts.com/modules/export-data.js"></script>
<script src="https://code.highcharts.com/modules/accessibility.js"></script>
<style>
#container {
    height: 400px;
    border: 2px solid blue;
}
</style>
<script>
function display(d){
	Highcharts.chart('container', {
	    chart: {
	        zoomType: 'xy'
	    },
	    title: {
	        text: 'Average Monthly Weather Data for Tokyo',
	        align: 'left'
	    },
	    subtitle: {
	        text: 'Source: WorldClimate.com',
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
	                color: Highcharts.getOptions().colors[2]
	            }
	        },
	        title: {
	            text: 'Temperature',
	            style: {
	                color: Highcharts.getOptions().colors[2]
	            }
	        },
	        opposite: true

	    }, { // Secondary yAxis
	        gridLineWidth: 0,
	        title: {
	            text: 'Rainfall',
	            style: {
	                color: Highcharts.getOptions().colors[0]
	            }
	        },
	        labels: {
	            format: '{value} mm',
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

	    }, {
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

	    }, {
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



function getdata(){
	$.ajax({
		url:'rchart_tot.mc',
		success:function(d){
			display(d);
			setInterval(function(){		
			display(d);
			}, 5000);
		}
	});
};



$(document).ready(function(){
	$('#c1').click(function(){
			getdata();
	});
});

</script>
</head>
<body>
<h1>R_CHART</h1>

	<button id="c1">Chart</button>
	<button id="c2">Chart</button>

<div id="container"></div>
</body>
</html>