<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title></title>
</head>
<body>
Hello, ${textFromController}<br/>
${name}<br/>
<img src="/resources/images/spring.png"/><br/>
<c:set var="user" value="${SPRING_SECURITY_CONTEXT.authentication.principal}"/>


<sec:authorize access="isAnonymous()">
<a href="/user/signin">로그인</a>
<a href="/user/signup">회원가입</a>
</sec:authorize>

<sec:authorize access="hasAnyRole('ROLE_USER', 'ROLE_ADMIN')">
    이 문장은 ROLE_USER 혹은 ROLE_ADMIN 권한을 가진 사람에게만 보입니다.<br/>
    <br/>
    현재 로그인된 유저: ${user}<br/>
    <br/>
    부여된 권한:
    <c:forEach var="authority" items="${user.authorities}">
        ${authority}
    </c:forEach>
    <br/>
    <br/>

    <c:choose>
        <c:when test="${user.age == -1}">
            <a href="/kakao/kakao_book_request?userId=${user.email}">카카오 책 검색</a>
            <br/>
        </c:when>
        <c:when test="${user.age == -2}">
            <a href="/facebook/facebookLikes_request?userId=${user.email}">페이스북 좋아요 검색</a>
            <br/>
        </c:when>
    </c:choose>

    <br/>

    <c:url var="logoutUrl" value="/j_spring_security_logout"/>
    <form action="${logoutUrl}" method="post">
        <input type="submit" value="로그 아웃" />
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
    <br/><br/>
</sec:authorize>

<sec:authorize access="hasRole('ROLE_USER')">
    이 문장은 ROLE_USER 권한을 가진 사람에게만 보입니다.<br/>
</sec:authorize>

<sec:authorize access="hasRole('ROLE_ADMIN')">
    이 문장은 ROLE_ADMIN 권한을 가진 사람에게만 보입니다.<br/>
</sec:authorize>

</body>
</html>
