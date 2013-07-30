<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@page import="security.Security,model.Files,org.apache.commons.codec.binary.Base64,database.FileDBAO,java.util.Scanner"%>
    <title>FileHaven - Download File</title>
    <link href="resources/css/bootstrap.css" rel="stylesheet" type="text/css" />
    <link href="resources/css/bootstrap-responsive.css" rel="stylesheet" type="text/css" />
    <link href="resources/css/application.css" rel="stylesheet" type="text/css" />
    <script src="resources/js/jquery-1.9.1.js"></script>
	<script src="resources/js/jquery-ui-1.9.1.js"></script> 
<style>
.subtitle{
font-weight:bold;
text-decoration:underline;
}
.content_space{
padding:60px;
			min-height:400px;
			background-color:#ECECE8;
			-webkit-border-radius: 6px;
			-moz-border-radius: 6px;
			border-radius: 6px;
			background-image: linear-gradient(bottom, rgb(242,239,242) 23%, rgb(240,240,240) 62%, rgb(237,237,237) 81%);
			background-image: -o-linear-gradient(bottom, rgb(242,239,242) 23%, rgb(240,240,240) 62%, rgb(237,237,237) 81%);
			background-image: -moz-linear-gradient(bottom, rgb(242,239,242) 23%, rgb(240,240,240) 62%, rgb(237,237,237) 81%);
			background-image: -webkit-linear-gradient(bottom, rgb(242,239,242) 23%, rgb(240,240,240) 62%, rgb(237,237,237) 81%);
			background-image: -ms-linear-gradient(bottom, rgb(242,239,242) 23%, rgb(240,240,240) 62%, rgb(237,237,237) 81%);
			background-image: -webkit-gradient(
				linear,
				left bottom,
				left top,
				color-stop(0.23, rgb(242,239,242)),
				color-stop(0.62, rgb(240,240,240)),
				color-stop(0.81, rgb(237,237,237))
			);
}
.error{
	color:red;
}
</style>
<script>
</script>
</head>
<body>
<%
		session.setAttribute("AtVerify","TRUE");%>
		<%@ include file="header.jsp"%>
		<%
		session.setAttribute("AtVerify","FALSE");%> 
      	<div class="span9">
			<div class="hero-unit">
			            <h1>Information</h1>
			            <p><%=session.getAttribute("info_line1")==null?new String(""):session.getAttribute("info_line1")%></p>
			            <p style="padding-bottom:0px;margin-bottom:0px"><%=session.getAttribute("info_line2")==null?new String(""):session.getAttribute("info_line2")%></p>
			            <p><%=session.getAttribute("info_line3")==null?new String(""):session.getAttribute("info_line3")%></p>
			            <p style="padding-bottom:0px;margin-bottom:0px"><%=session.getAttribute("info_line4")==null?new String(""):session.getAttribute("info_line4")%></p>
			            <p><%=session.getAttribute("info_line5")==null?new String(""):session.getAttribute("info_line5")%></p>
			</div>
			</div>
			<% 
			session.setAttribute("info_line1","");
			session.setAttribute("info_line2","");
			session.setAttribute("info_line3","");
			session.setAttribute("info_line4","");
			session.setAttribute("info_line5","");
			%>
<%@ include file="footer.jsp"%>
</body>
</html>