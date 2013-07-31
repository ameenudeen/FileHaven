<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>FileHaven</title>

<script src="http://code.jquery.com/jquery-latest.js"></script>

<link href="resources/css/bootstrap.css" rel="stylesheet"
	type="text/css" />
<link href="resources/css/bootstrap-responsive.css" rel="stylesheet"
	type="text/css" />
<link href="resources/css/application.css" rel="stylesheet"
	type="text/css" />

<style type="text/css" title="currentStyle">
@import "resources/css/demo_table.css";
</style>

<script type="text/javascript" language="javascript"
	src="resources/js/jquery.dataTables.js"></script>

<script type="text/javascript" charset="utf-8">
	/* Define two custom functions (asc and desc) for string sorting */
	jQuery.fn.dataTableExt.oSort['string-case-asc'] = function(x, y) {
		return ((x < y) ? -1 : ((x > y) ? 1 : 0));
	};

	jQuery.fn.dataTableExt.oSort['string-case-desc'] = function(x, y) {
		return ((x < y) ? 1 : ((x > y) ? -1 : 0));
	};

	$(document).ready(function() {
		/* Build the DataTable with third column using our custom sort functions */
		$('#example').dataTable({
			"aaSorting" : [ [ 0, 'asc' ], [ 1, 'asc' ] ],
			"aoColumnDefs" : [ {
				"sType" : 'string-case',
				"aTargets" : [ 2 ]
			} ]
		});
	});
</script>
</head>

<%@ page import="database.*,model.*, java.util.*"%>
<%
	Account currentUser = (Account) session.getAttribute("LoggedInUser");
	FileReportDBAO f1 = new FileReportDBAO();
	ArrayList<FileReport> fr = f1.getReports(currentUser.getCompanyID());
%>

<body>

	<div class="hero-unit">
		<h2>
			Welcome
			<%=currentUser.getName()%>!
		</h2>

	</div>

	<%@ include file="header.jsp"%>


	<div class="span9">

		<form method='POST' action='ReportServlet'>
			<h2>File Statistics</h2>
			Select your file <select id="department" name="depValue">
				<%
					ArrayList<FileReport> frs = fr;
					for (int i = 0; i < frs.size(); i++) {
				%>
				<option value="<%=frs.get(i).getFileID()%>"><%=frs.get(i).getFileName()%></option>
				<%
					}
				%>
			</select> <input type="submit" value="Submit" class="btn btn-primary">
			<hr/>
		</form>



		<% if(session.getAttribute("report")==null){ %>

		<div id="demo">

			<table cellpadding="0" cellspacing="0" border="0" class="display"
				id="example">


				<thead>
					<tr>

						<th>User</th>
						<th>IP Address</th>
						<th>File Owner</th>
						<th>Status</th>
						<th>Access Date</th>
						<th>Access Time</th>
					</tr>
				</thead>

				<tbody>

					<tr class="gradeA">

						<td>No Data</td>
						<td>No Data</td>
						<td>No Data</td>
						<td>No Data</td>
						<td>No Data</td>
						<th>No Data</th>
					</tr>
				</tbody>
				<tfoot>

				</tfoot>
			</table>

		</div>
		<%} else{ ArrayList<FileReport> report = (ArrayList<FileReport>)session.getAttribute("report");
			
			
			%>
		<div class="row-fluid">
			<div class="span5 offset10">
			<form action='PDF' method='POST'>
				<input type="submit" value="Export to PDF"  class="btn btn-primary">
				</form>
			</div>
		</div>
		<div class="row-fluid">
			<div id="demo">

				<table cellpadding="0" cellspacing="0" border="0" class="display"
					id="example">


					<thead>
						<tr>

								<th>User</th>
								<th>IP Address</th>
								<th>File Owner</th>
								<th>Status</th>
								<th>Access Date</th>
								<th>Access Time</th>
						</tr>
					</thead>

					<tbody>
						<% for (int i=0;i<report.size();i++){ %>
						<tr class="gradeA">

							<td><%=report.get(i).getUserName() %></td>
							<td><%=report.get(i).getIPAddress() %></td>
							<td><%=report.get(i).getAccountID() %></td>
							<td><%=report.get(i).getStatus() %></td>
							<td><%=report.get(i).getDownloadedDate() %></td>
							<td><%=report.get(i).getDownloadedTime() %></td>

						</tr>
						<%} %>
					</tbody>
					<tfoot>

					</tfoot>
				</table>

			</div>
		</div>

		<%} %>

	</div>
</body>
</html>