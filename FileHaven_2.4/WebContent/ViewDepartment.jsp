<%@page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html >
<html lang="en">
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


</head>

<%@ page import="database.*,model.*, java.util.*"%>
<%
	DepartmentDBAO d1 = new DepartmentDBAO();
	Account currentUser = (Account) session
			.getAttribute("LoggedInUser");
	ArrayList<Department> department = d1
			.getCompanyDepartment(currentUser.getUserName());
%>


<body>
	<div class="container-fluid">
		<div class="hero-unit">
			<h2>
				Welcome
				<%=currentUser.getName()%>!
			</h2>

		</div>
		<%@ include file="header.jsp"%>

		<div class="span9">
			<div class="row-fluid">


				<ul class="thumbnails">
					<%
						for (int i = 0; i < department.size(); i++) {
							Department m2ama = (Department) department.get(i);
					%>
					<li class="span4">
						<div class="thumbnail">
							<img src="<%=m2ama.getDepartmentLogo()%>"
								alt="resources/img/hardware.jpg">
							<div class="caption">
								<h3><%=m2ama.getDepartmentName()%></h3>
								<p><%=m2ama.getDepartmentDescription()%></p>

								
									
								<form action='ViewDepartment' method='POST'>
									<input type="hidden" value=<%=m2ama.getDepartmentName()%>
										name="hiddenField">
									<button class="btn btn-large btn-block btn-primary"
										type="submit">Edit</button>
								</form>
								<form action='DeleteDepartment' method='POST'>
									<input type="hidden" value=<%=m2ama.getDepartmentName()%>
										name="departmentName">
									<button class="btn btn-large btn-block btn-danger" type="submit">Delete</button>
								</form>

								
							</div>
						</div>
					</li>
					<%
						}
					%>
				</ul>


			</div>



		</div>
		<!--/row-->
	</div>
	<!--/span-->
	<!--/row-->

	<hr>

	<footer>
		<p>&copy; FileHaven 2013</p>
	</footer>

	<!--/.fluid-container-->

	<!-- Le javascript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="resources/js/bootstrap-transition.js"></script>
	<script src="resources/js/bootstrap-alert.js"></script>
	<script src="resources/js/bootstrap-modal.js"></script>
	<script src="resources/js/bootstrap-dropdown.js"></script>
	<script src="resources/js/bootstrap-scrollspy.js"></script>
	<script src="resources/js/bootstrap-tab.js"></script>
	<script src="resources/js/bootstrap-tooltip.js"></script>
	<script src="resources/js/bootstrap-popover.js"></script>
	<script src="resources/js/bootstrap-button.js"></script>
	<script src="resources/js/bootstrap-collapse.js"></script>
	<script src="resources/js/bootstrap-carousel.js"></script>
	<script src="resources/js/bootstrap-typeahead.js"></script>

</body>

</html>


