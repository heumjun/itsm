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
	text-transform: uppercase;
	border: 1px solid #000;
}

</style>
<form id="application_form" name="application_form" >
<input type="hidden" name="p_seq_id" id="p_seq_id"  value="" />
<input type="hidden" name="p_major_code" id="p_major_code"  value="" />
<input type="text" style="display: none;" />
<div class="container-fluid">
	<!-- Breadcrumbs-->
	<ol class="breadcrumb">
		<li class="breadcrumb-item"><a href="#">공통코드관리</a></li>
	</ol>
	<table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
		<col width="120">
		<col width="150">
		<col width="*"/>
		<tbody>
			<tr>
				<th style="font-size: 13px; font-weight: 100;">코드명</th>
				<td><input type="text" class="common" id="p_major_code_kor_name" name="p_major_code_kor_name" alt="코드명"  style="width:120px;" value="" /></td>
				
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
	
		<div id="divList" style="position:relative; float: left; width: 48%;">
			<table id="jqGridMasterList"></table>
			<div id="btnJqGridMasterList"></div>
		</div>
		<div id="divList1" style="position:relative; float: right; width: 48%;">
			<table id="jqGridDetailList" style="width: 100%"></table>
			<div id="btnJqGridDetailList"></div>
		</div>
		
	</div>
</div>
</form>
<!-- /.container-fluid-->
<!-- Bootstrap core JavaScript-->
<script src="/js/jquery-1.11.0.min.js"></script>
<!-- <script src="/js/bootstrap/js/bootstrap.bundle.min.js"></script> -->
<!-- Core plugin JavaScript-->
<!-- <script src="/js/jquery-easing/jquery.easing.min.js"></script> -->
<!-- Custom scripts for all pages-->
<!-- <script src="/js/sb-admin.min.js"></script> -->
<!-- Custom scripts for this page-->

<script type="text/javascript" src="/js/commonGridUtil.js" charset='utf-8'></script>
<script type="text/javascript" src="/js/jquery.jqGrid.min.js" charset='utf-8'></script>
<script type="text/javascript" src="/js/jqgrid/i18n/grid.locale-en.js" charset='utf-8'></script>

<script type="text/javascript">
var idRow;
var idCol;
var kRow;
var kCol;
var resultData = [];
var resultDetailData = [];

var jqGridMasterObj = $('#jqGridMasterList');
var jqGridDetailObj = $('#jqGridDetailList');

$(document).ready(function(){
	
	jqGridMasterObj.jqGrid({ 
	    datatype: 'json', 
	    mtype: 'POST',
	    url: 'admin.codeMgr.ManageMasterCodeList.do',
	    //postData : fn_getFormData('#application_form'),
	    colNames : ['seq', 'CODE_ID', 'CODE_TYPE', 'CODE_NAME', '영문명', 'DESC', '순서', '<input type="checkbox" id="chkUse_yn" onclick="checkBoxHeader(event, this)"/> 사용여부', 'use_yn_changed', 'OPER'],
	    colModel : [
					{name:'seq_id', index:'seq_id', width:55, align:'center', hidden: true},
					{name:'major_code',index:'major_code', editable : false, width:45, align:'center'},
					{name:'major_type',index:'major_type', editable : true, width:70, align:'left', hidden: true},
					{name:'major_code_kor_name',index:'major_code_kor_name', width:100, align:'left', editable : true},
					{name:'major_code_eng_name',index:'major_code_eng_name', width:100, align:'left', editable : true, hidden: true},
					{name:'description',index:'description', editable : true, width:180, hidden:false},
					{name:'order_num',index:'order_num', editable : true, width:30, align:'center', hidden: false},
					{name:'use_yn', index:'use_yn', width: 50, editable: true, sortable: false, edittype: 'checkbox', formatter: "checkbox", editoptions : { value : "Y:N" }, formatoptions : { disabled : false }, align : "center" },
					{name:'use_yn_changed',index:'use_yn_changed', width: 25, hidden:true},
					{name:'oper', index:'oper', width:50, align:'center', sortable:true, title:false, hidden: true}
				],
	    gridview: true,
	    toolbar: [false, "bottom"],
	    viewrecords: false,
	    autowidth: true,
	    cellEdit : true,
        cellsubmit : 'clientArray', // grid edit mode 2
	    scrollOffset : 17,
	    shrinkToFit:true,
	    multiselect: false,
	    pager: $('#btnJqGridMasterList'),
	    pgbuttons: false,
	    pgtext: null,
	    //rowList:[100,500,1000],
	    //recordtext: '내용 {0} - {1}, 전체 {2}',
	    emptyrecords:'조회 내역 없음',
	    rowNum : 9999, 
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
   			var currentPageIndex  = parseInt(jqGridMasterObj.getGridParam("page"));// 페이지 인덱스
   			var lastPageX         = parseInt(jqGridMasterObj.getGridParam("lastpage"));  
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
		onCellSelect : function( rowid, iCol, cellcontent, e ) {  
			
			jqGridDetailObj.saveCell(kRow, idCol );
			var cm = jqGridMasterObj.jqGrid('getGridParam', 'colModel');
			
			if(cm[iCol].name == 'major_code'){
				
				var item = jqGridMasterObj.getRowData(rowid);
				$("#p_seq_id").val(item.seq_id);
				$("#p_major_code").val(item.major_code);
				
				//검색 시 스크롤 깨짐현상 해결
				jqGridDetailObj.closest(".ui-jqgrid-bdiv").scrollLeft(0); 
				
				var sUrl = "admin.codeMgr.ManageDetailCodeList.do";
				jqGridDetailObj.jqGrid( "clearGridData" );
				jqGridDetailObj.jqGrid( 'setGridParam', {
					url : sUrl,
					mtype : 'POST',
					datatype : 'json',
					page : 1,
					postData : fn_getFormData( "#application_form" )
				} ).trigger( "reloadGrid" );
			}
		},
		gridComplete: function () {
			var rows = $( "#jqGridMasterList" ).getDataIDs();
			for ( var i = 0; i < rows.length; i++ ) {
				var major_type = $( "#jqGridMasterList" ).getCell( rows[i], "major_type" );
				if(major_type != '') {
					//$( '#jqGridMasterList' ).jqGrid( 'setCell', rows[i], 'major_type', '', {background : '#FF7E9D' } );
					$( '#jqGridMasterList' ).setRowData(rows[i], false, {background: '#FF9999'});
					/* var colModel = $( '#jqGridMasterList' ).jqGrid( 'getGridParam', 'colModel' );
					for( var j in colModel ) {
						$( '#jqGridMasterList' ).jqGrid( 'setCell', rows[i], colModel[j].name, '', 'not-editable-cell' );
					} */
				}
			}
		}
	}); //end of jqGrid
	
	//그리드 버튼 숨김
	$("#jqGridMasterList").jqGrid('navGrid', "#btnJqGridMasterList", {
			refresh : false,
			search : false,
			edit : false,
			add : false,
			del : false,								
		}
	);

	//Del 버튼
	$("#jqGridMasterList").navButtonAdd('#btnJqGridMasterList', {
		caption : "",
		buttonicon : "ui-icon-minus",
		onClickButton : deleteRow,
		position : "first",
		title : "Del",
		cursor : "pointer"
	});

	//Add 버튼
	$("#jqGridMasterList").navButtonAdd('#btnJqGridMasterList', {
		caption : "",
		buttonicon : "ui-icon-plus",
		onClickButton : addChmResultRow,
		position : "first",
		title : "Add",
		cursor : "pointer"
	});
	
	jqGridDetailObj.jqGrid({ 
	    datatype: 'json', 
	    mtype: 'POST',
	    url: '',
	    postData : fn_getFormData('#application_form'),
	    colNames : ['seq', 'CODE_ID', 'CODE_TYPE', 'CODE_NAME', '영문명', 'DESC', '순서', '<input type="checkbox" id="chkUse_yn" onclick="checkBoxHeaderDetail(event, this)"/> 사용여부', 'use_yn_changed', 'OPER'],
	    colModel : [
					{name:'seq_id', index:'seq_id', width:55, hidden: true},
					{name:'medium_code', index:'medium_code', editable:false, width:80, align:'center'},
					{name:'medium_type', index:'medium_type', editable:false, width:80, align:'left', hidden: true},
					{name:'medium_code_kor_name',index:'medium_code_kor_name', width:150, align:'left', editable : true},
					{name:'medium_code_eng_name',index:'medium_code_eng_name', width:80, align:'left', editable : true, hidden:true},
					{name:'description',index:'description', editable : true, width:80, hidden:false},
					{name:'order_num',index:'order_num', editable : true, width:30, align:'center'},
					{name:'use_yn', index:'use_yn', width: 50, editable: true, sortable: false, edittype: 'checkbox', formatter: "checkbox", editoptions : { value : "Y:N" }, formatoptions : { disabled : false }, align : "center" },
					{name:'use_yn_changed',index:'use_yn_changed', width: 25, hidden:true},
					{name:'oper', index:'oper', width:50, align:'center', sortable:true, title:false, hidden: true}
				],
	    gridview: true,
	    toolbar: [false, "bottom"],
	    viewrecords: false,
	    autowidth: true,
	    cellEdit : true,
        cellsubmit : 'clientArray', // grid edit mode 2
	    scrollOffset : 17,
	    shrinkToFit:true,
	    multiselect: false,
	    pager: $('#btnJqGridDetailList'),
	    pgbuttons: false,
	    pgtext: null,
	    //rowList:[100,500,1000],
	    //recordtext: '내용 {0} - {1}, 전체 {2}',
	    emptyrecords:'조회 내역 없음',
	    rowNum : 9999, 
		beforeEditCell :  function(rowid, cellname, value, iRow, iCol) {
	    	idRow=rowid;
	    	idCol=iCol;
	    	kRow = iRow;
	    	kCol = iCol;
		},
		beforeSaveCell : chmResultEditEndDetail,
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
   			var currentPageIndex  = parseInt(jqGridDetailObj.getGridParam("page"));// 페이지 인덱스
   			var lastPageX         = parseInt(jqGridDetailObj.getGridParam("lastpage"));  
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
		 gridComplete : function() {
			 var rows = $( "#jqGridDetailList" ).getDataIDs();
				for ( var i = 0; i < rows.length; i++ ) {
					var medium_type = $( "#jqGridDetailList" ).getCell( rows[i], "medium_type" );
					if(medium_type != '') {
						//$( '#jqGridMasterList' ).jqGrid( 'setCell', rows[i], 'major_type', '', {background : '#FF7E9D' } );
						$( '#jqGridDetailList' ).setRowData(rows[i], false, {background: '#FF9999'});
						/* var colModel = $( '#jqGridDetailList' ).jqGrid( 'getGridParam', 'colModel' );
						for( var j in colModel ) {
							$( '#jqGridDetailList' ).jqGrid( 'setCell', rows[i], colModel[j].name, '', 'not-editable-cell' );
						} */
					}
				}
		 },
	}); //end of jqGrid
	
	//그리드 버튼 숨김
	$("#jqGridDetailList").jqGrid('navGrid', "#btnJqGridDetailList", {
			refresh : false,
			search : false,
			edit : false,
			add : false,
			del : false,								
		}
	);

	//Del 버튼
	$("#jqGridDetailList").navButtonAdd('#btnJqGridDetailList', {
		caption : "",
		buttonicon : "ui-icon-minus",
		onClickButton : deleteRowDetail,
		position : "first",
		title : "Del",
		cursor : "pointer"
	});

	//Add 버튼
	$("#jqGridDetailList").navButtonAdd('#btnJqGridDetailList', {
		caption : "",
		buttonicon : "ui-icon-plus",
		onClickButton : addChmResultRowDetail,
		position : "first",
		title : "Add",
		cursor : "pointer"
	});

	// jqGrid 크기 동적화
	//fn_gridresize( $(window), $( "#jqGridList" ), 70 );
	fn_insideGridresize( $(window), $("#divList"), $("#jqGridMasterList"), -440, .47);
	fn_insideGridresize( $(window), $("#divList1"), $("#jqGridDetailList"), -440, .47);
	
	// Search 버튼 클릭 시 Ajax로 리스트를 받아 넣는다.
	$("#btnSearch").click(function(){
		
		if(uniqeValidation()){
			fn_search();
		}
	});
	
	//저장버튼
	$( "#btnSave" ).click( function() {
		fn_save();
	} );
	
});

//Del 버튼
function deleteRow() {
	$('#jqGridMasterList').saveCell(kRow, kCol);

	var selrow = $('#jqGridMasterList').jqGrid('getGridParam', 'selrow');
	var item = $('#jqGridMasterList').jqGrid('getRowData', selrow);
	
	if(item.major_type != '') {
		alert("필수 코드는 삭제 할수 없습니다.");
		return;
	}
	
	if (item.oper == 'I') {
		$('#jqGridMasterList').jqGrid('delRowData', selrow);
	} else {
		item.oper = 'D';

		$('#jqGridMasterList').jqGrid("setRowData", selrow, item);
		var colModel = $( '#jqGridMasterList' ).jqGrid( 'getGridParam', 'colModel' );
		for( var i in colModel ) {
			$( '#jqGridMasterList' ).jqGrid( 'setCell', selrow, colModel[i].name,'', {background : '#FF7E9D' } );
		}
	}

	$('#jqGridMasterList').resetSelection();
}

//Add 버튼 
function addChmResultRow() {

	$('#jqGridMasterList').saveCell(kRow, idCol);

	var item = {};
	var colModel = $('#jqGridMasterList').jqGrid('getGridParam', 'colModel');

	for ( var i in colModel)
		item[colModel[i].name] = '';

	item.oper = 'I';
	item.use_yn = 'Y';
	item.use_yn_changed = 'Y';

	$('#jqGridMasterList').resetSelection();
	$('#jqGridMasterList').jqGrid('addRowData', $.jgrid.randId(), item, 'first');
}

//Del 버튼
function deleteRowDetail() {
	
	if($("#p_seq_id").val() == '')
	{
		alert("마스터 코드를 선택하여 주세요.");
		return;
	}
	
	if(item.medium_type != '') {
		alert("필수 코드는 삭제 할수 없습니다.");
		return;
	}
	
	$('#jqGridDetailList').saveCell(kRow, kCol);

	var selrow = $('#jqGridDetailList').jqGrid('getGridParam', 'selrow');
	var item = $('#jqGridDetailList').jqGrid('getRowData', selrow);
	
	if (item.oper == 'I') {
		$('#jqGridDetailList').jqGrid('delRowData', selrow);
	} else {
		item.oper = 'D';

		$('#jqGridDetailList').jqGrid("setRowData", selrow, item);
		var colModel = $( '#jqGridDetailList' ).jqGrid( 'getGridParam', 'colModel' );
		for( var i in colModel ) {
			$( '#jqGridDetailList' ).jqGrid( 'setCell', selrow, colModel[i].name,'', {background : '#FF7E9D' } );
		}
	}

	$('#jqGridDetailList').resetSelection();
}

//Add 버튼 
function addChmResultRowDetail() {
	
	if($("#p_seq_id").val() == '')
	{
		alert("마스터 코드를 선택하여 주세요.");
		return;
	}

	$('#jqGridDetailList').saveCell(kRow, idCol);

	var item = {};
	var colModel = $('#jqGridDetailList').jqGrid('getGridParam', 'colModel');

	for ( var i in colModel)
		item[colModel[i].name] = '';

	item.oper = 'I';
	item.use_yn = 'Y';
	item.use_yn_changed = 'Y';

	$('#jqGridDetailList').resetSelection();
	$('#jqGridDetailList').jqGrid('addRowData', $.jgrid.randId(), item, 'first');
}

//필수 항목 Validation
var uniqeValidation = function(){
	var rnt = true;
	$(".required").each(function(){
		if($(this).val() == ""){
			$(this).focus();
			alert($(this).attr("alt")+ "가 누락되었습니다.");
			rnt = false;
			return false;
		}
	});
	return rnt;
}

//검색
function fn_search() {
	
	//검색 시 스크롤 깨짐현상 해결
	jqGridMasterObj.closest(".ui-jqgrid-bdiv").scrollLeft(0); 
	
	var sUrl = "admin.codeMgr.ManageMasterCodeList.do";
	jqGridMasterObj.jqGrid( "clearGridData" );
	jqGridMasterObj.jqGrid( 'setGridParam', {
		url : sUrl,
		mtype : 'POST',
		datatype : 'json',
		page : 1,
		postData : fn_getFormData( "#application_form" )
	} ).trigger( "reloadGrid" );
	
	//검색 시 스크롤 깨짐현상 해결
	jqGridDetailObj.closest(".ui-jqgrid-bdiv").scrollLeft(0); 
	
	var sUrl = "admin.codeMgr.ManageDetailCodeList.do";
	jqGridDetailObj.jqGrid( "clearGridData" );
	jqGridDetailObj.jqGrid( 'setGridParam', {
		url : sUrl,
		mtype : 'POST',
		datatype : 'json',
		page : 1,
		postData : fn_getFormData( "#application_form" )
	} ).trigger( "reloadGrid" );
}

function checkBoxHeader(e, tObj) {
	
	e = e||event;/* get IE event ( not passed ) */
	e.stopPropagation? e.stopPropagation() : e.cancelBubble = true; //이벤트 지우기
	
	var isChk = $("input[id="+tObj.id+"]").is(":checked");
	var cellName = tObj.id.toLowerCase().replace("chk", "");
	
	if(isChk){
		$("#jqGridMasterList").jqGrid('resetSelection');
        var ids = $("#jqGridMasterList").jqGrid('getDataIDs');
        for (var i=0, il=ids.length; i < il; i++) {
        	$("#jqGridMasterList").jqGrid('setCell',ids[i], cellName, "Y");
        }
	} else {
		$("#jqGridMasterList").jqGrid('resetSelection');
        var ids = $("#jqGridMasterList").jqGrid('getDataIDs');
        for (var i=0, il=ids.length; i < il; i++) {
           $("#jqGridMasterList").jqGrid('setCell',ids[i], cellName, "N");
        }
	}
}

function checkBoxHeaderDetail(e, tObj) {
	
	e = e||event;/* get IE event ( not passed ) */
	e.stopPropagation? e.stopPropagation() : e.cancelBubble = true; //이벤트 지우기
	
	var isChk = $("input[id="+tObj.id+"]").is(":checked");
	var cellName = tObj.id.toLowerCase().replace("chk", "");
	
	if(isChk){
		$("#jqGridDetailList").jqGrid('resetSelection');
        var ids = $("#jqGridDetailList").jqGrid('getDataIDs');
        for (var i=0, il=ids.length; i < il; i++) {
        	$("#jqGridDetailList").jqGrid('setCell',ids[i], cellName, "Y");
        }
	} else {
		$("#jqGridDetailList").jqGrid('resetSelection');
        var ids = $("#jqGridDetailList").jqGrid('getDataIDs');
        for (var i=0, il=ids.length; i < il; i++) {
           $("#jqGridDetailList").jqGrid('setCell',ids[i], cellName, "N");
        }
	}
}

//저장
function fn_save() 
{
	$( '#jqGridMasterList' ).saveCell( kRow, idCol );
	$( '#jqGridDetailList' ).saveCell( kRow, idCol );
	
	var changedData = $( "#jqGridMasterList" ).jqGrid( 'getRowData' );
	// 변경된 체크 박스가 있는지 체크한다.
	for( var i = 1; i < changedData.length + 1; i++ ) {
		var item = $( '#jqGridMasterList' ).jqGrid( 'getRowData', i );
		
		if ( item.oper != 'I' && item.oper != 'U' ) {
			
			if (item.use_yn_changed != item.use_yn) {
				item.oper = 'U';
			}

			if (item.oper == 'U') {
				// apply the data which was entered.
				$('#jqGridMasterList').jqGrid("setRowData", i, item);
			}
		}
	}
	
	changedData = $( "#jqGridDetailList" ).jqGrid( 'getRowData' );
	// 변경된 체크 박스가 있는지 체크한다.
	for( var i = 1; i < changedData.length + 1; i++ ) {
		var item = $( '#jqGridDetailList' ).jqGrid( 'getRowData', i );
		
		if ( item.oper != 'I' && item.oper != 'U' ) {
			
			if (item.use_yn_changed != item.use_yn) {
				item.oper = 'U';
			}

			if (item.oper == 'U') {
				// apply the data which was entered.
				$('#jqGridDetailList').jqGrid("setRowData", i, item);
			}
		}
	}
	
	if (!fn_checkValidate()) {
		return;
	}
	
	if ( confirm( '변경된 데이터를 저장하시겠습니까?' ) != 0 ) {
		
		var chmResultRows	 = [];
		var detailResultRows = [];

		//변경된 row만 가져 오기 위한 함수
		getChangedChmResultData( function( data, detailData ) {
			
			chmResultRows = data;
			detailResultRows = detailData;
			
			var dataList = { 
					chmResultList : JSON.stringify( chmResultRows ),
					chmDetailResultList : JSON.stringify(detailResultRows)
			};
			
			var url = "admin.codeMgr.SaveManageCode.do";
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

// 그리드 필수입력 체크
function fn_checkValidate() {
	
	var result = true;
	var message = "";
	var nChangedCnt = 0;
	
	var idsMaster = $("#jqGridMasterList").jqGrid('getDataIDs');
	var idsDetail = $("#jqGridDetailList").jqGrid('getDataIDs');

	for (var i = 0; i < idsMaster.length; i++) {
		var oper = $("#jqGridMasterList").jqGrid('getCell', idsMaster[i], 'oper');

		if (oper == 'I' || oper == 'U' || oper == 'D') {
			nChangedCnt++;
		}
	}
	
	for (var i = 0; i < idsDetail.length; i++) {
		var oper = $("#jqGridDetailList").jqGrid('getCell', idsDetail[i], 'oper');

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
function getChangedChmResultData(callback) {
	
	var changedData = $.grep($("#jqGridMasterList").jqGrid('getRowData'),
			function(obj) {
				return obj.oper == 'I' || obj.oper == 'U'
						|| obj.oper == 'D';
			});
	var changedDetailData = $.grep($("#jqGridDetailList").jqGrid('getRowData'),
			function(obj) {
				return obj.oper == 'I' || obj.oper == 'U'
						|| obj.oper == 'D';
			});

	callback.apply(this, [ changedData.concat(resultData), changedDetailData.concat(resultDetailData) ]);
	
}

//afterSaveCell oper 값 지정
function chmResultEditEnd( irowId, cellName, value, irow, iCol ) {
	var item = $( '#jqGridMasterList' ).jqGrid( 'getRowData', irowId );
	if( item.oper != 'I' ){
		item.oper = 'U';
		$( '#jqGridMasterList' ).jqGrid('setCell', irowId, cellName, '', { 'background' : '#6DFF6D' } );
	}
	$( '#jqGridMasterList' ).jqGrid( "setRowData", irowId, item );
	$( "input.editable,select.editable", this ).attr( "editable", "0" );
}

//afterSaveCell oper 값 지정
function chmResultEditEndDetail( irowId, cellName, value, irow, iCol ) {
	var item = $( '#jqGridDetailList' ).jqGrid( 'getRowData', irowId );
	if( item.oper != 'I' ){
		item.oper = 'U';
		$( '#jqGridDetailList' ).jqGrid('setCell', irowId, cellName, '', { 'background' : '#6DFF6D' } );
	}
	$( '#jqGridDetailList' ).jqGrid( "setRowData", irowId, item );
	$( "input.editable,select.editable", this ).attr( "editable", "0" );
}
</script>
