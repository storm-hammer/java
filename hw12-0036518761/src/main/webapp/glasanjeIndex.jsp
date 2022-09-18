<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
</head>
<body style="background-color: #${pickedBgCol}">
	<h1>Glasanje za omiljeni bend:</h1>
	<p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste
glasali!</p>
	<ol>
		<c:forEach items="${bendovi}" var="bend">
		<li><a href="glasanje-glasaj?id=${bend.id}">${bend.name}</a></li>
		</c:forEach>
	</ol>
</body>
</html>