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
<input type="hidden" id="p_parent_menu_id" name="p_parent_menu_id" value="${p_parent_menu_id}" />
<input type="hidden" id="menu_level" name="menu_level" value="${menu_level}" />
<div class="container-fluid">
	<table class="table table-bordered" id="dataTable" width="100%" cellspacing="0" style="border: 0px;">
		<col width="*"/>
		<tbody>
			<tr>
				<td>
					<div class="button endbox">
						<button type="button" id="btnSave" class="btn btn-primary btn-sm" >SAVE</button>
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
<script src="/js/jquery-easing/jquery.easing.min.js"></script>
<!-- Custom scripts for all pages-->
<script src="/js/sb-admin.min.js"></script>
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

var jqGridObj = $('#jqGridList');

$(document).ready(function(){
	jqGridObj.jqGrid({ 
	    datatype: 'json', 
	    mtype: 'POST',
	    url: 'admin.menuMgr.ManageMenuList.do',
	    postData : fn_getFormData('#application_form'),
	    colNames : ['SEQ', 'ID', '메뉴명', 'URL', '상위ID', '순서', '<input type="checkbox" id="chkUse_yn" onclick="checkBoxHeader(event, this)"/> 사용여부', 'use_yn_changed', '상위LINK', 'LEVEL', 'OPER'],
	    colModel : [
					{name:'seq_id',index:'seq_id', width:55, hidden : true},
					{name:'menu_id',index:'menu_id', align:'center', width:55},
					{name:'menu_name',index:'menu_name', width:100, editable: true},
					{name:'menu_url',index:'menu_url', width:100, align:'left', editable: true},
					{name:'parent_menu_id',index:'parent_menu_id', width:100, editable: false},
					{name:'order_num',index:'order_num', width:25, align:'center', sortable:false, editable: true},
					{name:'use_yn', index:'use_yn', width: 50, editable: true, sortable: false, edittype: 'checkbox', formatter: "checkbox", editoptions : { value : "Y:N" }, formatoptions : { disabled : false }, align : "center" },
					{name:'use_yn_changed',index:'use_yn_changed', width: 25, hidden:true},
					{name:'up_link',index:'up_link', width: 70, editable: true}, 
					{name:'menu_level',index:'menu_level', width: 70, hidden: true},
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
				
		},
		gridComplete : function() {
			
		},
	}); //end of jqGrid

	// jqGrid 크기 동적화
	fn_gridresize( $(window), $( "#jqGridList" ), 10 );
	
	//그리드 버튼 숨김
	$("#jqGridList").jqGrid('navGrid', "#btnjqGridList", {
			refresh : false,
			search : false,
			edit : false,
			add : false,
			del : false,								
		}
	);

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
	
	// Search 버튼 클릭 시 Ajax로 리스트를 받아 넣는다.
	$("#btnSearch").click(function(){
		//모두 대문자로 변환
		$("input[type=text]").each(function(){
			$(this).val($(this).val().toUpperCase());
		});
		
		if(uniqeValidation()){
			fn_search();
		}
	});
	
	//저장버튼
	$( "#btnSave" ).click( function() {
		fn_save();
	} );
	
});

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
	
	//모두 대문자로 변환
	$("input[type=text]").each(function(){
		$(this).val($(this).val().toUpperCase());
	});
	
	//검색 시 스크롤 깨짐현상 해결
	jqGridObj.closest(".ui-jqgrid-bdiv").scrollLeft(0); 
	
	var sUrl = "admin.menuMgr.ManageMenuList.do";
	jqGridObj.jqGrid( "clearGridData" );
	jqGridObj.jqGrid( 'setGridParam', {
		url : sUrl,
		mtype : 'POST',
		datatype : 'json',
		page : 1,
		postData : fn_getFormData( "#application_form" )
	} ).trigger( "reloadGrid" );
}

//Del 버튼
function deleteRow() {
	$('#jqGridList').saveCell(kRow, kCol);

	var selrow = $('#jqGridList').jqGrid('getGridParam', 'selrow');
	var item = $('#jqGridList').jqGrid('getRowData', selrow);
	
	if (item.oper == 'I') {
		$('#jqGridList').jqGrid('delRowData', selrow);
	} else {
		item.oper = 'D';

		$('#jqGridList').jqGrid("setRowData", selrow, item);
		var colModel = $( '#jqGridList' ).jqGrid( 'getGridParam', 'colModel' );
		for( var i in colModel ) {
			$( '#jqGridList' ).jqGrid( 'setCell', selrow, colModel[i].name,'', {background : '#FF7E9D' } );
		}
	}

	$('#jqGridList').resetSelection();
}

//Add 버튼 
function addChmResultRow() {

	$('#jqGridList').saveCell(kRow, idCol);

	var item = {};
	var colModel = $('#jqGridList').jqGrid('getGridParam', 'colModel');

	for ( var i in colModel)
		item[colModel[i].name] = '';

	item.oper = 'I';
	item.use_yn = 'Y';
	item.menu_level = $("#menu_level").val();
	item.parent_menu_id = $("#p_parent_menu_id").val();

	$('#jqGridList').resetSelection();
	$('#jqGridList').jqGrid('addRowData', $.jgrid.randId(), item, 'first');
}

function checkBoxHeader(e, tObj) {
	
	e = e||event;/* get IE event ( not passed ) */
	e.stopPropagation? e.stopPropagation() : e.cancelBubble = true; //이벤트 지우기
	
	var isChk = $("input[id="+tObj.id+"]").is(":checked");
	var cellName = tObj.id.toLowerCase().replace("chk", "");
	
	if(isChk){
		$("#jqGridList").jqGrid('resetSelection');
        var ids = $("#jqGridList").jqGrid('getDataIDs');
        for (var i=0, il=ids.length; i < il; i++) {
        	$("#jqGridList").jqGrid('setCell',ids[i], cellName, "Y");
        }
	} else {
		$("#jqGridList").jqGrid('resetSelection');
        var ids = $("#jqGridList").jqGrid('getDataIDs');
        for (var i=0, il=ids.length; i < il; i++) {
           $("#jqGridList").jqGrid('setCell',ids[i], cellName, "N");
        }
	}
}

//저장
function fn_save() {
	$( '#jqGridList' ).saveCell( kRow, idCol );
	
	var changedData = $( "#jqGridList" ).jqGrid( 'getRowData' );
	
	// 변경된 체크 박스가 있는지 체크한다.
	for( var i = 1; i < changedData.length + 1; i++ ) {
		var item = $( '#jqGridList' ).jqGrid( 'getRowData', i );
		
		if ( item.oper != 'I' && item.oper != 'U' ) {
			
			if (item.use_yn_changed != item.use_yn) {
				item.oper = 'U';
			}

			if (item.oper == 'U') {
				// apply the data which was entered.
				$('#jqGridList').jqGrid("setRowData", i, item);
			}
		}
	}
	
	if (!fn_checkValidate()) {
		return;
	}
	
	if ( confirm( '변경된 데이터를 저장하시겠습니까?' ) != 0 ) {
		var chmResultRows = [];

		//변경된 row만 가져 오기 위한 함수
		getChangedChmResultData( function( data ) {
			
			chmResultRows = data;
			var dataList = { chmResultList : JSON.stringify( chmResultRows ) };
			
			var url = 'admin.menuMgr.SaveManageMenu.do';
			var formData = fn_getFormData('#application_form');
			//객체를 합치기. dataList를 기준으로 formData를 합친다.
			var parameters = $.extend( {}, dataList, formData); 
			
			$.post( url, parameters, function( data ) {
				alert(data.resultMsg);
				if ( data.result == 'success' ) {
					parent.left.document.location.reload();
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
