<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>FileHaven - Upload File</title>
     <%@page import="database.FileDBAO,database.CompanyDBAO,database.ManagerDBAO,java.util.ArrayList,database.AccountDBAO,model.Account,model.Manager,model.Employee,model.Department,model.Company,database.DepartmentDBAO,model.Files,org.apache.commons.codec.binary.Base64,security.Security" %>
    <link href="resources/css/bootstrap.css" rel="stylesheet" type="text/css" />
    <link href="resources/css/bootstrap-responsive.css" rel="stylesheet" type="text/css" />
    <link href="resources/css/application.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="resources/css/keyboard.css" />
    <script type="text/javascript" src="resources/js/keyboard.js" charset="UTF-8"></script>
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

$(document).ready(function(){
	<%
	if(session.getAttribute("LoggedInUser")==null){
		response.sendRedirect("Login.jsp");
		return;
	}
	
	Account login=(Account)session.getAttribute("LoggedInUser");
	
	if(login.getType()!='C'&&login.getType()!='F'&&login.getType()!='M'){
		session.setAttribute("info_line1", "Access Denied");
		response.sendRedirect("Information.jsp");
		return;
	}
	%>
	$('#double_encrypt_checkbox').attr('checked', false);
	$("#double_enc").animate({
	    opacity: 0,
	    height:"0px"
	  }, 0 );
	$("#double_enc").hide();
});

function double_encrypt(){
	if(document.getElementById('double_encrypt_checkbox').checked){
		$("#double_enc").show();
		$("#double_enc").animate({
		    opacity: 1,
		    height:"70px"
		  }, 500 );
		
	}
	else{
		$("#double_enc").animate({
		    opacity: 0,
		    height:"0px"
		  }, 500,function(){$("#double_enc").hide();} ); 
	}
}
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
function validate_input(){
	var file_name=document.getElementById("input_file_name").value;
	var file_browse=document.getElementById("input_file_browse").value;
	var file_enc_1=document.getElementById("double_enc_1").value;
	var file_enc_2=document.getElementById("double_enc_2").value;
	var double_enc_checkbox=document.getElementById("double_encrypt_checkbox");
	
	$("#information_file_name").text("");
	$("#information_file_browse").text("");
	$("#information_double_enc").text("");
	
	
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
	if(!validate_empty(file_browse)){
		$("#information_file_browse").text("Please select a file");
		$("body").scrollTop($("body").offset().top);
		return false;
	}
	if(double_enc_checkbox.checked){
		if(!validate_empty(file_enc_1)){
			$("#information_double_enc").text("Both key must not empty");
			$("body").scrollTop($("body").offset().top);
			return false;
		}
		if(!validate_empty(file_enc_2)){
			$("#information_double_enc").text("Both key must not empty");
			$("body").scrollTop($("body").offset().top);
			return false;
		}
		if(file_enc_1.length>30||file_enc_1.length<6){
			$("#information_double_enc").text("Password must be 6-30 characters");
			$("body").scrollTop($("body").offset().top);
			return false;
		}
		if(file_enc_2.length>30||file_enc_2.length<6){
			$("#information_double_enc").text("Password must be 6-30 characters");
			$("body").scrollTop($("body").offset().top);
			return false;
		}
		if(file_enc_1!=file_enc_2){
			$("#information_double_enc").text("Password must be same");
			$("body").scrollTop($("body").offset().top);
			return false;
		}
	}
	var file = document.getElementById('input_file_browse').files[0];
	if(file.size>(50*1024*1024)){
		$("#information_file_browse").text("Maximum size allowed:50mb");
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
			            <h1>Upload File</h1>
			            <%
			            	CompanyDBAO cdb=new CompanyDBAO();
			            	int space=cdb.getCompanyDetails(login.getCompanyID()).getStorageSpace();
			            	cdb.remove();
			            	double used_space=0;
			            	FileDBAO fdb=new FileDBAO();
			            	ArrayList<Files> files=fdb.getFileList(login.getUserName());
			            	fdb.remove();
			            	for(int i=0;i<files.size();i++){
			            		used_space+=files.get(i).getFileSize();
			            	}
			            	
			            	//convert format
			            	used_space/=1024;
			            	used_space/=1024;
			            	used_space*=1000;
			            	used_space=(int)used_space;
			            	used_space/=1000;
			            %>
			            <p>You can upload and share your file here<br/>
			            Your company's space :  <%=used_space%> GB / <%=space %>.0 GB</p>
			            <div style="width:300px" class="progress progress-striped active">
											<div class="bar" style="width: <%=used_space/space*100%>%;"></div>
										</div>
			            <a href="Index.jsp#start_using" class="btn btn-primary btn-large">Add more space now!<br/>Click to view detail</a>
			</div>
				<div class="row-fluid" >
				<div class="content_space">
				<p class="subtitle">File</p>
				<form onsubmit="return validate_input()" method="POST" action="UploadFileServlet" enctype="multipart/form-data">
				File Name : <input id="input_file_name" name="FileName" placeholder="File Name" type="text"> * <span id="information_file_name" class="error"></span>
				<br>
				<input id="input_file_browse" name="upload_file" type="file" value="Browse"> * <span id="information_file_browse" class="error"></span>
				<p id="hint_file_size" style="font-style:italic;text-decoration:underline;">*Maximum size for 1 file : 50 MB</p>
				<br><br>
				<input onclick="double_encrypt()" id="double_encrypt_checkbox" type="checkbox" name="encryption_checkbox"> Encrypt the file
				<div id="double_enc">
				<span style="padding-right:10px">Encryption Key</span> : <input class="keyboardInput" id="double_enc_1" style="width:100px" name="input_double_enc" type="password"> * <span id="information_double_enc" class="error"></span></span><br>
				<span style="padding-right:20px">Re-Enter Key</span> : <input class="keyboardInput" id="double_enc_2" style="width:100px" type="password">
				</div>
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
					//Encrypt the department ID
					String depid=Integer.toString(pList.get(i).getId());
					depid=Base64.encodeBase64String(Security.encryptByte(depid.getBytes(),Security.generateAESKey("SYSTEM_KEY"),"AES"));

					%>
					
					<div><input id="department<%=depid%>" onclick="select_department('department<%=depid%>')" type="checkbox" name="department" value="<%=depid%>">
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