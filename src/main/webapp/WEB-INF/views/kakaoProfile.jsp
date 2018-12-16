<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
Daum ID: ${daumProfileDetail.id}<br/>
Daum User ID: ${daumProfileDetail.userid}<br/>
Daum NickName: ${daumProfileDetail.nickname}<br/>
Daum Image: <img src="${daumProfileDetail.imagePath}" border="0"/><br/>
Daum Big Image: <img src="${daumProfileDetail.bigImagePath}" border="0"/><br/>
</body>
</html>
