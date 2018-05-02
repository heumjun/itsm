<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
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
	/* text-transform: uppercase; */
	border: 1px solid #000;
	font-size: 13px; 
	font-weight: 100;
}

</style>

<form id="application_form" name="application_form" >
<input type="hidden" id="loginId" name="loginId" value="${loginId }" />
<input type="hidden" id="approvalAdmin" name="approvalAdmin" value="${approvalAdmin }" />
<div class="container-fluid">
	<!-- Breadcrumbs-->
	<div class="breadcrumb">
		<li class="breadcrumb-item"><a href="#">근태신청 및 관리</a></li>
	</div>
	
	<table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
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
				<th style="font-size: 13px; font-weight: 100;">아이디</th>
				<td><input type="text" class="common" id="p_login_id" name="p_login_id" alt="아이디"  style="width:120px;" value="" /></td>
				
				<th style="font-size: 13px; font-weight: 100;">사용자명</th>
				<td><input type="text" class="common" id="p_name" name="p_name" alt="이름"  style="width:120px;" value="" /></td>
				
				<th style="font-size: 13px; font-weight: 100;">휴가종류</th>
				<td>
					<select id="p_vac_type" name="p_vac_type"  style="width:120px; font-size: 13px; font-weight: 100;">
					</select>
				</td>
				
				<th style="font-size: 13px; font-weight: 100;">승인상태</th>
				<td>
					<select id="p_status" name="p_status"  style="width:120px; font-size: 13px; font-weight: 100;">
					</select>
				</td>
				
				<td>
					<div class="button endbox">
						<button type="button" id="btnSearch" class="btn btn-primary btn-sm" >조회</button>
						<button type="button" id="btnRequest" class="btn btn-primary btn-sm" >신청</button>
						<button type="button" id="btnCancel" class="btn btn-primary btn-sm" >취소</button>
						<button type="button" id="btnApproval" class="btn btn-primary btn-sm" >승인</button>
						<button type="button" id="btnRestore" class="btn btn-primary btn-sm" >반려</button>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
	<div class="table-responsive">
		<table id="jqGridList"></table>
		<div id="btnjqGridList"></div>
	</div>
</div>
</form>
<!-- /.container-fluid-->
<!-- Bootstrap core JavaScript-->
<script src="/js/jquery-1.11.0.min.js"></script>
<script src="/js/bootstrap/js/bootstrap.bundle.min.js"></script>
<!-- Core plugin JavaScript-->
<!-- <script src="/js/jquery-easing/jquery.easing.min.js"></script> -->
<!-- Custom scripts for all pages-->
<script src="/js/sb-admin.min.js"></script>
<!-- Custom scripts for this page-->

<script type="text/javascript" src="/js/commonGridUtil.js" charset='utf-8'></script>
<script type="text/javascript" src="/js/commonTextUtil.js" charset='utf-8'></script>
<script type="text/javascript" src="/js/jquery.jqGrid.min.js" charset='utf-8'></script>
<script type="text/javascript" src="/js/jqgrid/i18n/grid.locale-en.js" charset='utf-8'></script>
<script src="/js/jqueryAjax.js"></script>

<script type="text/javascript">
var idRow;
var idCol;
var kRow;
var kCol;
var resultData = [];

var jqGridObj = $('#jqGridList');

$(function() {
	
	// 휴가종류 SelectBox
	getAjaxHtml($("#p_vac_type"), "dlm.geuntaeMgr.vacTypeSelectBoxDataList.do?sb_type=sel&p_query=getVacTypeSelectBoxDataList", null, null);
	
	// 승인상태 SelectBox
	getAjaxHtml($("#p_status"), "dlm.geuntaeMgr.statusSelectBoxDataList.do?sb_type=sel&p_query=getStatusSelectBoxDataList", null, null);	
});

$(document).ready(function(){
	
	jqGridObj.jqGrid({ 
	    datatype: 'json', 
	    mtype: 'POST',
	    url: 'dlm.geuntaeMgr.gtmReqInfoList.do',
	    postData : fn_getFormData('#application_form'),
	    colNames : ['SEQ', '아이디', '이름', '휴가CODE', '휴가종류', '신청일자', '시작일', '종료일', '휴가신청일수', '신청사유', 'STATUS', '상태', '승인자', '승인일', 'OPER'],
	    colModel : [
					{name:'seq_id',index:'seq_id', width:55, hidden : true},
					{name:'icui_seq_id',index:'icui_seq_id', width:55, excel:'아이디', editable : false, hidden: true},
					{name:'user_name',index:'user_name', width:50, align:'center', editable : true},
					{name:'vac_type', index:'vac_type', width:50, align:'center', sortable:true, title:false, hidden: true},
					{name:'vac_type_name', index:'vac_type_name', width:50, align:'center', sortable:true, title:false, hidden: false},
					{name:'req_date',index:'req_date', width:80, sortable:false, align:'center', editable : true},
					{name:'vac_start_date',index:'vac_start_date', width:80, sortable:false, align:'center', editable : true},
					{name:'vac_end_date',index:'vac_end_date', width:80, sortable:false, align:'center', editable : true},
					{name:'vac_req_day',index:'vac_req_day', width:80, sortable:false, align:'center', editable : true},
					{name:'vac_req_reason',index:'vac_req_reason', width:80, sortable:false, align:'center', editable : true, hidden: true},
					{name:'status',index:'status', width:80, sortable:false, align:'center', editable : true, hidden: true},
					{name:'status_name',index:'status_name', width:80, sortable:false, align:'center', editable : true},
					{name:'vac_approver',index:'vac_approver', width:80, sortable:false, align:'center', editable : true, hidden: true},
					{name:'vac_approve_date',index:'vac_approve_date', width:50, sortable:false, align:'left', editable : true, hidden: true},
					{name:'oper', width:50, align:'center', sortable:true, title:false, hidden: true}
				],
	    gridview: true,
	    toolbar: [false, "bottom"],
	    viewrecords: true,
	    autowidth: true,
	    cellEdit : false,
        cellsubmit : 'clientArray', // grid edit mode 2
	    scrollOffset : 17,
	    shrinkToFit:true,
	    multiselect: true,
	    rownumbers: false,
	    pager: $('#btnjqGridList'),
	    rowList:[100,500,1000],
	    recordtext: '내용 {0} - {1}, 전체 {2}',
	    emptyrecords:'조회 내역 없음',
	    rowNum : 100, 
		beforeEditCell :  function(rowid, cellname, value, iRow, iCol) {
	    	idRow=rowid;
	    	idCol=iCol;
	    	kRow = iRow;
	    	kCol = iCol;
		},
		beforeSaveCell : chmResultEditEnd,
		jsonReader : {
	        root: "rows",
	        page: "page",
	        total: "total",
	        records: "records",  
	        repeatitems: false,
	    },        
	    //imgpath: 'themes/basic/images',
	    onPaging: function(pgButton) {
	    	
    		var pageIndex         = parseInt($(".ui-pg-input").val());
   			var currentPageIndex  = parseInt(jqGridObj.getGridParam("page"));// 페이지 인덱스
   			var lastPageX         = parseInt(jqGridObj.getGridParam("lastpage"));  
   			var pages = 1;
   			var rowNum 			  = 100;	   

   			if (pgButton == "user") {
   				if (pageIndex > lastPageX) {
   			    	pages = lastPageX
   			    } else pages = pageIndex;
   				
   				rowNum = $('.ui-pg-selbox option:selected').val();
   			}
   			else if(pgButton == 'next'){
   				pages = currentPageIndex+1;
   				rowNum = $('.ui-pg-selbox option:selected').val();
   			} 
   			else if(pgButton == 'last'){
   				pages = lastPageX;
   				rowNum = $('.ui-pg-selbox option:selected').val();
   			}
   			else if(pgButton == 'prev'){
   				pages = currentPageIndex-1;
   				rowNum = $('.ui-pg-selbox option:selected').val();
   			}
   			else if(pgButton == 'first'){
   				pages = 1;
   				rowNum = $('.ui-pg-selbox option:selected').val();
   			}
 	   		else if(pgButton == 'records') {
   				rowNum = $('.ui-pg-selbox option:selected').val();     
   			}
   			
   			$(this).jqGrid("clearGridData");
   			$(this).setGridParam({datatype: 'json',page:''+pages, rowNum:''+rowNum}).triggerHandler("reloadGrid"); 		
		 },		
		 loadComplete: function (data) {
			var $this = $(this);
			if ($this.jqGrid('getGridParam', 'datatype') === 'json') {
			    $this.jqGrid('setGridParam', {
			        datatype: 'local',
			        data: data.rows,
			        pageServer: data.page,
			        recordsServer: data.records,
			        lastpageServer: data.total
			    });
			
			    this.refreshIndex();
			
			    if ($this.jqGrid('getGridParam', 'sortname') !== '') {
			        $this.triggerHandler('reloadGrid');
			    }
			} else {
			    $this.jqGrid('setGridParam', {
			        page: $this.jqGrid('getGridParam', 'pageServer'),
			        records: $this.jqGrid('getGridParam', 'recordsServer'),
			        lastpage: $this.jqGrid('getGridParam', 'lastpageServer')
			    });
			    this.updatepager(false, true);
			}
			
			var rows = $(this).getDataIDs();
			for ( var i = 0; i < rows.length; i++ ) {
				
				var item = jqGridObj.jqGrid( 'getRowData', rows[i] );
				
				if(item.status == 'S') {
					$("tr#" + rows[i] + ".jqgrow > td > input.cbox").attr("disabled", true);	
				}
			}
			
		},
		gridComplete : function() {
			
			var rows = jqGridObj.getDataIDs();
			for( var i = 0; i < rows.length; i++ ) {
				
				var status = jqGridObj.getCell( rows[i], "status" );
				
				if(status == 'R') {
					jqGridObj.jqGrid( 'setCell', rows[i], 'status_name', '', { background : '#6DFF6D'} );
				} else if(status == 'B') {
					jqGridObj.jqGrid( 'setCell', rows[i], 'status_name', '', { background : 'red'} );
				}
			}
		},
		beforeSelectRow:function(rowid, e) {   
			var cbsdis = $("tr#"+rowid+".jqgrow > td > input.cbox:disabled", jqGridObj[0]);
	        if (cbsdis.length === 0) {
	            return true;    // allow select the row
	        } else {
	            return false;   // not allow select the row
	        }
		 },
		 onSelectAll: function(aRowids,status) {
			 if (status) {
	            // uncheck "protected" rows
	            var cbs = $("tr.jqgrow > td > input.cbox:disabled", jqGridObj[0]);
	            cbs.removeAttr("checked");

	            //modify the selarrrow parameter
	            jqGridObj[0].p.selarrrow = jqGridObj.find("tr.jqgrow:has(td > input.cbox:checked)")
	                .map(function() { return this.id; }) // convert to set of ids
	                .get(); // convert to instance of Array
	        }
		 },
	}); //end of jqGrid
	
	// jqGrid 크기 동적화
	fn_gridresize( $(window), $( "#jqGridList" ), 70 );
	
	$( "#jqGridList" ).jqGrid( 'setGroupHeaders', {
		useColSpanStyle : true, 
		groupHeaders : [ { startColumnName : 'req_date', numberOfColumns : 4, titleText : '휴가신청'}]
	} );
	
	if($("#loginId").val() == $("#approvalAdmin").val()) {
		$("#btnApproval").show();
		$("#btnRestore").show();
	} else {
		$("#btnApproval").hide();
		$("#btnRestore").hide();
	}
	
	// Search 버튼 클릭 시 Ajax로 리스트를 받아 넣는다.
	$("#btnSearch").click(function(){
		fn_search();
	});
	
	// 신청버튼
	$( "#btnRequest" ).click( function() {
		var sUrl = "dlm.geuntaeMgr.popUpGtmReqRequest.do";
		window.showModalDialog( sUrl, window, "dialogWidth:500px; dialogHeight:330px; center:on; scroll:off; status:off");
	} );
	
	// 승인버튼
	$("#btnApproval").click(function(){
		fn_statusChange('S');
	});
	
	// 반려버튼
	$("#btnRestore").click(function(){
		fn_statusChange('B');
	});
	
	// 취소버튼
	$("#btnCancel").click(function(){
		fn_statusChange('C');
	});
	
});


//검색
function fn_search() {
	//검색 시 스크롤 깨짐현상 해결
	jqGridObj.closest(".ui-jqgrid-bdiv").scrollLeft(0); 
	
	var sUrl = "dlm.geuntaeMgr.gtmReqInfoList.do";
	jqGridObj.jqGrid( "clearGridData" );
	jqGridObj.jqGrid( 'setGridParam', {
		url : sUrl,
		mtype : 'POST',
		datatype : 'json',
		page : 1,
		postData : fn_getFormData( "#application_form" )
	} ).trigger( "reloadGrid" );
}

// 승인
function fn_statusChange(status) {
	
	var result = true;
	// 확인 메세지
	var confirmMsg = '';
	// 선택항목 체크에 대한 메세지
	var resultMsg = '';
	
	// 체크한 것만 배열에 담음 
	var selarrrow = jqGridObj.jqGrid('getGridParam', 'selarrrow');
	
	// 각 ROW 별로 상태에 따라 작업
	for (var i = 0; i < selarrrow.length; i++) 
	{
		var item  = $('#jqGridList').jqGrid('getRowData', selarrrow[i]);
		
		if(status == 'S') {
			confirmMsg = '승인';
		}
		else if(status == 'B') 
		{
			confirmMsg = '반려';
		} 
		else if(status == 'C') 
		{
			confirmMsg = '취소';
		}
		
		if(item.status != 'R') 
		{
			result = false;
			resultMsg = '체크된 상태가 신청일때만 ' + confirmMsg + ' 가능합니다.';
		}
		
	}
	
	if (!result) {
    	alert(resultMsg);
    	return false;
    }
	
	var url = "dlm.geuntaeMgr.saveGtmReqApproveProcess.do?p_status=" + status;
	var formData = fn_getFormData('#application_form');
	
	if ( confirm( confirmMsg + ' 하시겠습니까?' ) != 0 ) {
		$.post( url, formData, function( data ) {
			alert(data.resultMsg);
			if ( data.result == 'success' ) {
				fn_search();
			}
		}, "json" ).error( function () {
			alert( "시스템 오류입니다.\n전산담당자에게 문의해주세요." );
		} );
	}
}


//afterSaveCell oper 값 지정
function chmResultEditEnd( irowId, cellName, value, irow, iCol ) {
	var item = $( '#jqGridList' ).jqGrid( 'getRowData', irowId );
	if( item.oper != 'I' ){
		item.oper = 'U';
		$( '#jqGridList' ).jqGrid('setCell', irowId, cellName, '', { 'background' : '#6DFF6D' } );
	}
	$( '#jqGridList' ).jqGrid( "setRowData", irowId, item );
	$( "input.editable,select.editable", this ).attr( "editable", "0" );
}

</script>
