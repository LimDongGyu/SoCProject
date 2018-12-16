<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit</title>
</head>
<body>
DaumBook Results<br/>

${daumBook}

<c:forEach var="u" items="${users}">
    ${u}<br/>
</c:forEach>
</body>
</html>