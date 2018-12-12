<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit</title>
    <meta charset="utf-8"/>
</head>
<body>
<h3>다음 책 검색 결과: 질의어 - ${searchWord}</h3>

<c:forEach var="item" items="${kakaoBook.documents}">
    제목: ${item.title}<br/>
    저자: ${item.authors}<br/>
    내용: ${item.contents}<br/>
    -------------------------<br/>
</c:forEach>
</body>
</html>