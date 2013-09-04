require("dojox/form/Uploader");
require("dojox/form/uploader/plugins/IFrame");

function uploadFileFormShow() {

	uploadFileFormDialog.show();

	tagid = "upload_doctype";

	if (hasRegister(tagid))
		return;

	require([ "dojo/data/ItemFileReadStore", "dijit/form/ComboBox",
			"dijit/registry", "dojo/domReady!" ], function(ItemFileReadStore, ComboBox,
			registry) {

		var stateStore = new ItemFileReadStore({
			url : "http://localhost:8080/Papteco/getPredefineDocumentTypes"
		});

		var comboBox = new ComboBox({
			id : "id",
			name : "name",
			store : stateStore,
			searchAttr : "name",
			onChange : setVal1
		}, "upload_doctype");

		function setVal1(value) {
			alert("Selected " + value);
		}
	});

}

function submitUploadFile() {

	// if(!prjcreateform.validate())
	// return;

	require([ "dojo/dom", "dojo/request/xhr", "dojo/json", "dojo/parser" ],
			function(dom, xhr, JSON, parser) {

				dataset = {
					upload_f1 : dom.byId("upload_f1").value,
					upload_f2 : dom.byId("upload_f2").value,
					upload_f3 : dom.byId("upload_f3").value,
					upload_f4 : dom.byId("upload_f4").value,
					upload_f5 : dom.byId("upload_f5").value,
					upload_f6 : dom.byId("upload_f6").value,
					upload_f7 : dom.byId("upload_f7").value,
					upload_doctype : dom.byId("upload_doctype").value

				};
				console.log("upload dataset", dataset);
				xhr("submitUploadFile", {
					handleAs : "json",
					data : JSON.stringify(dataset),
					method : "post",
					headers : {
						'Content-Type' : 'application/json'
					}
				}).then(function(datas) {

					console.log("datas", datas);

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