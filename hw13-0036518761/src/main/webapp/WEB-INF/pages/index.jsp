<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
</head>
<body>
	<h1>Ankete: </h1>
	<ol>
		<c:forEach items="${ankete}" var="anketa">
		<li><a href="glasanje?pollId=${anketa.id}">${anketa.title}</a></li>
		</c:forEach>
	</ol>
</body>
</html>