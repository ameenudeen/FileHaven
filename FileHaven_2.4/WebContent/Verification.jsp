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
<%@page import="java.util.GregorianCalendar,security.Security,org.apache.commons.codec.binary.Base64" %>

<script>
$(document).ready(function(){
	<%
	if(session.getAttribute("LoggedInUser")==null){
		response.sendRedirect("Login.jsp");
		return;
	}
	if(session.getAttribute("externalcompany").equals("false")){
		response.sendRedirect("Index.jsp");
		return;
	}
		GregorianCalendar c=new GregorianCalendar();
		String str="";
		// eg:2013:5:3:15:20
		str+=c.get(Calendar.YEAR)+":";
		str+=c.get(Calendar.MONTH)+":";
		str+=c.get(Calendar.DATE)+":";
		str+=c.get(Calendar.HOUR_OF_DAY)+":";
		str+=c.get(Calendar.MINUTE);
		str=Base64.encodeBase64String(Security.encryptByte(str.getBytes(),Security.generateAESKey("SYSTEM_KEY"),"AES"));
	%>
		document.getElementById("hidden_timestamp").value="<%=str%>";
});
</script>
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


				<form method="post" action="Verification" >
					<h2>Before we proceed...</h2>

					<div>
					<input type='text' id="hidden_timestamp" style='display:none' name='hidden_timestamp' >
						<input type="password" id="password" name="password"
							class="patternlock" /> <input type="submit" value="login" />
					</div>
				</form>


			</div>



		</div>
		<!--/row-->
	</div>


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
