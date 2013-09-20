function loadingFolders(tagid) {

	if (hasRegister(tagid))
		return;

	require([ "dijit/Tree", "dojo/data/ItemFileReadStore",
			"dijit/tree/ForestStoreModel", "dojo/domReady!" ], function(Tree,
			ItemFileReadStore, ForestStoreModel) {
		var store2 = new ItemFileReadStore({
			url : "getPredefineStructureFolders"
		});
		console.log(store2);

		var treeModel2 = new ForestStoreModel({
			store : store2,
			query : {
				"type" : "folder"
			},
			rootId : "root",
			rootLabel : "Continents"
		});

		var myTree2 = new Tree({
			model : treeModel2,
			showRoot : false
		}, tagid);
		myTree2.startup();
	});

}

function loadingClients(tagid,flag) {
	if (hasRegister(tagid))
		return;

	require([ "dojo/request/xhr", "dojo/json", "dojo/parser",
			"dojo/store/Memory", "dijit/tree/ObjectStoreModel", "dijit/Tree",
			"dijit/form/FilteringSelect" ], function(xhr, JSON, parser, Memory,
			ObjectStoreModel, Tree, FilteringSelect) {
		xhr.get("getClientInfo", {
			handleAs : "json"
		}).then(function(datas) {

			console.log("datas", datas);

			var clientStore = new Memory(datas);

			console.log("clientStore", clientStore);

			new FilteringSelect({
				id : tagid,
				name : "state",
				value : "",
				store : clientStore,
				searchAttr : "id",
				labelAttr : 'name',
				style : "width:340px;",
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

function loadingdate(tagid) {
	if (hasRegister(tagid))
		return;

	require([ "dojo/parser", "dijit/form/DateTextBox" ], function(parser,
			DateTextBox) {

		parser.parse();

		new DateTextBox({
			displayedValue : currentYearMonth(),
			name : tagid,
			constraints : {
				datePattern : 'yyyyMM'
			}

		}, tagid);

	});
}

function loadinguniqueno(tagid) {
	require([ "dojo/dom", "dojo/request/xhr", "dojo/json", "dojo/parser" ],
			function(dom, xhr, JSON, parser) {
				xhr.get("getUniqueNo", {
					handleAs : "json",
					preventCache : true
				}).then(function(datas) {
					console.log("datas", datas);

					var node = dom.byId(tagid);
					console.log("tagid", node);
					node.value = datas.max;
					dom.byId("prjPreserve").innerHTML = datas.preserve;
					//node.readOnly = true;

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

function createProjectShowUp() {
	loadingFolders("createProjectFolders");
	loadingClients("clientSelect",true);	
	loadingdate("prjym");
	loadinguniqueno("prjno");
	createProjectDialog.show();
}

function currentYearMonth() {

	var dateobj = new Date();
	var m = dateobj.getMonth() + 1;
	var rst = ""
	if (m < 10)
		rst = dateobj.getFullYear() + "0" + m;
	else
		rst = dateobj.getFullYear() + "" + m;

	return rst;
}

function submitCreateProject() {

	if (!prjcreateform.validate())
		return;

	require([ "dojo/dom", "dojo/request/xhr", "dojo/json", "dojo/parser" ],
			function(dom, xhr, JSON, parser) {

				dataset = {
					clientno : dom.byId("clientSelect").value,
					createDate : dom.byId("prjym").value,
					uniqueno : dom.byId("prjno").value,
					shortdesc : dom.byId("prjshortdesc").value,
					longdesc : dom.byId("prjlongdesc").value
				};
				console.log("requestdataset", dataset);
				xhr("createProject", {
					handleAs : "json",
					data : JSON.stringify(dataset),
					method : "post",
					preventCache : true,
					headers : {
						'Content-Type' : 'application/json'
					}
				}).then(
						function(datas) {

							console.log("datas", datas);

							if (datas.type == "success") {
								alert("Project created success.Project Id is "
										+ datas.projectcode);
								createProjectDialog.hide();
								refreshDocBroad(dom.byId("prjno").value);

								dom.byId("clientSelect").value = "";
								dom.byId("prjshortdesc").value = "";
								dom.byId("prjlongdesc").value = "";
							} else {
								alert("Project created fail. " + datas.message);
							}

							refreshProjectBroad(dom.byId("prjno").value);

							fadeIt("MainSearchTab");
							fadeItShow("DirectoryTab");

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

function preserveNos() {

	require([ "dojo/dom", "dojo/request/xhr", "dojo/json", "dojo/parser" ],
			function(dom, xhr, JSON, parser) {

				dom.byId("presrv_from").value = "";
				dom.byId("presrv_to").value = "";

				xhr("getPreserveNos", {
					handleAs : "json",
					method : "get",
					preventCache : true,
					headers : {
						'Content-Type' : 'application/json'
					}
				}).then(function(datas) {

					dom.byId("presrv_from").value = datas.from;
					dom.byId("presrv_to").value = datas.to;

				}, function(err) {
					// Handle the error condition
					console.log(err);
					alert("Get preserve number fail ." + err);
				}, function(evt) {
					// Handle a progress event from the request if the
					// browser supports XHR2
					console.log(evt);
					alert("Get preserve number fail ." + evt);
				});

			});
	presrvDialog.show();
}

function submitPresrvNos() {

	alert("hi");

	require([ "dojo/dom", "dojo/request/xhr", "dojo/json", "dojo/parser" ],
			function(dom, xhr, JSON, parser) {

				datafrom = dom.byId("presrv_from").value;
				datato = dom.byId("presrv_to").value;

				if (datafrom && datato) {

					xhr("submitPresrvNos", {
						handleAs : "json",
						method : "get",
						preventCache : true,
						headers : {
							'Content-Type' : 'application/json'
						}
					}).then(
							function(datas) {

								alert(datafrom + " - " + datato
										+ " has been preserved.");

								presrvDialog.hide();
							}, function(err) {
								// Handle the error condition
								console.log(err);
								alert("Fail preserve ." + err);
							}, function(evt) {
								// Handle a progress event from the request if
								// the
								// browser supports XHR2
								console.log(evt);
								alert("Fail preserve ." + evt);
							});

				} else {
					alert("Please input from or to completely.");
				}
			});

}