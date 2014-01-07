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
		name : 'No',
		field : 'col1',
		width : "150px"
	}, {
		name : 'Name',
		field : 'col2',
		width : "150px"
	}] ];

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

		createClientSelection(selected.row[0].id);

	});
	
	loadingClients("search_client_no", false);
	
	doClientSearch();
});

function doClientSearch() {

	require([ "dojo/dom", "dojo/request/xhr", "dojo/json", "dojo/parser" ],
			function(dom, xhr, JSON, parser) {

				/* set up data store */
				var data = {
					identifier : 'id',
					items : []
				};

				dataset = {
					searchClientNo : dom.byId("search_client_no").value,
					searchClientName : dom.byId("search_client_name").value
				};

				xhr("secure/doSearchClient", {
					handleAs : "json",
					query : dataset,
					method : "get",
					preventCache : true
				}).then(function(datas) {

					console.log("debug...",datas);
					if(datas["type"]=="fail"){
						alert(datas["message"]);
						return;
					}
					
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


function createClientSelection(clientId) {
	loadingClientSelectionBox(clientId);
}

function loadingClientSelectionBox(clientId) {

	require([ "dojo/dom", "dojo/request/xhr", "dojo/json", "dojo/parser" ],
			function(dom, xhr, JSON, parser) {

				dom.byId("createClientNo").value = "";
				dom.byId("createClientName").value = "";
				dom.byId("createClientNo").readOnly = false;
				dom.byId("deleteClientTag").innerHTML = "";

				dataset = {
						clientNo : clientId
				};

				console.log("request client", dataset);

				xhr("secure/getClientMaintList", {
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

						if (clientId) {
							dom.byId("createClientNo").value = datas.clientno;
							dom.byId("createClientName").value = datas.clientname;
							dom.byId("createClientNo").readOnly = true;
							dom.byId("deleteClientTag").innerHTML = "<a onClick=\"deleteClient('"+datas.clientno+"');\" class='insideFont bRed notext_wrap' href='#'>X</a>";
						}
						createClientDialog.show();
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

function deleteClient(clientno) {

	console.log("deleteClient"+clientno);

	if(!confirm(clientno+ " will be deteled. Are you sure?"))
		return;
	
	require([ "dojo/dom", "dojo/request/xhr", "dojo/json", "dojo/parser",
			"dojo/query" ], function(dom, xhr, JSON, parser, query) {
		
		dataset = {
				clientNo : clientno
		};
		console.log("requestdataset", dataset);
		
		xhr("secure/deleteClientRequest", {
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
				alert("Client has been deleted.");
				hideClientDialog();

				dom.byId("createClientNo").value = "";
				dom.byId("createClientName").value = "";
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

function submitClient() {

	console.log("submitClient");
	if (!clientform.validate())
		return;

	require([ "dojo/dom", "dojo/request/xhr", "dojo/json", "dojo/parser",
			"dojo/query" ], function(dom, xhr, JSON, parser, query) {

		dataset = {
				clientNo : dom.byId("createClientNo").value,
				clientName : dom.byId("createClientName").value
		};
		console.log("requestdataset", dataset);
		
		actionname = (dom.byId("createClientNo").readOnly)?"secure/updateClientRequest":"secure/createClientRequest";
		
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
				alert("Client information has been updated.");
				hideClientDialog();

				dom.byId("createClientNo").value = "";
				dom.byId("createClientName").value = "";
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

function hideClientDialog(){
	createClientDialog.hide();
	doClientSearch();
}


