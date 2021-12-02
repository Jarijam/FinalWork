<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
	var slideIndex = 1;
	function plusDivs(n){
		showDivs(slideIndex += n);
	}
	function showDivs(n){
		var i;
		var x = document.getElementsByClassName("mySlides");
		if(n > x.length){slideIndex=1;}
		if(n < 1){slideIndex=x.length};
		for(i=0; i<x.length; i++){
			x[i].style.display="none";
		}
		x[slideIndex-1].style.display="block";
	}
</script>
</head>
<body onload="showDivs(1)">
	<div align="center" class="large">
		<h2 align="center">Manual Slides</h2>
		<div class="small">
			<img class="mySlides" src="/np/img/graph1.png">
			<img class="mySlides" src="/np/img/graph2.png">
			<img class="mySlides" src="/np/img/graph3.jpg">
			<img class="mySlides" src="/np/img/logo1.png">
			<img class="mySlides" src="/np/img/cap01.jpg">
			<img class="mySlides" src="/np/img/cr7.jpg">
			
			<a class="leftbtn" onclick="plusDivs(-1)">◀</a>
			<a class="rightbtn" onclick="plusDivs(1)">▶</a>
		</div>
	</div>
</body>
</html>