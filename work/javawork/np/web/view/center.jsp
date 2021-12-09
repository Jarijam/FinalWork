<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    	
<style>
	.iot1{
		float: left;
		width: 50%;
		height: 500px;
	}
	.iot2{
		float: right;
		width: 50%;
		height: 500px;
	}
	.splitlayout {
	position: relative;
	overflow-x: hidden;
	min-height: 100%;
	width: 100%;
	}
	.side-left {
	float: left;
	left: 0;
	background: #47a3da;
	color: #fff;
	outline: 1px solid #47a3da; /* avoid gap */
	}
	.side-right {
		float: right;
		right: 0;
		background: #fff;
		color: #47a3da;
		outline: 1px solid #fff; /* avoid gap */
	}
	.top{
		
	}
</style>
<div class="container">
	<div class="top">
		<h1>산업 안전IoT장비</h1>
	</div>
	<div id="splitlayout" class="splitlayout">
			<div class="side-left">
				<div class="intro-content">
					<h1><span>수동 모드</span></h1>
				</div>				
			</div>
			<div class="side-right">
					<h1><span>자동 모드</span></h1>
				</div>
			</div>
		</div><!-- /intro -->
		<div class="page page-right page-large">
			<div class="page-inner">
				<section>
					<!-- content -->
				</section>
				<section>
					<!-- content -->
				</section>
				<!-- ... -->
			</div><!-- /page-inner -->
		</div><!-- /page-right -->
		<div class="page page-left page-fill">
			<div class="page-inner">
				<!-- ... -->
			</div><!-- /page-inner -->
		</div><!-- /page-left -->
	</div><!-- /splitlayout -->
</div><!-- /container -->

	<!-- <div class="iot1">
		<h4>정렬테스트1</h4>
	</div>
	<div class="iot2">
		<h4>정렬테스트2</h4>
	</div> -->

