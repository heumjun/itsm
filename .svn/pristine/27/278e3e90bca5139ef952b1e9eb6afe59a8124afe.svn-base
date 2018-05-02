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
}
</style>
<form id="application_form" name="application_form" >
<input type="hidden" id="p_stanAuthCode" name="p_stanAuthCode"/>
<div class="container-fluid">
	<!-- Breadcrumbs-->
	<ol class="breadcrumb">
		<li class="breadcrumb-item"><a href="#">표준권한관리</a></li>
	</ol>
	<table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
		<col width="*"/>
		<tbody>
			<tr>
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
	
		<div id="divList" style="position:relative; float: left; width: 29%;">
			<table id="jqGridMasterList"></table>
			<div id="btnJqGridMasterList"></div>
		</div>
		<div id="divList1" style="position:relative; float: right; width: 69%;">
			<table id="jqGridDetailList" style="width: 100%"></table>
			<div id="btnjqGridDetailList"></div>
		</div>
		
	</div>
</div>
</form>
<!-- /.container-fluid-->
<script src="/js/jquery-1.11.0.min.js"></script>
<script src="/js/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="/js/sb-admin.min.js"></script>
<script type="text/javascript" src="/js/commonGridUtil.js" charset='utf-8'></script>
<script type="text/javascript" src="/js/jquery.jqGrid.min.js" charset='utf-8'></script>
<script type="text/javascript" src="/js/jqgrid/i18n/grid.locale-en.js" charset='utf-8'></script>
<script src="/js/jqueryAjax.js"></script>
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
	    url: 'admin.stanAuthMgr.stanAuthMgrList.do',
	    postData : fn_getFormData('#application_form'),
	    colNames : ['', '권한코드', '권한', '설명', '순서', 'OPER'],
	    colModel : [
					{name:'seq_id', index:'seq_id', width: 30, align:'center', hidden: true},
					{name:'stand_auth_code', index:'stand_auth_code', width:80, align:'center', hidden: false},
					{name:'stand_auth_code_name',index:'stand_auth_code_name', width:80, align:'left', editable: true},
					{name:'description',index:'description', width:150, editable: true, hidden:false},
					{name:'order_num',index:'order_num', width:30, editable: true, align:'center', hidden:false},
					{name:'oper', index:'oper', width:50, align:'center', sortable:true, title:false, hidden: true}
				],
	    gridview: true,
	    toolbar: [false, "bottom"],
	    viewrecords: false,
	    autowidth: true,
	    cellEdit : true,
        cellsubmit : 'clientArray', // grid edit mode 2
	    scrollOffset : 17,
	    shrinkToFit: true,
	    multiselect: false,
	    pager: $('#btnJqGridMasterList'),
	    pgbuttons: false,
	    pgtext: null,
	    //rowList:[100,500,1000],
	    //recordtext: '내용 {0} - {1}, 전체 {2}',
	    emptyrecords:'조회 내역 없음',
	    rowNum : -1, 
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
			
			if(cm[iCol].name == 'stand_auth_code'){
				
				var item = jqGridMasterObj.getRowData(rowid);
				if(item.oper != 'I') {
					$("#p_stanAuthCode").val(item.seq_id);
					
					//검색 시 스크롤 깨짐현상 해결
					jqGridDetailObj.closest(".ui-jqgrid-bdiv").scrollLeft(0);
					
					var sUrl = "admin.stanAuthMgr.stanAuthMgrMenuList.do";
					jqGridDetailObj.jqGrid( "clearGridData" );
					jqGridDetailObj.jqGrid( 'setGridParam', {
						url : sUrl,
						mtype : 'POST',
						datatype : 'json',
						page : 1,
						postData : fn_getFormData( "#application_form" )
					} ).trigger( "reloadGrid" );
				}
				
			}
			
		}
		
	}); //end of jqGrid
	
	// 체크 되어 있는지 확인
	var checkCnt = $("td[aria-describedby=jqGridDetailList_seq_id] input[type='radio']:radio:checked");
	if (checkCnt.length == 0) {
		// default radio 체크 (첫 번째)
		setTimeout(function(){
			$("input:radio[name=radioid]").eq(0).attr("checked", true);
		}, 500);
	}
	
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
	    colNames : ['auth', '<input type="checkbox" id="chkAuth_check" onclick="checkBoxHeader(event, this)"/> 권한', 'auth_changed', 
	                '상세SEQ', '마스터SEQ', '메뉴SEQ', 'ID', '메뉴명', 'URL', '상위ID', '순서', '상위LINK', 'LEAF', 'LEV', 'OPER'
	               ],
	    colModel : [
					{name:'auth', index:'auth', width: 30, editable: false, sortable: false, align:"center", hidden: true },
					{name:'auth_check', index:'auth_check', width: 30, editable: false, sortable: false, align:"center", formatter : formatOptAuth },
					{name:'auth_changed',index:'auth_changed', width: 25, hidden: true},
					{name:'icsaid_seq_id',index:'icsaid_seq_id', width:55, hidden : true},
					{name:'icsaim_seq_id',index:'icsaim_seq_id', width:55, hidden : true},
					{name:'menu_seq_id',index:'menu_seq_id', width:55, hidden : true},
					{name:'menu_id',index:'menu_id', align:'center', width:55, hidden : true},
					{name:'menu_name',index:'menu_name', width:200},
					{name:'menu_url',index:'menu_url', width:100, align:'left', hidden : true},
					{name:'parent_menu_id',index:'parent_menu_id', width:100, hidden : true},
					{name:'order_num',index:'order_num', width:25, align:'center', sortable:false, hidden : true},
					{name:'up_link',index:'up_link', width: 200, hidden: true}, 
					{name:'leaf',index:'leaf', width: 70, hidden: true},
					{name:'lev',index:'lev', width:55, hidden : true},
					{name:'oper', width:50, align:'center', sortable:true, title:false, hidden: true},
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
	    //pager: $('#btnjqGridDetailList'),
	    rowList:[100,500,1000],
	    recordtext: '내용 {0} - {1}, 전체 {2}',
	    emptyrecords:'조회 내역 없음',
	    rowNum : 99999, 
		beforeEditCell :  function(rowid, cellname, value, iRow, iCol) {
	    	idRow=rowid;
	    	idCol=iCol;
	    	kRow = iRow;
	    	kCol = iCol;
		},
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
		},
	}); //end of jqGrid

	// jqGrid 크기 동적화
	//fn_gridresize( $(window), $( "#jqGridList" ), 70 );
	fn_insideGridresize( $(window), $("#divList"), $("#jqGridMasterList"), -440, .47);
	fn_insideGridresize( $(window), $("#divList1"), $("#jqGridDetailList"), -440, .47);
	
	
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

	$('#jqGridMasterList').saveCell(kRow, idCol);

	var item = {};
	var colModel = $('#jqGridMasterList').jqGrid('getGridParam', 'colModel');

	for ( var i in colModel)
		item[colModel[i].name] = '';

	item.oper = 'I';

	$('#jqGridMasterList').resetSelection();
	$('#jqGridMasterList').jqGrid('addRowData', $.jgrid.randId(), item, 'first');
	
}


//Del 버튼
function deleteRow() {
	$('#jqGridMasterList').saveCell(kRow, kCol);

	var selrow = $('#jqGridMasterList').jqGrid('getGridParam', 'selrow');
	var item = $('#jqGridMasterList').jqGrid('getRowData', selrow);
	
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

function authCodeChange(val) {
	
	$("#p_stanAuthCode").val(val);
	
	//검색 시 스크롤 깨짐현상 해결
	jqGridMasterObj.closest(".ui-jqgrid-bdiv").scrollLeft(0); 
	
	var sUrl = "admin.stanAuthMgr.stanAuthMgrMenuList.do";
	jqGridDetailObj.jqGrid( "clearGridData" );
	jqGridDetailObj.jqGrid( 'setGridParam', {
		url : sUrl,
		mtype : 'POST',
		datatype : 'json',
		page : 1,
		postData : fn_getFormData( "#application_form" )
	} ).trigger( "reloadGrid" );
}

//검색
function fn_search() {
	
	//모두 대문자로 변환
	$("input[type=text]").each(function(){
		$(this).val($(this).val().toUpperCase());
	});
	
	//검색 시 스크롤 깨짐현상 해결
	jqGridMasterObj.closest(".ui-jqgrid-bdiv").scrollLeft(0); 
	
	var sUrl = "admin.stanAuthMgr.stanAuthMgrList.do";
	jqGridMasterObj.jqGrid( "clearGridData" );
	jqGridMasterObj.jqGrid( 'setGridParam', {
		url : sUrl,
		mtype : 'POST',
		datatype : 'json',
		page : 1,
		postData : fn_getFormData( "#application_form" )
	} ).trigger( "reloadGrid" );
	
	//검색 시 스크롤 깨짐현상 해결
	jqGridMasterObj.closest(".ui-jqgrid-bdiv").scrollLeft(0); 
	
	var sUrl = "admin.stanAuthMgr.stanAuthMgrMenuList.do";
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
	
	if (($("#chkAuth_check").is(":checked"))) {
		var rows = $( "#jqGridDetailList" ).getDataIDs();
		for ( var i = 0; i < rows.length; i++ ) {
			var item = $( '#jqGridDetailList' ).jqGrid( 'getRowData', rows[i] );
			
			if(item.lev != '0') {
				item.auth = 'Y';
				$( '#jqGridDetailList' ).jqGrid( "setRowData",rows[i], item );
				$( "#auth" + rows[i] + "_flag" ).prop("checked", true);
			}
			
		}
		
	} else {
		var rows = $( "#jqGridDetailList" ).getDataIDs();
		for ( var i = 0; i < rows.length; i++ ) {
			var item = $( '#jqGridDetailList' ).jqGrid( 'getRowData', rows[i] );
			if(item.lev != '0') {
				item.auth = 'N';
				$( '#jqGridDetailList' ).jqGrid( "setRowData",rows[i], item );
				$( "#auth" + rows[i] + "_flag" ).prop("checked", false);
			}
		}
	}
	
}


//저장
function fn_save() {

	$( '#jqGridMasterList' ).saveCell( kRow, idCol );
	
	var changedData = $( "#jqGridDetailList" ).jqGrid( 'getRowData' );
	
	// 변경된 체크 박스가 있는지 체크한다.
	for( var i = 1; i < changedData.length + 1; i++ ) {
		var item = $( '#jqGridDetailList' ).jqGrid( 'getRowData', i );
		
		if ( item.oper != 'I' && item.oper != 'U' ) {
			
			if (item.auth_changed != item.auth) {
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

			var url = 'admin.stanAuthMgr.saveStanAuthMgrMenu.do';
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

//그리드 필수입력 체크
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

function formatOptAuth(cellvalue, options, rowObject){
	var rowid = options.rowId;
	
	if(rowObject.lev == '0') {
		return '';
	} else {
		if(rowObject.auth == "Y") {
			return "<input type='checkbox' id='auth"+rowid+"_flag' checked onclick='chkClickAuth(" + rowid + ")'/>";	
		} else {
			return "<input type='checkbox' id='auth"+rowid+"_flag' onclick='chkClickAuth(" + rowid + ")'/>";	
		}
	}

}

function chkClickAuth( rowid ) {
	if( ( $( "#auth" + rowid + "_flag" ).is( ":checked" ) ) ) {
		$("#jqGridDetailList").setRowData( rowid, { auth : "Y" } );
	} else {
		$("#jqGridDetailList").setRowData( rowid, { auth : "N" } );
	}
}
</script>
