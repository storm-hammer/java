<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
	</head>
	<body style="background-color: #${pickedBgCol}" >
		<a href="<c:url value="colors.jsp"/>">Background color chooser</a><br>
		<a href="<c:url value="trigonometric?a=0&b=90"/>">Sinusi i kosinusi stupnjeva od 0 do 90</a><br>
		<a href="<c:url value="stories"/>">Šale i pošalice</a><br>
		<a href="<c:url value="powers?a=1&b=100&n=3"/>">Potencije brojeva</a><br>
		<a href="<c:url value="/appinfo.jsp"/>">Trajanje aktivnosti aplikacije</a><br>
		<form action="trigonometric" method="GET">
			Početni kut:<br><input type="number" name="a" min="0" max="360" step="1" value="0"><br>
			Završni kut:<br><input type="number" name="b" min="0" max="360" step="1" value="360"><br>
			<input type="submit" value="Tabeliraj"><input type="reset" value="Reset">
		</form>
	</body>
</html>
