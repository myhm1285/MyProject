$(document).ready(function () {
  // menu toggle
  $('[data-toggle="offcanvas"]').click(function () {
    $('.row-offcanvas').toggleClass('active');
  });
  
  //menu toggle
  $('[data-toggle="searchDiv"]').click(function () {
	  if($('#searchDiv').is(".hidden-xs")){
        setCookie('searchBar','on',7);		  
	  } else {
  	    setCookie('searchBar','off',7);		  
	  }
	  $('#searchDiv').toggleClass('hidden-xs');
	  $(this).find('i').toggleClass('fa-caret-down');
	  $(this).find('i').toggleClass('fa-caret-up');
  });
  
  if(getCookie('searchBar') == 'on'){
	  $('#searchDiv').removeClass('hidden-xs');
	  $('[data-toggle="searchDiv"]').find('i').removeClass('fa-caret-down');
	  $('[data-toggle="searchDiv"]').find('i').addClass('fa-caret-up');
  }
});

// 프린트
function printNow() {
	window.print();
}

// 프린트 콜센터 민원 팝업
function complainPop() {
	window.open("/complain/complainPopup.do?isPopup=true&minWidth=330",
			"complainPop", "width=365,height=370,scrollbars=0");
}

// 프린트 팝업
function printPop() {
	window.open("", "printPop", "width=1280,height=500,scrollbars=1");
}

// textarea 글자수 제한
$(function() {
	$("textarea[maxlength]").keyup(function() {
		var str = $(this).val();
		var mx = parseInt($(this).attr('maxlength'));
		if (str.length > mx) {
			$(this).val(str.substr(0, mx));
			return false;
		}
	});
});

// 쿠키 세팅하기
function setCookie(name, value, expireDays) {
	var todayDate = new Date();
	todayDate.setDate(todayDate.getDate() + expireDays);

	document.cookie = name + "=" + escape(value) + ";path=/; expires="
			+ todayDate.toUTCString() + ";";
}

// 쿠키 가져오기
function getCookie(name) {
	var nameValue = document.cookie.match("(^|;)?" + name + "=([^;]*)(;|$)");

	return nameValue ? nameValue[2] : null;
}

// rowspan
function rowspan(name){
	var cnt = 1;
	$('td[name=' + name + ']').each(function(index){
		if(cnt == 1) {
			cnt = getRowspanCnt($(this), name, index);
			$(this).attr({rowspan:cnt, removeFlag:false});
		} else {
			$(this).attr({removeFlag:true});
			cnt--;
		}
	});
	
	$('td[name=' + name + ']').each(function(index){
		if($(this).attr('removeFlag') == 'true') $(this).remove();
	});
}
// rowspan-cnt
function getRowspanCnt(compareObj, name, index){
	var cnt = 1;
	var returnFlag = false;
	var compareHidden = $(compareObj).find("input[type='hidden']").val();
	$('td[name=' + name + ']').each(function(curIndex){
		if(returnFlag || curIndex <= index) return;
		else if(compareHidden == $(this).find("input[type='hidden']").val()) cnt++;
		else returnFlag = true;
	});
	return cnt;
}