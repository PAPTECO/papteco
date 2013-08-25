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
		name : 'Project ID',
		field : 'col1',
		width : "150px"
	}, {
		name : 'Last Modification At',
		field : 'col2',
		width : "150px"
	}, {
		name : 'Description',
		field : 'col3',
		width : "230px"
	}, {
		name : 'Searched document',
		field : 'col4',
		width : "300px"
	}, {
		name : 'Authorized Users',
		field : 'col5',
		width : "100px"
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
		refreshProjectBroad(selected.row[0].id);

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
			fadeIt("MainSearchTab");
			fadeItShow("DirectoryTab");

		});

	});

});

function refreshProjectBroad(projectId) {
	loadingDialog.closeButtonNode.hidden = true;
	loadingDialog.show();
	console.log("requesting",projectId);
	require([ "dojo/dom", "dojo/request/xhr", "dojo/json", "dojo/parser" ],
			function(dom, xhr, JSON, parser) {

				dataset = {
						"projectId" : projectId
				};

				xhr("getProject", {
					handleAs : "json",
					query : dataset,
					method : "get"
				}).then(function(datas) {

					console.log("projectsummary",datas);
					
					dojo.byId("prj_name").innerHTML=datas["projectIndentify"];
					dojo.byId("prj_creator").innerHTML=datas["createdBy"];
					dojo.byId("prj_modify").innerHTML=datas["createdAt"];
					dojo.byId("prj_desc").innerHTML=datas["description"];
					
				}, function(err) {
					// Handle the error condition
					console.log(err);
				}, function(evt) {
					// Handle a progress event from the request if the
					// browser supports XHR2
					console.log(evt);
				});

			});

	loadingDialog.hide();
}

function doSearch() {

	require([ "dojo/dom", "dojo/request/xhr", "dojo/json", "dojo/parser" ],
			function(dom, xhr, JSON, parser) {

				/* set up data store */
				var data = {
					identifier : 'id',
					items : []
				};

				dataset = {
					searchClinetno : dom.byId("search_clinetno").value,
					searchAnykey : dom.byId("search_anykey").value
				};

				xhr("doSearch", {
					handleAs : "json",
					query : dataset,
					method : "get"
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