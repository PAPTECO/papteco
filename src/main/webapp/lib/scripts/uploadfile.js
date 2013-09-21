require("dojox/form/Uploader");
require("dojox/form/uploader/plugins/IFrame");

function uploadFileFormShow() {

	uploadFileFormDialog.show();

	tagid = "upload_doctype";

	require([ "dojo/dom", "dojo/parser", "dojo/dom-class", "dojo/query",
			"dojo/on", "dojo/request/iframe", "dojo/json", "dijit/registry" ],
			function(dom, parser, domClass, query, on, iframe, json, registry) {
				query("input,textarea", "uploadFileForm").forEach(
						function(node) {

							node.value = "";
							
						});
				
				
				dom.byId("insert_content").innerHTML = "";
			});

	if (hasRegister(tagid))
		return;

	require(
			[ "dojo/dom", "dojo/data/ItemFileReadStore", "dijit/form/ComboBox",
					"dijit/registry", "dojo/request/xhr", "dojo/json",
					"dojo/domReady!" ], function(dom, ItemFileReadStore,
					ComboBox, registry, xhr, json) {

				var stateStore = new ItemFileReadStore({
					url : "getPredefineDocumentTypes"
				});

				console.log(stateStore);

				var comboBox = new ComboBox({
					id : "upload_doctype_id",
					name : "upload_doctype",
					store : stateStore,
					searchAttr : "id",
					labelAttr : 'name',
					onChange : setVal1
				}, "upload_doctype");

				function setVal1(value) {

					dataset = {
						docType : value,
						prjId : getProjectId()
					};

					xhr("getNumberingFormat", {
						handleAs : "json",
						query : dataset,
						method : "get",
						preventCache : true,
						headers : {
							'Content-Type' : 'application/json'
						}
					}).then(function(datas) {

						console.log("datas", datas);
						dom.byId("insert_content").innerHTML = datas.data;
						placeAtDate("dateCreated");
						placeAtDate("certDate");
						placeAtDate("paymentDueDate");
						placeAtDate("orderedDate");
						placeAtDate("completedDate");

					}, function(err) {
						// Handle the error condition
						console.log(err);
					}, function(evt) {
						// Handle a progress event from the request if the
						// browser supports XHR2
						console.log(evt);
					});

				}
			});

}

function placeAtDate(tagid) {

	console.log("begin placeAtDate");

	require([ "dojo/dom", "dijit/registry", "dojo/parser",
			"dijit/form/DateTextBox" ], function(dom, registry, parser,
			DateTextBox) {

		if (hasRegister(tagid)) {
			rst = registry.byId(tagid);
			rst.destroy();
		}

		rst = new DateTextBox({
			id : tagid,
			displayedValue : currentYearMonthDay(),
			name : tagid,
			constraints : {
				datePattern : 'yyyyMMdd'
			}

		}, tagid);

	});
}

function validateUploadForm() {

	require(
			[ "dojo/dom", "dojo/parser", "dojo/dom-class", "dojo/query",
					"dojo/on", "dojo/request/iframe", "dojo/json",
					"dijit/registry" ],
			function(dom, parser, domClass, query, on, iframe, json, registry) {
				query("input,textarea", "uploadFileForm").forEach(
						function(node) {

							// trim
							// node.value = node.value.replace(/^\s*/,
							// "").replace(/\s*$/);

							on.emit(node, "keyup", {
								bubbles : true,
								cancelable : true
							});
						});
				if (dojo.byId("uploader").value == "") {
					turnOnError(domClass, dojo.byId("uploader"));
				} else {
					turnOffError(domClass, dojo.byId("uploader"));
				}

				if (dojo.byId("upload_doctype_id").value == "") {
					turnOnError(domClass, dojo.byId("upload_doctype_id"));
				} else {
					turnOffError(domClass, dojo.byId("upload_doctype_id"));
				}

				var hasErr = false;
				query(".errorcolor", "uploadFileForm").forEach(function(node) {

					hasErr = true;
				});

				if (hasErr) {
					alert("Please input all the mandatory field highline in red.");
				} else {

					sethiddenValue(query, dom, "dateCreated");
					sethiddenValue(query, dom, "certDate");
					sethiddenValue(query, dom, "paymentDueDate");
					sethiddenValue(query, dom, "orderedDate");
					sethiddenValue(query, dom, "completedDate");

					iframe("submitUploadFile", {
						form : "uploadFileForm",
						handleAs : "html"
					}).then(function(xmldoc) {
						console.log(xmldoc);
						data = xmldoc.body.textContent;
						console.log(data);
						if(data){
							alert(data + " has been uploaded.");
							dom.byId("uploadFileForm").reset();
							uploadFileFormDialog.hide();
							refreshProjectBroad(getProjectId());
						}else{
							alert("File already exists. Please change file name.");
						}
					}, function(err) {
						alert("Error occurs " + err);

					});

				}
			});

}

function sethiddenValue(query, dom, tagid) {

	query("input[name='" + tagid + "']", "uploadFileForm").forEach(
			function(node) {

				node.value = dom.byId(tagid).value;
			});

}

function turnOnError(domClass, obj) {
	domClass.remove(obj.id + '_header', "normalcolor");
	domClass.add(obj.id + '_header', "errorcolor");
}

function turnOffError(domClass, obj) {
	domClass.remove(obj.id + '_header', "errorcolor");
	domClass.add(obj.id + '_header', "normalcolor");
}

function chkvaldpty(obj) {

	require([ "dojo/dom", "dojo/parser", "dojo/dom-class", "dojo/query" ],
			function(dom, parser, domClass, query) {

				if (obj.value == "") {
					turnOnError(domClass, obj);
				} else {
					turnOffError(domClass, obj);
				}
			});
}

function chkvaldate4(obj) {

	require([ "dojo/dom", "dojo/parser", "dojo/dom-class", "dojo/query" ],
			function(dom, parser, domClass, query) {

				if (!isValidDateyymm(obj.value)) {
					turnOnError(domClass, obj);
				} else {
					turnOffError(domClass, obj);
				}
			});
}

function isValidDateyymm(date) {
	var matches = /^(\d{2})(\d{2})$/.exec(date);
	if (matches == null)
		return false;

	var m = matches[2] - 1;
	var y = 20 + matches[1];
	var composedDate = new Date(y, m);
	return composedDate.getMonth() == m && composedDate.getFullYear() == y;
}

function isValidDateyymmdd(date) {
	var matches = /^(\d{2})(\d{2})(\d{2})$/.exec(date);
	if (matches == null)
		return false;
	var d = matches[3];
	var m = matches[2] - 1;
	var y = 20 + matches[1];
	var composedDate = new Date(y, m, d);
	return composedDate.getDate() == d && composedDate.getMonth() == m
			&& composedDate.getFullYear() == y;
}

function isNumber(n) {
	return !isNaN(parseFloat(n)) && isFinite(n);
}

function chkvaldate6(obj) {

	require([ "dojo/dom", "dojo/parser", "dojo/dom-class", "dojo/query" ],
			function(dom, parser, domClass, query) {

				if (!isValidDateyymmdd(obj.value)) {
					turnOnError(domClass, obj);
				} else {
					turnOffError(domClass, obj);
				}
			});
}

function chkvaldnum(obj) {

	require([ "dojo/dom", "dojo/parser", "dojo/dom-class", "dojo/query" ],
			function(dom, parser, domClass, query) {

				if (!isNumber(obj.value)) {

					domClass.remove(obj.id + '_header', "normalcolor");
					domClass.add(obj.id + '_header', "errorcolor");
				} else {
					domClass.remove(obj.id + '_header', "errorcolor");
					domClass.add(obj.id + '_header', "normalcolor");
				}
			});
}

function chkvaldrev(obj) {

	require([ "dojo/dom", "dojo/parser", "dojo/dom-class", "dojo/query" ],
			function(dom, parser, domClass, query) {

				if (!isNumber(obj.value)) {

					domClass.remove(obj.id + '_header', "normalcolor");
					domClass.add(obj.id + '_header', "errorcolor");
				} else {
					domClass.remove(obj.id + '_header', "errorcolor");
					domClass.add(obj.id + '_header', "normalcolor");
				}
			});
}