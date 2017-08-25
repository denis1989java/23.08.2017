<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<p>Application has encountered an error. Please contact support on ...</p>

Failed URL: ${url}
Exception: ${exception.message}
<c:forEach items="${exception.stackTrace}" var="ste">
    ${ste}}
</c:forEach>
</body>
</html>
