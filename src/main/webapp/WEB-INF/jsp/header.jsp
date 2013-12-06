<script language=javascript>
self.setInterval("checkifLogin()",60000);

function checkifLogin()
  {
	require([ "dojo/dom", "dojo/request/xhr", "dojo/json", "dojo/parser" ],
			function(dom, xhr, JSON, parser) {

				xhr("validateUser", {
					handleAs : "json",
					method : "get",
					preventCache : true,
					headers : {
						'Content-Type' : 'application/json'
					}
				}).then(function(datas) {

					console.log("return datas", datas);

					if (datas.type == "fail") {

						alert(datas.message);
						window.location = "login.jsp";
						return
					}

				}, function(err) {
					window.location = "login.jsp";
				}, function(evt) {
					window.location = "login.jsp";
				});

			});
  }
checkifLogin();
</script>

<!-- Container -->
	<div id="container">

		<!-- Header -->
		<div id="header">

			<!-- Top -->
			<div id="top">
				<!-- Logo -->
				<div class="logo">
					<a href="#" title="Administration Home" class="tooltip"><img
						src="assets/logo.png" alt="Wide Admin" /></a>
				</div>
				<!-- End of Logo -->

				<!-- Meta information -->
				<div class="meta">
					<p>Welcome, <%=request.getSession().getAttribute("LOGIN_USER")  %>!</p>
					<ul>
						<li><a href="#" onclick="doLogout()" title="End administrator session"
							class="tooltip"><span class="ui-icon ui-icon-power"></span>Logout</a></li>
						<li><a href="#" title="Change current settings"
							class="tooltip"><span class="ui-icon ui-icon-wrench"></span>Download Client</a></li>
						<li><a href="#" title="Go to your account" onclick="fillMyAccountBox('admin')" class="tooltip"><span
								class="ui-icon ui-icon-person"></span>My account</a></li>
					</ul>
				</div>
				<!-- End of Meta information -->
			</div>
			<!-- End of Top-->

			<!-- The navigation bar -->
			<div id="navbar">
				<ul class="nav">
					<li><a href="index">Dashboard</a></li>
					<li><a href="members">Members</a></li>
					<li><a href="users">PIMS Users</a></li>
					<!-- <li><a href="clients">Clients</a></li> -->
					<li><a href="templates">Templates</a></li>
					<li><a href="#">Others</a>
						<ul>
							<li><a href="#" onclick="aboutSysDialog.show()">About System</a></li>
						</ul></li>
				</ul>
			</div>
			<!-- End of navigation bar" -->

		</div>
		<!-- End of Header -->
	</div>
	<!-- End of Container -->
	
	<div data-dojo-type="dijit/Dialog" data-dojo-id="aboutSysDialog"
			title="About System"><div id="aboutDialogtext">Version 1.0.0</div>
			<br><div id="aboutDialogtext">@Copyright 2013</div></div>