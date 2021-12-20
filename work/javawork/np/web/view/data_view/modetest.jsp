<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<style>
form{
	color: white;
}
.btn1 {
	background-color: red;
	font-family: 'Titillium Web', sans-serif;
	width: 130px;
	height: 40px;
	float: left;	
	
}
.btn2 {
	background-color: #465B69;
	font-family: 'Titillium Web', sans-serif;
	width: 130px;
	height: 40px;
	float: left;
	
}
.btn3 {
	background-color: #CBC0AC;
	font-family: 'Titillium Web', sans-serif;
	width: 130px;
	height: 40px;
	float: left;
}
.test{
	
	margin: auto;
	margin-left: 800px;

}
</style>

			<div class = "test">
				<!-- <form  name="mode1" onclick="click()" method='GET'>
				      <input type = 'hidden' name='MODE' value='mode1'>
				      <input class = "btn1" type='submit' value='Automatic Patrol' />
				</form> -->
				
				<form  name="mode2" onclick="click()" method='GET'>
				      <input type = 'hidden' name='MODE' value='Y'>
				      <input class = "btn2" type='submit' value='Obstacle Detection' />
				</form>
				
				<form  name="mode3" onclick="click()" method='GET' >
				      <input type = 'hidden' name='MODE' value='Z'>
				      <input class = "btn3" type='submit' value='Manual Operation' />
				</form>
			</div><br/><br/>
				
			