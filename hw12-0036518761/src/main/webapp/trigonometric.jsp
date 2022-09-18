<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
</head>
<body style="background-color: #${pickedBgCol}">
	<table border="1">
    	<c:forEach items="${list}" var="vrijednost">
		    <tr>      
		        <td>${vrijednost.angle}</td>
		        <td>${vrijednost.sin}</td>
		        <td>${vrijednost.cos}</td>
		    </tr>
		</c:forEach>
	</table>
</body>
</html>
