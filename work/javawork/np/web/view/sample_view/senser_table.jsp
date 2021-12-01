<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	table{
		width: 100%;
		border-collapse: collapse;
		font-size: 14px;
		text-align: center;
	}
	table > caption{
		font-size: 20px;
		font-weight: 700;
		margin-bottom: 10px; 
	}
	table td, table th{
		padding: 8px 5px;
		border: 1px solid #ccc;
	}
	.blue{
		color: white;
		background: #81d733;
	}
	.yellow{
		background: yellow;
	}
	.orange{
		background: orange;
	}
	.red{
		background: red;
	}
</style>
</head>
<body>
	<table>
		<caption>화재알림</caption>
		<thead>
			<tr>
				<th colspan="2">경보 구간</th>
				<th class="blue">안전</th>
				<th class="green">보통</th>
				<th class="yellow">주의</th>
				<th class="orange">화재 주의</th>
				<th class="red" colspan="2">화재 발생</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td colspan="2">℃</td>
				<td>~20</td>
				<td>21~40</td>
				<td>41~60</td>
				<td>61~80</td>
				<td>81~100</td>
				<td>101~</td>
			</tr>
			<tr>
				<td rowspan="2">순찰IoT</td>
				<td>1번 순찰IoT</td>
				<td></td>
				<td></td>
				<td>사용자에게 FCM경보발송</td>
				<td>건물전체 대피 안내방송</td>
				<td>화재현장 사진 및 영상 전송</td>
				<td>119연락</td>
			</tr>
			<tr>
				<td>2번 순찰IoT</td>
				<td></td>
				<td></td>
				<td>사용자에게 FCM경보발송</td>
				<td>건물전체 대피 안내방송</td>
				<td>화재현장 사진 및 영상 전송</td>
				<td>119연락</td>
			</tr>
		</tbody>
	</table>
</body>
</html>