<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit</title>
</head>
<body>
<h2>위치 ${instt_name}</h2>

<c:forEach var="item" items="${water.springID}">
    ${water.testresult}, ${water.failresult}, ${water.testeddate}<br/><br/>
</c:forEach>
</body>
</html>