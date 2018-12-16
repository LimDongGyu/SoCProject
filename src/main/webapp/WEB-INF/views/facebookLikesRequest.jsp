<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit</title>
</head>
<body>
페이스북 좋아요 검색<br/>
이름: ${name}<br/>
이메일: ${email}<br/>
친구 명수: ${num_friends}<br/>
<br/>
<form action="/facebook/facebookLikes" method="GET">
    <input type="text" name="userId" value="${userId}"/>
    <input type="submit" value="검색">
</form>
</body>
</html>