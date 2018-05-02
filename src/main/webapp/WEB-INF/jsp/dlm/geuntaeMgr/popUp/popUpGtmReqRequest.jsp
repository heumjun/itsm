<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<link rel="stylesheet" href="/css/bootstrap.min.css">
<link rel="stylesheet" href="/css/jquery-ui.min.css">
<!-- <link rel="stylesheet" href="/css/jquery-ui.theme.min.css"> -->
<link rel="stylesheet" href="/css/font-awesome/font-awesome.min.css" type="text/css">
<style>
.endbox {
	position: relative;
	text-align: right;
	left: -10px;
	height:22px;
}

table th {
	text-align: left;
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
<input type="hidden" id="p_user_name" name="p_user_name" value="${loginUser.name}" />
<input type="hidden" id="p_vac_name" name="p_vac_name" value="" />
<input type="hidden" id="p_icui_seq_id" name="p_icui_seq_id" value="${loginUser.seq_id}" />
<div class="container-fluid">
	<!-- Breadcrumbs-->
	<ol class="breadcrumb" style="margin-bottom: 0px;">
		<li class="breadcrumb-item"><a href="#">휴가 신청</a></li>
	</ol>
	<div style="float: right; margin: 0px 0px 4px 0">
		<button type="button" id="btnSave" class="btn btn-primary btn-sm" >저장</button>
		<button type="button" id="btnClose" class="btn btn-primary btn-sm" >닫기</button>
	</div>
	<div class="table-responsive">
		<table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
			<col width="80"/>
			<col width="150"/>
			<tbody>
				<tr>
					<th style="font-size: 13px; font-weight: 100;">신청자</th>
					<td><input type="text" class="common" id="p_login_id" name="p_login_id" alt="아이디" style="width:120px;" disabled="disabled" value="${loginUser.name}" /></td>
				</tr>
				
				<tr>	
					<th style="font-size: 13px; font-weight: 100;">휴가종류</th>
					<td>
						<select id="p_vac_type" name="p_vac_type"  onchange="javascript:vacTypeChange(this.value);" style="width:120px; font-size: 13px; font-weight: 100;">
						</select>
					</td>
				</tr>
				
				<tr>	
					<th style="font-size: 13px; font-weight: 100;">휴가신청기간</th>
					<td>
						<input type="text" class="common" id="p_vac_start_date" name="p_vac_start_date" alt="시작일"  onchange="javascript:vacReqDayChage();" style="width:120px;" value="" />
						~
						<input type="text" class="common" id="p_vac_end_date" name="p_vac_end_date" alt="종료일" onchange="javascript:vacReqDayChage();" style="width:120px;" value="" />
					</td>
				</tr>	
				
				<tr>	
					<th style="font-size: 13px; font-weight: 100;">휴가신청일수</th>
					<td>
						<input type="text" class="common" id="p_vac_req_day" name="p_vac_req_day" alt="이름"  style="width:120px;" value="" readonly="readonly" />
					</td>
				</tr>
				
				<tr>	
					<th style="font-size: 13px; font-weight: 100;">신청사유</th>
					<td>
						<input type="text" class="common" id="p_vac_req_reason" name="p_vac_req_reason" alt="시작일"  style="width:300px;" value="" />
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
</form>
<!-- /.container-fluid-->
<script src="/js/jquery-1.11.0.min.js"></script>
<script src="/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="/js/commonGridUtil.js" charset='utf-8'></script>
<script src="/js/jqueryAjax.js"></script>

<script type="text/javascript">

var date = new Date(); 

//달력 셋팅
$(function() {
	
  	$( "#p_vac_start_date, #p_vac_end_date" ).datepicker({
    	dateFormat: 'yy-mm-dd',
    	prevText: '이전 달',
	    nextText: '다음 달',
	    monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
	    monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
	    dayNames: ['일','월','화','수','목','금','토'],
	    dayNamesShort: ['일','월','화','수','목','금','토'],
	    dayNamesMin: ['일','월','화','수','목','금','토'],
	    showMonthAfterYear: true,
	    yearSuffix: '년',
	    minDate: 0,
	    beforeShowDay: DisableSpecificDates,
	    
  	});
  	
 	// 휴가종류 SelectBox
	getAjaxHtml($("#p_vac_type"), "dlm.geuntaeMgr.vacTypeSelectBoxDataList.do?sb_type=sel&p_query=getVacTypeSelectBoxDataList", null, null);
  	
});

$(document).ready(function(){
	//저장버튼
	$( "#btnSave" ).click( function() {
		fn_save();
	} );
});

function fn_save() {
	
	var args = window.dialogArguments;
	
	$("#p_vac_name").val( $("#p_vac_type option:selected").text() );
	
	var url = "dlm.geuntaeMgr.saveGtmReqInfo.do";
	var formData = fn_getFormData('#application_form');

	if ( confirm( '휴가신청을 하시겠습니까?' ) != 0 ) {
		$.post( url, formData, function( data ) {
			alert(data.resultMsg);
			if ( data.result == 'success' ) {
				args.fn_search();
				self.close();
			}
		}, "json" ).error( function () {
			alert( "시스템 오류입니다.\n전산담당자에게 문의해주세요." );
		} );
	}
	
}


function DisableSpecificDates(date) {
	
	var disableddates = ["2018-5-1", "2018-4-25", "2018-4-26", "2018-4-27"];
	
	var m = date.getMonth();
	var d = date.getDate();
	var y = date.getFullYear();
 	
	var currentdate = y + '-' + (m + 1) + '-' + d;
	
	for (var i = 0; i < disableddates.length; i++) {
	
		if ($.inArray(currentdate, disableddates) != -1 ) {
			return [false];
		}
	}
	
	var weekenddate = $.datepicker.noWeekends(date);
	return weekenddate; 
 }

$(document).ready( function() {
	$("#btnClose").click(function() {
		self.close();
	});
});

function vacTypeChange(type) {
	
	if(type == 'VAC_BAN') {
		$("#p_vac_req_day").val('0.5');
	}
	
	vacReqDayChage();
	
}

function vacReqDayChage() {
	
	var result = true;
	var message = "";
	
	var strDate1 = $("#p_vac_start_date").val();
    var strDate2 = $("#p_vac_end_date").val();
    
    if(strDate1 != '' && strDate2 != '') {
    	
    	var arr1 = strDate1.split('-');
	    var arr2 = strDate2.split('-');
	    
	    var dat1 = new Date(arr1[0], arr1[1], arr1[2]);
	    var dat2 = new Date(arr2[0], arr2[1], arr2[2]);
	    
    	if($("#p_vac_type").val() == 'VAC_BAN') {
    		
    		if(strDate1 != strDate2) {
    			result = false;
    			message = '반차 사용시 휴가 시작일과 종료일이 같아야 합니다.';
    			$("#p_vac_end_date").focus();
    		}
    		
    	} else {
    		
    	    if(dat1.getTime() > dat2.getTime()) {
    	    	result = false;
    	    	message = "종료일은 시작일보다 이후 날짜로 선택하세요.";
    	    	$("#p_vac_end_date").focus();
    	    }
    	    
    	}
    	
    	if (!result) {
	    	alert(message);
	    	return false;
	    }
    	
    	//var diff = dat2 - dat1;
		//var currDay = 24 * 60 * 60 * 1000;// 시 * 분 * 초 * 밀리세컨
		
		var count = calcDate(strDate1, strDate2);
		
		if($("#p_vac_type").val() == 'VAC_BAN') {
			$("#p_vac_req_day").val('0.5');
		} else {
			//$("#p_vac_req_day").val(parseInt(diff/currDay) + 1);
			$("#p_vac_req_day").val(count);
		}
    	
    }
	
}

function calcDate(startDate, endDate) {
	
	var arr1 = startDate.split('-');
    var arr2 = endDate.split('-');
    
    var date1 = new Date(arr1[0], arr1[1]-1, arr1[2]); // 2017-11-30
    var date2 = new Date(arr2[0], arr2[1]-1, arr2[2]); // 2017-12-6

    var count = 0;

	while(true) {  
	
	    var temp_date = date1;
	    if(temp_date.getTime() > date2.getTime()) {
	        //console.log("count : " + count);
	        break;
	    } else {
	        var tmp = temp_date.getDay();
	        if(tmp == 0 || tmp == 6) {
	            // 주말
	            //console.log("주말");
	        } else {
	            // 평일
	            //console.log("평일");
	            count++;         
	        }
	        temp_date.setDate(date1.getDate() + 1); 
	    }
	}
	
	return count;

}

</script>
