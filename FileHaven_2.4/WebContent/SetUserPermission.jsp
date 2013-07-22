<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ page import="model.*, java.util.*,database.*" %>

<%
Account temp = (Account) session.getAttribute("LoggedInUser");

if(temp != null)
{
	if(temp.getType() == 'A' || temp.getType() == 'C' || temp.getType() == 'M')
	{
%>

<script type="text/javascript">
	function setSelectedAccount(){
        <%
        Permission pm = (Permission) session.getAttribute("permission");
        
        if(pm == null){
        %>
		<%
		}
		else
		{
		%>
		
		var ddln = document.getElementById("name");
		var optsn = ddln.options.length;
		for (var i=0; i<optsn; i++){
    		if (ddln.options[i].value == '<%= pm.getUserName()%>'){
        	ddln.options[i].selected = true;
        	break;
    		}
		}
		
		document.getElementById("startTimeYear").value = '<%= pm.getStartTime().substring(0, 4)%>';
		document.getElementById("startTimeMonth").value = '<%= pm.getStartTime().substring(5, 7)%>';
		document.getElementById("startTimeDay").value = '<%= pm.getStartTime().substring(8, 10)%>';
		
		document.getElementById("endTimeYear").value = '<%= pm.getEndTime().substring(0, 4)%>';
		document.getElementById("endTimeMonth").value = '<%= pm.getEndTime().substring(5, 7)%>';
		document.getElementById("endTimeDay").value = '<%= pm.getEndTime().substring(8, 10)%>';
		
		var ddlsh = document.getElementById("startHour");
		var optssh = ddlsh.options.length;
		for (var i=0; i<optssh; i++){
    		if (ddlsh.options[i].value == '<%= pm.getExtendStart().substring(0, 2)%>'){
        	ddlsh.options[i].selected = true;
        	break;
    		}
		}
		
		var ddlsm = document.getElementById("startMinute");
		var optssm = ddlsm.options.length;
		for (var i=0; i<optssm; i++){
    		if (ddlsm.options[i].value == '<%= pm.getExtendStart().substring(3, 5)%>'){
        	ddlsm.options[i].selected = true;
        	break;
    		}
		}
		
		var ddleh = document.getElementById("endHour");
		var optseh = ddleh.options.length;
		for (var i=0; i<optseh; i++){
    		if (ddleh.options[i].value == '<%= pm.getExtendEnd().substring(0, 2)%>'){
        	ddleh.options[i].selected = true;
        	break;
    		}
		}
		
		var ddlem = document.getElementById("endMinute");
		var optsem = ddlem.options.length;
		for (var i=0; i<optsem; i++){
    		if (ddlem.options[i].value == '<%= pm.getExtendEnd().substring(3, 5)%>'){
        	ddlem.options[i].selected = true;
        	break;
    		}
		}
		
		<%
		}
		%>
}
</script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Set User Permission</title>
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
<body onload='setSelectedAccount()'>
<%@ include file="header.jsp"%> 
      	<div class="span9">
			<div class="hero-unit">
			            <h1>Set User Permission</h1>
			            <p></p>
			</div>
			<div class="row-fluid" >
			<div class="content_space">
			
			<table cellspacing=15>
			<% 
			if (session.getAttribute("supmsg") == null) {
			%>
			
			<%
			} else { 
			%>
			<tr><td><div style="font-weight: bold;"><%= session.getAttribute("supmsg")%></div></td></tr>
			<%
			} 
			%>
			
			<% 
			if (session.getAttribute("damsg") == null) {
			%>
			
			<%
			} else { 
			%>
			<tr><td><div class="invalidAlert"><%= session.getAttribute("damsg")%></div></td></tr>
			<%
			} 
			%>
			
			</table>
			
			<br>
			
			<form action='CreatePermissionServlet' method='post'>
			
			<table cellspacing=15 cellpadding=10>
			<tr><td>Name :</td><td>
			<select id="name" name="name" style="height: 30px; width:180px;" onChange="">
			<%
				ArrayList<String> userNameListPm = (ArrayList) session.getAttribute("userNameListPm");
				ArrayList<String> nameListPm = (ArrayList) session.getAttribute("nameListPm");
				
				for (int i=0; i<nameListPm.size(); i++){
				%>
        		<option value="<%= userNameListPm.get(i)%>"><%out.print(nameListPm.get(i));%></option>
   				<%}%>	
			</select></td>
			</tr>
			
			</table>
			
			<table cellspacing=15 cellpadding=10>
			
			<tr><td>Start Date :</td><td><input type=text id="startTimeYear" name="startTimeYear" style="height: 20px; width: 120px;"/></td>
			<td>-</td><td><input type=text id="startTimeMonth" name="startTimeMonth" style="height: 20px; width: 100px;"/></td>
			<td>-</td><td><input type=text id="startTimeDay" name="startTimeDay" style="height: 20px; width: 100px;"/></td>
			</tr>
			
			<tr><td>End Date :</td><td><input type=text id="endTimeYear" name="endTimeYear" style="height: 20px; width: 120px;"/></td>
			<td>-</td><td><input type=text id="endTimeMonth" name="endTimeMonth" style="height: 20px; width: 100px;"/></td>
			<td>-</td><td><input type=text id="endTimeDay" name="endTimeDay" style="height: 20px; width: 100px;"/></td>
			</tr>
			
			</table>
			
			<table cellspacing=15 cellpadding=10>
			
			<tr><td>Start Time :</td><td>
			<select id="startHour" name="startHour" style="height: 30px; width:180px;" onChange="">
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
			
			<tr><td>End Time :</td><td>
			<select id="endHour" name="endHour" style="height: 30px; width:180px;" onChange="">
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
			
			<tr><td><input type="submit" value="Set" name="button" onClick="" class="btn"/></td></tr>
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