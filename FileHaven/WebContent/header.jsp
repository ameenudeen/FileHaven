
<link href="resources/css/jquery.contextMenu.css" rel="stylesheet"
	type="text/css" />
<link href="resources/css/reveal.css" rel="stylesheet" type="text/css" />
<link href="resources/css/header.css" rel="stylesheet" type="text/css" />

<link href="resources/css/jquery-ui.css" rel="stylesheet"
	type="text/css">
<link href="resources/css/smoothness/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css" />
<link href="resources/css/smoothness/demo_table_jui.css" rel="stylesheet" type="text/css" />
	
<script src="resources/js/jquery.dataTables.js" type="text/javascript"></script>
<script src="resources/js/jquery.fastLiveFilter.js" type="text/javascript"></script>
<script src="resources/js/DataTables/dataTables.fnReloadAjax.js" type="text/javascript"></script>
<script src="resources/js/DataTables/jquery.tablesorter.js" type="text/javascript"></script>
<script src="resources/js/DataTables/jquery.tablesorter.pager.js" type="text/javascript"></script>
<script src="resources/js/jquery.contextMenu.js" type="text/javascript"></script>
<script src="resources/js/jquery.reveal.js" type="text/javascript"></script>

<%@ page import="model.ChatSession,model.Account"%>

<style>
#chatInvUserDT .ui-toolbar
{
	height:34px;
}
</style>
<script>
	<% 	if(session.getAttribute("LoggedInUser")==null){
		response.sendRedirect("Login.jsp");
		return;
	} %>

	var hosturl = location.origin + "/" + location.pathname.split('/')[1] + "/";
	var activeroom = -1;
	var activeinv = -1;
	
	var daFilterTabVal = 0;
	
	var chatUserTable;
	var cutGaiSelected = [];
	
	retrieveChatRoomAjax();
	//setInterval(retrieveChatRoomAjax, 10000);
	setInterval(retrieveChatMsgAjax, 100);

	function sendChatMsgAjax() {
		if (activeroom > -1) {
			var xmlhttp;
			if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
				xmlhttp = new XMLHttpRequest();
			} else {// code for IE6, IE5
				xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status != 200) {
					alert("Invalid room ID");
				}
			}
			xmlhttp.open("GET", "SendChatMsgServ?msg="
					+ document.getElementById("chatfield").value, true);
			xmlhttp.send();
			document.getElementById("chatfield").value = "";
		}
	}
	
	function verifyPinAjax() {
		if($('#chatpinauth').val().length<6||$('#chatpinauth').val().length>12){
			$('#pinAuthErrorlbl').text("Pins must be between 6 to 12 numbers.");
			return false;
		}
		
			var xmlhttp;
			if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
				xmlhttp = new XMLHttpRequest();
			} else {// code for IE6, IE5
				xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
			xmlhttp.onreadystatechange = function() {
				if(xmlhttp.readyState == 4 && xmlhttp.status == 200){
					$('#pinDialog').trigger('reveal:close');
					$('#chatpinauth').val('');
					retrieveChatRoomAjax();
				}
				else if (xmlhttp.readyState == 4 && xmlhttp.status != 200) {
					alert("Connection error. Please try again.");
				}
			}
			xmlhttp.open("POST", "VerifyChatPinServ"
					+ document.getElementById("chatfield").value, true);
			xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
			xmlhttp.send("pin="+$('#chatpinauth').val());
	}
	
	function registerPinAjax() {
		
		if($('#chatpinreg').val().length<6||$('#chatpinreg').val().length>12){
			$('#pinRegErrorlbl').text("Pins must be between 6 to 12 numbers.");
			return false;
		}
		
		if($('#chatpinreg').val()!=$('#chatpincfm').val()){
			$('#pinRegErrorlbl').text("Pins do not match. Please double check and try again.");
			return false;
		}
		
		if(!$('#pinAPassword').val()){
			$('#pinRegErrorlbl').text("Password field must be filled in.");
			return false;
		}


		var xmlhttp;
		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange = function() {
			if(xmlhttp.readyState == 4 && xmlhttp.status == 200){
				msg=xmlhttp.responseText;
				if(msg=="success"){
					alert("Pin registered successfully.");
					$('#cpinDialog').trigger('reveal:close');
					$('#chatpinreg').val('');
					$('#chatpincfm').val('');
					$('#pinAPassword').val('');
					retrieveChatRoomAjax();
					
				}else{
					alert(msg);
				}
			}
			else if (xmlhttp.readyState == 4 && xmlhttp.status != 200) {
				alert("Connection error. Please try again.");
			}
		}
		xmlhttp.open("POST", "RegisterChatPinServ"
				+ document.getElementById("chatfield").value, true);
		xmlhttp.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		xmlhttp.send("pin=" + $('#chatpinreg').val() + "&password="+$('#pinAPassword').val());
	}

	function setInvStatusAjax(iid, status) {
		if (iid > -1) {

			var xmlhttp;
			if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
				xmlhttp = new XMLHttpRequest();
			} else {// code for IE6, IE5
				xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status != 200) {
					alert("Connection error");
				} else if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					retrieveChatRoomAjax();
				}
			};
			xmlhttp.open("GET", "SetInvStatusServ?activeIid=" + iid
					+ "&status=" + status, true);
			xmlhttp.send();
		}
	}

	function sendInvAjax(desc) {
		var xmlhttp;
		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				//return xmlhttp.responseText;
				//loop--;
				//sendInvAjax(desc, loop);
				alert('Invitations have been successfully sent.');
			} else if (xmlhttp.readyState == 4) {
				alert("Connection Error");
			}
		};
		xmlhttp.open("GET", "SendInvServ?&selectedUser=" + cutGaiSelected
				+ "&description=" + desc, false);
		xmlhttp.send();

	}

	function createRoomAjax() {
		var roomname = prompt("Please enter the name of the chatroom.", "");

		if (roomname) {
			var xmlhttp;
			if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
				xmlhttp = new XMLHttpRequest();
			} else {// code for IE6, IE5
				xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					//return xmlhttp.responseText;
					//loop--;
					//sendInvAjax(desc, loop);
					retrieveChatRoomAjax();
				} else if (xmlhttp.readyState == 4) {
					alert("Connection Error");
				}
			};
			xmlhttp.open("GET", "CreateRoomServ?roomname=" + roomname, false);
			xmlhttp.send();
		}

	}

	function setActiveRoom(clientRid) {

		var xmlhttp;
		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange = function() {

		}
		xmlhttp.open("GET", "ActiveRoomServ?clientRid=" + clientRid, true);
		xmlhttp.send();

	}

	function retrieveChatMsgAjax() {
		if (activeroom > -1) {
			var xmlhttp;
			if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
				xmlhttp = new XMLHttpRequest();
			} else {// code for IE6, IE5
				xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					document.getElementById("chatArea").innerHTML = xmlhttp.responseText;
				}
			}
			xmlhttp.open("GET", "GetChatMsgServ", true);
			xmlhttp.send();
		}
	}

	function retrieveChatRoomAjax() {
		var xmlhttp;
		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				document.getElementById("discussionarea").innerHTML = xmlhttp.responseText;

				//Live Filter
				// Retrieve the input field text and reset the count to zero
				var filter = $("#daFilter").val(), count = 0;

				// Loop through the comment list
				$("#discussionarea .displayInv,.displayChat").each(function() {

					// If the list item does not contain the text phrase fade it out
					if ($(this).text().search(new RegExp(filter, "i")) < 0) {
						$(this).hide();

						// Show the list item if the phrase matches and increase the count by 1
					} else {
						$(this).show();
						count++;
					}
				});
				daReinit();
			}
		}
		xmlhttp.open("GET", "GetChatRoomServ?tabFilter="+daFilterTabVal, true);
		xmlhttp.send();
	}

	function shuffleKeypadAuth(value) {

		var buttonsArr = [ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' ];
		for ( var j, x, i = buttonsArr.length; i; j = parseInt(Math.random()
				* i), x = buttonsArr[--i], buttonsArr[i] = buttonsArr[j], buttonsArr[j] = x)
			;

		$("#chatpinauth").val("" + $("#chatpinauth").val() + value);

		$("#virtualkeypadauth")
				.html(
						"<table>"
								+ "<tr><td><button type='button'  onclick='shuffleKeypadAuth("
								+ buttonsArr[0]
								+ ")'>"
								+ buttonsArr[0]
								+ "</button></td><td><button type='button'  onclick='shuffleKeypadAuth("
								+ buttonsArr[1]
								+ ")'>"
								+ buttonsArr[1]
								+ "</button></td><td><button type='button'  onclick='shuffleKeypadAuth("
								+ buttonsArr[2]
								+ ")'>"
								+ buttonsArr[2]
								+ "</button></td></tr>"
								+ "<tr><td><button type='button'  onclick='shuffleKeypadAuth("
								+ buttonsArr[3]
								+ ")'>"
								+ buttonsArr[3]
								+ "</button></td><td><button type='button'  onclick='shuffleKeypadAuth("
								+ buttonsArr[4]
								+ ")'>"
								+ buttonsArr[4]
								+ "</button></td><td><button type='button'  onclick='shuffleKeypadAuth("
								+ buttonsArr[5]
								+ ")'>"
								+ buttonsArr[5]
								+ "</button></td></tr>"
								+ "<tr><td><button type='button'  onclick='shuffleKeypadAuth("
								+ buttonsArr[6]
								+ ")'>"
								+ buttonsArr[6]
								+ "</button></td><td><button type='button'  onclick='shuffleKeypadAuth("
								+ buttonsArr[7]
								+ ")'>"
								+ buttonsArr[7]
								+ "</button></td><td><button type='button'  onclick='shuffleKeypadAuth("
								+ buttonsArr[8]
								+ ")'>"
								+ buttonsArr[8]
								+ "</button></td></tr>"
								+ "<tr><td></td><td><button type='button'  onclick='shuffleKeypadAuth("
								+ buttonsArr[9]
								+ ")'>"
								+ buttonsArr[9]
								+ "</button></td><td></td></tr>");
	}

	function shuffleKeypadReg(value, focusControl) {

		var buttonsArr = [ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' ];
		for ( var j, x, i = buttonsArr.length; i; j = parseInt(Math.random()
				* i), x = buttonsArr[--i], buttonsArr[i] = buttonsArr[j], buttonsArr[j] = x)
			;

		if (focusControl == 0) {
			$("#chatpinreg").val("" + $("#chatpinreg").val() + value);
		} else if (focusControl == 1) {
			$("#chatpincfm").val("" + $("#chatpincfm").val() + value);
		}

		$("#virtualkeypadreg")
				.html(
						"<table>"
								+ "<tr><td><button type='button'  onclick='shuffleKeypadReg("
								+ buttonsArr[0]
								+ ","
								+ focusControl
								+ ")'>"
								+ buttonsArr[0]
								+ "</button></td><td><button type='button'  onclick='shuffleKeypadReg("
								+ buttonsArr[1]
								+ ","
								+ focusControl
								+ ")'>"
								+ buttonsArr[1]
								+ "</button></td><td><button type='button'  onclick='shuffleKeypadReg("
								+ buttonsArr[2]
								+ ","
								+ focusControl
								+ ")'>"
								+ buttonsArr[2]
								+ "</button></td></tr>"
								+ "<tr><td><button type='button'  onclick='shuffleKeypadReg("
								+ buttonsArr[3]
								+ ","
								+ focusControl
								+ ")'>"
								+ buttonsArr[3]
								+ "</button></td><td><button type='button'  onclick='shuffleKeypadReg("
								+ buttonsArr[4]
								+ ","
								+ focusControl
								+ ")'>"
								+ buttonsArr[4]
								+ "</button></td><td><button type='button'  onclick='shuffleKeypadReg("
								+ buttonsArr[5]
								+ ","
								+ focusControl
								+ ")'>"
								+ buttonsArr[5]
								+ "</button></td></tr>"
								+ "<tr><td><button type='button'  onclick='shuffleKeypadReg("
								+ buttonsArr[6]
								+ ","
								+ focusControl
								+ ")'>"
								+ buttonsArr[6]
								+ "</button></td><td><button type='button'  onclick='shuffleKeypadReg("
								+ buttonsArr[7]
								+ ","
								+ focusControl
								+ ")'>"
								+ buttonsArr[7]
								+ "</button></td><td><button type='button'  onclick='shuffleKeypadReg("
								+ buttonsArr[8]
								+ ","
								+ focusControl
								+ ")'>"
								+ buttonsArr[8]
								+ "</button></td></tr>"
								+ "<tr><td></td><td><button type='button'  onclick='shuffleKeypadReg("
								+ buttonsArr[9]
								+ ","
								+ focusControl
								+ ")'>"
								+ buttonsArr[9]
								+ "</button></td><td></td></tr>");
	}
	
	function daReinit() {
		$(".displayChat")
				.click(
						function() {
							activeroom = $(this).attr('data-clientrid');
							setActiveRoom(activeroom);
							$("#chat").show();
							$(".chatName").text($(this).text());
							$(".chatUsers")
									.html(
											"<b>Users</b><ul><li>Jack</li><li>Alice</li><li>Robin</li></ul>");
						});

		$(".displayInv").click(function() {
			$("#cfmDialog").reveal();
		});
		
		$("#newChat").click(function() {
			createRoomAjax();
		});

		$("#chatPinReg").click(function() {
			$("#cpinDialog").reveal();
			shuffleKeypadReg("", 0);
		});

		$("#chatPinAuth").click(function() {
			$("#pinDialog").reveal();
			shuffleKeypadAuth("");
		});
	}

	$("#cfmDialog").reveal({
		/*autoOpen : false,
		height : 200,
		width : 350,
		modal : true*/
		animation : 'none',
		closeonbackgroundclick : true,
		dismissmodalclass : 'close-reveal-modal'
	});

	$("#pinDialog").reveal({
		animation : 'none',
		closeonbackgroundclick : true,
		dismissmodalclass : 'close-reveal-modal'
	});

	$("#invDialog").reveal({
		animation : 'none',
		closeonbackgroundclick : true,
		dismissmodalclass : 'close-reveal-modal'
	});

	function disableScroll() {
		//Firefox
		$('body').bind('DOMMouseScroll', function(e) {
			return false;
		});

		//IE, Opera, Safari
		$('body').bind('mousewheel', function(e) {
			return false;
		});
	}

	function RefreshTable(tableId, urlData) {
		$.getJSON(urlData, null, function(json) {
			table = $(tableId).dataTable();
			oSettings = table.fnSettings();

			table.fnClearTable(this);

			for ( var i = 0; i < json.aaData.length; i++) {
				table.oApi._fnAddData(oSettings, json.aaData[i]);
			}

			oSettings.aiDisplay = oSettings.aiDisplayMaster.slice();
			table.fnDraw();
		});
	}

	$(document)
			.ready(
					function() {
						
						chatUserTable = $("#chatInvUserDT").dataTable(
								{
									"bJQueryUI" : true,
									"bServerSide" : false,
									"bPaginate" : true,
									"sPaginationType" : "full_numbers",
									"bLengthChange" : false,
									"bInfo" : false,
									"iDisplayLength" : 5,
									"sAjaxSource" : "GetInvUserServ",
									"fnRowCallback" : function(nRow, aData,
											iDisplayIndex) {
										if (jQuery.inArray(aData[0],
												cutGaiSelected) != -1) {
											$(nRow).addClass('row_selected');
										}
										return nRow;
									},
									"aoColumns" : [ {
										"bVisible" : 0
									}, /* ID column */
									null, null, null, ]
								});

						$('#updateNotification').click(function(event) {  
		                    var username=$('#user').val();
		                	$.get('ActionServlet',{user:username},function(responseText) { 
		                        $('#welcometext').text(responseText);         
		                    });
		                });
						
						$('#chatInvForm')
								.submit(
										function() {
											if (cutGaiSelected.length == 0) {
												$("#sendInvErrorlbl")
														.text(
																"No invitees selected. Please select users you want to invite on the table above.");
												return false;
											}

											if ($('#roomdesc').val().length == 0) {
												$("#sendInvErrorlbl")
														.text(
																"Description field is empty.");
												return false;
											}

											sendInvAjax($('#roomdesc').val());

											$('#invDialog').trigger(
													'reveal:close');

											return false;

										});

						//$("#chatInvUserDT").tablesorter({widthFixed: true, widgets: ['zebra']}) 
						//.tablesorterPager({container: $("#pager")}); 

						//$("#daTab").tabs();
						
						$('.daTab li').click(function() {
						    daFilterTabVal = $(this).attr('data-tabVal');
						    $('.daTab li').removeAttr("id");
						    $(this).attr("id","current");
						    retrieveChatRoomAjax();
						});

						$("#chat").draggable({
							containment : 'window',
							scroll : false,
							handle : ".chathandle",
							appendTo : 'body',
						});

						$("#chat").mousedown(function() {
							disableScroll();
						}).mouseup(function() {
							$('body').unbind('mousewheel');
							$('body').unbind('DOMMouseScroll');
						});

						$("#chat").resizable({
							minHeight : 250,
							minWidth : 300,
							maxHeight : $(window).height() - 100,
							maxWidth : $(window).width() - 100,
							alsoResize : "#chatArea,#chatfield,#chatUsers",
						});

						$(window).bind("resize", function() {

							$('#sidebar').toggle($(this).width() >= 767);

						}).trigger("resize");

						$(".chathandle").disableSelection();

						$(".closeChat").click(function() {
							$("#chat").hide();
							activeroom = -1;
							setActiveRoom(activeroom);
						});

						$(".displayChat")
								.click(
										function() {
											activeroom = $(this).attr(
													'data-clientrid');
											setActiveRoom(activeroom);
											$("#chat").show();
											$(".chatName").text($(this).text());
											$(".chatUsers")
													.html(
															"<b>Users</b><ul><li>Jack</li><li>Alice</li><li>Robin</li></ul>");
										});

						$(".invUserLink").button().click(function() {
							$("#invDialog").reveal();
							RefreshTable('#chatInvUserDT', 'GetInvUserServ');
							$("#sendInvErrorlbl").text("");
							$("#roomdesc").val("");

							cutGaiSelected.length = 0;
						});

						$(".displayInv").click(function() {
							$("#cfmDialog").reveal();
						});
						
						$("#newChat").click(function() {
							createRoomAjax();
						});

						$("#chatPinReg").click(function() {
							$("#cpinDialog").reveal();
							shuffleKeypadReg("", 0);
						});

						$("#chatPinAuth").click(function() {
							$("#pinDialog").reveal();
							shuffleKeypadAuth("");
						});

						$.contextMenu({
							selector : '#dareaDiv',
							callback : function(key, options) {
								if (key == "create") {
									createRoomAjax();
								}
							},
							items : {
								"create" : {
									name : "Start New Chat Room"
								},

							}
						});

						$
								.contextMenu({
									selector : '.displayChat',
									callback : function(key, options) {
										if (key == "display") {
											activeroom = $(this).attr(
													'data-clientrid');
											setActiveRoom(activeroom);
											$("#chat").show();
											$(".chatName").text($(this).text());
											$(".chatUsers")
													.html(
															"<b>Users</b><ul><li>Jack</li><li>Alice</li><li>Robin</li></ul>");
										}
										if (key == "history") {
											window.location.href = "ViewChatHistory.jsp";
										}
									},
									items : {
										"display" : {
											name : "Display Chat Room"
										},
										"history" : {
											name : "View Chat History"
										},
										"leave" : {
											name : "Leave Chat Room"
										},

									}
								});

						$
								.contextMenu({
									selector : '.displayInv',
									autoHide : true,
									trigger : 'hover',
									delay : 500,
									callback : function(key, options) {
										if (key == "view") {
											activeinv = $(this).attr(
													'data-clientiid');
											//$("#cfmDialog").dialog("open");
											$('#cfmDialog').reveal();
										} else if (key == "accept") {
											var cfm = confirm("You are about to accept an invitation. Proceed?");
											if (cfm) {
												setInvStatusAjax($(this).attr(
														'data-clientiid'), 1);
											}
										} else if (key == "reject") {
											var cfm = confirm("You are about to reject an invitation. This action cannot be undone. Proceed?");
											if (cfm) {
												setInvStatusAjax($(this).attr(
														'data-clientiid'), 2);
											}
										}
									},
									items : {
										"view" : {
											name : "View Invitation"
										},
										"accept" : {
											name : "Accept"
										},
										"reject" : {
											name : "Reject"
										},

									}
								});

						//Table Selection
						$(document)
								.on(
										'click',
										'#chatInvUserDT tbody tr',
										function() {
											var aData = chatUserTable
													.fnGetData(this);
											var iId = aData[0];

											if (jQuery.inArray(iId,
													cutGaiSelected) == -1) {
												cutGaiSelected[cutGaiSelected.length++] = iId;
											} else {
												cutGaiSelected = jQuery
														.grep(
																cutGaiSelected,
																function(value) {
																	return value != iId;
																});
											}

											$(this).toggleClass('row_selected');
										});

						//Live Filter
						$("#daFilter").keyup(function() {
							// Update ajax area
							retrieveChatRoomAjax()
						});

					});
</script>

<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container-fluid">
			<button type="button" class="btn btn-navbar" data-toggle="collapse"
				data-target=".nav-collapse">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="brand" href="Index.jsp">File Haven</a>
			<div class="nav-collapse collapse">
				<p class="navbar-text pull-right">
				<%if(session.getAttribute("LoggedInUser")==null) {%>
					<a class="navbar-link" onclick="window.location.href=hosturl+'UploadFile.jsp';">Login</a>
				<%}
				else{%>
						
						Logged in as <a onclick="window.location.href=hosturl+'ViewPersonalInfoServlet';" class="navbar-link"><%= ((Account) request.getSession().getAttribute("LoggedInUser")).getUserName() %></a>
					
					<%} %>
				</p>
				
					<% if (((Account) request.getSession().getAttribute("LoggedInUser")).getType()=='A'||((Account) request.getSession().getAttribute("LoggedInUser")).getType()=='a'){ %>
						<ul class="nav">
							<li><a onclick="window.location.href=hosturl+'Index.jsp';">Home</a></li>

							<li><a>Account</a><ul>
						        <li><a onclick="window.location.href=hosturl+'RetrieveCompanyListServlet';">Create Account</a></li>
						        <li><a onclick="window.location.href=hosturl+'RetrieveAccountListServlet';">Update Accounts</a></li>
						        <li><a onclick="window.location.href=hosturl+'ViewAccountServlet';">View Accounts</a></li>
		        				</ul></li>
							<li><a>Company</a><ul>
						        <li><a onclick="window.location.href=hosturl+'CreateCompany.jsp';">Create Company</a></li>
		        				</ul></li>
		        			<li><a>Profile</a><ul>
						        <li><a onclick="window.location.href=hosturl+'ViewPersonalInfoServlet';">View Profile</a></li>
						        <li><a onclick="window.location.href=hosturl+'ChangePassword.jsp';">Change Password</a></li>
						        <li><a onclick="window.location.href=hosturl+'LogoutServlet';">Logout</a></li>
		        				</ul></li>

							<li><a>Notification</a></li>
						</ul>
					<% } %>
					
					<% if (((Account) request.getSession().getAttribute("LoggedInUser")).getType()=='C'||((Account) request.getSession().getAttribute("LoggedInUser")).getType()=='c'){ %>
						<ul class="nav">
							<li><a onclick="window.location.href=hosturl+'Index.jsp';">Home</a></li>
							<li><a>Account</a><ul>
						        <li><a onclick="window.location.href=hosturl+'RetrieveCompanyListServlet';">Create Account</a></li>
						        <li><a onclick="window.location.href=hosturl+'RetrieveAccountListServlet';">Update Accounts</a></li>
						        <li><a onclick="window.location.href=hosturl+'ViewAccountServlet';">View Accounts</a></li>
		        				</ul></li>
							<li><a>File</a><ul>
						        <li><a onclick="window.location.href=hosturl+'UploadFile.jsp';">Upload File</a></li>
						        <li><a onclick="window.location.href=hosturl+'ViewFileList.jsp';">View File List</a></li>
						        <li><a onclick="window.location.href=hosturl+'report.jsp';">File Statistics</a></li>
		        				</ul></li>
							<li><a>Profile</a><ul>
						        <li><a onclick="window.location.href=hosturl+'ViewPersonalInfoServlet';">View Profile</a></li>
						        <li><a onclick="window.location.href=hosturl+'ChangePassword.jsp';">Change Password</a></li>
						        <li><a onclick="window.location.href=hosturl+'LogoutServlet';">Logout</a></li>
		        				</ul></li>
							<li><a>Notification</a></li>
						</ul>
					<% } %>
					
					<% if (((Account) request.getSession().getAttribute("LoggedInUser")).getType()=='M'||((Account) request.getSession().getAttribute("LoggedInUser")).getType()=='m'){ %>
						<ul class="nav">
							<li><a onclick="window.location.href=hosturl+'Index.jsp';">Home</a></li>
							<li><a>Account</a><ul>
						        <li><a onclick="window.location.href=hosturl+'RetrieveCompanyListServlet';">Create Account</a></li>
						        <li><a onclick="window.location.href=hosturl+'RetrieveAccountListServlet';">Update Accounts</a></li>
						        <li><a onclick="window.location.href=hosturl+'ViewAccountServlet';">View Accounts</a></li>
		        				</ul></li>
							<li><a>File</a><ul>
						        <li><a onclick="window.location.href=hosturl+'UploadFile.jsp';">Upload File</a></li>
						        <li><a onclick="window.location.href=hosturl+'ViewFileList.jsp';">View File List</a></li>
						        <li><a onclick="window.location.href=hosturl+'report.jsp';">File Statistics</a></li>
		        				</ul></li>
							<li><a>Profile</a><ul>
						        <li><a onclick="window.location.href=hosturl+'ViewPersonalInfoServlet';">View Profile</a></li>
						        <li><a onclick="window.location.href=hosturl+'ChangePassword.jsp';">Change Password</a></li>
						        <li><a onclick="window.location.href=hosturl+'LogoutServlet';">Logout</a></li>
		        				</ul></li>
							<li><a>Notification</a></li>
						</ul>
					<% } %>
					
					<% if (((Account) request.getSession().getAttribute("LoggedInUser")).getType()=='F'||((Account) request.getSession().getAttribute("LoggedInUser")).getType()=='f'){ %>
						<ul class="nav">
							<li><a onclick="window.location.href=hosturl+'Index.jsp';">Home</a></li>
							
							<li><a>File</a><ul>
						        <li><a onclick="window.location.href=hosturl+'UploadFile.jsp';">Upload File</a></li>
						        <li><a onclick="window.location.href=hosturl+'ViewFileList.jsp';">View File List</a></li>
						        <li><a onclick="window.location.href=hosturl+'report.jsp';">File Statistics</a></li>
		        				</ul></li>
							<li><a>Profile</a><ul>
						        <li><a onclick="window.location.href=hosturl+'ViewPersonalInfoServlet';">View Profile</a></li>
						        <li><a onclick="window.location.href=hosturl+'ChangePassword.jsp';">Change Password</a></li>
						        <li><a onclick="window.location.href=hosturl+'LogoutServlet';">Logout</a></li>
		        				</ul></li>
							<li><a>Notification</a></li>
						</ul>
					<% } %>
					
					<% if (((Account) request.getSession().getAttribute("LoggedInUser")).getType()=='F'||((Account) request.getSession().getAttribute("LoggedInUser")).getType()=='f'){ %>
						<ul class="nav">
							<li><a onclick="window.location.href=hosturl+'Index.jsp';">Home</a></li>
							
							<li><a>File</a><ul>
						        <li><a onclick="window.location.href=hosturl+'UploadFile.jsp';">Upload File</a></li>
						        <li><a onclick="window.location.href=hosturl+'ViewFileList.jsp';">View File List</a></li>
						        <li><a onclick="window.location.href=hosturl+'report.jsp';">File Statistics</a></li>
		        				</ul></li>
							<li><a>Profile</a><ul>
						        <li><a onclick="window.location.href=hosturl+'ViewPersonalInfoServlet';">View Profile</a></li>
						        <li><a onclick="window.location.href=hosturl+'ChangePassword.jsp';">Change Password</a></li>
						        <li><a onclick="window.location.href=hosturl+'LogoutServlet';">Logout</a></li>
		        				</ul></li>
							<li><a>Notification</a></li>
						</ul>
					<% } %>
					
					<% if (((Account) request.getSession().getAttribute("LoggedInUser")).getType()=='E'||((Account) request.getSession().getAttribute("LoggedInUser")).getType()=='e'){ %>
						<ul class="nav">
							<li><a onclick="window.location.href=hosturl+'Index.jsp';">Home</a></li>
							
							<li><a onclick="window.location.href=hosturl+'ViewFileList.jsp';">View File List</a></li>
							<li><a>Profile</a><ul>
						        <li><a onclick="window.location.href=hosturl+'ViewPersonalInfoServlet';">View Profile</a></li>
						        <li><a onclick="window.location.href=hosturl+'ChangePassword.jsp';">Change Password</a></li>
						        <li><a onclick="window.location.href=hosturl+'LogoutServlet';">Logout</a></li>
		        				</ul></li>
							<li><a>Notification</a></li>
						</ul>
					<% } %>
				
				
			</div>
			<!--/.nav-collapse -->
		</div>
	</div>
</div>
<div id="sidebar" class="well sidebar-nav affix"
	style="margin-top: 60px; margin-left: 10px; width: 250px; z-index: 0;">
	<!--<ul class="nav nav-list">
		<li class="nav-header">Sidebar</li>
		<li class="active"><a href="#">Link</a></li>
		<li><a href="#">Link</a></li>
		<li><a href="#">Link</a></li>
		<li><a href="#">Link</a></li>
	 </ul> -->
	<p class="nav-header">Discussion Area</p>

	<ul class="daTab">
		<li id="current" data-tabVal="0"><a>ALL</a></li>
		<li data-tabVal="1"><a><img src='resources/img/chat/envelope.png' /></a></li>
		<li data-tabVal="2"><a><img
				src='resources/img/chat/iconmonstr-speech-bubble-15-icon.png' /></a></li>
	</ul>
	
	<div id="dareaDiv" style="border: solid 1px #ddd; height: 300px; padding: 5px;">
	<input type="text" id="daFilter" PlaceHolder="Type to filter" style="width:224px;"/>
		<div style="overflow-y:auto;overflow-x:hidden;height: 250px;">
			<ul id="discussionarea"  style="margin-left:0px;list-style-position:inside;">

			</ul>
		</div>
	</div>
</div>

<div id="chat" class="well sidebar-nav affix"
	style="display: none; padding-top: 1px; width: 300px; height: 250px; margin-top: 50px; margin-left: 10px; position: fixed !important; z-index: 1;">
	<span class="closeChat"
		style="cursor: pointer; float: right; display: inline;">&#215;</span>
	<div class="chathandle"
		style="cursor: move; display: inline; clear: none; text-align: center;">
		<h5 class="chatName">Chatroom1</h5>
		<hr />
	</div>
	<div id="chatUsers" class="chatUsers"
		style="padding: 1px; float: right; border: solid 1px grey; width: 80px !important; height: 130px; font-size: 10px;">
		<b>Users</b>
		<ul>
			<li>Jack</li>
			<li>Alice</li>
		</ul>

	</div>
	<div>
		<div id="chatArea"
			style="border: solid 1px grey; width: 200px; height: 130px; overflow-y: scroll;">

		</div>
		<br />
		<div style="display: inline;">
			<input type="text" id="chatfield" name="chatfield"
				style="margin: 1px; width: 130px; height: 20px !important; display: inline;" />
			<a href="#" class="btn" onclick="sendChatMsgAjax()"
				style="display: inline; margin: 0px;">Go</a>
		</div>
	</div>

	<a class="invUserLink"
		style="bottom: 30px; font-size: 10px; float: right;">Invite users</a>
</div>


<div id="invDialog" class="reveal-modal large"
	Style="background-color: white; border: grey solid 1px; z-index: 10000;">
	<a class="close-reveal-modal">&#215;</a>
	<form id="chatInvForm">
				<p><h4>Select users to invite</h4></p>
		<fieldset>
			<table class="display" id="chatInvUserDT">
	<thead>
		<tr>
			<th >ID</th>
			<th >UserID</th>
			<th >Name</th>
			<th >Position</th>

		</tr>
	</thead>
	<tbody>
		<tr>
			<td colspan="5" class="dataTables_empty">Loading data from server</td>
		</tr>
	</tbody>
	</table>
			<label for="roomdesc">Description</label> <textarea
				name="roomdesc" id="roomdesc"
				class="text ui-widget-content ui-corner-all"></textarea>
		</fieldset>
		<hr />
		
		<input type="submit" value="Send Invitation" />
		<button>Cancel</button>
	</form>
	<p><span id="sendInvErrorlbl" style="color:red;"></span></p>
</div>

<div id="cfmDialog" class="reveal-modal small"
	Style="background-color: white; border: grey solid 1px; z-index: 10000;">
	<a class="close-reveal-modal">&#215;</a>

	<h4>You are invited to Chatroom3</h4>
	<div>
		<p>This is a chatroom description</p>
	</div>

	<hr />
	<form>
		<button>Accept</button>
		<button>Deny</button>
		<button>Cancel</button>
	</form>

</div>

<div id="pinDialog" class="reveal-modal small"
	Style="background-color: white; border: grey solid 1px; z-index: 10000;">
	<a class="close-reveal-modal">&#215;</a>

	<h4>Please enter your pin</h4>

		<input onkeydown="return false;" id="chatpinauth" type="password" />
		<center><div id="virtualkeypadauth">
			
		</div></center>
	<hr />
	
		<button onclick="verifyPinAjax($('#chatpinauth').val())">Confirm</button>
		<button onclick="$('#chatpinauth').val('')">Clear</button>
		<button>Cancel</button>
	<p><span id="pinAuthErrorlbl" style="color:red;"></span></p>

</div>

<div id="cpinDialog" class="reveal-modal small"
	Style="background-color: white; border: grey solid 1px; z-index: 10000;">
	<a class="close-reveal-modal">&#215;</a>

	<h4>Create a New Pin</h4>

		<label for="chatpinreg">New pin:</label>
		<input onkeydown="return false;" onfocus="shuffleKeypadReg('',0)" name="chatpinreg" id="chatpinreg" type="password" />
		<label for="chatpinreg">Confirm pin:</label>
		<input onkeydown="return false;" onfocus="shuffleKeypadReg('',1)" name="chatpincfm" id="chatpincfm" type="password" />
		
		
		<center><div id="virtualkeypadreg">
			
		</div></center>
		<hr />
		
		<label for="pinAPassword">Account password:</label>
		<input name="pinAPassword" id="pinAPassword" type="password" />
	<hr />
	
		<button onclick="registerPinAjax()">Confirm</button>
		<button onclick="$('#chatpinreg').val('');$('#chatpincfm').val('');">Clear</button>
		<button>Cancel</button>
	<p><span id="pinRegErrorlbl" style="color:red;"></span></p>

</div>


<!--/.well -->
<div class="container-fluid">
	<div class="row-fluid">
		<div class="span3"></div>