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
		if(session.getAttribute("knownFilter") != null)
		{
			String knownFilter = session.getAttribute("knownFilter").toString();
			String dateFilter = session.getAttribute("dateFilter").toString();
		%>
	
		var ddlun = document.getElementById("userName");
		var optsun = ddlun.options.length;
		for (var i=0; i<optsun; i++){
    		if (ddlun.options[i].value == '<%= knownFilter%>'){
        	ddlun.options[i].selected = true;
        	break;
    		}
		}
		
		var ddld = document.getElementById("date");
		var optsd = ddld.options.length;
		for (var i=0; i<optsd; i++){
    		if (ddld.options[i].value == '<%= dateFilter%>'){
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
    <title>View Account Report</title>
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
	padding:7px;
	font-size:12px;
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
	font-size:16px;
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
	table.viewTable td.noCol
	{
		width: 30px;
	}
	table.viewTable td.timeCol
	{
		width: 80px;
	}
	table.viewTable td.usernameCol
	{
		width: 160px;
	}
</style>

</head>
<body onload='setLists()'>
<%@ include file="header.jsp"%> 
      	<div class="span9">
			<div class="hero-unit">
			            <h1>Account Login Report</h1>
			            <p>View Account Report</p>
			</div>
			<div class="row-fluid" >
			<div class="content_space">
			
			<form action='ViewAccountReportServlet' method='post'>
			
				<table cellspacing=15 cellpadding=15>
				<tr><td>User Name :</td><td>
			
				<select id="userName" name="userName" style="height: 30px; width:120px;">
				<option value="All">All</option>
				<option value="Known">Known</option>
				<option value="Unknown">Unknown</option>
				</select>
				</td>
				
				<td>Date :</td>
				
				<td>
				<select id="date" name="date" style="height: 30px; width:240px;">
				<option value="0">All</option>
				<option value="1">1 Day</option>
				<option value="3">3 Days</option>
				<option value="7">7 Days</option>
				<option value="14">14 Days</option>
				<option value="30">30 Days</option>
				</select>
				</td>
				
				<td><input type="submit" value="View" name="view" onclick="" class="btn"/></td>
				
				</tr>
				</table>
				</form>
				
				<div class="viewTableSpace">
				
				<table cellspacing=10 class="viewTable">
				
				<tr>
				<td class="noCol">No.</td>
				<td>Details</td>
				<td class="timeCol">Time</td>
				<td class="usernameCol">User Name</td>
				</tr>
				
				<%
				if(session.getAttribute("arList") != null)
				{
					ArrayList<AccountReport> arList = new ArrayList<AccountReport>();
					arList = (ArrayList<AccountReport>) session.getAttribute("arList");
					
					for(int i=0; i<arList.size(); i++)
					{
					
					int number = (i + 1);
					String details = arList.get(i).getDetails();
					String logTime = arList.get(i).getLogTime();
					String userName = arList.get(i).getUserName();
					
				%>
					<tr>
					<td class="noCol"><%= number%></td>
					<td><%= details%></td>
					<td class="timeCol"><%= logTime%></td>
					<td class="usernameCol"><%= userName%></td>
					
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