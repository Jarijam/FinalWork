<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://code.highcharts.com/highcharts.js"></script>
<script src="https://code.highcharts.com/modules/exporting.js"></script>
<script src="https://code.highcharts.com/modules/export-data.js"></script>
<script src="https://code.highcharts.com/modules/accessibility.js"></script>



</head>
<body>
<header>
		
</header>

<section>
	<c:choose>
		<c:when test="${center == null }">
			<jsp:include page="chart01.jsp"/>
		</c:when>
		<c:otherwise>
			<jsp:include page="${center }.jsp"/>
		</c:otherwise>
	</c:choose>
</section>
<footer></footer>
</body>
</html>

