<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
	style="font-size: 13px; font-weight: 100;"
}

</style>

<form id="application_form" name="application_form" >
<div class="container-fluid">
	<!-- Breadcrumbs-->
	<ol class="breadcrumb">
		<li class="breadcrumb-item"><a href="#">근태조회</a></li>
	</ol>
	<table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
		<col width="120">
		<col width="150">
		<col width="120">
		<col width="150">
		<col width="120">
		<col width="150">
		<col width="*"/>
		<tbody>
			<tr>
				<th style="font-size: 13px; font-weight: 100;">년도</th>
				<td>
					<select id="p_sel_year" name="p_sel_year" style="width: 100px;">
						<c:set var="today" value="<%=new java.util.Date()%>" />
						<fmt:formatDate value="${today}" pattern="yyyy" var="start"/>
						<option value="<c:out value="${start - 1}" />"><c:out value="${start - 1}" /></option> 
						<c:forEach begin="0" end="4" var="idx" step="1">
							<option value="<c:out value="${start + idx}"/>" <c:if test="${start eq start + idx}">selected</c:if> ><c:out value="${start + idx}" /></option>
						</c:forEach>
					</select>
				</td>
			
				<th style="font-size: 13px; font-weight: 100;">아이디</th>
				<td><input type="text" class="common" id="p_login_id" name="p_login_id" alt="아이디"  style="width:120px;" value="" /></td>
				
				<th style="font-size: 13px; font-weight: 100;">사용자명</th>
				<td><input type="text" class="common" id="p_name" name="p_name" alt="이름"  style="width:120px;" value="" /></td>
				
				<td>
					<div class="button endbox">
						<button type="button" id="btnSearch" class="btn btn-primary btn-sm" >조회</button>
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

<script type="text/javascript">
var idRow;
var idCol;
var kRow;
var kCol;
var resultData = [];

var jqGridObj = $('#jqGridList');

$(document).ready(function(){
	jqGridObj.jqGrid({ 
	    datatype: 'json', 
	    mtype: 'POST',
	    url: 'dlm.geuntaeMgr.gtmBaseInfoList.do',
	    postData : fn_getFormData('#application_form'),
	    colNames : ['SEQ', '아이디', '이름', '년도', '일반휴가', '특별휴가', '대체휴가', '합계', 
	                '일반휴가', '특별휴가', '대체휴가', '전년도사용', '합계', 
	                '잔여일수', '경조ㆍ교육ㆍ기타', '근무시작일', 'OPER'],
	    colModel : [
					{name:'seq_id',index:'seq_id', width:55, hidden : true},
					{name:'icui_seq_id',index:'icui_seq_id', width:55, excel:'아이디', editable : false, hidden: true},
					{name:'user_name',index:'user_name', width:60, align:'center', editable : true},
					{name:'year',index:'year', width:50, align:'left', editable : true, hidden: true},
					{name:'gen_using_poss_day',index:'gen_using_poss_day', align:'center', width:80},
					{name:'spe_using_poss_day',index:'spe_using_poss_day', width:80, sortable:false, align:'center'},
					{name:'bonus_day',index:'bonus_day', width:80, sortable:false, align:'center', cellattr: function (){return 'style="cursor:pointer"';}},
					{name:'total_vacation',index:'total_vacation', width:80, sortable:false, align:'center'},
					{name:'using_generalized_vacation',index:'generalized_vacation', align:'center', width:80, cellattr: function (){return 'style="cursor:pointer"';}},
					{name:'using_special_vacation',index:'special_vacation', width:80, sortable:false, align:'center', cellattr: function (){return 'style="cursor:pointer"';}},
					{name:'using_replace_vacation',index:'replace_vacation', width:80, sortable:false, align:'center', cellattr: function (){return 'style="cursor:pointer"';}},
					{name:'pre_year_using_day',index:'pre_year_using_day', width:80, sortable:false, align:'center'},
					{name:'using_total_vacation',index:'using_total_vacation', width:80, sortable:false, align:'center'},
					{name:'balance',index:'balance', width:100, sortable:false, align:'center'},
					{name:'etc',index:'etc', width:100, sortable:false, align:'center', cellattr: function (){return 'style="cursor:pointer"';}},
					{name:'work_start_date',index:'work_start_date', width:100, sortable:false, align:'center'},
					{name:'oper', width:50, align:'center', sortable:true, title:false, hidden: true}
				],
	    gridview: true,
	    toolbar: [false, "bottom"],
	    viewrecords: true,
	    autowidth: true,
	    cellEdit : true,
        cellsubmit : 'clientArray', // grid edit mode 2
	    scrollOffset : 17,
	    shrinkToFit:true,
	    multiselect: false,
	    rownumbers: true,
	    //pager: $('#btnjqGridList'),
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
			
		},
		onCellSelect: function(rowid, iCol) {
			
			jqGridObj.saveCell(kRow, idCol );
			var cm = jqGridObj.jqGrid('getGridParam', 'colModel');
			var item = jqGridObj.getRowData(rowid);
			//검색 시 스크롤 깨짐현상 해결
			jqGridObj.closest(".ui-jqgrid-bdiv").scrollLeft(0); 
			
			if(cm[iCol].name == 'using_generalized_vacation' || cm[iCol].name == 'using_special_vacation' 
					|| cm[iCol].name == 'using_replace_vacation' || cm[iCol].name == 'etc') {
				
				var vac_type;
				
				if(cm[iCol].name == 'using_generalized_vacation')
				{
					vac_type = "ger";
				} 
				else if(cm[iCol].name == 'using_special_vacation') 
				{
					vac_type = "spe";
				}
				else if(cm[iCol].name == 'using_replace_vacation') 
				{
					vac_type = "rep";
				}
				else if(cm[iCol].name == 'etc') 
				{
					vac_type = "etc";
				}
				
				var sURL = "dlm.geuntaeMgr.popUpGtmReqInfo.do?p_icui_seq_id=" + item.icui_seq_id + "&p_vac_type=" + vac_type;
				var popOptions = "width=900, height=540, resizable=yes, scrollbars=yes, status=yes";
				
				popupWin = window.open(sURL, "gtmReqInfoPopup", popOptions);
				popupWin.blur();
				
				setTimeout(function(){
					popupWin.focus();
				}, 500);
			} else if(cm[iCol].name == 'bonus_day') {
				var sURL = "dlm.geuntaeMgr.popUpGtmBonusInfo.do?p_icui_seq_id="+item.icui_seq_id;
				var popOptions = "width=700, height=540, resizable=yes, scrollbars=yes, status=yes";
				
				popupWin = window.open(sURL, "gtmBonusInfoPopup", popOptions);
				popupWin.blur();
				
				setTimeout(function(){
					popupWin.focus();
				}, 500);
			}
		},
	}); //end of jqGrid

	// jqGrid 크기 동적화
	fn_gridresize( $(window), $( "#jqGridList" ), 70 );
	
	$( "#jqGridList" ).jqGrid( 'setGroupHeaders', {
		useColSpanStyle : true, 
		groupHeaders : [ { startColumnName : 'gen_using_poss_day', numberOfColumns : 4, titleText : '사용가능일수'}
						,{ startColumnName : 'using_generalized_vacation', numberOfColumns : 5, titleText : '사용된일수'}
					   ]
	} );
	
	
	// Search 버튼 클릭 시 Ajax로 리스트를 받아 넣는다.
	$("#btnSearch").click(function(){
		
		fn_search();
	});
	
});

//검색
function fn_search() {
	//검색 시 스크롤 깨짐현상 해결
	jqGridObj.closest(".ui-jqgrid-bdiv").scrollLeft(0); 
	
	var sUrl = "dlm.geuntaeMgr.gtmBaseInfoList.do";
	jqGridObj.jqGrid( "clearGridData" );
	jqGridObj.jqGrid( 'setGridParam', {
		url : sUrl,
		mtype : 'POST',
		datatype : 'json',
		page : 1,
		postData : fn_getFormData( "#application_form" )
	} ).trigger( "reloadGrid" );
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
