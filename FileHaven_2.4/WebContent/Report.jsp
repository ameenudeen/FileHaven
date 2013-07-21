<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>FileHaven</title>


<script>
	$(document).ready(function() {

		$('#submit').click(function(event) {

			var dep = $('#department').val();

			$.get('ReportServlet', {
				department : dep
			}, function(html) {

				$('#demo').html(html);

			});

		});

	});
</script>
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
<link href="resources/css/bootstrap.css" rel="stylesheet"
	type="text/css" />
<link href="resources/css/bootstrap-responsive.css" rel="stylesheet"
	type="text/css" />
<link href="resources/css/application.css" rel="stylesheet"
	type="text/css" />
</head>

<%@ page import="database.*,model.*, java.util.*"%>
<%
	Account currentUser = (Account) session.getAttribute("LoggedInUser");
	FileReportDBAO f1 = new FileReportDBAO();
	ArrayList<String> departments = f1.getReports(currentUser.getCompanyID());
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

		<form id="form1" method="post" action="/FileHaven/PDF">
			<h2>File Statistics</h2>
			Select your department <select id="department">
				<%
					ArrayList<String> deps = departments;
					for (int i = 0; i < departments.size(); i++) {
				%>
				<option value="<%=departments.get(i)%>"><%=departments.get(i)%></option>
				<%
					}
				%>
			</select> <input type="button" class="btn  btn-primary" id="submit"
				value="Search" />
			<button class="btn  btn-primary" type="submit">Export to Pdf
				File</button>



			<div id="demo"></div>
		</form>


	</div>
</body>
</html>