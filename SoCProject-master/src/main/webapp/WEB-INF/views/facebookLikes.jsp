<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit</title>
</head>
<body>
<h2>Facebook 친구 검색 결과: 사용자 ID - ${userId}</h2>

<c:forEach var="item" items="${facebookLike.data}">
    ${item.name}, ${item.id}, ${item.createdTime}<br/><br/>
</c:forEach>
</body>
</html>