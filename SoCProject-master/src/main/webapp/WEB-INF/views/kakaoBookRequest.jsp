<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit</title>
    <meta charset="utf-8"/>
</head>
<body>
카카오 닉네임: ${nickname}<br/>
카카오 사진: <img border=0 src="${profile_image}" width="100px"/>
<br/>
카카오 책 검색<br/>
<form action="/kakao/kakao_book" method="GET">
    <input type="hidden" name="userId" value="${userId}"/>
    <input type="text" name="searchWord" placeholder="검색어"/>
    <input type="submit" value="검색">
</form>
</body>
</html>