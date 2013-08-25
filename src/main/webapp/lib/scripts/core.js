function hasRegister(widgetid) {

	var rst;
	require([ "dijit/registry" ], function(registry) {
		rst = registry.byId(widgetid);
	});
	return rst;
}