<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Update Department</title>

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
<link rel="stylesheet" href="resources/css/themes/blue/style.css"
	type="text/css" media="print, projection, screen" />

<script type="text/javascript" src="resources/js/jquery.tablesorter.js"></script>


<script type="text/javascript">
	$(function() {		
		$("#tablesorter-demo").tablesorter({sortList:[[0,0],[2,1]], widgets: ['zebra']});
		$("#options").tablesorter({sortList: [[0,0]], headers: { 3:{sorter: false}, 4:{sorter: false}}});
	});	
	</script>

</head>
<%@ page import="database.*,model.*, java.util.*"%>
<%
	ManagerDBAO m1 = new ManagerDBAO();
	Account currentUser = (Account) session.getAttribute("LoggedInUser");
	int inputDepartmentId=(Integer)session.getAttribute("departmentId");
	ArrayList<Manager> mama = m1.getDepartmentManagers(inputDepartmentId,currentUser.getCompanyID());
	
%>
<%
ManagerDBAO m2 = new ManagerDBAO();
ArrayList<Manager> availableManagers = m1.getSelectedManagers(currentUser.getCompanyID());
%>

<%
	EmployeeDBAO employee = new EmployeeDBAO();
	ArrayList<Employee> e2 = employee.getDepartmentEmployees(inputDepartmentId,currentUser.getCompanyID());
%>

<%
	DepartmentDBAO department = new DepartmentDBAO();
	ArrayList<Department> d1 =department.getDepartmentDetails(currentUser.getUserName(), inputDepartmentId);
%>

<%
	EmployeeDBAO e1 = new EmployeeDBAO();
	ArrayList<Employee> employMama = e1.getSelectedEmployees(currentUser.getCompanyID());
%>
<body>



	<div class="container-fluid">
		<div class="hero-unit">
			<h2>
				<%= session.getAttribute("departmentId") %></h2>

		</div>

		<%@ include file="header.jsp"%>

		<div class="span9">
			<div class="hero-unit">
				<form action='UpdateDepartment' method='POST'
					enctype="multipart/form-data" name="test" class="form-horizontal">

					<h1>Updating Department...</h1>
					<p>You're 3 steps away from updating a department</p>

					<div class="row-fluid">
						<div class="alert alert-info">
							<span><b>Step 1</b></span>
						</div>
						<legend>Department Details</legend>

						<div class="control-group">
							<label class="control-label" for="inputEmail">Department
								Name: </label>
							<div class="controls">
								<input type="text" name='departmentName'
									value="<%=d1.get(0).getDepartmentName() %>"
									id="inputDepartmentName" placeholder="E.g Accounts Dept">
							</div>

						</div>

						<div class="control-group">
							<label class="control-label" for="input">Manager: </label>
							<div class="controls">


								<select style="width: 220px; height: 50px;" id="populate"
									name="sometext" multiple>
									<%
								for (int i=0; i<mama.size(); i++) {
									Manager m2ama = (Manager)mama.get(i);
									
							%>
									<option value="<%=m2ama.getName() %>"><%= m2ama.getName() %></option>
									<%
								}
							%>
								</select> <a href="#myModal" role="button"
									class="btn btn-large btn-primary" data-toggle="modal"
									onclick="preload()">Add Manager</a>
							</div>



						</div>

						<div class="control-group">
							<label class="control-label" for="inputEmail">Employees:
							</label>
							<div class="controls">

								<select style="width: 220px; height: 50px;" id="populate1"
									name="employees" multiple>
									<%
								for (int i=0; i<e2.size(); i++) {
									Employee e3 = (Employee)e2.get(i);
									
							%>
									<option value="<%=e3.getName() %>"><%= e3.getName() %></option>
									<%
								}
							%>
								</select> <a href="#myModal1" role="button"
									class="btn btn-large btn-primary" data-toggle="modal"
									onclick="preload1()">Add Employee</a>
							</div>

						</div>

						<div class="control-group">
							<label class="control-label" for="inputDepartmentDescription"
								>Department
								Description: </label>
							<div class="controls">
								<textarea name="departmentDescription"
									placeholder="Department Description.." rows="3" ><%=d1.get(0).getDepartmentDescription() %></textarea>


							</div>

						</div>



						<div class="alert alert-info">
							<span><b>Step 2</b></span>
						</div>
						<legend>Miscellaneous</legend>

						<div class="control-group">
							<label class="control-label" for="inputEmail">Department
								Picture</label>
							<div class="controls">

								Upload: <input type="file" name="test">
							</div>

						</div>
						Current Picture Used
						<ul class="thumbnails">
							<li class="span2"><a href="#" class="thumbnail"> <img
									src="<%=d1.get(0).getDepartmentLogo() %>" alt="">
							</a></li>
						</ul>

					</div>

					<p align="right">

						<input type="submit" value="Submit" class="btn btn-primary">
					</p>
				</form>
			</div>



		</div>
		<!--/row-->
	</div>
	<!--/span-->
	</div>
	<!--/row-->

	<hr>

	<footer>
	<p>&copy; FileHaven 2013</p>
	</footer>

	</div>
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


	<!-- Dynamic Search Table
    ================================================== -->
	<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">×</button>
			<h3 id="myModalLabel">Managers</h3>
		</div>
		<div class="modal-body">



			<div id="container">


				<h4>Add/Remove Managers</h4>



				<div id="demo">
					<table cellpadding="0" cellspacing="0" border="0" class="display"
						id="example">


						<thead>
							<tr>

								<th>Manager's Name</th>
								<th>Phone Number</th>
								<th>Address</th>
								<th>Gender</th>
							</tr>
						</thead>

						<tbody>

							<%
								for (int i=0; i<availableManagers.size(); i++) {
																	Manager m2ama = (Manager)availableManagers.get(i);
																	int departmentId=(Integer)session.getAttribute("departmentId");
																	if(m2ama.getDepartmentID()==departmentId){
							%>

							<tr class="gradeA">

								<td><%=m2ama.getName()%></td>
								<td><%=m2ama.getPhoneNumber()%></td>
								<td class="center"><%=m2ama.getAddress()%></td>
								<td class="center">
									<button class="btn btn-small btn-primary btn-block"
										type="button" value="<%=m2ama.getName()%>"
										id="<%=m2ama.getName()%>" onclick="button4(this.value)"
										disabled>Add Manager</button>
									<button class="btn btn-small btn btn-danger btn-block"
										type="button" value="<%=m2ama.getName()%>"
										id="123<%=m2ama.getName()%>" onclick="myFunction(this.value)">Remove
										Manager</button>

								</td>
							</tr>

							<%
								} else if(m2ama.getDepartmentID()==0){
							%>


							<tr class="gradeA">

								<td><%=m2ama.getName()%></td>
								<td><%=m2ama.getPhoneNumber()%></td>
								<td class="center"><%=m2ama.getAddress()%></td>
								<td class="center">
									<button class="btn btn-small btn-primary btn-block"
										type="button" value="<%=m2ama.getName()%>"
										id="<%=m2ama.getName()%>" onclick="button4(this.value)">Add
										Manager</button>
									<button class="btn btn-small btn btn-danger btn-block"
										type="button" value="<%=m2ama.getName()%>"
										id="123<%=m2ama.getName()%>" onclick="myFunction(this.value)"
										disabled>Remove Manager</button>

								</td>
							</tr>

							<%
								}
							%>

							<%
								}
							%>
						</tbody>
						<tfoot>

						</tfoot>
					</table>
				</div>
				<div class="spacer"></div>

				<SCRIPT LANGUAGE="JavaScript">
				<!--
				function preload()
				{
					
					alert("hello");
					var tempValue;
					for(var i=0;i<document.getElementById("populate").options.length;i++)
					   {
						var value1 = document.lewis.alonso.value;
						 tempValue=document.getElementById("populate").options[i].value;
						document.lewis.alonso.value=value1+","+tempValue+",";
					   };
					
					
							
					
				}
					function myFunction(removingValue) {
						
						
						var re = removingValue;
						var str = document.lewis.alonso.value;

						if ( str.search(re) == -1 ){
						    alert("does not contain apples "+value );
						    
							
							name.submit();
						}else{
						   alert("contains apples!" );
						   
						   var success = document.lewis.alonso.value;
						   var works= success.replace(re,"");
						   alert(works);
						   document.lewis.alonso.value=works;
						   document.getElementById(removingValue).disabled=false;
							document.getElementById("123"+removingValue).disabled=true;
						   
						   var array1 = [];
						   var removingFromArray = document.getElementById("populate");
						   for(var i=0;i<document.getElementById("populate").options.length;i++)
							   {
							   	array1.push(document.getElementById("populate").options[i].value);
							   }
						  
						  
						  alert(removingFromArray.options[0].value);
						  
						 
						  
						  
						  
						  
						  var index = array1.indexOf(removingValue);
						  alert(index);
						  var removed = array1.splice(index, 1);
						  alert("Final"+removed);
						  for (i=0; i<array1.length; i++) {
							  
							  console.log(array1[i]);
						  }
			                document.test.populate.length = 0;
			              	alert(array1.length);
			              						  
			                for(var i=0;i<array1.length;i++){
			                    var select = document.getElementById("populate");
			                    select.options[select.options.length] = new Option(array1[i], array1[i]);
			                }
						  
						  
						   
						}
						  
						  
						  
						   
						}
						
					

					function button4(value) {
						var value1 = document.lewis.alonso.value;
						var array = [];
						if (value1 == "") {
							
							

							array.push(value);
							for(var i=0;i<array.length;i++){
			                    var select = document.getElementById("populate");
			                    select.options[select.options.length] = new Option(array[i], array[i]);
			                }
							document.lewis.alonso.value = value;
							document.getElementById(value).disabled=true;
							document.getElementById("123"+value).disabled=false;
							
							
						} else {
							
							var re = value;
							var str = document.lewis.alonso.value;

							if ( str.search(re) == -1 ){
							    alert("Does not contain "+value );
							    array.push(value);
								for(var i=0;i<array.length;i++){
				                    var select = document.getElementById("populate");
				                    select.options[select.options.length] = new Option(array[i], array[i]);
				                }
							    document.lewis.alonso.value = value1 + "," + value;
								document.getElementById(value).disabled=true;
								document.getElementById("123"+value).disabled=false;
								
								
							}else{
							   alert("You can't add the same manager!" );
							}

							
							
						}

					}
					
	
					function checkSubmit() {
						
						alert("hello");
						
						
						 var select = document.getElementById("populate");
						for (var i = 0, children = select.childNodes, l = children.length; i < l; i++) {
						    if (children[i].tagName === "OPTION") children[i].selected = true;
						}
						
						document.lewis.alonso.value="";
					}
				
				// -->
				</SCRIPT>

				<form name="lewis">

					<input name="alonso" type="text">

				</form>





			</div>




		</div>
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
			<button class="btn btn-primary" data-dismiss="modal"
				onclick="checkSubmit()">Save changes</button>
		</div>
	</div>

	<div id="myModal1" class="modal hide fade" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">×</button>
			<h3 id="myModalLabel">Employee</h3>
		</div>
		<div class="modal-body">

			<table id="tablesorter-demo" class="tablesorter" border="0"
				cellpadding="0" cellspacing="1">
				<thead>
					<tr>
						<th>Employee's Name</th>
						<th>Phone Number</th>
						<th>Address</th>
						<th>Gender</th>
					</tr>
				</thead>
				<tbody>


					<%
								for (int i=0; i<employMama.size(); i++) {
																	Employee employz = (Employee)employMama.get(i);
																	int departmentId=(Integer)session.getAttribute("departmentId");
																	if(employz.getDepartmentID()==departmentId){
							%>
					<tr>

						<td><%=employz.getName()%></td>
						<td><%=employz.getPhoneNumber()%></td>
						<td class="center"><%=employz.getAddress()%></td>
						<td class="center">
							<button class="btn btn-small btn-primary btn-block" type="button"
								value="<%=employz.getName()%>" id="<%=employz.getName()%>"
								onclick="notWorking(this.value)" disabled>Add Manager</button>
							<button class="btn btn-small btn btn-danger btn-block"
								type="button" value="<%=employz.getName()%>"
								id="123<%=employz.getName()%>" onclick="myFunction1(this.value)">Remove
								Manager</button>

						</td>
					</tr>
					<%
								} else if(employz.getDepartmentID()==0){
							%>
					<tr>

						<td><%=employz.getName()%></td>
						<td><%=employz.getPhoneNumber()%></td>
						<td class="center"><%=employz.getAddress()%></td>
						<td class="center">
							<button class="btn btn-small btn-primary btn-block" type="button"
								value="<%=employz.getName()%>" id="<%=employz.getName()%>"
								onclick="notWorking(this.value)">Add Manager</button>
							<button class="btn btn-small btn btn-danger btn-block"
								type="button" value="<%=employz.getName()%>"
								id="123<%=employz.getName()%>" onclick="myFunction1(this.value)"
								disabled>Remove Manager</button>

						</td>
					</tr>

					<%
								}
							%>

					<%
								}
							%>

				</tbody>
			</table>


			<SCRIPT LANGUAGE="JavaScript">
				<!--
				function preload1()
				{
					
					alert("hello");
					var tempValue;
					for(var i=0;i<document.getElementById("populate1").options.length;i++)
					   {
						var value1 = document.lewis1.alonso1.value;
						 tempValue=document.getElementById("populate1").options[i].value;
						document.lewis1.alonso1.value=value1+","+tempValue+",";
					   };
					
					
							
					
				}
				
				
				function notWorking(value)
				{
					var value1 = document.lewis1.alonso1.value;
					var array = [];
					if (value1 == "") {
						
						

						array.push(value);
						for(var i=0;i<array.length;i++){
		                    var select = document.getElementById("populate1");
		                    select.options[select.options.length] = new Option(array[i], array[i]);
		                }
						document.lewis1.alonso1.value = value;
						document.getElementById(value).disabled=true;
						document.getElementById("123"+value).disabled=false;
						
						
												
						
					} else {
						
						var re = value;
						var str = document.lewis1.alonso1.value;

						if ( str.search(re) == -1 ){
						    alert("Does not contain "+value );
						    array.push(value);
							for(var i=0;i<array.length;i++){
			                    var select = document.getElementById("populate1");
			                    select.options[select.options.length] = new Option(array[i], array[i]);
			                }
						    document.lewis1.alonso1.value = value1 + "," + value;
							document.getElementById(value).disabled=true;
							document.getElementById("123"+value).disabled=false;
							
							
						}else{
						   alert("You can't add the same manager!" );
						}
						
												
						
					}

	
				}
					function myFunction1(removingValue) {
						
						
						var re = removingValue;
						var str = document.lewis1.alonso1.value;

						if ( str.search(re) == -1 ){
						    alert("does not contain apples "+value );
						    
							
							name.submit();
						}else{
						   alert("contains apples!" );
						   
						   var success = document.lewis1.alonso1.value;
						   var works= success.replace(re,"");
						   alert(works);
						   document.lewis1.alonso1.value=works;
						   document.getElementById(removingValue).disabled=false;
							document.getElementById("123"+removingValue).disabled=true;
						   
						   var array1 = [];
						   var removingFromArray = document.getElementById("populate1");
						   for(var i=0;i<document.getElementById("populate1").options.length;i++)
							   {
							   	array1.push(document.getElementById("populate1").options[i].value);
							   }
						  
						  
						  alert(removingFromArray.options[0].value);
						  
						 
						  var index = array1.indexOf(removingValue);
						  alert(index);
						  var removed = array1.splice(index, 1);
						  alert("hey"+array1.length);
						  document.test.populate1.length = 0;
						  
						  for(var i=0;i<array1.length;i++){
							  var select = document.getElementById("populate1");
			                    select.options[select.options.length] = new Option(array1[i], array1[i]);
			                    
			                }
						  
						  
						  
						   
						}
						
					}

					function addEmployee(value) {
						var array = [];
						if (value1 == "") {
							
							

							array.push(value);
							for(var i=0;i<array.length;i++){
			                    var select = document.getElementById("populate1");
			                    select.options[select.options.length] = new Option(array[i], array[i]);
			                }
							
							document.getElementById(value).disabled=true;
							document.getElementById("123"+value).disabled=false;
							
							
							name.submit();
						} else {
							
							var re = value;
							

							if ( str.search(re) == -1 ){
							    alert("Does not contain "+value );
							    array.push(value);
								for(var i=0;i<array.length;i++){
				                    var select = document.getElementById("populate1");
				                    select.options[select.options.length] = new Option(array[i], array[i]);
				                }
								document.getElementById(value).disabled=true;
								document.getElementById("123"+value).disabled=false;
								
								
								name.submit();
							}else{
							   alert("You can't add the same manager!" );
							}

							
							
						}

					}
					
	
					function checkSubmit1() {
						
						alert("hello");
						
						
						 var select = document.getElementById("populate1");
						for (var i = 0, children = select.childNodes, l = children.length; i < l; i++) {
						    if (children[i].tagName === "OPTION") children[i].selected = true;
						}
						document.lewis1.alonso1.value="";
						
					}
				
				// -->
				</SCRIPT>
			<form name="lewis1">

				<input name="alonso1" type="text">

			</form>



		</div>
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
			<button class="btn btn-primary" data-dismiss="modal"
				onclick="checkSubmit1() ">Save changes</button>
		</div>
	</div>

</body>
</html>