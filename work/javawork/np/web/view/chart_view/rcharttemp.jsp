<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
			              [0, Highcharts.getOptions().colors[0]],
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
			setInterval(function(){
			display2(d);
			}, 5000);
		}
	});
};
$(document).ready(function(){
	getdata2();
});
</script>

<h3>Temperature Info.</h3>

	
<figure class="highcharts-figure">
	<div id="container_temp"></div>
</figure>

