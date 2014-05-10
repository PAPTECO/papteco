<!DOCTYPE html>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<!-- Meta -->
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<!-- End of Meta -->

<!-- Page title -->
<title><spring:message code="system.name" /></title>
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
<script type="text/javascript" src="lib/scripts/dosearch.js"></script>
<script type="text/javascript" src="lib/scripts/docopr.js"></script>
<script type="text/javascript" src="lib/scripts/uploadfile.js"></script>
<script type="text/javascript" src="lib/scripts/shortcut.js"></script>
<script type="text/javascript" src="lib/scripts/login.js"></script>
<script type="text/javascript" src="lib/scripts/loadcompleted.js"></script>
<!-- End of Libraries -->
</head>
<body class="claro" >

	<%@ include file="header.jsp"%>
	<div id="index_mainbc" data-dojo-id="mainbc"
		data-dojo-type="dijit/layout/BorderContainer"
		style="width: 100%; height: 100%; margin: 0px; padding: 0px;">

		<div data-dojo-id="maincpleft"
			data-dojo-type="dijit/layout/ContentPane"
			data-dojo-props="region:'leading'" style="margin: 0px; padding: 0px;">
			<div style="width: 250px; height: 100%;">
				<div data-dojo-id="maincp_tc"
					data-dojo-type="dijit/layout/TabContainer" class="leadingtabcus">

					<div data-dojo-id="maincp_tc_cp1"
						data-dojo-type="dijit/layout/ContentPane" title="<spring:message code="dashboard.left.menu.tools" />"
						data-dojo-props="selected:true">
						<div style="cursor: pointer" onclick="createProjectShowUp()"
							id="SearchButton3" class="bWhite notext_wrap" title="">
							<div>
								<img src="assets/icons/my_projects_folder.png"
									style="width: 48px" />
							</div>
							<div><spring:message code="dashboard.left.menu.create" /></div>
						</div>
						<br>
						<div style="cursor: pointer" onclick="preserveNos()"
							id="PresrvButton" class="bWhite notext_wrap" title="">
							<div>
								<img src="assets/icons/1387310090_Numbers-128.png"
									style="width: 48px" />
							</div>
							<div><spring:message code="dashboard.left.menu.preserve" /></div>
						</div>
					</div>
					<div data-dojo-id="maincp_tc_cp2"
						data-dojo-type="dijit/layout/ContentPane" title="<spring:message code="dashboard.left.menu.search" />">

						<ul class="filesDown" id="shortcut_ul">
							<li><span class="fileSuccess"></span> 202-039-4056
								SampleProject1 <span class="remove"></span></li>

						</ul>
					</div>

					<div data-dojo-id="maincp_tc_cp3"
						data-dojo-type="dijit/layout/ContentPane" title="<spring:message code="dashboard.left.menu.projects" />">
						<ul class="filesDown" id="prjshortcut_ul">
							<li><span class="fileSearch"></span> Client is 203 <span
								class="remove"></span></li>

						</ul>
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
					<legend><spring:message code="dashboard.left.menu.search" /></legend>
					<div class="content_search_area notext_wrap">
						<div class="notext_wrap" style="padding-left: 40px">&nbsp;</div>
						<div class="notext_wrap blank_interval"><spring:message code="dashboard.content.input.clientNo" /></div>
						<input id="searchclient" />

						<div class="notext_wrap" style="padding-left: 40px">&nbsp;</div>
						<div class="notext_wrap blank_interval"><spring:message code="dashboard.content.input.anyKey" /></div>
						<input type="text" name="search_anykey" id="search_anykey"
							value="" data-dojo-type="dijit/form/ValidationTextBox"
							data-dojo-props="regExp:'[\\w]+', invalidMessage:'Invalid Non-Space Text.'" />
						<div class="notext_wrap" style="padding-left: 20px">&nbsp;</div>
						<a id="SearchButton1" class="insideFont bLightBlue notext_wrap"
							title="" href="#" onClick="doSearch()"><spring:message code="dashboard.left.menu.search" /></a>
						<div class="blank_interval notext_wrap">&nbsp;</div>
						<a id="SearchButton1" class="insideFont bGreen notext_wrap"
							title="" href="#"
							onclick="initSearchShortcutName();searchSaveDialog.show()"><spring:message code="dashboard.content.input.addtoCustomFilter" /></a>
						<div id="result1"></div>
					</div>
				</fieldset>
				<div id="gridDiv"></div>
			</div>
			<!-- MainSearchTab -->
			<div id="DirectoryTab" style="display: none">

				<div id="blank_interrupt">&nbsp;</div>
				<fieldset class="bgray">
					<legend>

						<a id="SearchButton2" onClick="backtosearch();"
							class="insideFont bGold notext_wrap " title="" href="#"><spring:message code="dashboard.content.project.back" /></a>
						<div class="blank_interval notext_wrap">&nbsp;</div>
						<a id="SearchButton3" class="insideFont bLightBlue notext_wrap"
							title="" href="#" onClick="refreshProjectBroad(getProjectId())"><spring:message code="dashboard.content.project.refresh" /></a>
						<div class="blank_interval notext_wrap">&nbsp;</div>
						<a id="SearchButton10" onClick="deleteProject();"
							class="insideFont bRed notext_wrap " title="" href="#"><spring:message code="dashboard.content.project.delete" /></a>

					</legend>
					<div style="float: left; padding: 30px">
						<input type="hidden" id="prj_id"></input> <input type="hidden"
							id="prj_cde"></input>
						<h1 id="prj_name"><spring:message code="dashboard.content.project.name" /></h1>
						<div class="blank_interrupt"></div>
						<a id="SearchButton2"
							onClick="initProjectShortcutName();projectstSaveDialog.show()"
							class="insideFont bGreen notext_wrap " title="" href="#"><spring:message code="dashboard.content.project.addToShortCut" /></a>
					</div>
					<div style="padding: 30, 0, 30, 0">
						<label for="sf" class="labelstyle"><spring:message code="dashboard.content.project.creator" /> </label><label
							for="sf" class="labelContentstyle" id="prj_creator">John</label>
						<label for="sf"><spring:message code="dashboard.content.project.lastmo" /> </label> <label for="sf"
							class="notext_wrap labelContentstyle" id="prj_modify"><b>07
								May 2013 12:59:80</b></label>
						<div class="blank_interrupt"></div>
						<span style="width: auto;" id="prj_desc">Here's an example
							of creating a Tree in markup. It's not wrapping the store in
							Observable(), so that store updates won't be reflected into the
							tree. (Wrapping the store in Observable is not easy to do through
							markup. If you need the functionality, we suggest creating the
							store in JavaScript, or create your own custom store. In any
							case, the dijit.tree.Model and dijit.Tree themselves can still be
							created in markup.) </span>
					</div>
				</fieldset>
				<div class="bgray1">
					<div data-dojo-id="sectioncp"
						data-dojo-type="dijit/layout/ContentPane" title="Projects"
						style="float: left">
						<div class="fileInfo">
							<fieldset>
								<legend><spring:message code="dashboard.content.project.documentdetails" /></legend>


								<p style="float: right">
									<a id="ViewDoc" onClick="alert('Please select file.')"
										class="insideFont bGold notext_wrap " title="" href="#"><spring:message code="dashboard.content.project.viewOnly" /></a>&nbsp;&nbsp;&nbsp; <a id="UploadDoc"
										onClick="uploadFileFormShow();"
										class="insideFont bGreen notext_wrap " title="" href="#"><spring:message code="dashboard.content.project.uploaddoc" /></a>
								</p>

								<hr />
								<p>
									<b><span id="doc_name"></span></b>
								</p>
								<div id="field_details"></div>

							</fieldset>

						</div>

					</div>
					<div data-dojo-id="sectioncp2"
						data-dojo-type="dijit/layout/ContentPane" title="Projects"
						style="width: 400px">
						<div id="doctree"></div>
					</div>
				</div>
				<%@ include file="PopupMenu.jsp"%>

			</div>

			<div data-dojo-type="dijit/Dialog" data-dojo-id="waitingDialog"
				title="Release object ..." id="waitingDialog">
				<div id="waitingDialogtext"></div>
			</div>



			<div data-dojo-type="dijit/Dialog"
				style="overflow-y: visible; background-color: white"
				data-dojo-id="uploadFileFormDialog" title="<spring:message code="dashboard.content.project.uploaddoc" />"
				execute="alert('submitted w/args:\n' + dojo.toJson(arguments[0], true));">

				<form method="post" action="submitUploadFile" id="uploadFileForm"
					enctype="multipart/form-data">
					<div class="dijitDialogPaneContentArea">
						<span><label for="date" id="upload_doctype_id_header">Document
								Type: </label><input id="upload_doctype"></span>&nbsp;&nbsp;&nbsp;&nbsp;<span><label
							for="date" id="uploader_header">From File: </label><input
							name="uploadfile" type="file" id="uploader" style="display: none" /><input
							class="uploadfileqryonly" id="uploadedCopyForm" readOnly
							name="uploadedCopyForm" value="" size="30" style="display: none" /><input
							id="isRev" name="isRev" type="hidden"> </span>

						<div id="insert_content"></div>
						<div class="dijitDialogPaneActionBar">
							<input label="Upload" data-dojo-type="dijit/form/Button"
								onClick="validateUploadForm();" type="button" />
							<button data-dojo-type="dijit/form/Button" type="button"
								onClick="uploadFileFormDialog.hide()">Cancel</button>
						</div>
					</div>


				</form>
			</div>

			<div data-dojo-type="dijit/Dialog" data-dojo-id="presrvDialog"
				title="<spring:message code="dashboard.left.menu.preserve" />"
				execute="alert('submitted w/args:\n' + dojo.toJson(arguments[0], true));">

				<div>
					<table class="dijitdialog_index">
						<tr>
							<td><label for="name"><spring:message code="dashboard.left.menu.from" /> </label></td>
							<td><input type="text" id="presrv_from" value=""
								class="dialogText"></td>
							<td><label for="name"><spring:message code="dashboard.left.menu.to" /> </label></td>
							<td><input type="text" id="presrv_to" value=""
								class="dialogText"></td>
						</tr>
					</table>
				</div>

				<div class="dijitDialogPaneActionBar">
					<button id="submitPresrvnos" data-dojo-type="dijit/form/Button"
						type="button" onClick="submitPresrvNos()"
						data-dojo-id="submitPresrvnos"><spring:message code="dashboard.left.menu.save" /></button>
					<button data-dojo-type="dijit/form/Button" type="button"
						onClick="presrvDialog.hide()" data-dojo-id="canncelPresrvnos"><spring:message code="dashboard.left.menu.cancel" /></button>
				</div>
			</div>


			<div data-dojo-type="dijit/Dialog" data-dojo-id="searchSaveDialog"
				title="<spring:message code="dashboard.left.menu.search" />"
				execute="alert('submitted w/args:\n' + dojo.toJson(arguments[0], true));">

				<div>
					<table class="dijitdialog_index">
						<tr>
							<td><label for="name"><spring:message code="dashboard.left.menu.searchname" /> </label></td>
							<td><input data-dojo-type="dijit/form/TextBox" type="text"
								id="search_sav_name" value="New searcher" class="dialogText"></td>
						</tr>
					</table>
				</div>

				<div class="dijitDialogPaneActionBar">
					<button data-dojo-type="dijit/form/Button" type="button"
						onClick="submitSaveSearch()"><spring:message code="dashboard.left.menu.save" /></button>
					<button data-dojo-type="dijit/form/Button" type="button"
						onClick="searchSaveDialog.hide()"><spring:message code="dashboard.left.menu.cancel" /></button>
				</div>
			</div>

			<div data-dojo-type="dijit/Dialog" data-dojo-id="projectstSaveDialog"
				title="<spring:message code="dashboard.left.menu.projects" />"
				execute="alert('submitted w/args:\n' + dojo.toJson(arguments[0], true));">

				<div>
					<table class="dijitdialog_index">
						<tr>
							<td><label for="name"><spring:message code="dashboard.left.menu.projects" /></label></td>
							<td><input data-dojo-type="dijit/form/TextBox" type="text"
								id="prj_sav_name" value="New shortcutname" class="dialogText"></td>
						</tr>
					</table>
				</div>

				<div class="dijitDialogPaneActionBar">
					<button data-dojo-type="dijit/form/Button" type="button"
						onClick="submitPrjSaveSearch()"><spring:message code="dashboard.left.menu.save" /></button>
					<button data-dojo-type="dijit/form/Button" type="button"
						onClick="projectstSaveDialog.hide()"><spring:message code="dashboard.left.menu.cancel" /></button>
				</div>
			</div>


			<div data-dojo-type="dijit/Dialog" data-dojo-id="createProjectDialog"
				title="<spring:message code="dashboard.content.project.crtnewprj" />"
				execute="alert('submitted w/args:\n' + dojo.toJson(arguments[0], true));">

				<div data-dojo-type="dijit/form/Form" id="prjcreateform"
					data-dojo-id="prjcreateform" encType="multipart/form-data"
					action="" method="">

					<div class="dijitDialogPaneContentArea">

						<div style="float: left">
							<fieldset style="float: left">
								<legend><spring:message code="dashboard.content.project.reqinfo" /></legend>
								<table class="dijitdialog_index">
									<tr>
										<td><label for="name">Client No: </label></td>
										<td><input class="blackbox" id="clientSelect" /></td>
									</tr>
									<tr>
										<td><label for="name">Create Date: </label></td>
										<td><input id="prjym" /></td>
									</tr>
									<tr>
										<td><label for="name">Unique No: </label></td>
										<td><input id="prjno" style="width: 130px;" maxLength="3" /><span
											id="prjPreserve"></span></td>
									</tr>
									<tr>
										<td><label for="name">Short Desc: </label></td>
										<td><input id="prjshortdesc"
											style="width: 330px; height: 24px" required="true"
											data-dojo-type="dijit/form/ValidationTextBox" /></td>
									</tr>
									<tr>
										<td><label for="desc">Long Desc: </label></td>
										<td><textarea id="prjlongdesc" name="prjlongdesc"
												data-dojo-type="dijit/form/Textarea" rows="4"
												style="width: 340px;"></textarea></td>
									</tr>

								</table>
								<div class="dijitDialogPaneActionBar">
									<button data-dojo-id="createprjsubmit"
										data-dojo-type="dijit/form/Button" type="button"
										onClick="submitCreateProject()"><spring:message code="dashboard.content.project.create" /></button>
									<button data-dojo-type="dijit/form/Button" type="button"
										onClick="createProjectDialog.hide()"><spring:message code="dashboard.content.project.cancel" /></button>
								</div>
							</fieldset>
						</div>

						<fieldset>
							<legend><spring:message code="dashboard.content.project.folders" /></legend>
							<div id="createProjectFolders"></div>
						</fieldset>
					</div>
				</div>

			</div>

		</div>
		<div data-dojo-id="maincpbottom" class="buttom"
			data-dojo-type="dijit/layout/ContentPane"
			data-dojo-props="region:'bottom'">

			<div>PAPTECO Engineering & Sales (GZ) Co., Ltd.</div>
			<div>China: Room 706, Building #6, Hisun Xingyue, N0. 383
				Panyu avenue north, Panyu, Guangzhou, Guangdong province, People's
				Republic of China P.C. 511400</div>
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


