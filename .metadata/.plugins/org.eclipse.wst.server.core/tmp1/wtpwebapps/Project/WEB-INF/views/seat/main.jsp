<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@include file="../includes/header.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>좌석 예약 서비스</title>
<link rel="stylesheet" href="/resources/css/seat_main.css">
<link rel="stylesheet" href="/resources/css/image-picker.css">

<script src="/resources/js/image-picker.min.js"></script>

<script src="https://cdn.jsdelivr.net/npm/fullcalendar@6.1.8/index.global.min.js"></script>
<script>
$(function() {
	let startStr;
	initFullCalendar();
	function initFullCalendar() {
		var calendarEl = document.getElementById('calendar');
		var calendar = new FullCalendar.Calendar(calendarEl, {
			initialView: 'dayGridMonth',
			selectable: true,
			unselectAuto: false,
			select: function(info) {
				//console.log(info.startStr);
				startStr = info.startStr;
				$('#seatPicker').prop('selectedIndex', 0).trigger('change');
			}
		});
		calendar.setOption('contentHeight', 500);
		calendar.render();
		startStr = calendar.getDate().toISOString();
	}
	initSeatNo();
	function initSeatNo() {
		$.getJSON("/seat/getSeatNo/", function(data) {
			//console.log(date);
			$(data).each(function(i, item) {
				var option = $("<option data-img-src='/resources/image/number_0" + (i+1) + ".png' value='"+(i+1)+"'></option>");
				$('#seatPicker').append(option);
			});
			$("select").imagepicker();
			$('#selectPicker').prop('selectedIndex', 0).trigger('change');
		});
	}
	function getTime() {
		//alert(startStr);
		const d = new Date(startStr);
		d.setHours(0);
		d.setMinutes(0);
		d.setSeconds(0); //시 분 초 0으로 바꿔줌
		return d.getTime();
	}
	
	$("#seatPicker").on('change', function() {
		$.getJSON("/seat/getResState/",
			{sno:$("#seatPicker").find(':selected').val(), resdate: getTime()}, function(data){
			$("#seats").empty();
			console.log(data);
			console.log("${auth.userid}");
			$(data).each(function(i, item) {
				var input;
				if (item.userid === null) {
					input = $("<label class='box'><input type='checkbox' id="+ (i+1) + ">" + "[" + item.tno +
						" " + item.duration + "]" + "[예약가능] </input><span class ='checkmark'></span><label><br/>");
				}else{
					if("${auth.userid}" === item.userid) {
						input = $("<label class='box'><input type='checkbox' disabled='disabled' id="+ (i+1) + ">" + "[" + item.tno +
								" " + item.duration + "]" + "[예약중] </input><span class ='checkmark'></span><label><br/>");
					}else{
						input = $("<label class='box'><input type='checkbox' disabled='disabled' id="+ (i+1) + ">" + "[" + item.tno +
								" " + item.duration + "]" + "[예약불가] </input><span class ='checkmark'></span><label><br/>");
					}
				}
				$("#seats").append(input);
			});
		});
	});
	$("#reservation").on('click',function(){
		let list = new Array();
		$("#seats input").each(function(i, item) {
			let inputObj = $(item);
			if(inputObj.is(':checked')) {
				//alert(startStr + " " +$("#seatPicker option:checked").val() + " " + inputObj.attr('id'));
				let obj = {userid:"${auth.userid}", sno:$("#seatPicker option:checked").val(),
						tno:inputObj.attr('id'), resdate:getTime()};
				list.push(obj);
			}
		});
		$.ajax({
			url:"/seat/reservation/",
			data:JSON.stringify(list),
			dataType:'json',
			contentType:'application/json',
			type:'post',
			success : function(result) {
				alert(result + "번 좌석 예약완료");
				$('#seatPicker').prop('selectedIndex', result-1).trigger('change');
			}
		});
	});
});

</script>

</head>
<body>
<div class="wrapper_reservation">
	<div class="head_reservation">
		<h1> 좌석 예약</h1>
	</div>
 
		<div class="wrapper main" style="display: flex; margin-top:5px;">
			<div id='calendar' style="width:50%;"></div>
			<div style="width:50%; margin-left:20px;">
				<div style="height:15%;">
					<select id="seatPicker" class="image-picker show-html"></select>
				</div>
				<div id="seats" style="height:80%; font-size:23px;">
				</div>
				<div style="height:5%;">
					<button id="reservation" type="button">예약하기</button>
				</div>
			</div>
		</div>
</div>
 
<%@include file="../includes/footer.jsp" %>
</body>
</html>