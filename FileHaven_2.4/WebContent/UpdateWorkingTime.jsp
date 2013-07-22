<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ page import="model.*, java.util.*,database.*, java.text.SimpleDateFormat" %>

<%
Account tempaccount = (Account) session.getAttribute("LoggedInUser");

if(tempaccount != null)
{
	if(tempaccount.getType() == 'A' || tempaccount.getType() == 'C')
	{
%>

<script type="text/javascript">
	function updateWorkingTime(){
		
		<%
		Company com = (Company) session.getAttribute("company");
		String startHour = com.getStartTime().substring(0, 2);
		String endHour = com.getEndTime().substring(0, 2);
		
		String startMinute = com.getStartTime().substring(3, 5);
		String endMinute = com.getEndTime().substring(3, 5);
		%>
		
		var ddlsh = document.getElementById("startHour");
		var optssh = ddlsh.options.length;
		for (var i=0; i<optssh; i++){
    		if (ddlsh.options[i].value == '<%= startHour%>'){
        	ddlsh.options[i].selected = true;
        	break;
    		}
		}
    		
    	var ddleh = document.getElementById("endHour");
    	var optseh = ddleh.options.length;
    	for (var i=0; i<optseh; i++){
        	if (ddleh.options[i].value == '<%= endHour%>'){
           	ddleh.options[i].selected = true;
           	break;
        	}
    	}
        	
        var ddlsm = document.getElementById("startMinute");
    	var optssm = ddlsm.options.length;
    	for (var i=0; i<optssm; i++){
        	if (ddlsm.options[i].value == '<%= startMinute%>'){
            ddlsm.options[i].selected = true;
            break;
        	}
    	}
        	
        var ddlem = document.getElementById("endMinute");
    	var optsem = ddlem.options.length;
    	for (var i=0; i<optsem; i++){
        	if (ddlem.options[i].value == '<%= endMinute%>'){
            ddlem.options[i].selected = true;
            break;
        	}
    	}
}
</script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Update Account</title>
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
<body onload='updateWorkingTime()'>
<%@ include file="header.jsp"%> 
      	<div class="span9">
			<div class="hero-unit">
			            <h1>Company Management</h1>
			            <p></p>
			</div>
			<div class="row-fluid" >
			<div class="content_space">
			
			<table cellspacing=15>
			<% 
			if (session.getAttribute("wtmsg") == null) {
			%>
			
			<%
			} else { 
			%>
			<tr><td><div style="font-weight: bold;"><%= session.getAttribute("wtmsg")%></div></td></tr>
			<%
			} 
			%>
			</table>
			
			<br><br>
			
			<form action='UpdateWorkingTimeServlet' method='post'>
				
				<table cellspacing=15 cellpadding=10>
				<tr><td>Start Hour :</td><td>
				<select id="startHour" name="startHour" style="height: 30px; width:240px;">
				<%
				for(int i=0; i<24; i++)
				{
					String hour = "";
					String display = "";
					if(i < 10)
					{
						hour = "0" + i;
					}
					else
					{
						hour = Integer.toString(i);
					}
					
					if(i == 0)
					{
						display = hour + "   " + "(" + hour + ":00 a.m. Midnight)";
					}
					else if(i < 12)
					{
						display = hour + "   " + "(" + hour + ":00 a.m.)";
					}
					else if (i == 12)
					{
						display = hour + "   " + "(" + hour + ":00 p.m. Noon)";
					}
					else
					{
						display = hour + "   " + "(" + (i - 12) + ":00 p.m.)";
					}
				%>
				<option value="<%= hour%>"><%= display%></option>
				<%
				}
				%>
				</select></td>
				
				<td></td>
				
				<td>Start Minute :</td>
				<td>
				<select id="startMinute" name="startMinute" style="height: 30px; width:120px;">
				<%
				for(int i=0; i<60; i++)
				{
					String minute = "";
					if(i < 10)
					{
						minute = "0" + i;
					}
					else
					{
						minute = Integer.toString(i);
					}
				%>
				<option value="<%= minute%>"><%= minute%></option>
				<%
				}
				%>
				</select></td>
				</tr>
				
				<tr><td>End Hour :</td><td>
				<select id="endHour" name="endHour" style="height: 30px; width:240px;">
				<%
				for(int i=0; i<24; i++)
				{
					String hour = "";
					String display = "";
					if(i < 10)
					{
						hour = "0" + i;
					}
					else
					{
						hour = Integer.toString(i);
					}
					
					if(i == 0)
					{
						display = hour + "   " + "(" + hour + ":00 a.m. Midnight)";
					}
					else if(i < 12)
					{
						display = hour + "   " + "(" + hour + ":00 a.m.)";
					}
					else if (i == 12)
					{
						display = hour + "   " + "(" + hour + ":00 p.m. Noon)";
					}
					else
					{
						display = hour + "   " + "(" + (i - 12) + ":00 p.m.)";
					}
				%>
				<option value="<%= hour%>"><%= display%></option>
				<%
				}
				%>
				</select></td>
				
				<td></td>
				
				<td>End Minute :</td>
				<td>
				<select id="endMinute" name="endMinute" style="height: 30px; width:120px;">
				<%
				for(int i=0; i<60; i++)
				{
					String minute = "";
					if(i < 10)
					{
						minute = "0" + i;
					}
					else
					{
						minute = Integer.toString(i);
					}
				%>
				<option value="<%= minute%>"><%= minute%></option>
				<%
				}
				%>
				</select></td>
				</tr>
				
				<tr></tr>
				
				<tr><td><input type="submit" value="Update" name="button" onClick="" class="btn"/></td></tr>
				</table>
				
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