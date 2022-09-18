<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
</head>
<body style="background-color: #${pickedBgCol}">
<a href="<c:url value="/setcolor?color=FFFFFF"/>">WHITE</a>
<a href="<c:url value="/setcolor?color=FF0000"/>">RED</a>
<a href="<c:url value="/setcolor?color=00FF00"/>">GREEN</a>
<a href="<c:url value="/setcolor?color=00FFFF"/>">CYAN</a>
</body>
</html>
