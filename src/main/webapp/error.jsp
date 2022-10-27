<%@page import="com.dora.weblab2.servlet.HttpException" %>
<%
    HttpException exception = (HttpException) pageContext.getException();
    response.setStatus(exception.statusCode);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Exception</title>
</head>
<body>
<h2>Exception occurred while processing the request</h2>
<p>Type: <%= exception %></p>
<p>Message: <%= exception.getMessage() %></p>
</body>
</html>