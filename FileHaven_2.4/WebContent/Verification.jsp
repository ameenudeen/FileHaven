<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title></title>

<script src="http://code.jquery.com/jquery-latest.js"></script>
<link href="resources/css/bootstrap.css" rel="stylesheet"
	type="text/css" />
<link href="resources/css/bootstrap-responsive.css" rel="stylesheet" type="text/css" />
<link href="resources/css/application.css" rel="stylesheet" type="text/css" />

<link href="resources/css/patternlock.css" rel="stylesheet"
	type="text/css" />
	
<script src="resources/js/patternlock.js"></script>

</head>

<%@ page import="database.*,model.*, java.util.*"%>
<%
	DepartmentDBAO d1 = new DepartmentDBAO();
	Account currentUser=(Account)session.getAttribute("LoggedInUser");
%>

<body>

		


	<div class="container-fluid">
		<div class="hero-unit">
			<h2>
				Welcome
				<%=currentUser.getName()%>!
			</h2>

		</div>
		<%
		session.setAttribute("AtVerify","TRUE");%>
		<%@ include file="header.jsp"%>
		<%
		session.setAttribute("AtVerify","FALSE");%> 
		<div class="span9">
			<div class="row-fluid">


				<form method="post" action="Index.jsp" onsubmit="return submitform()">
					<h2>Before we proceed...</h2>

					<div>
						<input type="password" id="password" name="password"
							class="patternlock" /> <input type="submit" value="login" />
					</div>
				</form>


			</div>



		</div>
		<!--/row-->
	</div>

	<script>
		function submitform() {
			if(document.getElementById("password").value==<%=currentUser.getUserPattern() %>){
				alert("Success");
				return true;
			}
			else {
				alert("Wrong Pattern");
				 location.reload();

				return false;
			}
			
		}
	</script>
	
	
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