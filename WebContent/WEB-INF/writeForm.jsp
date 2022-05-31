<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h1>전화번호부</h1>
	<h2>등록폼</h2>
	<p>
		전화번호를 등록하려면 <br>
		아래 항목을 기입하고 "등록" 버튼을 클릭하세요.
	</p>
	
	<form action="./pbc" method="get">
		이름(name) <input type="text" name="name" value=""> <br>
		핸드폰(hp) <input type="text" name="hp" value=""> <br>
		회사번호(company) <input type="text" name="company" value=""> <br><br>
		<input type="text" name="action" value="write"> <!-- 사용자가 쓸 때는 type을 hidden으로 함 -->
		<button type="submit">등록</button>
	</form>

</body>
</html>