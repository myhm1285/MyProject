	$(function() {
		$(".datepicker").datepicker({
			showOn: 'both',
			buttonImage: '/images/common/date.png',
			buttonImageOnly: true,
			dateFormat : 'yy-mm-dd',
			dayNames: ["일요일","월요일","화요일","수요일","목요일","금요일","토요일"],
			dayNamesShort: ["일","월","화","수","목","금","토"],
			dayNamesMin: ["일","월","화","수","목","금","토"],
			monthNames: ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"],
			monthNamesShort : ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"],
			weekHeader: "Wk",
			isRTL: false,
			showMonthAfterYear: true,
			yearSuffix: " ",
			changeYear: true,
			changeMonth: true
		});
		$('img.ui-datepicker-trigger').css({'cursor':'pointer', 'margin-top':'2px'});
		$(".datepicker").attr('readonly','readonly');
		$(".datepicker").css('text-align','center');
	});
