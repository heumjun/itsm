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
<input type="hidden" id="p_col_name" name="p_col_name"/>
<input type="hidden" id="p_data_name" name="p_data_name"/>
<div class="container-fluid">
	<!-- Breadcrumbs-->
	<ol class="breadcrumb">
		<li class="breadcrumb-item"><a href="#">사용자관리</a></li>
		<!-- <li class="breadcrumb-item active">목록</li> -->
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
				<th style="font-size: 13px; font-weight: 100;">아이디</th>
				<td><input type="text" class="common" id="p_login_id" name="p_login_id" alt="아이디"  style="width:120px;" value="" /></td>
				
				<th style="font-size: 13px; font-weight: 100;">이름</th>
				<td><input type="text" class="common" id="p_name" name="p_name" alt="이름"  style="width:120px;" value="" /></td>
				
				<th style="font-size: 13px; font-weight: 100;">사용여부</th>
				<td>
					<select id="p_sel_status" name="p_sel_status" style="width:120px; font-size: 13px; font-weight: 100;">
						<option value="">선택</option>
						<option value="Y">사용</option>
						<option value="N">비사용</option>
					</select>
				</td>
				
				<td>
					<div class="button endbox">
						<button type="button" id="btnSearch" class="btn btn-primary btn-sm" >조회</button>
						<button type="button" id="btnSave" class="btn btn-primary btn-sm" >저장</button>
						<button type="button" id="btnExport" class="btn btn-success btn-sm" >엑셀</button>
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
	    url: 'admin.userMgr.ManageUserList.do',
	    postData : fn_getFormData('#application_form'),
	    colNames : ['SEQ', '아이디', '이름', '이메일', '회사', '부서', '직급코드', '직급', '담당업무', 
	                '주소', '연락처', '보조연락처', '사무실연락처', '시스템',
	                '<input type="checkbox" id="chkSm_man_flag" onclick="checkBoxHeader(event, this)"/> 운영여부', 'sm_man_flag_changed',
	                '<input type="checkbox" id="chkStatus" onclick="checkBoxHeader(event, this)"/> 사용여부', 'status_changed', 'OPER'],
	    colModel : [
					{name:'seq_id',index:'seq_id', width:55, hidden : true},
					{name:'login_id',index:'login_id', width:55, excel:'아이디', editable : false},
					{name:'name',index:'name', width:60, align:'center', editable : true, excel:'이름'},
					{name:'e_mail',index:'e_mail', width:150, align:'left', editable : true, excel:'이메일'},
					{name:'company_name',index:'company_name', editable : true, width:150, excel:'회사'},
					{name:'department',index:'department', width:80, sortable:false, editable : true, align:'center', excel:'부서'},
					{name:'rank_code',index:'rank_code', width:80, sortable:false, editable : false, align:'center', hidden: true},
					{name:'rank',index:'rank', width:60, sortable:false, editable : true, align:'center', excel:'직위',
						 edittype : "select",
				  		 editrules : { required : false },
				  		 cellattr: function (){return '';},
				  		 editoptions: {
						 	dataUrl: function(){
				            	var item = jqGridObj.jqGrid( 'getRowData', idRow );
				            	var url = "admin.userMgr.getUserRankList.do?p_rank_code=" + item.rank_code + "&p_seq_id=" + item.seq_id;
								return url;
						 	},
				  		 	buildSelect: function(data){
				     		 	if(typeof(data)=='string'){
				     		 		data = $.parseJSON(data);
				     		 	}
				      		 	var rtSlt = '<select id="selectRank" name="selectRank" >';
				      		 	for ( var idx = 0 ; idx < data.length ; idx ++) {
				      		 		rtSlt +='<option value="'+data[idx].sb_value+'" name="'+data[idx].sb_value+'" ' + data[idx].selected + '>'+data[idx].sb_name+'</option>';	
				      		 	}
					       		rtSlt +='</select>';
					       		
					       		return rtSlt;
				  		 	},
				  		 	dataEvents: [{
				            	type: 'change'
				            	, fn: function(e, data) {
				            		var row = $(e.target).closest('tr.jqgrow');
				                    var rowId = row.attr('id');
				                    jqGridObj.jqGrid('setCell', rowId, 'rank_code', e.target.value);
				                }
				            },{ type : 'keydown'
				            	, fn : function( e) { 
				            		var row = $(e.target).closest('tr.jqgrow');
				                    var rowId = row.attr('id');
				                    
				                    var key = e.charCode || e.keyCode; 
				            		if( key == 13 || key == 9) {
				            			jqGridObj.jqGrid('setCell', rowId, 'rank_code', e.target.value);
				            		}
				                    
				            	}
				            },{ type : 'blur'
				            	, fn : function( e) { 
				            		var row = $(e.target).closest('tr.jqgrow');
				                    var rowId = row.attr('id');
				                    jqGridObj.jqGrid('setCell', rowId, 'rank_code', e.target.value);
				            	}
				            }]
				  		 }
					},
					{name:'business_role',index:'business_role', width:150, sortable:false, editable : true, excel:'전화번호'},
					{name:'home_address',index:'home_address', width:150, sortable:false, editable : true, excel:'전화번호'},
					{name:'phone1',index:'phone1', width:80, sortable:false, editable : true, align:'center', excel:'전화번호'},
					{name:'phone2',index:'phone2', width:80, sortable:false, editable : true, align:'center', excel:'전화번호'},
					{name:'tel',index:'tel', width:80, sortable:false, editable : true, align:'center', excel:'전화번호'},
					{name:'erp_legacy_gubun',index:'erp_legacy_gubun', width:60, sortable:false, editable : true, align:'center', excel:'전화번호'},
					{name:'sm_man_flag', index:'sm_man_flag', width: 50, editable: true, sortable: false, edittype: 'checkbox', formatter: "checkbox", editoptions : { value : "Y:N" }, formatoptions : { disabled : false }, align : "center", excel:'사용여부' },
					{name:'sm_man_flag_changed',index:'sm_man_flag_changed', width: 25, hidden:true},
					{name:'status', index:'status', width: 50, editable: true, sortable: false, edittype: 'checkbox', formatter: "checkbox", editoptions : { value : "Y:N" }, formatoptions : { disabled : false }, align : "center", excel:'사용여부' },
					{name:'status_changed',index:'status_changed', width: 25, hidden:true},
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
	    multiselect: true,
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
		onCellSelect: function(row_id, colId) {
			var item = $( "#jqGridList" ).jqGrid( 'getRowData', row_id );
			if(item.oper == 'I') {
				$("#jqGridList").setColProp('login_id', { editable : true});
			} else {
				$("#jqGridList").setColProp('login_id', { editable : false});
			}
		},
		gridComplete : function() {
			
		},
		//에디트가 종료되면, 셀의 에디트 가능 여부를 false 로 돌린다.
		afterEditCell: function (rowid, cellname, value, iRow, iCol) {
		    $(this).setColProp("login_id", { editable: false });
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
	
	// Search 버튼 클릭 시 Ajax로 리스트를 받아 넣는다.
	$("#btnSearch").click(function(){
		//모두 대문자로 변환
		/* $("input[type=text]").each(function(){
			$(this).val($(this).val().toUpperCase());
		}); */
		
		if(uniqeValidation()){
			fn_search();
		}
	});
	
	//저장버튼
	$( "#btnSave" ).click( function() {
		fn_save();
	} );
	
	//btnExport 버튼 클릭 시 
	$("#btnExport").click(function(){
		//fn_downloadStart();
		fn_excelDownload();	
	});
	
});

//Del 버튼
function deleteRow() {
	$('#jqGridList').saveCell(kRow, kCol);
	
	//체크한 것만 배열에 담음 
	var recs = $("#jqGridList").jqGrid('getGridParam', 'selarrrow');
	var rows = recs.length;
	
	/* 각 ROW 별로 상태에 따라 작업 */
	for (var i = rows - 1; i >= 0; i--) {
		
		var item  = $('#jqGridList').jqGrid('getRowData', recs[i]);
		
		if (item.oper == 'I') {
			$('#jqGridList').jqGrid('delRowData', recs[i]);
		} else {
			item.oper = 'D';
			$('#jqGridList').jqGrid("setRowData", recs[i], item);
			var colModel = $( '#jqGridList' ).jqGrid( 'getGridParam', 'colModel' );
			for( var j in colModel ) {
				$( '#jqGridList' ).jqGrid( 'setCell', recs[i], colModel[j].name,'', {background : '#FF7E9D' } );
			}
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
	item.status = 'Y';
	item.status_changed = 'Y';

	$('#jqGridList').resetSelection();
	$('#jqGridList').jqGrid('addRowData', $.jgrid.randId(), item, 'first');
	
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
	jqGridObj.closest(".ui-jqgrid-bdiv").scrollLeft(0); 
	
	var sUrl = "admin.userMgr.ManageUserList.do";
	jqGridObj.jqGrid( "clearGridData" );
	jqGridObj.jqGrid( 'setGridParam', {
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
			
			if (item.sm_man_flag_changed != item.sm_man_flag) {
				item.oper = 'U';
			}
			
			if (item.status_changed != item.status) {
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

			var url = 'admin.userMgr.SaveManageUser.do';
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



//엑셀 다운로드 화면 호출
function fn_excelDownload() {
	//그리드의 label과 name을 받는다.
	//용도 : 엑셀 출력 시 헤더 및 데이터 맵퍼 자동 설정
	var colName = new Array();
	var dataName = new Array();
	
	var cn = $( "#jqGridList" ).jqGrid( "getGridParam", "colNames" );
	var cm = $( "#jqGridList" ).jqGrid( "getGridParam", "colModel" );
	for(var i=1; i<cm.length; i++ ){
		
		if(cm[i]['hidden'] == false) {
			colName.push(cm[i]['excel']);
			dataName.push(cm[i]['index']);	
		}
	}
	$( '#p_col_name' ).val(colName);
	$( '#p_data_name' ).val(dataName);
	var form    = document.application_form;
	form.action = "admin.userMgr.manageUserListExcelExport.do";
	form.method = "post";
	form.submit();
}

</script>
