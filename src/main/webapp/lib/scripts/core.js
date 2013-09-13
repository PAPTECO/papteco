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
