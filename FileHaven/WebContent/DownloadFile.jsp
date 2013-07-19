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

		$(document).ready(function(){
			<%
			if(session.getAttribute("LoggedInUser")==null){
				response.sendRedirect("Login.jsp");
				return;
			}
				if(session.getAttribute("DownloadFile")==null){
					response.sendRedirect("ViewFileList.jsp");
					return;
				}
				Files file=(Files)session.getAttribute("DownloadFile");
				
				if(!file.getFileDeletedTime().equals("NIL")){
					session.setAttribute("info_line1", "File access denied");
					session.setAttribute("info_line2","File deleted");
					response.sendRedirect("Information.jsp");
				}
				if(file.getEncrypted().equals("FALSE")){
			%>
			$("#double_encrypt").animate({
			    opacity: 0,
			    height:"0px"
			  }, 0,function(){$("#double_encrypt").hide();});
			<%
			}
				String str=new Integer(file.getFileID()).toString();
				str=Base64.encodeBase64String(Security.encryptByte(str.getBytes(),Security.generateAESKey("SYSTEM_KEY"),"AES"));
			%>
			document.getElementById("hidden_file_id").value="<%=str%>";
		});
		
		function validate(){
			$("#information_double_enc").text("");
			<%if(file.getEncrypted().equals("TRUE")){
			%>
			var enc1=document.getElementById("input_double_enc1");
			var enc2=document.getElementById("input_double_enc2");
			if(enc1!=enc2){
				$("#information_double_enc").text("Inputs are not similar");
				return false;
			}
			<%	
			}
			%>
			return true;
		}
</script>
</head>
<body>
<%@ include file="header.jsp"%> 
      	<div class="span9">
			<div class="hero-unit">
			            <h1>Download File</h1>
			            <p>You can download the file you selected here</p>
			            <p>File Name : <%=file.getFileName()%></p>
			            <br>
			            <p>Please notice that your information will be recorded.</p>
			</div>
			<div class="row-fluid" >
				<div class="content_space">
				<form onsubmit="return validate()" method="POST" action="DownloadFileServlet">
					<p class="subtitle">File Information</p>
					<input type='text' id="hidden_file_id" style='display:none' name='hidden_file_id' >
					<p><span style="padding-right:67px">File Name</span>: <span><%=file.getFileName() %></span></p>
			        <p><span style="padding-right:33px">Extension Type</span>: <span><%=file.getFileExtension() %></span></p>
					<p><span style="padding-right:75px">File Size</span>: <span>
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
					%></span></p>
			 		<p><span style="padding-right:47px">Uploaded By</span>: <span><%=file.getAccountID()%></span></p>
					<p><span style="padding-right:26px">Uploaded When</span>: <span>
					<% 
						Scanner sc=new Scanner(file.getFileUploadedTime());
						sc.useDelimiter("-");
					%>
						<%=sc.next()%> -
						<%switch(Integer.parseInt(sc.next())){
						case 1:%><%="Jan"%><%break;
						case 2:%><%="Feb"%><%break;
						case 3:%><%="Mar"%><%break;
						case 4:%><%="Apr"%><%break;
						case 5:%><%="May"%><%break;
						case 6:%><%="Jun"%><%break;
						case 7:%><%="Jul"%><%break;
						case 8:%><%="Aug"%><%break;
						case 9:%><%="Sep"%><%break;
						case 10:%><%="Oct"%><%break;
						case 11:%><%="Nov"%><%break;
						case 12:%><%="Dec"%><%
						}%>
						- <%=sc.next()%> &nbsp; &nbsp; <%=sc.next()%>
						<%sc.close();%>
					</span></p>
					<p><span style="padding-right:65px">SHA-256</span> : <span><%=file.getHash()%></span></p>
					<br>
					<br>
					<div id="double_encrypt">
					<span style="padding-right:10px">Encryption Key</span> : <input id="input_double_enc1" name="input_double_enc" style="width:100px" type="password"> <span class="error" id="information_double_enc"></span><br>
					<span style="padding-right:20px">Re-Enter Key</span> : <input id="input_double_enc2" style="width:100px" type="password"> 
					<p style="color:red;text-decoration:underline;font-weight:bold;font-style:italic">Download will start even if key is incorrect</p>
					</div>
					<br>
					<p style="color:red;text-decoration:underline;font-weight:bold;font-style:italic">Your information will be stored once download started</p>
					<input class="btn btn-primary btn-large" type="Submit" value="Download"><br>
					<a class="btn btn-primary btn-large" href="StartHashCheckServlet?ID=<%=str%>">Check Downloaded File</a>
				</form>
				</div>
			</div></div>
<%@ include file="footer.jsp"%>
</body>
</html>