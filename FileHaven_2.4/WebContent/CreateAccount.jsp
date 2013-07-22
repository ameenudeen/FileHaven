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
	function storeLoginAccount(){
        <%
        if(session.getAttribute("userName") == null){
        %>
		<%
		}
		else
		{
		
		String userName = session.getAttribute("userName").toString();
		String name = session.getAttribute("name").toString();
		String password = session.getAttribute("password").toString();
		String type = session.getAttribute("type").toString();
		String gender = session.getAttribute("gender").toString();
		String dateOfBirth = session.getAttribute("dateOfBirth").toString();
		String phoneNumber = session.getAttribute("phoneNumber").toString();
		String email = session.getAttribute("email").toString();
		String address = session.getAttribute("address").toString();
		String NRIC = session.getAttribute("NRIC").toString();
		%>
		
		document.getElementById("userName").value = '<%= userName%>';
		document.getElementById("name").value = '<%= name%>';
		document.getElementById("password").value = '<%= password%>';
		document.getElementById("dateOfBirth").value = '<%= dateOfBirth%>';
		document.getElementById("phoneNumber").value = '<%= phoneNumber%>';
		document.getElementById("email").value = '<%= email%>';
		document.getElementById("address").value = '<%= address%>';
		document.getElementById("NRIC").value = '<%= NRIC%>';
		
		var ddln = document.getElementById("gender");
		var optsn = ddln.options.length;
		for (var i=0; i<optsn; i++){
    		if (ddln.options[i].value == '<%= gender%>'){
        	ddln.options[i].selected = true;
        	break;
    		}
		}
		
		var ddlt = document.getElementById("type");
		var optst = ddlt.options.length;
		for (var i=0; i<optst; i++){
    		if (ddlt.options[i].value == '<%= type%>'){
        	ddlt.options[i].selected = true;
        	break;
    		}
		}
		<%
		}
		%>
}
</script>

<script type="text/javascript">
	function showdv()
	{	
		var ele = document.getElementById("type");
		var selected = ele.options[ele.selectedIndex].value;
	
		if(selected == "C")
		{
		document.getElementById("box1").style.display='block';
		document.getElementById("box2").style.display='block';
		}
		
		else
		{
		document.getElementById("box1").style.display='none';
		document.getElementById("box2").style.display='none';
		}
	}
</script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Create Account</title>
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
</style>

<style type="text/css">
	.invalidAlert
	{
	color:#CC0000;
	font-weight: bold;
	}
</style>

</head>
<body onload='storeLoginAccount()'>
<%@ include file="header.jsp"%> 
      	<div class="span9">
			<div class="hero-unit">
			            <h1>Account Management</h1>
			            <p>Create Account</p>
			</div>
			<div class="row-fluid" >
			<div class="content_space">
			
			<%
			String msg = (String)session.getAttribute("msg");
			if (msg == null) {
			} else {
			%>
			<div style="font-weight: bold;">
			<%= msg %>
			</div>
			<br><br>
			<%
			} 
			%>
			
			<form action='CreateAccountServlet' method='post'>
				
				<table cellspacing=15 cellpadding=10>
				<tr><td>Username :</td><td><input type=text id="userName" name="userName" style="height: 20px; width:180px"/></td>
				<td>
				<%
				if(session.getAttribute("unmsg") != null)
				{
				%>
				<div class="invalidAlert">
				<%= session.getAttribute("unmsg")%>
				</div>
				<%
				}
				%>
				</td></tr>
				
				<tr><td>Password :</td><td><input type=password id="password" name="password" style="height: 20px; width: 180px;"/></td></tr>
				
				<tr><td>Account Type :</td><td>
				<select id="type" name="type" onchange="showdv()" style="height: 30px; width:120px;">
				
				<%
				Account acc = (Account) session.getAttribute("LoggedInUser");
				
				if(acc.getType() == 'A')
				{
				%>
				<option value="M">Manager</option>
				<option value="F">Filemanager</option>
				<option value="E">Employee</option>
				<option value="C">Ceo</option>
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
				</select></td>
				
				</tr>
				
				<tr><td></td></tr>
				
				<tr><td>Name :</td><td><input type=text id="name" name="name" style="height: 20px; width:180px"/></td></tr>
				<tr><td>NRIC :</td><td><input type=text id="NRIC" name="NRIC" style="height: 20px; width:120px;"/></td>
				<td>
				<%
				if(session.getAttribute("nricmsg") != null)
				{
				%>
				<div class="invalidAlert">
				<%= session.getAttribute("nricmsg")%>
				</div>
				<%
				}
				%>
				</td></tr>
				
				<tr><td>Gender :</td><td>
				<select id="gender" name="gender" style="height: 30px; width:100px;">
				<option value="Male">Male</option>
				<option value="Female">Female</option>
				</select>
				</td></tr>
				
				<tr><td>Date Of Birth :</td><td><input type=text id="dateOfBirth" name="dateOfBirth" style="height: 20px; width:120px"/></td>
				<td>
				<%
				if(session.getAttribute("dobmsg") != null)
				{
				%>
				<div class="invalidAlert">
				<%= session.getAttribute("dobmsg")%>
				</div>
				<%
				}
				else
				{
				%>
				(YYYY-MM-DD)
				<%
				}
				%>
				</td>
				</tr>
				
				<tr><td>Phone Number :</td><td><input type=text id="phoneNumber" name="phoneNumber" style="height: 20px; width:100px;"/></td>
				<td>
				<%
				if(session.getAttribute("phmsg") != null)
				{
				%>
				<div class="invalidAlert">
				<%= session.getAttribute("phmsg")%>
				</div>
				<%
				}
				%>
				</td></tr>
				
				<tr><td>Email :</td><td><input type=text id="email" name="email" style="height: 20px; width: 180px;"/></td>
				<td>
				<%
				if(session.getAttribute("emmsg") != null)
				{
				%>
				<div class="invalidAlert">
				<%= session.getAttribute("emmsg")%>
				</div>
				<%
				}
				%>
				</td></tr>
				
				<tr><td>Address :</td><td><input type=text id="address" name="address" style="height: 20px; width: 180px;"/></td></tr>
				
				
				<tr><td><div id="box1" style="display:none;">CompanyID :</div></td><td>
				
				<div id="box2" style="display:none;">
				<select id="companyID" name="companyID" style="height: 30px; width:280px;">
				
				<%
				ArrayList<Company> companyList = (ArrayList) session.getAttribute("companyList");
				for(int i=0; i<companyList.size(); i++)
				{
				int companyID = companyList.get(i).getCompanyID();
				String companyName = companyList.get(i).getCompanyName();
				%>
				<option value="<%= companyID%>"><%out.print(companyName);%></option>
				<%
				}
				%>
				
				</select>
				</div>
				</td></tr>
				
				</table>

				<p><input type="submit" value="Submit" name="submit" onclick="" class="btn"/></p>
				</form>
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