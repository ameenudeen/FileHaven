<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ page import="model.Account, java.util.*" %>

<%
Account tempaccount = (Account) session.getAttribute("LoggedInUser");

if(tempaccount == null)
{
%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Login</title>
    <link href="resources/css/bootstrap.css" rel="stylesheet" type="text/css" />

    <link href="resources/css/application.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="resources/css/keyboard.css" />
    <script src="resources/js/jquery-1.9.1.js"></script>
	<script src="resources/js/jquery-ui-1.9.1.js"></script>
	<script type="text/javascript" src="resources/js/keyboard.js" charset="UTF-8"></script>
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
      body {
        padding-top: 40px;
        padding-bottom: 40px;
        background-color: #f5f5f5;
      }

      .form-signin {
        max-width: 300px;
        padding: 19px 29px 29px;
        margin: 0 auto 20px;
        background-color: #fff;
        border: 1px solid #e5e5e5;
        -webkit-border-radius: 5px;
           -moz-border-radius: 5px;
                border-radius: 5px;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
           -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
                box-shadow: 0 1px 2px rgba(0,0,0,.05);
      }
      .form-signin .form-signin-heading,
      .form-signin .checkbox {
        margin-bottom: 10px;
      }
      .form-signin input[type="text"],
      .form-signin input[type="password"] {
        font-size: 16px;
        height: auto;
        margin-bottom: 15px;
        padding: 7px 9px;
      }

    </style>
    
    <style type="text/css">
	.invalidAlert
	{
	color:#CC0000;
	font-weight: bold;
	}
	</style>
    
        <link href="resources/css/bootstrap-responsive.css" rel="stylesheet" type="text/css" />
</head>
<body>
<%@ include file="LoginHeader.jsp"%> 
      	<div class="container">

      <form class="form-signin" action='LoginServlet' method='post'>
        <h2 class="form-signin-heading">Please sign in</h2>
        <input type="text" name="userName" class="input-block-level" placeholder="User Name" style= "width:240px">
       <input type=password name="password" style="height: 20px; width:220px" placeholder="Password" class="keyboardInput" />
       <hr/>
       <p> Please enter the Captcha shown below</p>
          <img src="CaptchaServlet">
        	<p></p>
          <input type="text" name="code"  />
          
          <%
          if(session.getAttribute("alert") != null)
          {
          %>
          <div class="invalidAlert">
          <%= session.getAttribute("alert").toString()%>
          </div>
          <%
          }
          else
          %>
         
          
          <hr/>
        <button class="btn btn-large btn-primary" name="submit" value="Login" type="submit">Sign in</button>
      </form>

    </div> <!-- /container -->
			
			
</body>

<%
}
else
{
	response.sendRedirect("Index.jsp");
}
%>

</html>