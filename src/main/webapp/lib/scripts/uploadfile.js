require("dojox/form/Uploader");
require("dojox/form/uploader/plugins/IFrame");

function editFile(projectId,foldertype, editfilename,fileid) {
	
	require([ "dojo/dom", "dijit/registry", "dojo/request/xhr", "dojo/json" ],
			function(dom, registry, xhr, json) {

				dataset = {
						projectId : projectId,
						docType : foldertype,
						filename : encodeURIComponent(editfilename),
						fileid : fileid
				};

				xhr("secure/editFile", {
					handleAs : "json",
					query : dataset,
					method : "get",
					preventCache : true,
					headers : {
						'Content-Type' : 'application/json'
					}
				}).then(function(datas) {

					console.log("edit debug...",datas);
					if(datas["type"]=="fail"){
						alert(datas["message"]);
						return;
					}else if(datas.message){
						alert(datas.message);
					}
					console.log("edited",datas);
					refreshProjectBroad(getProjectId());
					
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

function releaseFile(projectId,foldertype, editfilename,fileid) {
	
	require([ "dojo/dom", "dijit/registry", "dojo/request/xhr", "dojo/json" ],
			function(dom, registry, xhr, json) {

				waitingDialog.show();
				dojo.byId("waitingDialogtext").innerHTML="Please wait ...";
				dataset = {
						projectId : projectId,
						docType : foldertype,
						filename : encodeURIComponent(editfilename),
						fileid : fileid
				};

				xhr("secure/releaseFile", { 
					handleAs : "json",
					query : dataset,
					method : "get",
					preventCache : true,
					//sync : false,
					headers : {
						'Content-Type' : 'application/json'
					}
				}).then(function(datas) {

					console.log("datas", datas);
					dojo.byId("waitingDialogtext").innerHTML=datas["message"];
					if(datas["type"] == "success"){
						waitingDialog.hide();
						refreshProjectBroad(getProjectId());
					}
					
					
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

function uploadFileFormShow(foldertype, templatename, addrev) {

	uploadFileFormDialog.show();

	tagid = "upload_doctype_id";
	
	require([ "dojo/dom", "dojo/parser", "dojo/dom-class", "dojo/query",
			"dojo/on", "dojo/request/iframe", "dojo/json", "dijit/registry" ],
			function(dom, parser, domClass, query, on, iframe, json, registry) {
				query("input,textarea", "uploadFileForm").forEach(
						function(node) {

							node.value = "";

						});

				dom.byId("insert_content").innerHTML = "";
				
				dom.byId("uploadedCopyForm").value = templatename;
				
				if(registry.byId(tagid)){
					registry.byId(tagid)._lastValueReported ="";
				}
				
				if(addrev){
					dom.byId("isRev").value="isRev";
				}
			});
	
	
	if (!hasRegister(tagid)) {
		require([ "dojo/dom", "dojo/data/ItemFileReadStore",
				"dijit/form/ComboBox",

				"dojo/domReady!" ], function(dom, ItemFileReadStore, ComboBox) {

			var stateStore = new ItemFileReadStore({
				url : "getPredefineDocumentTypes"
			});

			console.log(stateStore);

			comboBox = new ComboBox({
				id : "upload_doctype_id",
				name : "upload_doctype",
				store : stateStore,
				searchAttr : "id",
				labelAttr : 'name',
				value:foldertype,
				onChange : setVal1
			}, "upload_doctype");

			if (foldertype) {
				comboBox.readOnly = "true";
				comboBox.disable = "disabled";
				console.log("set combox read only",comboBox);
			}
		});

	}

	require([ "dojo/dom", "dijit/registry", "dojo/query" ], function(dom,
			registry, query) {
		// is modify
		if (foldertype) {
			
			if(registry.byId(tagid)){
				registry.byId(tagid).value = foldertype;
				// console.log("dijit",registry.byId(tagid));
				registry.byId(tagid).readOnly = "true";
				registry.byId(tagid).disable = "disabled";
				dom.byId(tagid).value = foldertype;
			}
			
			setVal2(foldertype,addrev?templatename:"");
			
			console.log("uploadformobj",dom.byId("uploadedCopyForm"));
			console.log("templatename",templatename);

			// show copy from file, hide uploader
			query("#uploadedCopyForm").style("display", "inline");
			query("#uploader").style("display", "none");
		} else {
			query("#uploadedCopyForm").style("display", "none");
			query("#uploader").style("display", "inline");

			dom.byId("uploadedCopyForm").value = "";
			registry.byId(tagid).readOnly = "";
			registry.byId(tagid).disable = "";
		}

	});

}

function setVal1(value) {
	
	require([ "dojo/dom", "dijit/registry", "dojo/query" ], function(dom,
			registry, query) {
		if(dom.byId("isRev").value=="isRev"){
			setVal2(value,dom.byId("isRev")?dom.byId("uploadedCopyForm").value:"");
		}else{
			setVal2(value,"");
		}
		
		
	});
	
	
	
}

function setVal2(value,previouslyFileName) {

	require([ "dojo/dom", "dijit/registry", "dojo/request/xhr", "dojo/json" ],
			function(dom, registry, xhr, json) {

				dataset = {
					docType : value,
					prjId : getProjectId(),
					revFileName : encodeURIComponent(previouslyFileName)
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
					
					//p-form
					console.log("Doctype:",value);
					if(value == "P"){
						if(previouslyFileName){
							
						}else{
							//suggest no.
							requestPformRef();	
						}
					}
					

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

function requestPformRef(date){
	
	
	
	require([ "dojo/dom", "dijit/registry", "dojo/request/xhr", "dojo/json" ],
			function(dom, registry, xhr, json) {

		console.log("projectid,",getProjectId());
		console.log("date,",dom.byId("dateWith4digs").value);
		
				dataset = {
					prjId : getProjectId(),
					date : dom.byId("dateWith4digs").value
				};

				xhr("getPformRef", { 
					handleAs : "json",
					query : dataset,
					method : "get",
					preventCache : true,
					headers : {
						'Content-Type' : 'application/json'
					}
				}).then(function(datas) {
					
					console.log("datas", datas);
					
					if(datas.type="success"){
						dom.byId("ref").value = datas['no'];
					}

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

function placeSubmit(tagid) {

	console.log("begin placeSubmit");

	require([ "dojo/dom", "dijit/registry", "dojo/parser",
			"dijit/form/DateTextBox" ], function(dom, registry, parser,
			DateTextBox) {

		dom.byId(tagid).innerHTML="<div class='dijitDialogPaneActionBar'><input label='Upload' data-dojo-type='dijit/form/Button' onClick='validateUploadForm();' type='button' /><button data-dojo-type='dijit/form/Button' type='button' onClick='uploadFileFormDialog.hide()'>Cancel</button></div>";

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
				if (dojo.byId("uploader").value == ""
						&& dojo.byId("uploadedCopyForm").value == "") {
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
					sethiddenValue(query, dom, "upload_doctype_tmp");
					
					iframe("secure/submitUploadFile", {
						form : "uploadFileForm",
						handleAs : "html"
					})
							.then(
									function(xmldoc) {
										console.log("json",xmldoc);
										data = xmldoc.body.textContent;
										console.log(data);
										r= JSON.parse(data);
										console.log(r);
										console.log("edit debug...",r);
										if(r.type=="fail"){
											alert(r.message);
											return;
										}else if(r.type=="success"){
											if(r.message){
												alert(r.message);
											}
											else{
												alert(decodeURIComponent(r.filename)+" has been uploaded.");
											}
											dom.byId("uploadFileForm").reset();
											uploadFileFormDialog.hide();
											refreshProjectBroad(getProjectId());
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