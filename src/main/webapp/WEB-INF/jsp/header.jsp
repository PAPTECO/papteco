<script language=javascript>
	self.setInterval("checkifLogin()", 60000);

	function checkifLogin() {
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
				<p>
					<spring:message code="menu.toolstips.welcome" />,
					<%=request.getSession().getAttribute("LOGIN_USER")%>!
				</p>
				<ul>
					<li><a href="#" onclick="doLogout()"
						title="End administrator session" class="tooltip"><span
							class="ui-icon ui-icon-power"></span><spring:message code="menu.toolstips.logout" /></a></li>
					<li><a href="#" title="Change current settings"
						class="tooltip"><span class="ui-icon ui-icon-wrench"></span><spring:message code="menu.toolstips.downloadclient" /></a></li>
					<li><a href="#" title="Go to your account"
						onclick="fillMyAccountBox('<%=request.getSession().getAttribute("LOGIN_USER")%>')"
						class="tooltip"><span class="ui-icon ui-icon-person"></span><spring:message code="menu.toolstips.myacc" /></a></li>
				</ul>
			</div>
			<!-- End of Meta information -->
		</div>
		<!-- End of Top-->

		<!-- The navigation bar -->
		<div id="navbar">
			<ul class="nav">
				<li><a href="index"><spring:message code="menu.dashboard" /></a></li>
				<li><a href="members"><spring:message code="menu.members" /></a></li>
				<li><a href="users"><spring:message code="menu.users" /></a></li>
				<li><a href="clients"><spring:message code="menu.clients" /></a></li>
				<li><a href="templates"><spring:message code="menu.templates" /></a></li>
				<li><a href="#"><spring:message code="menu.others" /></a>
					<ul>
						<li><a href="?language=en_US"><spring:message code="menu.others.english"/></a></li>
						<li><a href="?language=cn_ZH"><spring:message code="menu.others.chinese"/></a></li>
						<li><a href="#" onclick="aboutSysDialog.show()"><spring:message code="menu.others.about"/></a></li>
					</ul></li>
			</ul>
		</div>
		<!-- End of navigation bar" -->

	</div>
	<!-- End of Header -->
</div>
<!-- End of Container -->

	<div id="occupydiv" class="occupydiv">
		<div id="occupydivloading" class="occupydivloading"></div>
	</div>
	
<div data-dojo-type="dijit/Dialog" data-dojo-id="aboutSysDialog"
	title="About System">
	<div id="aboutDialogtext">Version 1.0.0</div>
	<br>
	<div id="aboutDialogtext">@Copyright 2013</div>
</div>

<div data-dojo-type="dijit/Dialog" data-dojo-id="myuserDialog" id="myuserDialog" 
	title="<spring:message code="account.info" />">

	<div data-dojo-type="dijit/form/Form" id="myuserform"
		data-dojo-id="myuserform" encType="multipart/form-data" action=""
		method="">

		<div class="dijitMyuserAccDialogPaneContentArea">

			<div>
				<fieldset style="float: left; height: 200px;">
					<legend><spring:message code="custom.left.menu.requireinfo" /> </legend>
					<table class="dijitdialog_index">
						<tr>
							<td><label for="name"><spring:message code="users.left.menu.username" />: </label></td>
							<td><input id="createMyUserName" required="true" readOnly
								data-dojo-type="dijit/form/ValidationTextBox" />
							</div></td>
						</tr>
						<tr>
							<td><label for="name"><spring:message code="users.left.menu.password" />: </label></td>
							<td><input id="createMyPassword" type="password" /></td>
						</tr>
						<tr>
							<td><label for="name"><spring:message code="users.left.menu.email" />: </label></td>
							<td><input id="createMyEmail" required="true"
								data-dojo-type="dijit/form/ValidationTextBox" /></td>
						</tr>

					</table>
					<div class="dijitDialogPaneActionBar">
						<button data-dojo-id="createprjsubmit"
							data-dojo-type="dijit/form/Button" type="button"
							onClick="submitMyUser()"><spring:message code="users.left.menu.submit" /></button>
						<button data-dojo-type="dijit/form/Button" type="button"
							onClick="hideMyUserDialog()"><spring:message code="users.left.menu.cancel" /></button>
					</div>
				</fieldset>
			</div>


		</div>
	</div>

</div>