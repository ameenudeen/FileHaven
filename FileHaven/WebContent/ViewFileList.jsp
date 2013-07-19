<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>FileHaven - View File List</title>
   <%@page import="database.ManagerDBAO,database.EmployeeDBAO,model.Privilege,database.PrivilegeDBAO,model.Account,model.Manager,model.Employee,model.Department,model.Company,database.AccountDBAO,database.DepartmentDBAO,java.util.Scanner,java.util.ArrayList,security.Security,model.Files,org.apache.commons.codec.binary.Base64,database.FileDBAO" %>
    <link href="resources/css/bootstrap.css" rel="stylesheet" type="text/css" />
    <link href="resources/css/bootstrap-responsive.css" rel="stylesheet" type="text/css" />
    <link href="resources/css/application.css" rel="stylesheet" type="text/css" />
    <link href="resources/css/dataTable.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="resources/css/jquery-ui.css">
	<script src="resources/js/jquery-1.9.1.js"></script>
	<script src="resources/js/jquery-ui-1.9.1.js"></script> 
	<script src="resources/js/jquery.dataTables.js"></script>
<style>
.inner_space{
width:800px;
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
.department_list{
background-image: linear-gradient(left , rgb(245,235,238) 14%, rgb(197,200,235) 57%, rgb(220,215,245) 79%);
background-image: -o-linear-gradient(left , rgb(245,235,238) 14%, rgb(197,200,235) 57%, rgb(220,215,245) 79%);
background-image: -moz-linear-gradient(left , rgb(245,235,238) 14%, rgb(197,200,235) 57%, rgb(220,215,245) 79%);
background-image: -webkit-linear-gradient(left , rgb(245,235,238) 14%, rgb(197,200,235) 57%, rgb(220,215,245) 79%);
background-image: -ms-linear-gradient(left , rgb(245,235,238) 14%, rgb(197,200,235) 57%, rgb(220,215,245) 79%);

background-image: -webkit-gradient(
	linear,
	left top,
	right top,
	color-stop(0.14, rgb(245,235,238)),
	color-stop(0.57, rgb(197,200,235)),
	color-stop(0.79, rgb(220,215,245))
);

}
.inner_content{
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
			session.setAttribute("info_line1", "Access Denied");
			response.sendRedirect("Information.jsp");
			return;
		}
		ArrayList<Department> pList=new DepartmentDBAO().getCompanyDepartment(login.getUserName());
		System.out.println("PList:"+pList.size());
		if(login.getType()!='C'&&login.getType()!='F'){
			int departmentID=-1;
			if(login.getType()=='E')
				departmentID=new EmployeeDBAO().getEmployeeDetails(login.getUserName()).getDepartmentID();
			if(login.getType()=='M')
				departmentID=new ManagerDBAO().getManagerDetails(login.getUserName()).getDepartmentID();
			for(int i=0;i<pList.size();i++){
				if(departmentID!=pList.get(i).getId())
					pList.remove(i--);
			}
		}
		%>
		$('#search_table').dataTable({
			"bPaginate":true,
			"bLengthChange":true,
			"bFilter":true,
			"bSort":true,
			"bInfo":false,
			"bAutoWidth":false
		});
	});
	
	$(function() {
		var availableTags = [
		<%
	      ArrayList<Files> files=new FileDBAO().getFileList(login.getUserName());
		
		if(login.getType()!='C'&&login.getType()!='F'){
			for(int i=0;i<files.size();i++){
				//filter privilege
				ArrayList<Privilege> privilege=new PrivilegeDBAO().getPrivilege(files.get(i).getFileID());
				if(privilege==null)
					privilege=new ArrayList<Privilege>();
				boolean permit=false;
				if(privilege.isEmpty()){
					if(login.getUserName().equals(files.get(i).getAccountID())){
						continue;
					}
					files.remove(i--);
					continue;
				}
				for(Privilege p:privilege){
					for(int j=0;j<pList.size();j++){
						if(p.getDepartmentID()==pList.get(j).getId()){
							permit=true;
							break;
						}
					}
					if(permit)
						break;
				}
				if(!permit)
					files.remove(i--);
			}
		}

			for(int i=0;i<files.size();i++){
	      %> "<%=files.get(i).getFileName()%>"<%
	      	if(i<(files.size()-1)){
	%>,
	      <%
	      	}
	      }
	      %>
		];
		$( "#search_table_filter input[type=text]" ).autocomplete({
			source: availableTags
		});
	});
</script>
</head>
<body>

<%@ include file="header.jsp"%> 
      	<div class="span9">
			<div class="hero-unit">
			            <h1>View File List</h1>
			            <p>You can view list of files that u can access here:<br/></p>
			            Department that you can access :<br/> 
			            <div class="department_list" style="height:100px;overflow:auto">
						<%
						
						for(int i=0;i<pList.size();i++){
						%>
							<%=pList.get(i).getDepartmentName()%><br/>
						<%
						}
						%>
			            </div>
			</div>
				<div class="row-fluid" >
				<div class="content_space">
				
				<p class="subtitle" style="margin-top:30px;">Available Files</p>
						<table style="width:800px" id="search_table" class="search_table" >
							<thead>
								<tr>
									<th style="width:150px">File Name</th>
									<th style="width:150px">Uploader</th>
									<th style="width:150px">Size</th>
									<th style="width:175px">Uploaded Time</th>
									<th style="width:175px">Deleted Time</th>
								</tr>
							</thead>
							<tbody>
							<% 
							%>
							<%for(int i=0;i<files.size();i++){%>
							 			<tr>
							 				<%
							 				String str=new Integer(files.get(i).getFileID()).toString();
							 				str=Base64.encodeBase64String(Security.encryptByte(str.getBytes(),Security.generateAESKey("SYSTEM_KEY"),"AES"));
											%>
							 				<td><a href="ViewFileServlet?FileID=<%=str%>"><%=files.get(i).getFileName()%></a></td>
							 				<td><a href="" ><%=files.get(i).getAccountID()%></a></td>
							 				<td><%
					double size=files.get(i).getFileSize();
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
					%></td>
											<td><% 
											Scanner sc=new Scanner(files.get(i).getFileUploadedTime());
											sc.useDelimiter("-");
										%>
											<%=sc.next()%>-<%=sc.next()%>-<%=sc.next()%> <%=sc.next()%>
											<%sc.close();%></td>
							 				<td><% 
						if(files.get(i).getFileDeletedTime().equals("NIL")){
							%>
								<%="Valid"%>
							<%
						}
						else{
						sc=new Scanner(files.get(i).getFileDeletedTime());
						sc.useDelimiter("-");
					%>
						<%=sc.next()%>-<%=sc.next()%>-<%=sc.next()%>  <%=sc.next()%>
						<%sc.close();}%></td>
							 			</tr>
							 <%}%>
							</tbody>
						</table>
				</div>
			</div>
		</div>
<%@ include file="footer.jsp"%>
</body>
</html>