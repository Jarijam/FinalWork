<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
	#container{
		width: 960px;
		margin: 0px auto;
		padding: 20px;
		border: 1px solid #5D5D5D;
	}
	#header{
		padding: 20px;
		margin-bottom: 20px;
		border: 1px solid #5D5D5D;
	}
	#content {
		width: 600px;
		padding: 20px;
		margin-bottom: 20px;
		float: left;
		border: 1px solid #5D5D5D;
	}
	#sidebar{
		width: 260px;
		padding: 20px;
		margin-bottom: 20px;
		float: right;
		border: 1px solid #5D5D5D;
	}
	#footer{
		clear: both;
		padding: 20px;
		border: 1px solid #5D5D5D;
	}
	@media(max-width: 550px){
		#container{
			width: auto;
		}
		#content{
			float: none;
			width: auto;
		}
		#sidebar{
			float: none;
			width: auto;
		}
	}
</style>
</head>
<body>
	<div id="container">
		<div id="header">
			<h1>데스크톱용 레이아웃 테스트</h1>
		</div>
		<div id="content">
			<h2>쓰고싶은 내용</h2>
			<p>저희는 화재 감지 순찰 장비를 판매합니다.</p>
			<p>연락처는 010-1234-5678입니다.</p>
		</div>
		<div id="sidebar">
			<h2>참고 사이트</h2>
			<ol>
				<li><a href="/np/view/sample_view/senser_table.jsp">테이블</a></li>
				<li><a href="/np/view/sample_view/homeTest.jsp">home</a></li>
				<li><a href="https://www.naver.com/">네이버</a></li>
				<li><a href="https://www.daum.net/">다음</a></li>
			</ol>
		</div>
		<div id="footer">
			<p>자세히보기<a href="https://github.com/Jarijam">github</a></p>
		</div>
	</div>
</body>
</html>