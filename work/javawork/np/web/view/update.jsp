<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html>
<html lang="en">
<head>
	<title>Update</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
<!--===============================================================================================-->	
	<link rel="icon" type="image/png" href="/np/img/icons/favicon.ico"/>
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="/np/vendor/bootstrap/css/bootstrap.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="/np/fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="/np/fonts/iconic/css/material-design-iconic-font.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="/np/vendor/animate/animate.css">
<!--===============================================================================================-->	
	<link rel="stylesheet" type="text/css" href="/np/vendor/css-hamburgers/hamburgers.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="/np/vendor/animsition/css/animsition.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="/np/vendor/select2/select2.min.css">
<!--===============================================================================================-->	
	<link rel="stylesheet" type="text/css" href="/np/vendor/daterangepicker/daterangepicker.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="/np/css/util.css">
	<link rel="stylesheet" type="text/css" href="/np/css/main.css">
</head>
<body>
	
	<div class="limiter">
		<div class="container-login100">
			<div class="wrap-login100">
				<form class="login100-form validate-form" action="uupdateimpl.mc" method="post">
					<span class="login100-form-title p-b-26">
						회원수정 & 탈퇴1
					</span>
					<span class="login100-form-title p-b-48">
						<img src="/np/img/cap01.jpg">
					</span>
						<div class="wrap-input100 validate-input" data-validate = "check your ID">
							<span class="focus-input100"></span>
							ID : <input type="text" name="id" value="${user.id }"  readonly="readonly">						
						</div>
						<div class="wrap-input100 validate-input" data-validate="Enter password">
							<span class="btn-show-pass">
								<i class="zmdi zmdi-eye"></i>
							</span>
							PWD : <input type="text" name="pwd" value="${user.pwd }">
							<span class="focus-input100"></span>		
						</div>	
						<input type="submit" value="update"><br>
						<button>sd</button>							
				</form>
			</div>
		</div>
	</div>
	

	<div id="dropDownSelect1"></div>
	
<!--===============================================================================================-->
	<script src="/np/vendor/jquery/jquery-3.2.1.min.js"></script>
<!--===============================================================================================-->
	<script src="/np/vendor/animsition/js/animsition.min.js"></script>
<!--===============================================================================================-->
	<script src="/np/vendor/bootstrap/js/popper.js"></script>
	<script src="/np/vendor/bootstrap/js/bootstrap.min.js"></script>
<!--===============================================================================================-->
	<script src="/np/vendor/select2/select2.min.js"></script>
<!--===============================================================================================-->
	<script src="/np/vendor/daterangepicker/moment.min.js"></script>
	<script src="/np/vendor/daterangepicker/daterangepicker.js"></script>
<!--===============================================================================================-->
	<script src="/np/endor/countdowntime/countdowntime.js"></script>
<!--===============================================================================================-->
	<script src="/np/js/login.js"></script>

</body>
</html>