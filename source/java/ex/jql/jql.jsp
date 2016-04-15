<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko" lang="ko">
<head>
<jsp:include page="/include/head.do"/>
<script type="text/javascript">
var dataList = new Array();

$(document).ready(function(){
	var site = "";
	var stationNm = $("select[name=selectedStationCd] option:selected").text();
	<c:if test="${searchStationVO.stationCd != null && searchStationVO.stationCd != ''}">
	site = "http://swopenapi.seoul.go.kr/api/subway/6e746b43436c696f313135476972546e/json/gateInfo/1/1000/" + encodeURIComponent(stationNm);
	$.getJSON("http://query.yahooapis.com/v1/public/yql?q=" + encodeURIComponent("select * from json where url='" + site + "'") + "&format=json&callback=?"
			,function(result,status){
		//alert("Data: " + result + "\nStatus: " + status);
		//alert(JSON.stringify(result));
		var data = result["query"].results.json;
		if( data["gateList"] == undefined ) {
			return;
		}
		$("#infoText").css("display", "none");
		var dataLen = eval(data["errorMessage"].total);
		for( i = 0; i < dataLen; i++ ) {
			dataList[i] = eval(data["gateList"][i]);
			// 해당 출구 표시
			$("#exit_A_"+dataList[i].ectrcNo ).show();
		
			if( $("#exit_B_"+dataList[i].ectrcNo ).html() == "" ) {
				if( $("#exit_B_"+dataList[i].ectrcNo ).html().indexOf( dataList[i].cfrBuild ) == -1 ) {
					$("#exit_B_"+dataList[i].ectrcNo ).append( dataList[i].cfrBuild );
				}
			} else {
				if( $("#exit_B_"+dataList[i].ectrcNo ).html().indexOf( dataList[i].cfrBuild ) == -1 ) {
					$("#exit_B_"+dataList[i].ectrcNo ).append( ", " + dataList[i].cfrBuild );
				}
			}
		}
	});
	</c:if>
});

function addForm( gateID ) {
	<%
  		if( com.seoulmetro.safetykeeper.util.GrantUtil.isEditGrant(request, "/station/exit") ) {
	%>
	$("#add_ipt_"+gateID).show();
	$("#btn_"+gateID).hide();
	$("#btn_cancel_"+gateID).show();
    <%
		} else {
	%>
        alert("편집 권한이 없습니다.");
	<%	
		}
	%>
}

function delForm( gateID ) {
	<%
		if( com.seoulmetro.safetykeeper.util.GrantUtil.isEditGrant(request, "/station/exit") ) {
	%>
	$(".iconDel_"+gateID).show();
	$("#btn_"+gateID).hide();
	$("#btn_cancel_"+gateID).show();
    <%
		} else {
	%>
        alert("편집 권한이 없습니다.");
	<%	
		}
	%>
}

function add( gateID ) {
	if(!$("#poiStr_"+gateID).val()){
		alert('내용을 입력하세요.');
		return;
	}
	var data = {"stationCd":"${searchStationVO.stationCd}", "exitNo":gateID, "info":$("#poiStr_"+gateID).val()};
	$.post("/station/exitWriteProc.do",data,function(data){
		data = $.trim(data);
		var newData = "<!--DEL_START_"+data+"-->, <a href=\"javascript:del('"+gateID+"','"+data+"')\" title=\"삭제\"><img src=\"/images/common/btn_delete.gif\" align=\"absmiddle\" style=\"vertical-align:middle;display:none\" class=\"iconDel_"+gateID+"\"></a> ";
		newData += $("#poiStr_"+gateID).val() + "<!--DEL_END_"+data+"-->";
		$("#exit_C_"+gateID ).append( newData );
		$("#poiStr_"+gateID).val("");
	});
}

function del( gateID, idx ) {
	var data = {"idx":idx};
	$.post("/station/exitDeleteProc.do",data,function(){
		data = $.trim(data);
		if( data ) {
			var htmlStr = $("#exit_C_"+gateID ).html();
			var startIdx = htmlStr.indexOf("<!--DEL_START_"+idx+"-->");
			var endIdx = htmlStr.indexOf("<!--DEL_END_"+idx+"-->")+15+(idx.length);
			result = htmlStr.substring(0,startIdx) + htmlStr.substring(endIdx);
			$("#exit_C_"+gateID ).html( result );
		} else {
			alert("삭제 중 오류가 발생하였습니다. 관리자에게 문의하시기 바랍니다.");
		}
	});
}

function close( gateID ) {
	$(".iconDel_"+gateID).hide();
	$("#poiStr_"+gateID).val("");
	$("#add_ipt_"+gateID).hide();
	$("#btn_"+gateID).show();
	$("#btn_cancel_"+gateID).hide();
} 

function goEvent( event, gateID ) {
	if( event.keyCode == 13 ) {
		add( gateID );
	}
}

// 호선 변경 시
function lineNumCdChange() {
	var f = document.procForm;
	$.post("/station/exitInfoByLine.do",{lineNumCd:$("select[name=selectedLineNumCd]").val()}, function(data) {
		f.selectedStationCd.length = 0;
		f.selectedStationCd.add(new Option("선택하세요", ""));
		for(var i=0; i<data.length; i++){
			f.selectedStationCd.add(new Option(data[i].stationNm, data[i].stationCd));
		}
	});
	
}
// 역 변경 시
function stationCdChange(obj) {
	var f = document.procForm;
	
	f.stationCd.value = obj[obj.selectedIndex].value;
	f.lineNumCd.value = f.selectedLineNumCd[f.selectedLineNumCd.selectedIndex].value;
	f.action = "/station/exitView.do";
	f.submit();
}

</script>
</head>
<body>
<form name="procForm" method="post">
<input type="hidden" name="stationCd" value="${searchStationVO.stationCd}"/>
<input type="hidden" name="lineNumCd" value="${searchLineVO.lineNumCd}"/>
<div id="skipNav">
	<a href="#gnb" title="본문바로가기">주메뉴바로가기</a>
	<a href="#contWrap" title="본문바로가기">본문바로가기</a>
</div>
<div class="wrap">
<jsp:include page="/include/top.do"/>
	<div class="nistWrap">
<jsp:include page="/include/left.do"/>
<!-- contents -->
    <div id="contWrap">
      <div class="content">
        <!-- pagetitle -->
        <div class="pageInfo">
          <h4>출구정보</h4>
          <div class="linemap"> <a href="#">HOME</a><img src="/images/common/icon_allow.png" alt="" /><a href="#">역사관리</a><img src="/images/common/icon_allow.png" alt="" /><em>출구정보</em></div>
        </div>
        <!-- //pagetitle end --> 
				<div class="setion">
        	<h5>ㆍ목록</h5>
        </div>
				<div class="setion">
					<div class="viewTable">
            <table style="width:500px;" summary="호선과 역별 등록된 부대시설 수를 나타내는 표를 제공합니다.">
              <colgroup>
              	<col width="170" />
              	<col width="170" />
              	<col width="160" />
              </colgroup>
              <tr>
                <th>호선</th>
                <th>역명</th>
              </tr>
              <tr>
                  <td>
                  <select name="selectedLineNumCd" style="margin:0 5px;width:100px;" onchange="javascript:lineNumCdChange()">
					<c:forEach var="lineVO" items="${lineVOList}" varStatus="status">
					<option value="${lineVO.lineNumCd}" <c:if test="${lineVO.lineNumCd eq searchLineVO.lineNumCd}">selected</c:if>>${lineVO.lineNumNm}</option>
					</c:forEach>
				  </select>
				  </td>
                  <td>
                    <select name="selectedStationCd" style="margin:0 5px;width:100px;" onchange="javascript:stationCdChange(this)">
						<c:if test="${searchStationVO.stationCd == ''}"><option value="">선택하세요</option></c:if>
						<c:forEach var="stationVO" items="${stationVOList}" varStatus="status">
						<option value="${stationVO.stationCd}" <c:if test="${stationVO.stationCd eq searchStationVO.stationCd}">selected</c:if>>${stationVO.stationNm}</option>
						</c:forEach>
					</select>
                  </td>
              </tr>
            </table>
          </div>
        </div>
        <c:if test="${searchStationVO.stationCd != ''}">
				<div class="setion">
					<div class="viewTable" align="center">
						<span id="infoText">
						해당 역에 대한 출구정보가 존재하지 않습니다.
						</span>
					</div>
					<div class="viewTable">
            <table summary="출구 정보를 등록 또는 삭제 하는 표를 제공합니다.">
              <colgroup>
              	<col width="120" />
              	<col />
              </colgroup>
              <c:forEach var="i" begin="1" end="20" varStatus="status">
              <tr style="display:none" id="exit_A_${i}">
                <th>${i}번 출구</th>
                <td class="longField">
                	<span id="exit_B_${i}"></span><span id="exit_C_${i}" style="color:blue"><c:forEach var="exitVO" items="${exitVOList}" varStatus="status"><!--DEL_START_${exitVO.idx}--><c:if test="${exitVO.exitNo eq i}">, <a href="javascript:del('${i}','${exitVO.idx}')" title="삭제"><img src="/images/common/btn_delete.gif" align="absmiddle" style="vertical-align:middle;display:none" class="iconDel_${i}"></a>${exitVO.info}<!--DEL_END_${exitVO.idx}--></c:if></c:forEach></span>
					<span id="add_ipt_${i}" style="display:none"><input type="text" name="poiStr_${i}" id="poiStr_${i}" maxlength="20" onkeyup="javascript:goEvent(event,'${i}'); return false;"/> <a href="javascript:add('${i}')" title="등록"><img src="/images/common/btn_reg.gif" alt="등록" align="absmiddle" style="vertical-align:middle"/></a></span>
					<span id="btn_${i}">
						<a href="javascript:addForm('${i}')" title="추가"><img src="/images/common/btn_plus.gif" alt="추가" align="absmiddle" style="vertical-align:middle"/></a>
						<a href="javascript:delForm('${i}')" title="삭제"><img src="/images/common/btn_minus.gif" alt="삭제" align="absmiddle" style="vertical-align:middle"/></a>
					</span>
					<span id="btn_cancel_${i}" style="display:none">
						<a href="javascript:close('${i}')" title="취소"><img src="/images/common/btn_cancel.gif" alt="취소" align="absmiddle" style="vertical-align:middle"/></a>
					</span>
				</td>
              </tr>
              </c:forEach>
            </table>
          </div>
        </div>
        </c:if>
      </div>
    </div>
  </div>
<!-- //nistWrap end -->
</div>
</form>
</body>
</html>