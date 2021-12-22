<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    			<!-- MODE 변경 -->
				<jsp:include page="data_view/modetest.jsp"/>
				<jsp:include page="data_view/countdata.jsp"/>
				<!-- 가스 값-->
				<jsp:include page="data_view/gasgage.jsp"/>
				<!-- 온도 값 -->
				<jsp:include page="data_view/tempgage.jsp"/>
				<!-- 종합정보 -->
				<jsp:include page="chart_view/rcharttot.jsp"/>
							 
						
				   
				
		<script src="/np/js/classie.js"></script>
		<script src="/np/js/main.js"></script>
	</body>
</html>