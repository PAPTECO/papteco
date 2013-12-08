require([ "dijit/Tree", "dojo/data/ItemFileReadStore",
		"dijit/tree/ForestStoreModel", "dojo/store/Memory",
		"dijit/tree/ObjectStoreModel", "dojo/domReady!" ], function(Tree,
		ItemFileReadStore, ForestStoreModel, Memory, ObjectStoreModel) {

	var data = {
		identifier : 'id',
		"label" : "name",
		items : []
	};

	var docstore = new ItemFileReadStore({
		data : data
	});
	console.log("tree model", docstore);
	var doctreeModel = new ForestStoreModel({
		store : docstore,
		query : {
			"type" : "continent"
		},
		rootId : "root",
		rootLabel : "Continents",
		childrenAttrs : [ "children" ],
		openOnClick : true
	});
	console.log("tree treeModel4", doctreeModel);
	var docTree = new Tree({
		model : doctreeModel,
		showRoot : false
	}, "doctree");

	console.log("tree myTree4", docTree);
	docTree.startup();

	dojo.connect(docTree, 'onClick', function(item, node, evt) {
		console.log("Item", item); // This gives you the object in your store
		console.log("Node", node); // This gives you the dijit widget object
		// (UI)
		console.log("Event", evt); // This gives you the event object
		console.log('identifier: ' + docTree.getLabel(item));

		filename = docTree.getLabel(item);
		toIndex = filename.indexOf("(") > 0 ? filename.indexOf("(")
				: filename.length;
		
		//display docname
		
//		doc_name = filename.substring(0, toIndex);
//		alert(filename.substring(0, toIndex));
		dojo.byId("doc_name").innerHTML = filename.substring(0, toIndex);
		console.log("doc_name",dojo.byId("doc_name"));
		
		// dojo.byId("doc_last_modi_at").innerHTML = docTree.getLabel(item);

		if (item.ftype == "file") {
			dojo.byId("field_details").innerHTML = item.field_details;
		} else {
			doc_type = item.docType;
			fileList = templatesInfo[doc_type];
			
			console.log("doc_type",doc_type);
			console.log(fileList);
			
			templatelink = "";
			if(fileList){
			for(var i=0;i<fileList.length;i++){
				templatelink = templatelink +
				"<div style=\"height:40px;\"><a onClick=\"uploadFileFormShow('"+doc_type+"','"+fileList[i]+"');\" class='insideFont bGreen notext_wrap' href='#'>Copy from "+fileList[i]+"</a></div>";
				console.log(fileList[i]);
			}
			}
			dojo.byId("field_details").innerHTML = templatelink;
		}
		// dojo.byId("ViewDoc").onClick =
		// "window.open('viewDocs?projectId=a;filename='"+docTree.getLabel(item)+"')";

		console.log(dojo.byId("ViewDoc"));

		dojo.byId("ViewDoc").onclick = function() {

			if (item.field_details) {
				window.open("secure/viewDocs?projectId=" + item.projectId
						+ "&filename=" + filename.substring(0, toIndex));
			} else {
				alert('Please select file.');
			}

		}

		// dojo.byId("doc_init_upload_at").innerHTML=docTree.getLabel(item);
		// dojo.byId("doc_init_upload_by").innerHTML=docTree.getLabel(item);
	});

});

function beforeExpandAll(treeId) {
	var openedTab=new Array(); 
    var tree = dijit.byId(treeId);
    nodes = tree.rootNode.getChildren();
    var node;
    var count=0;
    for (var i = 0; i < nodes.length; i++){
            node = nodes[i];
            if (node.isExpanded) {
            	console.log("opened node ",node.label.substring(0,3));
            	openedTab[count++]=node.label.substring(0,3);
            }
    }
    console.log("opened ids ",openedTab);
    return openedTab;
} 

function expandTabs(treeId,openedTab) {
    var tree = dijit.byId(treeId);
    nodes = tree.rootNode.getChildren();
    var node;
    for (var i = 0; i < nodes.length; i++){
            node = nodes[i];
            console.log("opened ids node",node.label.substring(0,3));
            for (var j = 0; j < openedTab.length; j++){
            	
            	if (node.label.substring(0,3) == openedTab[j]) {
                	console.log("matched");
                        tree._expandNode(node);
                }
            }
            
    }
} 

function refreshDocBroad(projectId) {

	console.log("requesting", projectId);
	require([ "dojo/dom", "dojo/request/xhr", "dojo/json", "dojo/parser",
			"dijit/Tree", "dojo/data/ItemFileReadStore",
			"dijit/tree/ForestStoreModel" ], function(dom, xhr, JSON, parser,
			Tree, ItemFileReadStore, ForestStoreModel) {

		openedTab = beforeExpandAll("doctree");
		
		dataset = {
			"projectId" : projectId
		};

		xhr("getDocs", {
			handleAs : "json",
			query : dataset,
			method : "get",
			preventCache : true
		}).then(function(data) {

			console.log("refreshDocBroad", data);

			var docstore = new ItemFileReadStore({
				data : data
			});

			console.log("docstore", docstore);

			var doctreeModel = new ForestStoreModel({
				store : docstore,
				query : {
					"type" : "continent"
				},
				rootId : "root",
				rootLabel : "Continents",
				childrenAttrs : [ "children" ],
				openOnClick : true
			});

			dijit.byId("doctree").dndController.selectNone(); // As
			// per
			// the
			// answer
			// below
			// Credit to this discussion:
			// http://mail.dojotoolkit.org/pipermail/dojo-interest/2010-April/045180.html
			// Close the store (So that the store will do a new
			// fetch()).
			dijit.byId("doctree").model.store.clearOnClose = true;
			dijit.byId("doctree").model.store.close();

			// Completely delete every node from the dijit.Tree
			dijit.byId("doctree")._itemNodesMap = {};
			dijit.byId("doctree").rootNode.state = "UNCHECKED";
			dijit.byId("doctree").model.root.children = null;

			// Destroy the widget
			dijit.byId("doctree").rootNode.destroyRecursive();

			// Recreate the model, (with the model again)
			dijit.byId("doctree").model.constructor(doctreeModel);

			// Rebuild the tree
			dijit.byId("doctree").postMixInProperties();
			dijit.byId("doctree")._load();

			expandTabs("doctree",openedTab);
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

function fillMyAccountBox(userid) {

	require([ "dojo/dom", "dojo/request/xhr", "dojo/json", "dojo/parser" ],
			function(dom, xhr, JSON, parser) {

				dom.byId("createMyUserName").value = "";
				dom.byId("createMyPassword").value = "";
				dom.byId("createMyEmail").value = "";

				dataset = {
					createUserName : userid
				};

				console.log("request user roles", dataset);

				xhr("getMyUsersRoleList", {
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

						if (userid) {
							dom.byId("createMyUserName").value = datas.username;
							dom.byId("createMyPassword").value = datas.password;
							dom.byId("createMyEmail").value = datas.email;
						}
						myuserDialog.show();
					} else if (datas.type == "fail") {

						alert(datas.message);
					}else {
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

function submitMyUser() {

	console.log("submitMyUser");
	if (!myuserform.validate())
		return;

	require([ "dojo/dom", "dojo/request/xhr", "dojo/json", "dojo/parser",
			"dojo/query" ], function(dom, xhr, JSON, parser, query) {
		
		if(dom.byId("createMyPassword").value){
			if (!confirm("Password will be reset. Are you sure ?")) {
				return;
			}
		}
		
		dataset = {
			createUserName : dom.byId("createMyUserName").value,
			createPassword : dom.byId("createMyPassword").value,
			createEmail : dom.byId("createMyEmail").value
		};
		console.log("requestdataset", dataset);
		
		xhr("updateMyUserRequest", {
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
				alert("Your information has been updated.");
				hideMyUserDialog();

				dom.byId("createMyUserName").value = "";
				dom.byId("createMyPassword").value = "";
				dom.byId("createMyEmail").value = "";
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

function hideMyUserDialog(){
	myuserDialog.hide();
}