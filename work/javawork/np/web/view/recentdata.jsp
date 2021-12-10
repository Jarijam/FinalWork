<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
					<jsp:include page="data_view/modetest.jsp"/>
					<jsp:include page="data_view/countdata.jsp"/>
						<div class="ex-layout">
							<div class="main">
								<div class="left-menu">
									<jsp:include page="chart_view/rcharttot.jsp"/>	
								</div>
								<div class="contents">
									<div class="article">
										 <jsp:include page="chart_view/rchartgas.jsp"/> 
									</div>
									<div class="comment">
										<jsp:include page="chart_view/rcharttemp.jsp"/>
									</div>
							   </div>
							</div>
						</div>	
		<script src="/np/js/classie.js"></script>
		<script src="/np/js/main.js"></script>
	</body>
</html>