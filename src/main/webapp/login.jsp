<!DOCTYPE html>
<html>
<head>
<!-- Meta -->
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<!-- End of Meta -->

<!-- Page title -->
<title>Papteco Information Management System</title>
<!-- End of Page title -->

<!-- Libraries -->
<link type="text/css" href="css/login.css" rel="stylesheet" />
<link type="text/css" rel="stylesheet"
	href="lib/dijit/themes/claro/claro.css">
<link rel="stylesheet" href="lib/dojox/form/resources/FileInput.css" />
<script type="text/javascript" src="lib/dojo/dojo.js"></script>
<script type="text/javascript" src="lib/scripts/login.js"></script>
<script>
	// for validation on input box
	require([ "dojo/parser", "dijit/form/ValidationTextBox" ]);
	require([ "dojo/parser", "dijit/Dialog", "dijit/form/Button",
			"dijit/form/TextBox", "dijit/form/DateTextBox",
			"dijit/form/TimeTextBox" ]);
</script>
<!-- End of Libraries -->
</head>
<body>
	<div id="container">
		<div class="logo">
			<a href="#"><img src="assets/logo.png" alt="" /></a>
		</div>
		<div id="box">
			<div data-dojo-type="dijit/form/Form" id="loginform"
				data-dojo-id="loginform" encType="multipart/form-data" action=""
				method="">
				<p class="main">
					<label>Username: </label> <input id="username" name="username"
						value="" data-dojo-type="dijit/form/ValidationTextBox"
						data-dojo-props="regExp:'[\\w]+', invalidMessage:'Invalid Non-Space Text.'" />
					<label>Password: </label> <input id="password" type="password"
						name="password" value=""
						data-dojo-type="dijit/form/ValidationTextBox"
						data-dojo-props="regExp:'[\\w]+', invalidMessage:'Invalid Non-Space Text.'">
				</p>

				<p class="space">
					<input type="submit" value="Login" class="login" href="#"
						onClick="doLogin()" />
				</p>
			</div>
		</div>
	</div>

</body>
</html>