<!DOCTYPE html>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<head>
<!-- Meta -->
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<!-- End of Meta -->

<!-- Page title -->
<title>Wide Admin</title>
<!-- End of Page title -->

<!-- Libraries -->
<link type="text/css" href="css/layout.css" rel="stylesheet" />
<link type="text/css" rel="stylesheet"
	href="lib/dijit/themes/claro/claro.css">
<link type="text/css" rel="stylesheet" href="css/customs.css">


<script>
	dojoConfig = {
		async : true,
		parseOnLoad : true
	}
</script>


<script type="text/javascript" src="lib/dojo/dojo.js"></script>
<script>
	require([ "dojo/parser", "dijit/layout/BorderContainer",
			"dijit/layout/ContentPane" ]); // layout of content

	require([ "dojo/parser", "dijit/layout/AccordionContainer",
			"dijit/layout/AccordionPane" ]); // left side

	require([ "dojo/parser", "dijit/layout/TabContainer",
			"dijit/layout/ContentPane" ]);

	require([ "dojox/grid/LazyTreeGrid", "dijit/tree/ForestStoreModel",
			"dojo/data/ItemFileWriteStore" ]);

	/** for tab container**/
	require([ "dojo/parser", "dijit/layout/TabContainer",
			"dijit/layout/ContentPane" ]);

	require([ "dojo/parser", "dijit/Menu", "dijit/MenuItem",
			"dijit/MenuSeparator", "dijit/PopupMenuItem", "dijit/ColorPalette" ]);

</script>
<script type="text/javascript" src="lib/scripts/core.js"></script>
<script type="text/javascript" src="lib/scripts/login.js"></script>
<script type="text/javascript" src="lib/scripts/docopr.js"></script>
<script type="text/javascript" src="lib/scripts/loadcompleted.js"></script>
<!-- End of Libraries -->
</head>
<body class="claro">

	<%@ include file="header.jsp"%>

	<div style="width: 100%; height: 100%; margin: 0px; padding: 0px;">

		<div class="member_bg">
				<div class="member_center">
				&nbsp;<br>
				<table class="member_box">
					<tr >
					<td class="member_pic"><img src="membrs/obama.png" width="170"></img><td>
					<td class="member_info"><div class="member_name">
						Barack Obama
						</div>
						<div class="member_desc">
						Obama was born on August 4, 1961,[1] at Kapi olani Maternity & Gynecological Hospital (now Kapi olani Medical Center for Women and Children) in Honolulu, Hawaii,[2][3][4] and is the first President to have been born in Hawaii.[5] His mother, Stanley Ann Dunham, was born in Wichita, Kansas, and was of mostly English ancestry.[6] His father, Barack Obama, Sr., was a Luo from Nyang'oma Kogelo, Kenya.
						</div></td>
					</tr>
				</table>
				<div class="member_divider"></div>
				<table class="member_box">
					<tr >
					<td class="member_pic"><img src="membrs/obama.png" width="170"></img><td>
					<td class="member_info"><div class="member_name">
						Barack Obama
						</div>
						<div class="member_desc">
						Obama was born on August 4, 1961,[1] at Kapi olani Maternity & Gynecological Hospital (now Kapi olani Medical Center for Women and Children) in Honolulu, Hawaii,[2][3][4] and is the first President to have been born in Hawaii.[5] His mother, Stanley Ann Dunham, was born in Wichita, Kansas, and was of mostly English ancestry.[6] His father, Barack Obama, Sr., was a Luo from Nyang'oma Kogelo, Kenya.
						</div></td>
					</tr>
				</table>
				<div class="member_divider"></div>
				<table class="member_box">
					<tr >
					<td class="member_pic"><img src="membrs/obama.png" width="170"></img><td>
					<td class="member_info"><div class="member_name">
						Barack Obama
						</div>
						<div class="member_desc">
						Obama was born on August 4, 1961,[1] at Kapi olani Maternity & Gynecological Hospital (now Kapi olani Medical Center for Women and Children) in Honolulu, Hawaii,[2][3][4] and is the first President to have been born in Hawaii.[5] His mother, Stanley Ann Dunham, was born in Wichita, Kansas, and was of mostly English ancestry.[6] His father, Barack Obama, Sr., was a Luo from Nyang'oma Kogelo, Kenya.
						</div></td>
					</tr>
				</table>
				<div class="member_divider"></div>
				<table class="member_box">
					<tr >
					<td class="member_pic"><img src="membrs/obama.png" width="170"></img><td>
					<td class="member_info"><div class="member_name">
						Barack Obama
						</div>
						<div class="member_desc">
						Obama was born on August 4, 1961,[1] at Kapi olani Maternity & Gynecological Hospital (now Kapi olani Medical Center for Women and Children) in Honolulu, Hawaii,[2][3][4] and is the first President to have been born in Hawaii.[5] His mother, Stanley Ann Dunham, was born in Wichita, Kansas, and was of mostly English ancestry.[6] His father, Barack Obama, Sr., was a Luo from Nyang'oma Kogelo, Kenya.
						</div></td>
					</tr>
				</table>
				<div class="member_divider"></div>
				<table class="member_box">
					<tr >
					<td class="member_pic"><img src="membrs/obama.png" width="170"></img><td>
					<td class="member_info"><div class="member_name">
						Barack Obama
						</div>
						<div class="member_desc">
						Obama was born on August 4, 1961,[1] at Kapi olani Maternity & Gynecological Hospital (now Kapi olani Medical Center for Women and Children) in Honolulu, Hawaii,[2][3][4] and is the first President to have been born in Hawaii.[5] His mother, Stanley Ann Dunham, was born in Wichita, Kansas, and was of mostly English ancestry.[6] His father, Barack Obama, Sr., was a Luo from Nyang'oma Kogelo, Kenya.
						</div></td>
					</tr>
				</table>
				</div>
		</div>
		<div class="buttom">

			<div>PAPTECO Engineering & Sales (GZ) Co., Ltd.</div>
			<div>China: Room 706, Building #6, HighSun Xing Yue, N0. 383
				Panyu avenue north, Panyu, Guangzhou, Guangdong province, People¡¯s
				republic of China P.O£º511400</div>
			<div>TEL.: 86-20-3104 0418, FAX.: 86-20-3104 0298, E-mail:
				garylai@papteco.com, fabian@papteco.com</div>
			<div>Copyright 2013-2014 by Papteco. All Rights Reserved.</div>
		</div>
		<!-- end of bottom-->

	</div>
</body>
</html>


