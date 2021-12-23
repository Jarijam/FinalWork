<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
							ID : <input type="text" name="id" value="${loginid }"  readonly="readonly">						
						</div>
						<div class="wrap-input100 validate-input" data-validate="Enter password">
							<span class="btn-show-pass">
								<i class="zmdi zmdi-eye"></i>
							</span>
							PWD : <input type="text" name="pwd" value="${uuser.pwd }">
							<span class="focus-input100"></span>		
						</div>	
						<input type="submit" value="update"><br>
						<button>sd</button>							
				</form>
			</div>
		</div>
	</div>
	

	<div id="dropDownSelect1"></div>
	

