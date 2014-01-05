function hasRegister(widgetid) {

	var rst;
	require([ "dijit/registry" ], function(registry) {
		rst = registry.byId(widgetid);
	});
	return rst;
}

function getProjectId() {

	var prj_id;
	require([ "dojo/dom", "dojo/parser" ], function(dom, parser) {

		prj_id = dojo.byId("prj_id").value;
	});

	return prj_id;
}

function fadeIt(tabname) {
	require([ "dojo/_base/fx", "dojo/dom-style" ], function(fx, style) {
		// Function linked to the button to trigger the fade.
		style.set(tabname, "opacity", "1");
		var fadeArgs = {
			node : tabname
		};
		fx.fadeOut(fadeArgs).play();
		style.set(tabname, "display", "none");
	});
}
function fadeItShow(tabname) {
	require([ "dojo/_base/fx", "dojo/dom-style" ], function(fx, style) {
		// Function linked to the button to trigger the fade.
		style.set(tabname, "opacity", "0");
		style.set(tabname, "display", "block");
		var fadeArgs = {
			node : tabname
		};
		fx.fadeIn(fadeArgs).play();
	});

}

function fadeIt3s(tabname) {
	require([ "dojo/_base/fx", "dojo/dom-style" ], function(fx, style) {
		// Function linked to the button to trigger the fade.
		style.set(tabname, "opacity", "1");
		var fadeArgs = {
			node : tabname,
			duration: 3000,
			onEnd: function(){
				style.set(tabname, "display", "none");
		    }
		};
		fx.fadeOut(fadeArgs).play();
		
	});
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

function currentYearMonthDay() {

	var dateobj = new Date();
	var m = dateobj.getMonth() + 1;
	var d = dateobj.getDate();
	var rst = ""
	if (m < 10)
		rst = dateobj.getFullYear() + "0" + m;
	else
		rst = dateobj.getFullYear() + "" + m;
	if (d < 10)
		rst = rst + "0" + d;
	else
		rst = rst + "" + d;
	console.log("m",m,"d",d);
	return rst;
}

function gotoPage(address) {
	require([ "dojo/dom", "dojo/request/xhr", "dojo/json", "dojo/parser" ],
			function(dom, xhr, JSON, parser) {

				xhr(address, {
					handleAs : "json",
					method : "get",
					preventCache : true
				}).then(function(datas) {

					console.log("debug...", datas);
					if (datas["type"] == "fail") {
						alert(datas["message"]);
						return;
					}
					
				}, function(err) {
					// Handle the error condition
					console.log(err);
				}, function(evt) {
					// Handle a progress event from the request if the
					// browser supports XHR2
					console.log(evt);
				});
				window.location = address;
			});
}

