<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ page import="model.*, java.util.*,database.*" %>

<%
Account tempaccount = (Account) session.getAttribute("LoggedInUser");

if(tempaccount != null)
{
	if(tempaccount.getType() == 'A' || tempaccount.getType() == 'C' || tempaccount.getType() == 'M')
	{
%>

<script type="text/javascript">
	function setLists()
	{
		<%
		if(session.getAttribute("typeFilter") != null)
		{
			String typeFilter = session.getAttribute("typeFilter").toString();
			String deptFilter = session.getAttribute("deptFilter").toString();
		%>
	
		var ddlt = document.getElementById("type");
		var optst = ddlt.options.length;
		for (var i=0; i<optst; i++){
    		if (ddlt.options[i].value == '<%= typeFilter%>'){
        	ddlt.options[i].selected = true;
        	break;
    		}
		}
		
		var ddld = document.getElementById("departmentID");
		var optsd = ddld.options.length;
		for (var i=0; i<optsd; i++){
    		if (ddld.options[i].value == '<%= deptFilter%>'){
        	ddld.options[i].selected = true;
        	break;
    		}
		}
		<%
		}
		%>
	}
</script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>View Account</title>
    <link href="resources/css/bootstrap.css" rel="stylesheet" type="text/css" />
    <link href="resources/css/bootstrap-responsive.css" rel="stylesheet" type="text/css" />
    <link href="resources/css/application.css" rel="stylesheet" type="text/css" />
    <script src="resources/js/jquery-1.9.1.js"></script>
	<script src="resources/js/jquery-ui-1.9.1.js"></script> 
<style>
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

			th a:link      { text-decoration: none; color: black }
     		th a:visited   { text-decoration: none; color: black }
     		.rows          { background-color: white }
     		.hiliterows    { background-color: #848484; color: white; font-weight: bold }
     		.alternaterows { background-color: #D8D8D8 }
     		.header        { background-color: #2E2E2E; color: #D8D8D8;font-weight: bold }
            .datagrid      { border: 1px solid #C7C5B2; font-family: arial; font-size: 9pt; font-weight: normal }

</style>

<style type="text/css">
	.viewTableSpace {
	margin:0px;padding:0px;
	width:100%;
	box-shadow: 10px 10px 5px #888888;
	border:1px solid #000000;
	
	-moz-border-radius-bottomleft:0px;
	-webkit-border-bottom-left-radius:0px;
	border-bottom-left-radius:0px;
	
	-moz-border-radius-bottomright:0px;
	-webkit-border-bottom-right-radius:0px;
	border-bottom-right-radius:0px;
	
	-moz-border-radius-topright:0px;
	-webkit-border-top-right-radius:0px;
	border-top-right-radius:0px;
	
	-moz-border-radius-topleft:0px;
	-webkit-border-top-left-radius:0px;
	border-top-left-radius:0px;
}.viewTableSpace table{
	width:100%;
	height:100%;
	margin:0px;padding:0px;
}.viewTableSpace tr:last-child td:last-child {
	-moz-border-radius-bottomright:0px;
	-webkit-border-bottom-right-radius:0px;
	border-bottom-right-radius:0px;
}
.viewTableSpace table tr:first-child td:first-child {
	-moz-border-radius-topleft:0px;
	-webkit-border-top-left-radius:0px;
	border-top-left-radius:0px;
}
.viewTableSpace table tr:first-child td:last-child {
	-moz-border-radius-topright:0px;
	-webkit-border-top-right-radius:0px;
	border-top-right-radius:0px;
}.viewTableSpace tr:last-child td:first-child{
	-moz-border-radius-bottomleft:0px;
	-webkit-border-bottom-left-radius:0px;
	border-bottom-left-radius:0px;
}.viewTableSpace tr:hover td{
	
}
.viewTableSpace tr:nth-child(odd){ background-color:#e5e5e5; }
.viewTableSpace tr:nth-child(even)    { background-color:#ffffff; }.viewTableSpace td{
	vertical-align:middle;
	
	
	border:1px solid #000000;
	border-width:0px 1px 1px 0px;
	text-align:center;
	padding:5px;
	font-size:10px;
	font-family:Arial;
	font-weight:normal;
	color:#000000;
}.viewTableSpace tr:last-child td{
	border-width:0px 1px 0px 0px;
}.viewTableSpace tr td:last-child{
	border-width:0px 0px 1px 0px;
}.viewTableSpace tr:last-child td:last-child{
	border-width:0px 0px 0px 0px;
}
.viewTableSpace tr:first-child td{
		background:-o-linear-gradient(bottom, #4c4c4c 5%, #000000 100%);	background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #4c4c4c), color-stop(1, #000000) );
	background:-moz-linear-gradient( center top, #4c4c4c 5%, #000000 100% );
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr="#4c4c4c", endColorstr="#000000");	background: -o-linear-gradient(top,#4c4c4c,000000);

	background-color:#4c4c4c;
	border:0px solid #000000;
	text-align:center;
	border-width:0px 0px 1px 1px;
	font-size:13px;
	font-family:Arial;
	font-weight:bold;
	color:#ffffff;
}
.viewTableSpace tr:first-child:hover td{
	background:-o-linear-gradient(bottom, #4c4c4c 5%, #000000 100%);	background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #4c4c4c), color-stop(1, #000000) );
	background:-moz-linear-gradient( center top, #4c4c4c 5%, #000000 100% );
	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr="#4c4c4c", endColorstr="#000000");	background: -o-linear-gradient(top,#4c4c4c,000000);

	background-color:#4c4c4c;
}
.viewTableSpace tr:first-child td:first-child{
	border-width:0px 0px 1px 0px;
}
.viewTableSpace tr:first-child td:last-child{
	border-width:0px 0px 1px 1px;
}
	
	table.viewTable
	{
	table-layout: fixed;
	width: 100%;
	}
	
	table.viewTable td
	{
	word-wrap: break-word;
	overflow: hidden;
	text-align: center;
	}
	table.viewTable td.genderCol
	{
		width: 54px;
	}
	table.viewTable td.phoneCol
	{
		width: 64px;
	}
</style>

</head>
<body onload='setLists()'>
<%@ include file="header.jsp"%> 
      	<div class="span9">
			<div class="hero-unit">
			            <h1>Account Management</h1>
			            <p>View Account</p>
			</div>
			<div class="row-fluid" >
			<div class="content_space">
			
			<form action='ViewAccountServlet' method='post'>
			
				<table cellspacing=15 cellpadding=15>
				<tr><td>Account type :</td><td>
			
				<select id="type" name="type" style="height: 30px; width:120px;">
				
				<option value="All">All</option>
				
				<%
				Account acc = (Account) session.getAttribute("LoggedInUser");
				
				if(acc.getType() == 'A')
				{
				%>
				<option value="C">Ceo</option>
				<option value="M">Manager</option>
				<option value="F">Filemanager</option>
				<option value="E">Employee</option>
				<%
				}
				else if(acc.getType() == 'C')
				{
				%>
				<option value="M">Manager</option>
				<option value="F">Filemanager</option>
				<option value="E">Employee</option>
				<%
				}
				else
				{
				%>
				<option value="E">Employee</option>
				<%
				}
				%>
				</select>
				</td>
				
				<td></td>
				
				<td>Department :</td>
				
				<td>
				<select id="departmentID" name="departmentID" style="height: 30px; width:240px;">
				<option value="All">All</option>
				
				<%
				if(session.getAttribute("tempList") != null)
				{
					ArrayList<Department> tempList = new ArrayList<Department>();
					tempList = (ArrayList<Department>) session.getAttribute("tempList");
				
					for(int i=0; i<tempList.size(); i++){
						Department dept = tempList.get(i);
						int deptID = dept.getId();
						String deptName = dept.getDepartmentName();
				%>
				<option value=<%= deptID%>><%= deptName%></option>
				<%
					}
				}
				%>
				
				</select>
				</td>
				
				<td></td>
				
				<td><input type="submit" value="View" name="view" onclick="" class="btn"/></td>
				
				</tr>
				</table>
				</form>
				
				<div class="viewTableSpace">
				
				<table cellspacing=10 class="viewTable">
				
				<tr>
				<td>Name</td>
				<td>Username</td>
				<td>Type</td>
				
				<td class="genderCol">Gender</td>
				<td>DOB</td>
				<td class="phoneCol">Phone No.</td>
				<td>Email</td>
				<td>Address</td>
				<td>NRIC</td>
				<td>Department</td>
				</tr>
				
				<%
				if(session.getAttribute("accList") != null)
				{
					ArrayList<Account> accList = new ArrayList<Account>();
					accList = (ArrayList<Account>) session.getAttribute("accList");
					
					ArrayList<String> deptList = new ArrayList<String>();
					deptList = (ArrayList<String>) session.getAttribute("deptList");
					
					for(int i=0; i<accList.size(); i++)
					{
					
					String name = accList.get(i).getName();
					String userName = accList.get(i).getUserName();
					String type = Character.toString(accList.get(i).getType());
					
					String gender = "";
					String dateOfBirth = "";
					String phoneNumber = "";
					String email = "";
					String address = "";
					String NRIC = "";
					String department = "";
					
					if(type.equals("E"))
					{
						Employee emp = (Employee) accList.get(i);
						
						type = "Employee";
						
						gender = emp.getGender();
						dateOfBirth = emp.getDOB().toString();
						phoneNumber = emp.getPhoneNumber();
						email = emp.getEmail();
						address = emp.getAddress();
						NRIC = emp.getNRIC();
						department = deptList.get(i);
					}
					else if(type.equals("F"))
					{
						Filemanager fm = (Filemanager) accList.get(i);
						
						type = "File manager";
						
						gender = fm.getGender();
						dateOfBirth = fm.getDOB().toString();
						phoneNumber = fm.getPhoneNumber();
						email = fm.getEmail();
						address = fm.getAddress();
						NRIC = fm.getNRIC();
						department = deptList.get(i);
					}
					else
					{
						Manager mg = (Manager) accList.get(i);
						
						type = "Manager";
						
						gender = mg.getGender();
						dateOfBirth = mg.getDOB().toString();
						phoneNumber = mg.getPhoneNumber();
						email = mg.getEmail();
						address = mg.getAddress();
						NRIC = mg.getNRIC();
						department = deptList.get(i);
					}
					
				%>
					<tr>
					<td><%= name%></td>
					<td><%= userName%></td>
					<td><%= type%></td>
					
					<td class="genderCol"><%= gender%></td>
					<td><%= dateOfBirth%></td>
					<td class="phoneCol"><%= phoneNumber%></td>
					<td><%= email%></td>
					<td><%= address%></td>
					<td><%= NRIC%></td>
					<td><%= department%></td>
					
					</tr>
				<%
					}
				}
				%>
				
				</table>
				</div>
				
			</div>
		</div>
	</div>
<%@ include file="footer.jsp"%>
</body>

<%
	}
	else
	{
		response.sendRedirect("Error.jsp");
	}
}
else
{
	response.sendRedirect("Error.jsp");
}
%>

</html>