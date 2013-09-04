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
		
		dojo.byId("doc_name").innerHTML=docTree.getLabel(item);
		dojo.byId("doc_last_modi_at").innerHTML=docTree.getLabel(item);
		dojo.byId("doc_last_modi_by").innerHTML=docTree.getLabel(item);
		dojo.byId("doc_init_upload_at").innerHTML=docTree.getLabel(item);
		dojo.byId("doc_init_upload_by").innerHTML=docTree.getLabel(item);
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
			method : "get"
		}).then(function(datas) {

			console.log("refreshDocBroad", datas);

			var data = {
				identifier : 'id',
				"label" : "name",
				items : []
			};

			data.items = [ {
				"id" : "AF",
				"name" : "Africa",
				"type" : "continent",
				"population" : "900 million",
				"area" : "30,221,532 sq km",
				"timezone" : "-1 UTC to +4 UTC",
				"children" : [ {
					"_reference" : "EG"
				}, {
					"_reference" : "KE"
				}, {
					"_reference" : "SD"
				} ]
			}, {
				"id" : "EG",
				"name" : "Egypt",
				"type" : "country"
			}, {
				"id" : "KE",
				"name" : "Kenya",
				"type" : "country",
				"children" : [ {
					"_reference" : "Nairobi"
				}, {
					"_reference" : "Mombasa"
				} ]
			}, {
				"id" : "Nairobi",
				"name" : "Nairobi",
				"type" : "city"
			}, {
				"id" : "Mombasa",
				"name" : "Mombasa",
				"type" : "city"
			}, {
				"id" : "SD",
				"name" : "Sudan",
				"type" : "country",
				"children" : {
					"_reference" : "Khartoum"
				}
			}, {
				"id" : "Khartoum",
				"name" : "Khartoum",
				"type" : "city"
			}, {
				"id" : "AS",
				"name" : "Asia",
				"type" : "continent",
				"children" : [ {
					"_reference" : "CN"
				}, {
					"_reference" : "IN"
				}, {
					"_reference" : "RU"
				}, {
					"_reference" : "MN"
				} ]
			}, {
				"id" : "CN",
				"name" : "China",
				"type" : "country"
			}, {
				"id" : "IN",
				"name" : "India",
				"type" : "country"
			}, {
				"id" : "RU",
				"name" : "Russia",
				"type" : "country"
			}, {
				"id" : "MN",
				"name" : "Mongolia",
				"type" : "country"
			}, {
				"id" : "OC",
				"name" : "Oceania",
				"type" : "continent",
				"population" : "21 million",
				"children" : {
					"_reference" : "AU"
				}
			}, {
				"id" : "AU",
				"name" : "Australia",
				"type" : "country",
				"population" : "21 million"
			}, {
				"id" : "EU",
				"name" : "Europe",
				"type" : "continent",
				"children" : [ {
					"_reference" : "DE"
				}, {
					"_reference" : "FR"
				}, {
					"_reference" : "ES"
				}, {
					"_reference" : "IT"
				} ]
			}, {
				"id" : "DE",
				"name" : "Germany",
				"type" : "country"
			}, {
				"id" : "FR",
				"name" : "France",
				"type" : "country"
			}, {
				"id" : "ES",
				"name" : "Spain",
				"type" : "country"
			}, {
				"id" : "IT",
				"name" : "Italy",
				"type" : "country"
			}, {
				"id" : "NA",
				"name" : "North America",
				"type" : "continent",
				"children" : [ {
					"_reference" : "MX"
				}, {
					"_reference" : "CA"
				}, {
					"_reference" : "US"
				} ]
			}, {
				"id" : "MX",
				"name" : "Mexico",
				"type" : "country",
				"population" : "108 million",
				"area" : "1,972,550 sq km",
				"children" : [ {
					"_reference" : "Mexico City"
				}, {
					"_reference" : "Guadalajara"
				} ]
			}, {
				"id" : "Mexico City",
				"name" : "Mexico City",
				"type" : "city",
				"population" : "19 million",
				"timezone" : "-6 UTC"
			}, {
				"id" : "Guadalajara",
				"name" : "Guadalajara",
				"type" : "city",
				"population" : "4 million",
				"timezone" : "-6 UTC"
			}, {
				"id" : "CA",
				"name" : "Canada",
				"type" : "country",
				"population" : "33 million",
				"area" : "9,984,670 sq km",
				"children" : [ {
					"_reference" : "Ottawa"
				}, {
					"_reference" : "Toronto"
				} ]
			}, {
				"id" : "Ottawa",
				"name" : "Ottawa",
				"type" : "city",
				"population" : "0.9 million",
				"timezone" : "-5 UTC"
			}, {
				"id" : "Toronto",
				"name" : "Toronto",
				"type" : "city",
				"population" : "2.5 million",
				"timezone" : "-5 UTC"
			}, {
				"id" : "US",
				"name" : "United States of America",
				"type" : "country"
			}, {
				"id" : "SA",
				"name" : "South America",
				"type" : "continent",
				"children" : [ {
					"_reference" : "BR"
				}, {
					"_reference" : "AR"
				} ]
			}, {
				"id" : "BR",
				"name" : "Brazil",
				"type" : "country",
				"population" : "186 million"
			}, {
				"id" : "AR",
				"name" : "Argentina",
				"type" : "country",
				"population" : "40 million"
			} ];

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