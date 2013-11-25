require([ 'dojox/grid/EnhancedGrid', 'dojox/grid/enhanced/plugins/Pagination',
		'dojo/data/ItemFileWriteStore', 'dojox/grid/enhanced/plugins/Selector',
		'dojo/domReady!' ], function(EnhancedGrid, Pagination,
		ItemFileWriteStore, Selector) {
	/* set up data store */
	var data = {
		identifier : 'id',
		items : []
	};

	var store = new dojo.data.ItemFileWriteStore({
		data : data
	});

	/* set up layout */
	var layout = [ [ {
		name : 'Name',
		field : 'col1',
		width : "150px"
	}, {
		name : 'Email',
		field : 'col2',
		width : "150px"
	}, {
		name : 'Roles',
		field : 'col3',
		width : "230px"
	} ] ];

	/* create a new grid: */
	var grid = new dojox.grid.EnhancedGrid({
		id : 'grid',
		store : store,
		structure : layout,
		rowSelector : '20px',
		selectionMode : 'none',
		plugins : {
			pagination : {
				pageSizes : [ "25", "50", "All" ],
				defaultPageSize : "25",
				description : true,
				sizeSwitch : true,
				pageStepper : true,
				gotoButton : true,
				/* page step to be displayed */
				maxPageStep : 4,
				/* position of the pagination bar */
				position : "bottom"
			},
			selector : {
				row : "single",
				col : false,
				cell : false
			}
		}
	}, document.createElement('div'));

	/* append the new grid to the div */
	dojo.byId("gridDiv").appendChild(grid.domNode);

	/* Call startup() to render the grid */
	grid.startup();

	var handle = dojo.connect(grid, "onEndSelect", function(type, startPoint,
			endPoint, selected) {
		// alert("startPoint:"+selected.row[0].col3);
		console.log("selected id", selected.row[0].id);

		createRolesSelection(selected.row[0].id);

	});

	loadingRoles("search_roles", false);
});

var templatesInfo;

function refreshProjectBroad(projectId) {
	// loadingDialog.closeButtonNode.hidden = true;
	// loadingDialog.show();
	console.log("requesting", projectId);
	require(
			[ "dojo/dom", "dojo/request/xhr", "dojo/json", "dojo/parser" ],
			function(dom, xhr, JSON, parser) {

				dataset = {
					"projectId" : projectId
				};

				xhr("getProject", {
					handleAs : "json",
					query : dataset,
					method : "get",
					preventCache : true
				})
						.then(
								function(datas) {

									console.log("projectsummary", datas);

									dojo.byId("prj_id").value = projectId;
									dojo.byId("prj_cde").value = datas["projectIndentify"];
									dojo.byId("prj_name").innerHTML = datas["projectIndentify"];
									dojo.byId("prj_creator").innerHTML = datas["createdBy"];
									dojo.byId("prj_modify").innerHTML = datas["createdAt"];
									dojo.byId("prj_desc").innerHTML = datas["description"];
									templatesInfo = datas["templates"];

									refreshDocBroad(projectId);
								}, function(err) {
									// Handle the error condition
									console.log(err);
								}, function(evt) {
									// Handle a progress event from the request
									// if the
									// browser supports XHR2
									console.log(evt);
								});

			});

	// loadingDialog.hide();
}

function doUserSearch() {

	require([ "dojo/dom", "dojo/request/xhr", "dojo/json", "dojo/parser" ],
			function(dom, xhr, JSON, parser) {

				/* set up data store */
				var data = {
					identifier : 'id',
					items : []
				};

				dataset = {
					searchRoles : dom.byId("search_roles").value,
					searchUserName : dom.byId("search_user_name").value
				};

				xhr("doSearchUser", {
					handleAs : "json",
					query : dataset,
					method : "get",
					preventCache : true
				}).then(function(datas) {

					data.items = datas;
					var store = new dojo.data.ItemFileWriteStore({
						data : data
					});

					var grid = dijit.byId("grid");
					grid.setStore(store);
				}, function(err) {
					// Handle the error condition
					console.log(err);
				}, function(evt) {
					// Handle a progress event from the request if the
					// browser supports XHR2
					console.log(evt);
				});

			});
}

function loadingRoles(tagid, flag) {
	if (hasRegister(tagid))
		return;

	require([ "dojo/request/xhr", "dojo/json", "dojo/parser",
			"dojo/store/Memory", "dijit/tree/ObjectStoreModel", "dijit/Tree",
			"dijit/form/FilteringSelect" ], function(xhr, JSON, parser, Memory,
			ObjectStoreModel, Tree, FilteringSelect) {
		xhr.get("loadingRoles", {
			handleAs : "json"
		}).then(function(datas) {

			console.log("datas", datas);

			var clientStore = new Memory(datas);

			console.log("loadingRoles", clientStore);

			new FilteringSelect({
				id : tagid,
				name : "state",
				value : "",
				store : clientStore,
				searchAttr : "id",
				labelAttr : 'name',
				style : "width:240px;",
				required : flag,
				autoComplete : false
			}, tagid);

		}, function(err) {
			// Handle the error condition
			alert(err);
		}, function(evt) {
			// Handle a progress event from the request if the
			// browser supports XHR2
			alert(evt);
		});
	});

}

function roleDisplay() {
	loadingRolesBox("roleDisplay");
	// loadingClients("clientSelect",true);
	// loadingdate("prjym");
	// loadinguniqueno("prjno");
	roleDisplayDialog.show();
}

function loadingRolesBox(tagId) {

	require([ "dojo/dom", "dojo/request/xhr", "dojo/json", "dojo/parser" ],
			function(dom, xhr, JSON, parser) {

				xhr("getRoleDisplay", {
					handleAs : "json",
					method : "post",
					preventCache : true,
					headers : {
						'Content-Type' : 'application/json'
					}
				}).then(function(datas) {

					console.log("return datas", datas);

					if (datas.type == "success") {

						dojo.byId(tagId).innerHTML = datas.data;

					} else {
						alert("Fetch role lists error. ");
					}

				}, function(err) {
					// Handle the error condition
					console.log(err);
					alert("Fetch role lists error. " + err);
				}, function(evt) {
					// Handle a progress event from the request if the
					// browser supports XHR2
					console.log(evt);
					alert("Fetch role lists error. " + evt);
				});

			});

}

function createRolesSelection(userid) {
	loadingSelectionBox("createSelection", userid);
	// loadingClients("clientSelect",true);
	// loadingdate("prjym");
	// loadinguniqueno("prjno");
	createUserDialog.show();
}

function loadingSelectionBox(tagId, userid) {

	require([ "dojo/dom", "dojo/request/xhr", "dojo/json", "dojo/parser" ],
			function(dom, xhr, JSON, parser) {

				dom.byId("createUserName").value = "";
				dom.byId("createPassword").value = "";
				dom.byId("createEmail").value = "";
				dom.byId("createUserName").readOnly = false;

				dataset = {
					createUserName : userid
				};

				console.log("request user roles", dataset);

				xhr("getUsersRoleList", {
					handleAs : "json",
					data : JSON.stringify(dataset),
					method : "post",
					preventCache : true,
					headers : {
						'Content-Type' : 'application/json'
					}
				}).then(function(datas) {

					console.log("return datas", datas);

					if (datas.type == "success") {

						dojo.byId(tagId).innerHTML = datas.roles;
						if (userid) {
							dom.byId("createUserName").value = datas.username;
							dom.byId("createPassword").value = datas.password;
							dom.byId("createUserName").readOnly = true;
							dom.byId("createEmail").value = datas.email;
						}

					} else {
						alert("Fetch role lists error. ");
					}

				}, function(err) {
					// Handle the error condition
					console.log(err);
					alert("Fetch role lists error. " + err);
				}, function(evt) {
					// Handle a progress event from the request if the
					// browser supports XHR2
					console.log(evt);
					alert("Fetch role lists error. " + evt);
				});

			});

}

function submitEditUser() {

	console.log("submitEditUser");
	if (!userform.validate())
		return;

	require([ "dojo/dom", "dojo/request/xhr", "dojo/json", "dojo/parser",
			"dojo/query" ], function(dom, xhr, JSON, parser, query) {

		var list = [];
		var nodes = query(".rolecls");
		console.log("nodes", nodes);
		// iterate over every node in the document....SLOOOW
		for ( var x = 0; x < nodes.length; x++) {
			if (nodes[x].checked)
				list.push(nodes[x].value);

		}
		console.log("roles:", list);

		if(list.length == 0){
			alert("Please select at least one role");
			return;
		}
		
		if(dom.byId("createUserName").readOnly && 
				dom.byId("createPassword").value){
			if (!confirm("Password will be reset. Are you sure ?")) {
				return;
			}
		}else if(!dom.byId("createUserName").readOnly &&
				!dom.byId("createPassword").value){
			alert("Please input the new password.");
			return;
		}
		
		dataset = {
			createUserName : dom.byId("createUserName").value,
			createPassword : dom.byId("createPassword").value,
			createEmail : dom.byId("createEmail").value,
			createRoles : list
		};
		console.log("requestdataset", dataset);
		
		actionname = (dom.byId("createUserName").readOnly)?"updateUserRequest":"createUserRequest";
		
		xhr(actionname, {
			handleAs : "json",
			data : JSON.stringify(dataset),
			method : "post",
			preventCache : true,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).then(function(datas) {

			console.log("datas", datas);

			if (datas.type == "success") {
				alert("User information has been updated.");
				hideUserDialog();

				dom.byId("createUserName").value = "";
				dom.byId("createPassword").value = "";
				dom.byId("createEmail").value = "";
			} else {
				alert(datas.message);
			}

		}, function(err) {
			// Handle the error condition
			console.log(err);
			alert("Project created fail ." + err);
		}, function(evt) {
			// Handle a progress event from the request if the
			// browser supports XHR2
			console.log(evt);
			alert("Project created fail ." + evt);
		});
	});
}

function hideUserDialog(){
	createUserDialog.hide();
	doUserSearch();
}
