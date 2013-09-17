function submitSaveSearch(){
	
	//searchSaveDialog.isValid();
	
	require([ "dojo/dom", "dojo/request/xhr", "dojo/json", "dojo/parser" ],
			function(dom, xhr, JSON, parser) {

				dataset = {
					searchClinetno : dom.byId("search_clinetno").value,
					searchAnykey : dom.byId("search_anykey").value,
					searchSavName : dom.byId("search_sav_name").value
					
				};

				xhr("saveSearch", {
					handleAs : "json",
					query : dataset,
					method : "get"
				}).then(function(datas) {

					alert("saved");
					searchSaveDialog.hide();
					refreshSearchShortCut();
				}, function(err) {
					// Handle the error condition
					alert("saved fail." + err);
				}, function(evt) {
					// Handle a progress event from the request if the
					// browser supports XHR2
					alert("saved fail." + evt);
				});

			});
}

function submitPrjSaveSearch() {

	// searchSaveDialog.isValid();

	require([ "dojo/dom", "dojo/request/xhr", "dojo/json", "dojo/parser" ],
			function(dom, xhr, JSON, parser) {

				dataset = {
					prjId : dom.byId("prj_id").value,
					prjSavName : dom.byId("prj_sav_name").value

				};

				xhr("savePrjshortcut", {
					handleAs : "json",
					query : dataset,
					method : "get"
				}).then(function(datas) {

					alert("saved");
					searchSaveDialog.hide();
					refreshPrjShortCut();
				}, function(err) {
					// Handle the error condition
					alert("saved fail." + err);
				}, function(evt) {
					// Handle a progress event from the request if the
					// browser supports XHR2
					alert("saved fail." + evt);
				});

			});
}

function refreshSearchShortCut() {
	require([ "dojo/dom", "dojo/request/xhr", "dojo/json", "dojo/parser","dojo/domReady!" ],
			function(dom, xhr, JSON, parser) {

				dataset = {
					userid : 1
				};
				
				console.log("requestdataset", dataset);
				xhr("getSearchShortcut", {
					handleAs : "json",
					query : dataset,
					method : "get",
					preventCache : true,
					headers : {
						'Content-Type' : 'application/json'
					}
				}).then(
						function(datas) {
							console.log("datas", datas);
							dom.byId("shortcut_ul").innerHTML = datas.data;

						}, function(err) {
							// Handle the error condition
							console.log(err);
							alert("request fail ." + err);
						}, function(evt) {
							// Handle a progress event from the request if the
							// browser supports XHR2
							console.log(evt);
							alert("request fail ." + evt);
						});
			});
}

function refreshPrjShortCut() {
	require([ "dojo/dom", "dojo/request/xhr", "dojo/json", "dojo/parser","dojo/domReady!" ],
			function(dom, xhr, JSON, parser) {

				dataset = {
					userid : 1
				};
				
				console.log("requestdataset", dataset);
				xhr("getPrjShortcut", {
					handleAs : "json",
					query : dataset,
					method : "get",
					preventCache : true,
					headers : {
						'Content-Type' : 'application/json'
					}
				}).then(
						function(datas) {
							console.log("datas", datas);
							dom.byId("prjshortcut_ul").innerHTML = datas.data;

						}, function(err) {
							// Handle the error condition
							console.log(err);
							alert("request fail ." + err);
						}, function(evt) {
							// Handle a progress event from the request if the
							// browser supports XHR2
							console.log(evt);
							alert("request fail ." + evt);
						});
			});
}

refreshSearchShortCut();
refreshPrjShortCut();