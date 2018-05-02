<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link rel="stylesheet" href="/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/font-awesome/font-awesome.min.css" type="text/css">
<link rel="stylesheet" href="/css/ui.jqgrid.css" type="text/css" />
<link rel="stylesheet" href="/css/redmond/jquery-ui-1.10.4.custom.css" type="text/css" />
<style>
.endbox {
	position: relative;
	text-align: right;
	left: -10px;
	height:22px;
	margin-top : -3px;
}
table th {
	text-align: center;
}

.required {
	background-color: #FFFFA2;
	text-transform: uppercase;
	border: 1px solid #000;
}

.common {
	/* font-family: '돋움'; */
	text-transform: uppercase;
	border: 1px solid #000;
}

</style>
<form id="application_form" name="application_form" >
<div class="container-fluid">
	<!-- Breadcrumbs-->
	<ol class="breadcrumb">
		<li class="breadcrumb-item"><a href="#">메뉴관리</a></li>
		<!-- <li class="breadcrumb-item active">목록</li> -->
	</ol>
	<table style="width: 100%; height: 85%">
		<tr>
			<td width="15%">
				<iframe id="leftFrame" src="admin.menuMgr.menuTree.do?menu_id=${menu_id}&up_link=${up_link}" scrolling=no name="left" frameborder=0 style="width: 250px; height: 100%;"></iframe>
			</td>
			<td width="85%">
				<iframe id="centerFrame" src="admin.menuMgr.manageMenu.do?menu_level=1" name="menu_body" scrolling=no frameborder=0 style="width: 99%; height: 100%;"></iframe>
			</td>
		</tr>
	</table>
</div>
</form>
<!-- /.container-fluid-->
<!-- Bootstrap core JavaScript-->
<script src="/js/jquery-1.11.0.min.js"></script>
<script src="/js/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- Core plugin JavaScript-->
<script src="/js/jquery-easing/jquery.easing.min.js"></script>
<!-- Custom scripts for all pages-->
<script src="/js/sb-admin.min.js"></script>
<!-- Custom scripts for this page-->

<script type="text/javascript" src="/js/commonGridUtil.js" charset='utf-8'></script>
<script type="text/javascript" src="/js/jquery.jqGrid.min.js" charset='utf-8'></script>
<script type="text/javascript" src="/js/jqgrid/i18n/grid.locale-en.js" charset='utf-8'></script>

<script type="text/javascript">

</script>
