<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script>
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

	

<div class="cbox">
    <h3 class="cbox-body">Gas Info.</h3>
    <div id="container_gas"></div>
  </div>

