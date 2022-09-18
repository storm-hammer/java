<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
</head>
<body style="background-color: #${pickedBgCol}">
	<% 
	long interval = System.currentTimeMillis() - (Long) getServletContext().getAttribute("time");
	long ms = interval % 1000;
	long sec = (interval / 1000) % 60;
	long min = (interval / (1000 * 60)) % 60;
	long hrs = (interval / (1000 * 60 * 60)) % 24;
	long days = (interval / (1000 * 60 * 60 * 24));
	%>
	<p>Aplikacija je upaljena već: 
	<%= String.format("%d days %d hours %d minutes %d seconds and %d milliseconds", days, hrs, min, sec, ms) %></p>
</body>
</html>