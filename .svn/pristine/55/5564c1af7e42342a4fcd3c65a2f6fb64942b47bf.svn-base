<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<link rel="stylesheet" href="/css/bootstrap.min.css">
<!-- <link rel="stylesheet" href="/css/font-awesome/font-awesome.min.css" type="text/css"> -->
<link href='/css/calendar/fullcalendar.css' rel='stylesheet' />
<link href='/css/calendar/fullcalendar.print.min.css' rel='stylesheet' media='print' />
<style>
/* body {
	margin: 40px 10px;
	padding: 0;
	font-family: "Lucida Grande", Helvetica, Arial, Verdana, sans-serif;
	font-size: 14px;
} */

#calendar {
	max-width: 80%;
	margin: 0 auto; 
}

.fc-sat {color:#0000FF;}
.fc-sun {color:#FF0000;}
.fc-content {
	color : #000000;
	background-color: #E8D9FF;
}

.setDiv {
    padding-top: 100px;
    text-align: center;
}
.mask {
    position:absolute;
    left:0;
    top:0;
    z-index:9999;
    /* background-color:#000; */
    display:none;
}
.window {
    display: none;
    background-color: #F6FFCC;
    height: 200px;
    z-index:99999;
}
</style>
<script src='/js/calendar/moment.min.js'></script>
<script src="/js/jquery-1.11.0.min.js"></script>
<script src='/js/calendar/fullcalendar.min.js'></script>
<script src='/js/calendar/locale/ko.js'></script>
<script>

$(document).ready(function() {
	
	var date = new Date();
	var day = date.getDate();
	var month = date.getMonth();
	var year = date.getFullYear();
	
	$('#calendar').fullCalendar({
		header : {
			left: "",
			center : 'title',
			right: "today prev,next"
		},
		lang: 'ko',
		contentHeight: '100',
		allDayDefault: false,
		displayEventTime: false,
		defaultView: "month",
		//defaultDate : '2018-04-18',
		navLinks : false, // can click day/week names to navigate views
		editable : false,
		eventLimit : false, // allow "more" link when too many events
		events : function(start, end, timezone, callback) {
		    $.ajax({
				url: 'dlm.geuntaeMgr.monthViewList.do',
				method : 'POST',
				dataType: 'json',
				success: function(data) {
                    var events = [];
                    $(data).each(function() {
                        events.push({
                            title: $(this).attr('title'),
                            start: $(this).attr('vac_start_date'),
                            end: $(this).attr('vac_end_date'),
                            content : $(this).attr('vac_req_reason')
                            //url: "/test/eventDetail.do?id="+$(this).attr('id')+"&amp;lang="+$(this).attr('lang')+"&amp;start="+$(this).attr('start')+"&amp;end="+$(this).attr('end'),
                            //lang : $(this).attr('lang')
                        });
                    });
                    callback(events);
                },
				error : function(request, status, error) {
					if (request.status != '0') {
						alert("code : " + request.status + "\r\nmessage : " + request.reponseText + "\r\nerror : " + error);
					}
				}
			});
		},
		/* eventRender: function(event, element, view) {
			if (event.holiday == '1') {
		        var dateString = event.start.format("YYYY-MM-DD");
		        $(view.el[0]).find('.fc-day[data-date=' + dateString + ']').css('background-color', '#FAA732');
		    }
		}, */ // 사용자 지정 holiday 추가 가능
		eventClick: function(calEvent, jsEvent, view) {
			wrapWindowByMask(calEvent);
	    },
	});
	
	 var calHeight = $(window).height()*0.9;
	 $('#calendar').fullCalendar('option', 'height', calHeight);
	 

     // 닫기(close)를 눌렀을 때 작동합니다.
     $('.window .close').click(function (e) {
         e.preventDefault();
         $('.mask, .window').hide();
     });

     // 뒤 마스크를 클릭시에도 모두 제거하도록 처리합니다.
     $('.mask').click(function () {
         $(this).hide();
         $('.window').hide();
     });

});

function wrapWindowByMask(calEvent){
    // 화면의 높이와 너비를 변수로 만듭니다.
    var maskHeight = $(document).height();
    var maskWidth = $(window).width();

    // 마스크의 높이와 너비를 화면의 높이와 너비 변수로 설정합니다.
    $('.mask').css({'width':maskWidth,'height':maskHeight});

    // fade 애니메이션 : 1초 동안 검게 됐다가 80%의 불투명으로 변합니다.
    //$('.mask').fadeIn(1000);
    $('.mask').fadeTo("slow",0.8);

    // 레이어 팝업을 가운데로 띄우기 위해 화면의 높이와 너비의 가운데 값과 스크롤 값을 더하여 변수로 만듭니다.
    var left = ( $(window).scrollLeft() + ( $(window).width() - $('.window').width()) / 2 );
    var top = ( $(window).scrollTop() + ( $(window).height() - $('.window').height()) / 2 );

    // css 스타일을 변경합니다.
    $('.window').css({'left':left,'top':top, 'position':'absolute'});
    
    $("#title").text(calEvent.title);
    $("#content").text(calEvent.content);

    // 레이어 팝업을 띄웁니다.
    $('.window').show();
}
</script>
</head>
<body>
<div class="container-fluid">
	<!-- Breadcrumbs-->
	<ol class="breadcrumb">
		<li class="breadcrumb-item"><a href="#">근태현황(월)</a></li>
	</ol>
	
	<div class="table-responsive">
	
		<div id='calendar'></div>
		
		<div class="setDiv">
		    <div class="mask"></div>
		    <div class="window">
                <p style="width:500px;height:30px;text-align:center; background:#E4F7BA; padding:0px;" id="title"></p>
                <p style="width:500px;height:200px;text-align:center;vertical-align:middle;" id="content"></p>
            </div>
		</div>
		
	</div>
	
	
</div>
</body>
</html>