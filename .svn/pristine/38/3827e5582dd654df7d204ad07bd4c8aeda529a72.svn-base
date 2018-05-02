<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<head>
<title>ITMS</title>
<!-- Bootstrap core CSS-->
<link href="/css/bootstrap.min.css" rel="stylesheet">
<link href="/css/font-awesome/font-awesome.min.css" rel="stylesheet" type="text/css">
<style type="text/css">
ul.mylist, ol.mylist {
    list-style: none;
    margin: 0px;
    padding: 0px;
    max-width: 250px;
    width: 100%;
}

ul.mylist li, ol.mylist li {
	list-style:none;
    padding: 0px 0px 0px 5px;
    margin-bottom: 5px;
    border-bottom: 1px solid #efefef;
    font-size: 13px;
}

ul.mylist li:last-child,
ol.mylist li:last-child {
    border-bottom: 0px;
}

ul.mylist li:before,
ol.mylist li:before {
    content: ">";
    display: inline-block;
    vertical-align: middle;
    padding: 0px 5px 6px 0px;
}

</style>
</head>
<body>
	<div style="height:800px;">
		<ul class="mylist" style="height:700px; overflow: auto;">
			<li>
				<a href="<c:url value="admin.menuMgr.manageMenu.do?p_parent_menu_id=&menu_level=1&up_link=${up_link}"/>" target="menu_body"> 
					<span class="nav-link-text">최상위메뉴</span>
				</a>
				<ul style="list-style: none; padding-left: 10px;">
			
					<c:forEach var="item" items="${treeMenuList}" varStatus="status">
						<c:choose>
							<c:when test="${item.lev == 0}">
								<li>
									<a href="<c:url value="admin.menuMgr.manageMenu.do?p_parent_menu_id=${item.menu_id}&menu_level=2&up_link=${up_link}"/>" target="menu_body"> 
										<span class="nav-link-text"> 
											${item.menu_name}
										</span>
									</a>
									<ul style="list-style: none; padding-left: 10px;">
									<c:forEach var="item1" items="${treeMenuList}" varStatus="status">
										<c:if test="${item1.lev == 1 and item1.parent_menu_id == item.menu_id}">
											<li class="test">
												<%-- <c:choose>
													<c:when test="${item1.leaf == 0}">
														<a href="<c:url value="admin.menuMgr.manageMenu.do?p_parent_menu_id=${item1.menu_id}&menu_level=3&up_link=${up_link}"/>" target="menu_body">${item1.menu_name} </a>
													</c:when>
													<c:otherwise>
														${item1.menu_name}
													</c:otherwise>
												</c:choose> --%>
												<a href="<c:url value="admin.menuMgr.manageMenu.do?p_parent_menu_id=${item1.menu_id}&menu_level=3&up_link=${up_link}"/>" target="menu_body">${item1.menu_name} </a>
												<ul style="list-style: none; padding-left: 10px;">
													<c:forEach var="item2" items="${treeMenuList}" varStatus="status">
														<c:if test="${item2.lev == 2 and item2.parent_menu_id == item1.menu_id}">
															<li class="test"><a href="#">${item2.menu_name}</a></li>
														</c:if>
													</c:forEach>			
												</ul>
											</li>
										</c:if>
									</c:forEach>
									</ul>
								</li>
							</c:when>
						</c:choose>
					</c:forEach>
				</ul>
			</li>
		</ul>
	</div>	
</body>	
<!-- Bootstrap core JavaScript-->
<script src="/js/jquery-1.11.0.min.js"></script>
<!-- Page level plugin JavaScript-->
<script type="text/javascript">
</script>
</html>