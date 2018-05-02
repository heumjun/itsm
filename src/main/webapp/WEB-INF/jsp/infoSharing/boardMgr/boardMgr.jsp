<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<link rel="stylesheet" href="/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/font-awesome/font-awesome.min.css" type="text/css">
<link rel="stylesheet" href="/css/datatables/dataTables.bootstrap4.css">
<link rel="stylesheet" href="/css/sb-admin.css" >
<style>
.endbox {
	position: relative;
	text-align: right;
	left: -10px;
	height:22px;
}
</style>
<div class="container-fluid">
	<!-- Breadcrumbs-->
	<ol class="breadcrumb">
		<li class="breadcrumb-item"><a href="#">사용자관리</a></li>
		<li class="breadcrumb-item active">목록</li>
	</ol>
	<table class="table table-bordered" width="100%" cellspacing="0">
		<col width="120">
		<col width="150">
		<col width="120">
		<col width="150">
		<col width="120">
		<col width="150">
		<col width="120">
		<col width="150">
		<col width="*"/>
		<tbody>
			<tr>
				<th style="border-right: 0px;">아이디</th>
				<td><input type="text" class="common" id="p_id" name="p_id" alt="아이디"  style="width:120px;" value="" /></td>
				
				<th>이름</th>
				<td><input type="text" class="common" id="p_name" name="p_name" alt="이름"  style="width:120px;" value="" /></td>
				
				<th>회사</th>
				<td><input type="text" class="common" id="p_company" name="p_company" alt="회사"  style="width:120px;" value="" /></td>
				
				<th>전화번호</th>
				<td><input type="text" class="common" id="p_tel" name="p_tel" alt="전화번호"  style="width:120px;" value="" /></td>
				
				<td>
					<div class="button endbox">
						<button type="button" id="btnExport" class="btn btn-success btn-sm" >EXPORT</button>
						<button type="button" id="btnSave" class="btn btn-primary btn-sm" >SAVE</button>
						<button type="button" id="btnSearch" class="btn btn-primary btn-sm" >SEARCH</button>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
	
	<div class="card mb-3" style="margin-top: -5px;">
		<!-- <div class="card-header">
			<i class="fa fa-table"></i> Data Table Example
		</div> -->
		<div class="card-body">
			<div class="table-responsive">
				<table id="example" class="table table-bordered" width="100%" cellspacing="0">
					<thead>
						<tr>
							<td>1</td>
							<td>2</td>
							<td>3</td>
							<td>4</td>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
</div>
<!-- /.container-fluid-->
<script type="text/javascript" src="/js/jquery/jquery.min.js"></script>
<script type="text/javascript" src="/js/bootstrap/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="/js/jquery-easing/jquery.easing.min.js"></script>
<script type="text/javascript" src="/js/datatables/jquery.dataTables.js"></script>
<script type="text/javascript" src="/js/datatables/dataTables.bootstrap4.js"></script>
<script type="text/javascript" src="/js/sb-admin.min.js"></script>
<script type="text/javascript" src="/js/sb-admin-datatables.min.js"></script>
<script type="text/javascript" src="/js/commonGridUtil.js" charset='utf-8'></script>
<script type="text/javascript" src="/js/commonTextUtil.js" charset='utf-8'></script>

<script type="text/javascript">

//Korean
var lang_kor = {
    "decimal" : "",
    "emptyTable" : "데이터가 없습니다.",
    "info" : "_START_ - _END_ (총 _TOTAL_)",
    "infoEmpty" : "0명",
    "infoFiltered" : "(전체 _MAX_ 명 중 검색결과)",
    "infoPostFix" : "",
    "thousands" : ",",
    "lengthMenu" : "_MENU_ 개씩 보기",
    "loadingRecords" : "로딩중...",
    "processing" : "처리중...",
    "search" : "검색 : ",
    "zeroRecords" : "검색된 데이터가 없습니다.",
    "paginate" : {
        "first" : "첫 페이지",
        "last" : "마지막 페이지",
        "next" : "다음",
        "previous" : "이전"
    },
    "aria" : {
        "sortAscending" : " :  오름차순 정렬",
        "sortDescending" : " :  내림차순 정렬"
    }
};

$(document).ready(function(){
	$('#example').DataTable( {
		initComplete : function() {
	        var input = $('.dataTables_filter input').unbind(),
	            self = this.api(),
	            $searchButton = $('<button>')
	                       .text('search')
	                       .click(function() {
	                          self.search(input.val()).draw();
	                       }),
	            $clearButton = $('<button>')
	                       .text('clear')
	                       .click(function() {
	                          input.val('');
	                          $searchButton.click(); 
	                       }) 
	        $('.dataTables_filter').append($searchButton, $clearButton);
	    },
		"sPaginationType" : "full_numbers",
		"iDisplayLength": 10,
        bPaginate: true,
        bLengthChange: true,
        lengthMenu : [ [ 10, 50, 100 ], [ 10, 50, 100 ] ],
        bAutoWidth: true,
        processing: true,
        ordering: true,
        serverSide: true,
        searching: true,
        ajax : {
            "url":"infoSharing.boardMgr.boardMgrList.do",
            "type":"POST",
            "dataType": "json",
            "data": function (d) {
                d.company_name = "NR";
            }
        },
        columns : [
            {"data": "login_id"},
            {"data": "name"},
            {"data": "e_mail"},
            {"data": "company_name"}
        ],
        language : lang_kor //or lang_eng
        
    } );
	
});

</script>
