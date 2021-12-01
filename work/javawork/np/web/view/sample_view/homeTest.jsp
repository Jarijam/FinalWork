<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/np/view/sample_view/style.css">
</head>
<body>
<!-- header -->
<header>
	<div class="container">
		<div class="logo"><img src="/np/img/logo1.png"></div>
			<ul class="sidemenu">
				<li><a href="#">Home</a></li>
				<li><a href="#">SiteMap</a></li>
			</ul>		
	</div>
</header>
<!-- nav -->
<nav>
	<div class="container">
		<ul class="leftMenu">
			<li class="active"><a href="#">Home</a></li>
			<li><a href="#">갤러리</a></li>
			<li class="dropBox">
				<a href="#">게시판</a>
				<span class="dropmenu">
					<span><a href="/np/view/sample_view/practice01.jsp">자유 게시판</a></span>
					<span><a href="#">문의 게시판</a></span>
					<span><a href="#">건의 게시판</a></span>
				</span>
			</li>
			<li class="dropBox">
				<a href="#">멀티미디어</a>
				<span class="dropmenu">
					<span><a href="#">짧은 영상</a></span>
					<span><a href="#">긴 영상</a></span>
				</span>
			</li>
			<li><a href="#">상품 구매</a></li>
			<li><a href="#">찾아오시는 길</a></li>
		</ul>
		<ul class="rightMenu">
			<li><a href="#">회원가입</a></li>
			<li><a href="#">로그인</a></li>
		</ul>
	</div>
</nav>
<!-- section -->
<section id="section1">
	<div class="container">
		<div class="leftBox">
			<div class="graph1"><img src="/np/img/graph1.png">
				<!-- 그래프1 추가 -->
			</div>
			<div class="graph2"><img src="/np/img/graph2.png">				
				<!-- 그래프2 추가 -->
			</div>
		</div>
		<div class="rightBox">
			<div class="imgBox"><img src="/np/img/img1.png">
				
				<!-- 이미지 추가예정 -->
			</div>
			<div class="table"><img src="/np/img/graph3.jpg">
				
				<!-- 테이블 추가예정 -->
			</div>
		</div>
	</div>
</section>
<div class="clear"></div>
<!-- footer -->
<footer>
	<div class="top">
		<div class="container">
			<span>개인정보처리방침</span>
			<span>저작권지침및신고</span>
			<span>이메일무단수집거부</span>
		</div>
	</div>
	<div class="container">
		<strong>
			본사 : 서울시 강남구 연락처 : 010-1234-5678 
		</strong>
		<div class="copyright">
			DESIGNED BY 불IT낫어
		</div>
	</div>
</footer>
</body>
</html>