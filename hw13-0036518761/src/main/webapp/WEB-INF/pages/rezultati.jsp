<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<style type="text/css">
	 		table.rez td {text-align: center;}
		</style>
	</head>
	
	<body>
		<h1>Rezultati glasanja</h1>
		<p>Ovo su rezultati glasanja.</p>
		
		<table border="1" class="rez">
			<thead><tr><th>Opcija</th><th>Broj glasova</th></tr></thead>
			<tbody>
				<c:forEach items="${opcije}" var="opcija">
				<tr><td>${opcija.optionTitle}</td><td>${opcija.votesCount}</td></tr>
				</c:forEach>
			</tbody>
		</table>
		
		<h2>Grafički prikaz rezultata</h2>
		<img alt="Pie-chart" src="servleti/glasanje-grafika" width="400" height="400" />
		
		<h2>Rezultati u XLS formatu</h2>
		<p>Rezultati u XLS formatu dostupni su <a href="servleti/glasanje-xls">ovdje</a></p>
		
	</body>
</html>