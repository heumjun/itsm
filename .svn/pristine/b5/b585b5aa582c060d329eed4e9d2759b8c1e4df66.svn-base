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
	style="font-size: 13px; font-weight: 100;"
}

</style>

<form id="application_form" name="application_form" >
<input type="hidden" id="loginId" name="loginId" value="${loginId }" />
<input type="hidden" id="approvalAdmin" name="approvalAdmin" value="${approvalAdmin }" />
<div class="container-fluid">
	<!-- Breadcrumbs-->
	<ol class="breadcrumb">
		<li class="breadcrumb-item"><a href="#">대체휴가 등록</a></li>
	</ol>
	<table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
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
				
				<td>
					<div class="button endbox">
						<button type="button" id="btnSearch" class="btn btn-primary btn-sm" >조회</button>
						<button type="button" id="btnSave" class="btn btn-primary btn-sm" >저장</button>
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
	    url: 'dlm.geuntaeMgr.gtmBonusInfoList.do',
	    postData : fn_getFormData('#application_form'),
	    colNames : ['SEQ', '아이디', '대상자', '적용년도', 'YEAR', '휴가일수', '발생사유', 'OPER'],
	    colModel : [
					{name:'seq_id',index:'seq_id', width:55, hidden : true},
					{name:'icui_seq_id',index:'icui_seq_id', width:55, excel:'아이디', editable : false, hidden: true},
					{name:'user_name',index:'user_name', width:50, align:'center', editable : true,
						edittype : "select",
						editrules : { required : false },
						cellattr: function (){return '';},
						editoptions: {
							dataUrl: function(){
								var item = jqGridObj.jqGrid( 'getRowData', idRow );
								var url = "dlm.geuntaeMgr.getUserSelectBoxList.do";
								return url;
							},
							buildSelect: function(data){
							 	if(typeof(data)=='string'){
							 		data = $.parseJSON(data);
							 	}
					 		 	var rtSlt = '<select id="selectUser" name="selectUser" >';
					 		 	for ( var idx = 0 ; idx < data.length ; idx ++) {
					 		 		rtSlt +='<option value="'+data[idx].sb_value+'" name="'+data[idx].sb_value+'" >'+data[idx].sb_name+'</option>';	
					 		 	}
					   			rtSlt +='</select>';
							   		
							   	return rtSlt;
							},
							dataEvents: [{
						       	type: 'change'
							       	, fn: function(e, data) {
							       		var row = $(e.target).closest('tr.jqgrow');
							               var rowId = row.attr('id');
							               jqGridObj.jqGrid('setCell', rowId, 'icui_seq_id', e.target.value);
							           }
						       },{ type : 'keydown'
						       	, fn : function( e) { 
						       		var row = $(e.target).closest('tr.jqgrow');
						               var rowId = row.attr('id');
						               
						               var key = e.charCode || e.keyCode; 
						       		if( key == 13 || key == 9) {
						       			jqGridObj.jqGrid('setCell', rowId, 'icui_seq_id', e.target.value);
						       		}
						               
						       	}
						       },{ type : 'blur'
						       	, fn : function( e) { 
						       		var row = $(e.target).closest('tr.jqgrow');
						               var rowId = row.attr('id');
						               jqGridObj.jqGrid('setCell', rowId, 'icui_seq_id', e.target.value);
						       	}
							}]
						}
					},
					{name:'bonus_apply_year',index:'bonus_apply_year', align:'center', width:80, editable : true,
						edittype : "select",
						editrules : { required : false },
						cellattr: function (){return '';},
						editoptions: {
							dataUrl: function(){
								var item = jqGridObj.jqGrid( 'getRowData', idRow );
								var url = "getYear.do?p_year=" + item.year;
								return url;
							},
							buildSelect: function(data){
							 	if(typeof(data)=='string'){
							 		data = $.parseJSON(data);
							 	}
					 		 	var rtSlt = '<select id="selectBonus" name="selectBonus" >';
					 		 	for ( var idx = 0 ; idx < data.length ; idx ++) {
					 		 		rtSlt +='<option value="'+data[idx].sb_value+'" name="'+data[idx].sb_value+'" >'+data[idx].sb_name+'</option>';	
					 		 	}
					   			rtSlt +='</select>';
							   		
							   	return rtSlt;
							},
							dataEvents: [{
						       	type: 'change'
							       	, fn: function(e, data) {
							       		var row = $(e.target).closest('tr.jqgrow');
							               var rowId = row.attr('id');
							               jqGridObj.jqGrid('setCell', rowId, 'year', e.target.value);
							           }
						       },{ type : 'keydown'
						       	, fn : function( e) { 
						       		var row = $(e.target).closest('tr.jqgrow');
						               var rowId = row.attr('id');
						               
						               var key = e.charCode || e.keyCode; 
						       		if( key == 13 || key == 9) {
						       			jqGridObj.jqGrid('setCell', rowId, 'year', e.target.value);
						       		}
						               
						       	}
						       },{ type : 'blur'
						       	, fn : function( e) { 
						       		var row = $(e.target).closest('tr.jqgrow');
						               var rowId = row.attr('id');
						               jqGridObj.jqGrid('setCell', rowId, 'year', e.target.value);
						       	}
							}]
						}
					},
					{name:'year', index:'year', width:50, align:'center', sortable:true, title:false, hidden: true},
					{name:'bonus_day',index:'bonus_day', width:80, sortable:false, align:'center', editable : true},
					{name:'bonus_occur_reason',index:'bonus_occur_reason', width:200, sortable:false, align:'left', editable : true},
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
			
		}
	}); //end of jqGrid

	// jqGrid 크기 동적화
	fn_gridresize( $(window), $( "#jqGridList" ), 70 );
	
	//그리드 버튼 숨김
	$("#jqGridList").jqGrid('navGrid', "#btnjqGridList", {
			refresh : false,
			search : false,
			edit : false,
			add : false,
			del : false,								
		}
	);

	//Refresh
	$("#jqGridList").navButtonAdd('#btnjqGridList', {
		caption : "",
		buttonicon : "ui-icon-refresh",
		onClickButton : function() {
			fn_search();
		},
		position : "first",
		title : "Refresh",
		cursor : "pointer"
	});
	
	//Del 버튼
	$("#jqGridList").navButtonAdd('#btnjqGridList', {
		caption : "",
		buttonicon : "ui-icon-minus",
		onClickButton : deleteRow,
		position : "first",
		title : "Del",
		cursor : "pointer"
	});

	//Add 버튼
	$("#jqGridList").navButtonAdd('#btnjqGridList', {
		caption : "",
		buttonicon : "ui-icon-plus",
		onClickButton : addChmResultRow,
		position : "first",
		title : "Add",
		cursor : "pointer"
	});
	
	if($("#loginId").val() == $("#approvalAdmin").val()) {
		$("#btnSave").show();
		jqGridObj.jqGrid('setGridParam', {cellEdit : true});
	} else {
		$("#btnSave").hide();
		jqGridObj.jqGrid('setGridParam', {cellEdit : false});
	}
	
	// Search 버튼 클릭 시 Ajax로 리스트를 받아 넣는다.
	$("#btnSearch").click(function(){
		fn_search();
	});
	
	//저장버튼
	$( "#btnSave" ).click( function() {
		fn_save();
	} );
	
});

//Add 버튼 
function addChmResultRow() {

	$('#jqGridList').saveCell(kRow, idCol);

	var item = {};
	var colModel = $('#jqGridList').jqGrid('getGridParam', 'colModel');

	for ( var i in colModel)
		item[colModel[i].name] = '';

	item.oper = 'I';
	item.status = 'Y';
	item.status_changed = 'Y';

	$('#jqGridList').resetSelection();
	$('#jqGridList').jqGrid('addRowData', $.jgrid.randId(), item, 'first');
	
}

//Del 버튼
function deleteRow() {
	
	jqGridObj.saveCell(kRow, kCol);

	var selrow = jqGridObj.jqGrid('getGridParam', 'selrow');
	var item = jqGridObj.jqGrid('getRowData', selrow);
	
	if (item.oper == 'I') {
		jqGridObj.jqGrid('delRowData', selrow);
	} else {
		item.oper = 'D';

		jqGridObj.jqGrid("setRowData", selrow, item);
		var colModel = jqGridObj.jqGrid( 'getGridParam', 'colModel' );
		for( var i in colModel ) {
			jqGridObj.jqGrid( 'setCell', selrow, colModel[i].name,'', {background : '#FF7E9D' } );
		}
	}

	jqGridObj.resetSelection();
}

//검색
function fn_search() {
	//검색 시 스크롤 깨짐현상 해결
	jqGridObj.closest(".ui-jqgrid-bdiv").scrollLeft(0); 
	
	var sUrl = "dlm.geuntaeMgr.gtmBonusInfoList.do";
	jqGridObj.jqGrid( "clearGridData" );
	jqGridObj.jqGrid( 'setGridParam', {
		url : sUrl,
		mtype : 'POST',
		datatype : 'json',
		page : 1,
		postData : fn_getFormData( "#application_form" )
	} ).trigger( "reloadGrid" );
}

//저장
function fn_save() {
	$( '#jqGridList' ).saveCell( kRow, idCol );
	
	if (!fn_checkValidate()) {
		return;
	}
	
	if ( confirm( '변경된 데이터를 저장하시겠습니까?' ) != 0 ) {
		var chmResultRows = [];

		//변경된 row만 가져 오기 위한 함수
		getChangedChmResultData( function( data ) {
			
			chmResultRows = data;
			var dataList = { chmResultList : JSON.stringify( chmResultRows ) };

			var url = 'dlm.geuntaeMgr.SaveGtmBonusInfo.do';
			var formData = fn_getFormData('#application_form');
			//객체를 합치기. dataList를 기준으로 formData를 합친다.
			var parameters = $.extend( {}, dataList, formData);
			
			$.post( url, parameters, function( data ) {
				alert(data.resultMsg);
				if ( data.result == 'success' ) {
					fn_search();
				}
			}, "json" ).error( function () {
				alert( "시스템 오류입니다.\n전산담당자에게 문의해주세요." );
			} );
		} );
	}
}

//그리드 필수입력 체크
function fn_checkValidate() {
	var result = true;
	var message = "";
	var nChangedCnt = 0;
	var ids = $("#jqGridList").jqGrid('getDataIDs');

	for (var i = 0; i < ids.length; i++) {
		var oper = $("#jqGridList").jqGrid('getCell', ids[i], 'oper');

		if (oper == 'I' || oper == 'U' || oper == 'D') {
			nChangedCnt++;
			
		}
	}

	if (nChangedCnt == 0) {
		result = false;
		message = "변경된 내용이 없습니다.";
	}

	if (!result) {
		alert(message);
	}

	return result;
}

//가져온 배열중에서 필요한 배열만 골라내기 
function getChangedChmResultData( callback ) {
	var changedData = $.grep( $( "#jqGridList" ).jqGrid( 'getRowData' ), function( obj ) {
		return obj.oper == 'I' || obj.oper == 'U' || obj.oper == 'D';
	} );
	
	callback.apply( this, [ changedData.concat(resultData) ] );
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
