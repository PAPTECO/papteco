function hasRegister(widgetid) {

	var rst;
	require([ "dijit/registry" ], function(registry) {
		rst = registry.byId(widgetid);
	});
	return rst;
}

function getProjectId(){
	
	var prj_id;
	require([ "dojo/dom", "dojo/parser" ],
			function(dom,parser) {
		
		prj_id = dojo.byId("prj_id").value;
	});
	
	return prj_id;
}

