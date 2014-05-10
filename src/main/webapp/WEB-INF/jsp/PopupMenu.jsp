				<ul id="menuTree_menu" data-dojo-type="dijit/Menu"
					data-dojo-props='style:"display: none;", targetNodeIds: ["doctree"], selector: ".dijitTreeNode"'>
					<li data-dojo-type="dijit/MenuItem"><script
							type="dojo/connect" data-dojo-event="onClick">
                            // get a hold of the dijit.TreeNode that was the source of this open event
                            var tn = dijit.byNode(this.getParent().currentTarget);

                            // now print the data store item that backs the tree node
                            console.debug("menu click for item: ", tn.item.name);

var r=confirm("Are you sure to delete "+tn.item.id +" ?");
if (r==false)
	return;

//alert("deleting"+tn.item.id);
							require([ "dojo/dom", "dojo/request/xhr", "dojo/json", "dojo/parser" ],
			function(dom, xhr, JSON, parser) {

				dataset = {
						projectId:getProjectId(),
						filename : encodeURIComponent(tn.item.id)
				};

				xhr("secure/deleteDocs", {
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
					console.log("deleted",datas);
					alert(tn.item.id + " has been deleted.");
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

							

                    </script><spring:message code="dashboard.content.project.opr.delete" /></li>
					<li data-dojo-type="dijit/MenuItem"><script
							type="dojo/connect" data-dojo-event="onClick">
                            // get a hold of the dijit.TreeNode that was the source of this open event
                            var tn = dijit.byNode(this.getParent().currentTarget);

                            // now print the data store item that backs the tree node
                            console.debug("menu click for item: ", tn.item.name);
console.debug("menu click for item: ", tn.item.docType);



var r=confirm("Are you sure to add revision on "+tn.item.id +" ?");
if (r==false)
	return;

uploadFileFormShow(tn.item.docType,tn.item.id,true);

                    </script><spring:message code="dashboard.content.project.opr.addRev" /></li>
					<li data-dojo-type="dijit/MenuItem"><script
							type="dojo/connect" data-dojo-event="onClick">
                            // get a hold of the dijit.TreeNode that was the source of this open event
                            var tn = dijit.byNode(this.getParent().currentTarget);

                            // now print the data store item that backs the tree node
                            console.debug("menu click for item: ", tn.item.name);
console.debug("menu click for item: ", tn.item.docType);



var r=confirm("Are you sure to edit on "+tn.item.id +" ?");
if (r==false)
	return;

editFile(tn.item.projectId,tn.item.docType,tn.item.id,tn.item.fileId); 

                    </script><spring:message code="dashboard.content.project.opr.edit" /></li>
					<li data-dojo-type="dijit/MenuItem"><script
							type="dojo/connect" data-dojo-event="onClick">
                            // get a hold of the dijit.TreeNode that was the source of this open event
                            var tn = dijit.byNode(this.getParent().currentTarget);

                            // now print the data store item that backs the tree node
                            console.debug("menu click for item: ", tn.item.name);
console.debug("menu click for item: ", tn.item.docType);



var r=confirm("Are you sure to release object on "+tn.item.id +" ?");
if (r==false)
	return;
releaseFile(tn.item.projectId,tn.item.docType,tn.item.id,tn.item.fileId);


                    </script><spring:message code="dashboard.content.project.opr.relObj" /></li>
				</ul>