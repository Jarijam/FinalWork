<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
#container {
    height: 400px;
    border: 2px solid blue;
}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<script src="https://code.highcharts.com/modules/annotations.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<script src="https://code.highcharts.com/modules/export-data.js"></script>
<script src="https://code.highcharts.com/modules/accessibility.js"></script>
<script>
function display(d){
	Highcharts.getJSON(
			  'https://cdn.jsdelivr.net/gh/highcharts/highcharts@v7.0.0/samples/data/usdeur.json',
			  function(data) {

			    Highcharts.chart('container', {
			      chart: {
			        zoomType: 'x'
			      },
			      title: {
			        text: 'USD to EUR exchange rate over time'
			      },
			      subtitle: {
			        text: document.ontouchstart === undefined ?
			          'Click and drag in the plot area to zoom in' : 'Pinch the chart to zoom in'
			      },
			      xAxis: {
			        type: 'datetime'
			      },
			      yAxis: {
			        title: {
			          text: 'Exchange rate'
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
			        name: 'USD to EUR',
			        data: d
			      }]
			    });
			  });
}
function getdata(){
	$.ajax({
		url:'rchart.mc',
		success:function(d){
			display(d);
			//alert(d);
		}
	});
};
$(document).ready(function(){
	getdata();
});
</script>

<h1>R_CHART</h1>

	

<div id="container"></div>
