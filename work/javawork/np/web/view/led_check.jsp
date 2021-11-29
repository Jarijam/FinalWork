<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@page import="com.vo.DataVO"%>
<%@page import="java.util.ArrayList"%>    
<!DOCTYPE html>
<html>
<head>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<meta charset="UTF-8">
<title>Insert title here</title>
<style >
.box{
	text-align: center;
}
.container{
	display: inline-block;
	
}
.btn btn-danger{
	width: 300px;
}
a {
font-size: 40px;
}
.row{
	margin: 20px;
}
span {
font-size: 40px;
}
.tempimg{
width: 200px;
height: 200px;

}

</style>
<script>
	function getData() {
		$.ajax({
			url:'dataajax.mc',
			success:function(data){
				console.log(data);
				$(data).each(function(idx,item){
					console.log(item.btn+": btnVAl");
					console.log(item.temp+": btnVAl");
					$('#btn').html(item.btn);
					$('#temp').html(item.temp);
				});
			}
		});
	};
	$(document).ready(function(){
		setInterval(function(){
			getData();
		}, 5000);
	});
</script>
</head>
<body>
   <!-- <form name="category" action='/http_test/led' method='GET'> -->
   <div class="box">
	   <div class="container">
		   <c:choose>
		   <c:when test="${status eq 'on' }">
		   		<a><img src="/np/img/led_on.png"></a>
		   		</c:when>
		   	<c:otherwise>
		   		<a><img src="/np/img/led_off.png"></a>
		   	</c:otherwise>
		   	</c:choose><br/>
	   			 <a >LED status : ${status }</a><br/>
		   	<div class="row">
			   	<div class="col-sm-6" style="text-align: right;" >
				   <form name="btn_on" onclick="click()" method='GET'>
				      <input type = 'hidden' name='LED' value='on'>
				      <input class='btn btn-danger' type='submit' value='on' style="height: 50px; width: 120px;"/>
				   </form>
		  	  </div>
		   <!-- <form action='/http_test/led' method='GET'> -->
		  		 <div class="col-sm-6" style="text-align: left;">
				   <form name="btn_off" onclick="click()" method='GET'>
				      <input type = 'hidden' name='LED' value='off'>
				      <input class='btn btn-info' type='submit' value='off' style="height: 50px; width: 120px;"/>
			     </form>
			  </div>
	   	    </div>
	   			<a><img src="/np/img/btn_push.png"></a><br/>
	   				<a>Button status : </a><span id = "btn" ></span><br/>
	   				
	   			<a><img src="/np/img/temp1.png" class="tempimg"></a><br/>
	   				<a>Temperature : </a><span id = "temp"></span>
	   </div>
	</div>
</body>
</html>