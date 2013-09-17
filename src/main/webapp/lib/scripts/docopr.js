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
		dojo.byId("doc_name").innerHTML = filename.substring(0, toIndex);
		// dojo.byId("doc_last_modi_at").innerHTML = docTree.getLabel(item);
		if (item.field_details) {
			dojo.byId("field_details").innerHTML = item.field_details;
		} else {
			dojo.byId("field_details").innerHTML = "";
		}
		// dojo.byId("ViewDoc").onClick =
		// "window.open('viewDocs?projectId=a;filename='"+docTree.getLabel(item)+"')";

		console.log(dojo.byId("ViewDoc"));

		dojo.byId("ViewDoc").onclick = function() {

			if (item.field_details) {
				window.open("viewDocs?projectId=" + item.projectId
						+ "&filename=" + docTree.getLabel(item));
			} else {
				alert('Please select file.');
			}

		}

		// dojo.byId("doc_init_upload_at").innerHTML=docTree.getLabel(item);
		// dojo.byId("doc_init_upload_by").innerHTML=docTree.getLabel(item);
	});

});

function refreshDocBroad(projectId) {

	console.log("requesting", projectId);
	require([ "dojo/dom", "dojo/request/xhr", "dojo/json", "dojo/parser",
			"dijit/Tree", "dojo/data/ItemFileReadStore",
			"dijit/tree/ForestStoreModel" ], function(dom, xhr, JSON, parser,
			Tree, ItemFileReadStore, ForestStoreModel) {

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


