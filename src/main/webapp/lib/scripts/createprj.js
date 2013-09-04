function loadingFolders(tagid) {

	if (hasRegister(tagid))
		return;

	require([ "dijit/Tree", "dojo/data/ItemFileReadStore",
			"dijit/tree/ForestStoreModel", "dojo/domReady!" ], function(Tree,
			ItemFileReadStore, ForestStoreModel) {
		var store2 = new ItemFileReadStore({
			url : "http://localhost:8080/Papteco/getPredefineStructureFolders"
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

function loadingClients(tagid) {
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

			var filteringSelect = new FilteringSelect({
				id : tagid,
				name : "state",
				value : "",
				store : clientStore,
				searchAttr : "id",
				labelAttr: 'name',
				style : "width:340px;",
				required : true,
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
					node.value = datas['max'];
					node.readOnly = true;

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
	loadingClients("clientSelect");
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
	
	if(!prjcreateform.validate())
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
				}).then(function(datas) {

					console.log("datas", datas);
					dom.byId("clientSelect").value = "";
					dom.byId("prjshortdesc").value = "";
					dom.byId("prjlongdesc").value = "";
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