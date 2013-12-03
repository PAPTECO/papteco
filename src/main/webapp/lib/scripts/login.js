function doLogin() {

	console.log("doLogin");
	//if (!loginform.validate())
	//	return;

	require([ "dojo/dom", "dojo/request/xhr", "dojo/json", "dojo/parser",
			"dojo/query" ], function(dom, xhr, JSON, parser, query) {

		
		dataset = {
				createUserName : dom.byId("username").value,
				createPassword : dom.byId("password").value
		};
		console.log("requestdataset", dataset);

		xhr("userlogin", {
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
				alert("login succ, page will goto index.html");
				window.location = "index.html";
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

function doLogout() {

	console.log("doLogout");
	//if (!loginform.validate())
	//	return;

	require([ "dojo/dom", "dojo/request/xhr", "dojo/json", "dojo/parser",
			"dojo/query" ], function(dom, xhr, JSON, parser, query) {

		
		if(!confirm("Are you sure to logout?")){
			return;
		}
		
		xhr("userlogout", {
			handleAs : "json",
			method : "get",
			preventCache : true,
			headers : {
				'Content-Type' : 'application/json'
			}
		}).then(function(datas) {

			console.log("datas", datas);

			if (datas.type == "success") {
//				alert("login succ, page will goto index.html");
				window.location = "login.html";
			} else {
				alert(datas.message);
			}

		}, function(err) {
			// Handle the error condition
			console.log(err);
			alert("Logout fail ." + err);
		}, function(evt) {
			// Handle a progress event from the request if the
			// browser supports XHR2
			console.log(evt);
			alert("Logout fail ." + evt);
		});
	});
}