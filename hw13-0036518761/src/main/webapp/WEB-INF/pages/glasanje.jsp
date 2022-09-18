<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
</head>
<body>
	<h1>${anketa.title}</h1>
	<p>${anketa.message}</p>
	<ol>
		<c:forEach items="${opcije}" var="opcija">
		<li><a href="glasanje-glasaj?id=${opcija.id}&pollId=${pollId}">${opcija.optionTitle}</a></li>
		</c:forEach>
	</ol>
</body>
</html>