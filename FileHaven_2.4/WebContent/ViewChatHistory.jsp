<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ page import="model.ChatSession,model.Account" %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>FileHaven-Display Chat History</title>
    <link href="resources/css/bootstrap.css" rel="stylesheet" type="text/css" />
    <link href="resources/css/bootstrap-responsive.css" rel="stylesheet" type="text/css" />
<link href="resources/css/jquery-ui.css" rel="stylesheet"
	type="text/css">
	<link href="resources/css/dataTable.css" rel="stylesheet" type="text/css" />
	<link href="resources/css/data_table.css" rel="stylesheet" type="text/css" />
<link href="resources/css/smoothness/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css" />
<link href="resources/css/smoothness/demo_table_jui.css" rel="stylesheet" type="text/css" />
    <script src="resources/js/jquery-1.9.1.js"></script>
	<script src="resources/js/jquery-ui-1.9.1.js"></script> 
	<script src="resources/js/jquery.dataTables.js" type="text/javascript"></script>
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
<script>
$(document).ready(function() {
    $('#chatlog').dataTable();
} );

</script>
</head>
<body>
<%@ include file="header.jsp"%> 

<% 
Account loginUser=(Account) request.getSession().getAttribute("LoggedInUser"); 
String userid=loginUser.getUserName();
ChatSession cs=(ChatSession) request.getSession().getAttribute("chatsession"); 
int clientRid=Integer.parseInt(request.getParameter("clientrid"));
cs.getChatroomList().get(clientRid).refreshChatMsg(userid);
%>
      	<div class="span9">
			<div class="hero-unit">
			            <h1>View Chat History</h1>
			            <p>This page displays the conversation log</p>
			</div>
			<div class="row-fluid" >
			<div class="content_space">
				
				
				<h4><%= cs.getChatroomList().get(clientRid).getDescription() %></h4>
				<table id="chatlog" style="width:100%" class="display">
				<thead><tr><th style="width:10%;">User</th><th style="width:78%">Message</th><th>Time</th></tr></thead>
				
				<tbody>
					<% for(int i=0;i<cs.getChatroomList().get(clientRid).getChatlogList().size();i++){ %>
						<tr>
						<td><%=cs.getChatroomList().get(clientRid).getChatlogList().get(i).getUser() %></td>
						<td><%=cs.getChatroomList().get(clientRid).getChatlogList().get(i).getChatMsg() %></td>
						<td><%=cs.getChatroomList().get(clientRid).getChatlogList().get(i).getTimestamp().substring(0, 19) %></td>
						</tr>
					<%} %>
					</tbody>
				</table>
			</div>
		</div>
<%@ include file="footer.jsp"%>
</body>
</html>