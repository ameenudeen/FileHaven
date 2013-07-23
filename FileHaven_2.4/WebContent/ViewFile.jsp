<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>FileHaven - View File</title>
    <%@page import="model.Account,model.Department,java.util.Scanner,  model.Files,security.Security,org.apache.commons.codec.binary.Base64" %>
    <link href="resources/css/bootstrap.css" rel="stylesheet" type="text/css" />
    <link href="resources/css/bootstrap-responsive.css" rel="stylesheet" type="text/css" />
    <link href="resources/css/application.css" rel="stylesheet" type="text/css" />
    <script src="resources/js/jquery-1.9.1.js"></script>
	<script src="resources/js/jquery-ui-1.9.1.js"></script> 
<style>
.information{
padding-top:20px;
width:400px;
overflow:hidden;
}

.information p{
padding:0px;
margin:0px;
}
.information_button{
position:absolute;
top:140px;
right:100px;
}
.information_button a{
width:200px;
margin-bottom:15px;
display:block;
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
</style>
<script>
$(document).ready(function(){
	<%
	if(session.getAttribute("LoggedInUser")==null){
		response.sendRedirect("Login.jsp");
		return;
	}
	Account login=(Account)session.getAttribute("LoggedInUser");
	
	Files file=(Files)session.getAttribute("SelectedFile");
	if(file==null){
		response.sendRedirect("ViewFileList.jsp");
		return;
	}
	if(login.getType()=='E'){
		%>
		$("#update_btn").hide();
		$("#delete_btn").hide();
		$("#update_btn").hide();
		<%
	}
	else if(login.getType()=='M'){
		if(file.getAccountID()!=login.getUserName()){
		%>
		$("#update_btn").hide();
		$("#delete_btn").hide();
		$("#update_btn").hide();
		<%
		}
	}
	/*
	get uploader of file - Account
	compare uploader's company and logged in account company -Boolean
	
	check account type
	if CEO/FileManager
	then allow access
	
	else
	check privilege of the file
	if return null then allow access
	if return list
			-check compare if department== account's department
	*/
	if(file.getFileDeletedTime().equals("NIL")){
	%>
	$(".deleted").hide();
	<%}
	else
	{%>
	$(".not_deleted").hide();
	<%
	}
	%>
});

</script>
</head>
<body>
<%@ include file="header.jsp"%> 
      	<div class="span9">
			<div class="hero-unit">
			            <h1>File Detail</h1>
			            <div class="information">
			            <p><span style="padding-right:70px">File Name</span>: <span><%=file.getFileName() %></span></p>
			            <p><span style="padding-right:26px">Extension Type</span>: <span><%=file.getFileExtension()%></span></p>
			            <p><span style="padding-right:83px">File Size</span>: <span>
						<%
					double size=file.getFileSize();
					if(size>=1048576){
							size=(size/1024/1024);
							size*=1000;
							size=(int)size;
							size/=1000;
							%>
							<%=size%> GB
							<%
					}else if(size>=1024){
							size/=1024;
							size*=1000;
							size=(int)size;
							size/=1000;
							%>
							<%=size%> MB
							<%
					} 
					else{
						%>
							<%=size%> KB
						<%
					}
					%>
						</span></p>
			            <p><span style="padding-right:47px">Uploaded By</span>: <span><%=file.getAccountID()%></span></p>
			            <p><span style="padding-right:20px">Uploaded When</span>: <span>
						<% 
						Scanner sc=new Scanner(file.getFileUploadedTime());
						sc.useDelimiter("-");
						%>
						<%=sc.next()%>-<%=sc.next()%>-<%=sc.next()%>  <%=sc.next()%>
						<%sc.close();%>
						</span></p>
			            <p><span style="padding-right:36px">Deleted When</span>: <span>
						<% 
						if(file.getFileDeletedTime().equals("NIL")){
							%>
								<%="Valid"%>
							<%
						}
						else{
						sc=new Scanner(file.getFileDeletedTime());
						sc.useDelimiter("-");
					%>
						<%=sc.next()%>-<%=sc.next()%>-<%=sc.next()%>  <%=sc.next()%>
						<%sc.close();}%>
						</span></p> 
						<br>
						</div>
						<%
							String str=new Integer(file.getFileID()).toString();
							str=Base64.encodeBase64String(Security.encryptByte(str.getBytes(),Security.generateAESKey("SYSTEM_KEY"),"AES"));
						%>
						<div class="not_deleted">
						
						<a href="StartDownloadFileServlet?ID=<%=str%>" class="btn btn-primary btn-large">Start Download File</a>
						<a id ="update_btn" href="StartUpdateFileServlet?ID=<%=str%>" class="btn btn-primary btn-large">Edit File Information</a>
						<a id="delete_btn" href="StartDeleteFileServlet?ID=<%=str%>" class="btn btn-primary btn-large">Delete File</a>
						</div>
						<div class="deleted">
						<a id="recover_btn" href="StartRecoverFileServlet?ID=<%=str%>" class="btn btn-primary btn-large">Recover File</a>
						</div>
			</div>
			<div style="min-height:248px" class="row-fluid" >
				
			</div></div>
<%@ include file="footer.jsp"%>
</body>
</html>