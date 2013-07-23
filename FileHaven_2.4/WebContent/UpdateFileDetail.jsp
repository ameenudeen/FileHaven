<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>FileHaven - Update File</title>
    <%@page import="database.ManagerDBAO,java.util.ArrayList,model.Account,model.Department,database.DepartmentDBAO,database.AccountDBAO,model.Files,org.apache.commons.codec.binary.Base64,security.Security" %>
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
var num=0;


function select_department(id){
	if(document.getElementById(id).checked){
		num++;
		$("#number_department").text(num);
	}
	else{
		if(num==0)
			num++;
		num--;
		$("#number_department").text(num);
	}
}

$(document).ready(function(){
<%
	if(session.getAttribute("LoggedInUser")==null){
		response.sendRedirect("Login.jsp");
		return;
	}
	if(session.getAttribute("UpdateFile")==null){
		response.sendRedirect("ViewFileList.jsp");
		return;
	}
	Account login=(Account)session.getAttribute("LoggedInUser");
	
	if(login.getType()!='C'&&login.getType()!='F'&&login.getType()!='M'){
		session.setAttribute("info_line1", "Access Denied");
		response.sendRedirect("Information.jsp");
		return;
	}
	else if(login.getType()=='M'){
		if(!((Files)session.getAttribute("UpdateFile")).getAccountID().equals(login.getUserName())){
			session.setAttribute("info_line1", "Access Denied");
			response.sendRedirect("Information.jsp");
		}
			
	}
	
	Files file=(Files)session.getAttribute("UpdateFile");
	
	String str=new Integer(file.getFileID()).toString();
	str=Base64.encodeBase64String(Security.encryptByte(str.getBytes(),Security.generateAESKey("SYSTEM_KEY"),"AES"));

%>
document.getElementById("hidden_file_id").value="<%=str%>";
});

function validate_input(){
	var file_name=document.getElementById("input_file_name").value;
	
	$("#information_file_name").text("");
	
	if(!validate_empty(file_name)){
		$("#information_file_name").text("Please enter a file name");
		 $("body").scrollTop($("body").offset().top);
		return false;
	}
	if(file_name.length>20||file_name.length<4){
		$("#information_file_name").text("Name must be 4-20 characters");
		$("body").scrollTop($("body").offset().top);
		return false;
	}
	if(!validate_letter_number(file_name)){
		$("#information_file_name").text("Character allowed: 0-9 A-z");
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
			            <h1>Update File Detail</h1>
			            <p>You can edit information of the file that is available<br/>
			            <p>File Name : <%=file.getFileName()%></p>
			</div>
				<div class="row-fluid" >
				<div class="content_space">
				<form onsubmit="return validate_input()" action="UpdateFileDetailServlet" method="POST">
				<input type='text' id="hidden_file_id" style='display:none' name='hidden_file_id' >
				<p class="subtitle">File</p>
				File Name : <input id="input_file_name" name="FileName" type="text" placeholder="File Name" value="<%=file.getFileName()%>"> * <span class="error" id="information_file_name"></span>
				<br>
				<p class="subtitle" style="margin-top:30px;">Department</p>
				<div class="inner_space">
					<%
					DepartmentDBAO ddb=new DepartmentDBAO();
					ArrayList<Department> pList=ddb.getCompanyDepartment(login.getUserName());
					ddb.remove();
					if(login.getType()!='C'&&login.getType()!='F'){
						int departmentID=-1;
						ManagerDBAO mdb=new ManagerDBAO();
						departmentID=mdb.getManagerDetails(login.getUserName()).getDepartmentID();
						mdb.remove();
						for(int i=0;i<pList.size();i++){
							if(departmentID!=pList.get(i).getId())
								pList.remove(i--);
						}
					}
					for(int i=0;i<pList.size();i++){
						
						String depid=new Integer(pList.get(i).getId()).toString();
						depid=Base64.encodeBase64String(Security.encryptByte(depid.getBytes(),Security.generateAESKey("SYSTEM_KEY"),"AES"));

					%>
					<div><input id="department<%=depid%>" type="checkbox" name="department" value="<%=depid%>"  onclick="select_department('department<%=depid%>')">
					<span style="padding-left:25px;overflow:hidden">
					 <%=pList.get(i).getDepartmentName()%>
					</span>
					</div>
					<% 
					}
					%>
				</div>
				<p style="margin-bottom:0px"><span id="number_department" style="text-decoration:underline">0</span> department/s selected</p>
				<p id="hint_no_department" style="font-style:italic;text-decoration:underline;">*If no department selected,the file will be private</p>
				<div style="margin-bottom:90px"></div>
				<input class="btn btn-primary btn-large" type="Submit" value="Submit">
				</form>
				</div>
			</div>
		</div>
<%@ include file="footer.jsp"%>
</body>
</html>