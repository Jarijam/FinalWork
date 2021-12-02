<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html lang="en" class="no-js">
	<head>
		<meta charset="UTF-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=edge"> 
		<meta name="viewport" content="width=device-width, initial-scale=1"> 
		<title>Off-Canvas Menu Effects - Top Side</title>
		<meta name="description" content="Modern effects and styles for off-canvas navigation with CSS transitions and SVG animations using Snap.svg" />
		<meta name="keywords" content="sidebar, off-canvas, menu, navigation, effect, inspiration, css transition, SVG, morphing, animation" />
		<meta name="author" content="Codrops" />
		<link rel="shortcut icon" href="../favicon.ico">
		<link rel="stylesheet" type="text/css" href="/np/css/normalize.css" />
		<link rel="stylesheet" type="text/css" href="/np/css/demo.css" />
		<link rel="stylesheet" type="text/css" href="/np/css/font.css" />
		<link rel="stylesheet" type="text/css" href="/np/css/menu_topside.css" />
		<!--[if IE]>
  		<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
		<![endif]-->
	</head>
	<body>
		<div class="container">
			<div class="menu-wrap">
				<nav class="menu-top">
					<div class="profile"><img src="img/user1.png" alt="Matthew Greenberg"/><span>Matthew Greenberg</span></div>
					<div class="icon-list">
						<a href="#"><i class="fa fa-fw fa-star-o"></i></a>
						<a href="#"><i class="fa fa-fw fa-bell-o"></i></a>
						<a href="#"><i class="fa fa-fw fa-envelope-o"></i></a>
						<a href="#"><i class="fa fa-fw fa-comment-o"></i></a>
					</div>
				</nav>
				<nav class="menu-side">
					<a href="#">Recent Stories</a>
					<a href="#">Reading List</a>
					<a href="#">My Stories</a>
					<a href="#">Categories</a>
				</nav>
			</div>
			<button class="menu-button" id="open-button">Open Menu</button>
			<div class="content-wrap">
				<div class="content">
					<header class="codrops-header">
						<div class="codrops-links">
							<a class="codrops-icon codrops-icon-prev" href="http://tympanus.net/Development/TabStylesInspiration/"><span>Previous Demo</span></a>
							<a class="codrops-icon codrops-icon-drop" href="http://tympanus.net/codrops/?p=20100"><span>Back to the Codrops Article</span></a>
						</div>
						<h1>Off-Canvas Menu Effects <span>Showing (off-canvas) menus stylishly</span></h1>
						<nav class="codrops-demos">
							<a class="current-demo" href="index.html">Top Side</a>
							<a href="test01.mc">Side Slide</a>
							<a href="/np/view/sample_view/layout_test.jsp">Corner Box</a>
							<a href="cornerbox_nested.html">Nested Corner Box</a>
							<a href="topexpand.html">Top Expand</a>
							<a href="cornermorph.html">Corner Morph</a>
							<a href="elastic.html">Elastic</a>
							<a href="bubble.html">Bubble</a>
							<a href="wave.html">Wave</a>
						</nav>
						<p>Based on the <a href="https://dribbble.com/shots/1663008-Old-Designspiration-Menu-Concept">Dribble shot by Michael Martinho</a></p>
					</header>
					<!-- Related demos -->
					<section class="related">
						<p>If you enjoyed this demo you might also like:</p>
						<a href="http://tympanus.net/Development/SidebarTransitions/">
							<img src="img/related/sidebartransitions.png" />
							<h3>Transitions for Off-Canvas Navigations</h3>
						</a>
						<a href="http://tympanus.net/Development/PerspectivePageViewNavigation/">
							<img src="img/related/PerspectiveNavigation.png" />
							<h3>Perspective Page View Navigation</h3>
						</a>
					</section>
				</div>
			</div><!-- /content-wrap -->
		</div><!-- /container -->
		<script src="/np/js/classie.js"></script>
		<script src="/np/js/main.js"></script>
	</body>
</html>