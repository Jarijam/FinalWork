<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
		    type: 'line'
		  },
		  title: {
		    text: 'Monthly Average Temperature'
		  },
		  subtitle: {
		    text: 'Source: WorldClimate.com'
		  },
		  xAxis: {
		    categories: d.time
		  },
		  yAxis: {
		    title: {
		      text: 'Temperature (°C)'
		    }
		  },
		  plotOptions: {
		    line: {
		      dataLabels: {
		        enabled: true
		      },
		      enableMouseTracking: false
		    }
		  },
		  series: d.data
		});
};
function getdata(){
	$.ajax({
		url:'chart01.mc',
		success:function(d){
			display(d);
		}
	});
};

function display2(){
	Highcharts.chart('container', {
	    chart: {
	        type: 'area'
	    },
	    title: {
	        text: 'Historic and Estimated Worldwide Population Growth by Region'
	    },
	    subtitle: {
	        text: 'Source: Wikipedia.org'
	    },
	    xAxis: {
	        categories: ['1750', '1800', '1850', '1900', '1950', '1999', '2050'],
	        tickmarkPlacement: 'on',
	        title: {
	            enabled: false
	        }
	    },
	    yAxis: {
	        title: {
	            text: 'Billions'
	        },
	        labels: {
	            formatter: function () {
	                return this.value / 1000;
	            }
	        }
	    },
	    tooltip: {
	        split: true,
	        valueSuffix: ' millions'
	    },
	    plotOptions: {
	        area: {
	            stacking: 'normal',
	            lineColor: '#666666',
	            lineWidth: 1,
	            marker: {
	                lineWidth: 1,
	                lineColor: '#666666'
	            }
	        }
	    },
	    series: [{
	        name: 'Asia',
	        data: [502, 635, 809, 947, 1402, 3634, 5268]
	    }, {
	        name: 'Africa',
	        data: [106, 107, 111, 133, 221, 767, 1766]
	    }, {
	        name: 'Europe',
	        data: [163, 203, 276, 408, 547, 729, 628]
	    }, {
	        name: 'America',
	        data: [18, 31, 54, 156, 339, 818, 1201]
	    }, {
	        name: 'Oceania',
	        data: [2, 2, 2, 6, 13, 30, 46]
	    }]
	});
};

function getdata2(){
	display2();
};

$(document).ready(function(){
	$('#c1').click(function(){
		getdata();
	});
	$('#c2').click(function(){
		getdata2();
	});
});
</script>

<h1>RChart</h1>
<button id="c1">Chart1</button>
<button id="c2">Chart2</button>
<div id="container"></div>