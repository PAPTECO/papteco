<!DOCTYPE html>
<html>
<head>
<!-- Meta -->
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<!-- End of Meta -->

<!-- Page title -->
<title>Wide Admin</title>
<!-- End of Page title -->

<!-- Libraries -->
<link type="text/css" href="css/layout.css" rel="stylesheet" />
<link type="text/css" rel="stylesheet"
	href="lib/dijit/themes/claro/claro.css">
<link type="text/css" rel="stylesheet" href="css/customs.css">

<!-- for grid display -->
<link type="text/css" rel="stylesheet"
	href="lib/dojox/grid/enhanced/resources/claro/EnhancedGrid.css">
<link type="text/css" rel="stylesheet"
	href="lib/dojox/grid/enhanced/resources/EnhancedGrid_rtl.css">

<link type="text/css" rel="stylesheet"
	href="lib/dojox/grid/resources/Grid.css">
<link type="text/css" rel="stylesheet"
	href="lib/dojox/grid/resources/claroGrid.css">

<link rel="stylesheet" href="lib/dojox/form/resources/FileInput.css" />

<script>
	dojoConfig = {
		async : true,
		parseOnLoad : true
	}
</script>


<script type="text/javascript" src="lib/dojo/dojo.js"></script>
<script>
	require([ "dojo/parser", "dijit/layout/BorderContainer",
			"dijit/layout/ContentPane" ]); // layout of content

	require([ "dojo/parser", "dijit/layout/AccordionContainer",
			"dijit/layout/AccordionPane" ]); // left side

	require([ "dojo/parser", "dijit/layout/TabContainer",
			"dijit/layout/ContentPane" ]);

	require([ "dojox/grid/LazyTreeGrid", "dijit/tree/ForestStoreModel",
			"dojo/data/ItemFileWriteStore" ]);

	/** for dialog**/
	require([ "dojo/parser", "dijit/Dialog", "dijit/form/Button",
			"dijit/form/TextBox", "dijit/form/DateTextBox",
			"dijit/form/TimeTextBox" ]);

	/** for textara**/
	require([ "dojo/parser", "dijit/form/Textarea" ]);

	/** for upload**/
	require([ "dojox/form/FileInput" ]);

	/** for tab container**/
	require([ "dojo/parser", "dijit/layout/TabContainer",
			"dijit/layout/ContentPane" ]);

	require([ "dojo/parser", "dijit/Menu", "dijit/MenuItem",
			"dijit/MenuSeparator", "dijit/PopupMenuItem", "dijit/ColorPalette" ]);

	/** for folder tree*/
	require([ "dojo/parser", "dojo/data/ItemFileReadStore", "dijit/Tree" ]);

	require([ "dojo/parser", "dojo/store/Memory", "dojo/query!css2",
			"dijit/Menu", "dijit/MenuItem", "dijit/tree/ObjectStoreModel",
			"dijit/Tree" ]);

	/** for folder tree end*/

	require([ "dojo/parser", "dojo/store/Memory",
			"dijit/tree/ObjectStoreModel", "dijit/Tree" ]);

	require([ "dojo/parser", "dijit/form/DateTextBox" ]);

	// for validation on input box
	require([ "dojo/parser", "dijit/form/ValidationTextBox" ]);

	require([ "dojox/grid/TreeGrid" ]);

	require([ "dijit/form/Button", "dojo/dom", "dojo/domReady!" ], function(
			Button, dom) {
		// Create a button programmatically:
		var myButton = new Button({
			label : "Search",
			onClick : function() {
				// Do something:
				dom.byId("result1").innerHTML += "Thank you! ";
			}
		}, "SearchButton");
	});

	function backtosearch() {
		require([ "dojo/_base/fx", "dojo/dom-style" ], function(fx, style) {
			// Function linked to the button to trigger the fade.
			function fadeIt(tabname) {
				style.set(tabname, "opacity", "1");
				var fadeArgs = {
					node : tabname
				};
				fx.fadeOut(fadeArgs).play();
				style.set(tabname, "display", "none");
			}
			function fadeItShow(tabname) {
				style.set(tabname, "opacity", "0");
				style.set(tabname, "display", "block");
				var fadeArgs = {
					node : tabname
				};
				fx.fadeIn(fadeArgs).play();

			}
			fadeItShow("MainSearchTab");
			fadeIt("DirectoryTab");

		});
	}
</script>
<script type="text/javascript" src="lib/scripts/core.js"></script>
<script type="text/javascript" src="lib/scripts/createprj.js"></script>
<script type="text/javascript" src="lib/scripts/users.js"></script>
<script type="text/javascript" src="lib/scripts/docopr.js"></script>
<script type="text/javascript" src="lib/scripts/uploadfile.js"></script>
<script type="text/javascript" src="lib/scripts/login.js"></script>

<!-- End of Libraries -->
</head>
<body class="claro">

	<%@ include file="header.jsp"%>

	<div data-dojo-id="mainbc"
		data-dojo-type="dijit/layout/BorderContainer"
		style="width: 100%; height: 100%; margin: 0px; padding: 0px;">

		<div data-dojo-id="maincpleft"
			data-dojo-type="dijit/layout/ContentPane"
			data-dojo-props="region:'leading'" style="margin: 0px; padding: 0px;">
			<div style="width: 250px; height: 100%;">
				<div data-dojo-id="maincp_tc"
					data-dojo-type="dijit/layout/TabContainer" class="leadingtabcus">

					<div data-dojo-id="maincp_tc_cp1"
						data-dojo-type="dijit/layout/ContentPane" title="Tools"
						data-dojo-props="selected:true">
						<div style="cursor:pointer" onclick="createRolesSelection()" id="SearchButton3"
							class="bWhite notext_wrap" title="" href="#">
							<div>
								<img src="assets/icons/1387312350_human-folder-public.png" style="width: 48px" />
							</div>
							<div>Create Users</div>
						</div>
						<br>
						<div style="cursor:pointer" onclick="roleDisplay()" id="roleDisplayButton"
							class="bWhite notext_wrap" title="" href="#">
							<div>
								<img src="assets/icons/1387312500_readme.png" style="width: 48px" />
							</div>
							<div>Roles</div>
						</div>
					</div>

				</div>
			</div>
		</div>
		<div data-dojo-id="maincpcenter"
			data-dojo-type="dijit/layout/ContentPane"
			data-dojo-props="region:'center'" style="margin: 0px; padding: 0px;">

			<div id="MainSearchTab" class="gridsize">
				<div class="blank_interrupt"></div>
				<fieldset>
					<legend>Search</legend>
					<div class="content_search_area notext_wrap">
						<div class="notext_wrap" style="padding-left: 40px">&nbsp;</div>
						<div class="notext_wrap blank_interval">User Name</div>
						<input type="text" name="search_user_name" id="search_user_name"
							value="" data-dojo-type="dijit/form/ValidationTextBox"
							data-dojo-props="regExp:'[\\w]+', invalidMessage:'Invalid Non-Space Text.'" />

						<div class="notext_wrap" style="padding-left: 40px">&nbsp;</div>
						<div class="notext_wrap blank_interval">Roles</div>
						<input id="search_roles" />
						<div class="notext_wrap" style="padding-left: 20px">&nbsp;</div>

						<a id="SearchButton1" class="insideFont bLightBlue notext_wrap"
							title="" href="#" onClick="doUserSearch() ">Search</a>

						<div id="result1"></div>
					</div>
				</fieldset>
				<div id="gridDiv"></div>
			</div>

			<div data-dojo-type="dijit/Dialog" data-dojo-id="createUserDialog"
				title="User Information"
				execute="alert('submitted w/args:\n' + dojo.toJson(arguments[0], true));">

				<div data-dojo-type="dijit/form/Form" id="userform"
					data-dojo-id="userform" encType="multipart/form-data" action=""
					method="">

					<div class="dijitDialogPaneContentArea">

						<div>
							<fieldset style="float: left; height: 430px;">
								<legend>Require Information </legend>
								<table class="dijitdialog_index">
									<tr>
										<td><label for="name">User Name: </label></td>
										<td><input id="createUserName" required="true"
											data-dojo-type="dijit/form/ValidationTextBox" /> &nbsp;&nbsp;<div id="deletetag" style="float:right"></div></td>
									</tr>
									<tr>
										<td><label for="name">Password: </label></td>
										<td><input id="createPassword" type="password" required="false"
											data-dojo-type="dijit/form/ValidationTextBox"/></td>
									</tr>
									<tr>
										<td><label for="name">Email: </label></td>
										<td><input id="createEmail" required="true"
											data-dojo-type="dijit/form/ValidationTextBox" /></td>
									</tr>

								</table>
								<div class="dijitDialogPaneActionBar">
									<button data-dojo-id="createprjsubmit"
										data-dojo-type="dijit/form/Button" type="button"
										onClick="submitEditUser()">Submit</button>
									<button data-dojo-type="dijit/form/Button" type="button"
										onClick="hideUserDialog()">Cancel</button>
								</div>
							</fieldset>
							<fieldset>
								<legend>Roles (<a href="#" onclick="roleDisplay()">Definition?</a>)</legend>

								<div style="height: 410px; overflow-y: auto;"
									id="createSelection"></div>

							</fieldset>
						</div>


					</div>
				</div>

			</div>

			<div data-dojo-type="dijit/Dialog" data-dojo-id="roleDisplayDialog"
				title="Role Information"
				execute="alert('submitted w/args:\n' + dojo.toJson(arguments[0], true));">

				<div data-dojo-type="dijit/form/Form" id="roleform"
					data-dojo-id="roleform" encType="multipart/form-data" action=""
					method="">

					<div style="margin:20px;" class="roleDijitDialogPaneContentArea">

						<div >
							<fieldset>
								<legend>Roles</legend>

								<div style="height: 410px; overflow-y: auto;"
									id="roleDisplay"></div>
								<div class="dijitDialogPaneActionBar">
									<button data-dojo-type="dijit/form/Button" type="button"
										onClick="roleDisplayDialog.hide()">OK</button>
								</div>
							</fieldset>
						</div>


					</div>
				</div>

			</div>

		</div>
		<div data-dojo-id="maincpbottom" class="buttom"
			data-dojo-type="dijit/layout/ContentPane"
			data-dojo-props="region:'bottom'">

			<div>PAPTECO Engineering & Sales (GZ) Co., Ltd.</div>
			<div>China: Room 706, Building #6, HighSun Xing Yue, N0. 383
				Panyu avenue north, Panyu, Guangzhou, Guangdong province, People¡¯s
				republic of China P.O£º511400</div>
			<div>TEL.: 86-20-3104 0418, FAX.: 86-20-3104 0298, E-mail:
				garylai@papteco.com, fabian@papteco.com</div>
			<div>Copyright 2013-2014 by Papteco. All Rights Reserved.</div>
		</div>
		<!-- end of bottom-->
		<div data-dojo-type="dijit/Dialog" data-dojo-id="loadingDialog"
			title="Loading...">Please wait...</div>

	</div>
</body>
</html>


