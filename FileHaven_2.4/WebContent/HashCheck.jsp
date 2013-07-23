<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>FileHaven - Hash Check</title>
     <%@page import="model.Files,org.apache.commons.codec.binary.Base64,security.Security" %>
    <link href="resources/css/bootstrap.css" rel="stylesheet" type="text/css" />
    <link href="resources/css/bootstrap-responsive.css" rel="stylesheet" type="text/css" />
    <link href="resources/css/application.css" rel="stylesheet" type="text/css" />
    <script src="resources/js/jquery-1.9.1.js"></script>
	<script src="resources/js/jquery-ui-1.9.1.js"></script> 
	<script src="resources/js/validation.js"></script>
<style>
.inner_space{
background-color:#FFF;
border-collapse:collapse;
border:1px solid #BBB;
height:200px;
margin-bottom:30px;
-webkit-border-radius: 6px;
-moz-border-radius: 6px;
border-radius: 6px;
overflow:auto;
}
.inner_space div{
	padding-left:20px;
	padding-top:10px;
	padding-bottom:10px;
	border-collapse:collapse;
	border-bottom:1px solid #BBB;
	background-image: linear-gradient(right , rgb(184,191,230) 28%, rgb(220,233,237) 64%, rgb(247,247,247) 82%);
background-image: -o-linear-gradient(right , rgb(184,191,230) 28%, rgb(220,233,237) 64%, rgb(247,247,247) 82%);
background-image: -moz-linear-gradient(right , rgb(184,191,230) 28%, rgb(220,233,237) 64%, rgb(247,247,247) 82%);
background-image: -webkit-linear-gradient(right , rgb(184,191,230) 28%, rgb(220,233,237) 64%, rgb(247,247,247) 82%);
background-image: -ms-linear-gradient(right , rgb(184,191,230) 28%, rgb(220,233,237) 64%, rgb(247,247,247) 82%);

background-image: -webkit-gradient(
	linear,
	right top,
	left top,
	color-stop(0.28, rgb(184,191,230)),
	color-stop(0.64, rgb(220,233,237)),
	color-stop(0.82, rgb(247,247,247))
);
	
}
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

$(document).ready(function(){
	<%
	if(session.getAttribute("LoggedInUser")==null){
		response.sendRedirect("Login.jsp");
		return;
	}
	Account login=(Account)session.getAttribute("LoggedInUser");
	if(login.getType()=='A'){
		session.setAttribute("info_line1", "File access denied");
		session.setAttribute("info_line2","File deleted");
		response.sendRedirect("Information.jsp");
	}
	if(session.getAttribute("HashCheck")==null){
		response.sendRedirect("ViewFileList.jsp");
		return;
	}
	Files file=(Files)session.getAttribute("HashCheck");
	
	if(!file.getFileDeletedTime().equals("NIL")){
		session.setAttribute("info_line1", "File access denied");
		session.setAttribute("info_line2","File deleted");
		response.sendRedirect("Information.jsp");
	}
	String str=new Integer(file.getFileID()).toString();
	str=Base64.encodeBase64String(Security.encryptByte(str.getBytes(),Security.generateAESKey("SYSTEM_KEY"),"AES"));
	
%>
document.getElementById("hidden_file_id").value="<%=str%>";
});


function validate_input(){

	var file_browse=document.getElementById("input_file_browse").value;
	
	$("#information_file_browse").text("");

	if(!validate_empty(file_browse)){
		$("#information_file_browse").text("Please select a file");
		$("body").scrollTop($("body").offset().top);
		return false;
	}
	return true;
}

</script>
</head>
<body>

<%@ include file="header.jsp"%> 
      	<div class="span9">
			<div class="hero-unit">
			            <h1>Compare File</h1>
			            <p>You can upload the file and compare with the selected file here<br/>
			            This will show u whether the file is modified</p>
			            
			</div>
				<div class="row-fluid" >
				<div class="content_space">
				<p class="subtitle">Compare File</p>
				<form onsubmit="return validate_input()" method="POST" action="HashCheckServlet" enctype="multipart/form-data">
				<input type='text' id="hidden_file_id" style='display:none' name='hidden_file_id' >
				Compare File's Name : <span id="input_file_name"><%=file.getFileName() %></span>
				<br><br>
				Select File to compare : <input id="input_file_browse" name="upload_file" type="file" value="Browse"> * <span id="information_file_browse" class="error"></span><br><br>
				<div style="margin-bottom:90px"></div>
				<input class="btn btn-primary btn-large" type="Submit" value="Submit">
				</form>
				</div>
			</div>
		</div>
<%@ include file="footer.jsp"%>
</body>
</html>