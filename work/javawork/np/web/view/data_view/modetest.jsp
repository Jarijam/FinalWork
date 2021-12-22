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
	text-align: center;
	

}
.test2 {
	display: inline-block;
}
</style>

			<div class = "test">
				
				<div class= "test2">
				<form  name="mode2" onclick="click()" method='GET'>
				      <input type = 'hidden' name='MODE' value='X'>
				      <input class = "btn2" type='submit' value='Obstacle Detection' />
				</form>
				
				<form  name="mode3" onclick="click()" method='GET' >
				      <input type = 'hidden' name='MODE' value='Y'>
				      <input class = "btn3" type='submit' value='Manual Operation' />
				</form>
				</div>
			</div><br/><br/>
				
			