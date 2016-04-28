<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/views/include/jstl.jsp"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/views/include/head.jsp" />
<script type="text/javascript">
	//페이지이동
	function pageMove(pg) {
		var f = document.procForm;
		f.pg.value = pg;
		f.action = "/history/historyList.do";
		f.submit();
	}
	// 검색
	function searchClick() {
		var f = document.procForm;
		f.pg.value = 1;
		f.action = "/history/historyList.do";
		f.submit();
	}
	// 초기화
	function searchResetClick() {
		location.href = "/history/historyList.do";
	}
	// 등록
	function writeClick() {
		location.href = "/history/historyWrite.do";
	}
	// 엔터처리
	function enterEvent(e) {
		if (e.keyCode == 13) {
			searchClick();
		}
	}
	// OS 타입 검색
	function searchOsType(osType) {
		$("input[name='searchOsType']").val(osType);
		searchClick();
	}
	// 푸시 요청 상태 검색
	function searchPushReqSts(pushReqSts) {
		$("input[name='searchPushReqSts']").val(pushReqSts);
		searchClick();
	}
	// 메시지 내용 검색
	function searchMsgContent() {
		$("input[name='searchMsgContent']").val($("input[name='searchMsgContent']").val());
		searchClick();
	}
</script>
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<form name="procForm" method="post">
	<input type="hidden" name="pg" value="${searchVO.pg}"/>
	<input type="hidden" name="searchOsType" value="${searchVO.searchOsType}"/>
	<input type="hidden" name="searchPushReqSts" value="${searchVO.searchPushReqSts}"/>
		<div class="wrapper">
			<header class="main-header">
			<jsp:include page="/WEB-INF/views/include/top.jsp"/>
			</header>
			<aside class="main-sidebar">
			<jsp:include page="/WEB-INF/views/include/left.jsp"/>
			</aside>
			<!-- contents -->
			<div class="content-wrapper">
				<!-- pagetitle -->
				<section class="content-header">
				  <h1>이력<small>목록</small></h1>
				  <ol class="breadcrumb">
				  	<li><a href="#">HOME</a></li>
				  	<li><a href="#">이력</a></li>
				  	<li><a href="#">메시지 이력</a></li>
			  	  </ol>
				</section>
				<!-- table -->
				<section class="content">
					<!-- Info boxes -->
					<div class="row">
					  <div class="col-md-3 col-sm-6 col-xs-12">
					    <div class="info-box">
					      <span class="info-box-icon bg-yellow"><i class="fa fa-envelope-o"></i></span>
					      <div class="info-box-content">
					        <span class="info-box-text">메시지 총 건수</span>
					        <span class="info-box-number"><big>90</big><small> 건</small></span>
					      </div><!-- /.info-box-content -->
					    </div><!-- /.info-box -->
					  </div><!-- /.col -->
					  <div class="col-md-3 col-sm-6 col-xs-12">
					    <div class="info-box">
					      <span class="info-box-icon bg-aqua"><i class="fa fa-bell-o"></i></span>
					      <div class="info-box-content">
					        <span class="info-box-text">메시지 응답 건수</span>
					        <span class="info-box-number"><big>90</big><small> 건</small></span>
					      </div><!-- /.info-box-content -->
					    </div><!-- /.info-box -->
					  </div><!-- /.col -->
					
					  <!-- fix for small devices only -->
					  <div class="clearfix visible-sm-block"></div>
					
					  <div class="col-md-3 col-sm-6 col-xs-12">
					    <div class="info-box">
					      <span class="info-box-icon bg-olive"><i class="fa fa-check"></i></span>
					      <div class="info-box-content">
					        <span class="info-box-text">메시지 요청 완료</span>
					        <span class="info-box-number"><big>90</big><small> 건</small></span>
					      </div><!-- /.info-box-content -->
					    </div><!-- /.info-box -->
					  </div><!-- /.col -->
					  <div class="col-md-3 col-sm-6 col-xs-12">
					    <div class="info-box">
					      <span class="info-box-icon bg-red"><i class="fa fa-close"></i></span>
					      <div class="info-box-content">
					        <span class="info-box-text">메시지 요청 미완료</span>
					        <span class="info-box-number"><big>90</big><small> 건</small></span>
					      </div><!-- /.info-box-content -->
					    </div><!-- /.info-box -->
					  </div><!-- /.col -->
					</div><!-- /.row -->
					<div class="row">
			          <div class="col-xs-12">
			            <div class="box">
			              <div class="box-header">
			                <div class="btn-box-tool">
			                  <div class="btn-group" style="margin:5px;">
			                      <button type="button" class="btn btn-default">OS 타입</button>
			                      <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
			                        <span class="caret"></span>
			                        <span class="sr-only">Toggle Dropdown</span>
			                      </button>
			                      <ul class="dropdown-menu" role="menu">
			                        <li><a href="javascript:searchOsType('')">전체</a></li>
			                        <li class="divider"></li>
			                        <li class="${'A' eq searchVO.searchOsType ? 'active':''}"><a href="javascript:searchOsType('A')">안드로이드</a></li>
			                        <li class="${'I' eq searchVO.searchOsType ? 'active':''}"><a href="javascript:searchOsType('I')">IOS</a></li>
			                      </ul>
			                  </div>
			                  <div class="btn-group" style="margin:5px;">
			                      <button type="button" class="btn btn-default">푸시 요청 상태</button>
			                      <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
			                        <span class="caret"></span>
			                        <span class="sr-only">Toggle Dropdown</span>
			                      </button>
			                      <ul class="dropdown-menu" role="menu">
			                        <li><a href="javascript:searchPushReqSts('')">전체</a></li>
			                        <li class="divider"></li>
			                        <li class="${'Y' eq searchVO.searchPushReqSts ? 'active':''}"><a href="javascript:searchPushReqSts('Y')">요청</a></li>
			                        <li class="${'N' eq searchVO.searchPushReqSts ? 'active':''}"><a href="javascript:searchPushReqSts('N')">미요청</a></li>
			                      </ul>
			                  </div>
			                  <div class="input-group" style="display:inline-flex;margin:5px;">
			                    <input type="text" class="form-control" name="searchMsgContent" value="${searchVO.searchMsgContent}" placeholder="메시지 내용">
			                    <div class="input-group-btn">
		                          <button class="btn btn-default" onclick="javascript:searchClick()"><i class="fa fa-search"></i></button>
		                        </div>
			                  </div><!-- /input-group -->
	                  		</div>
						  </div><!-- /.box-header -->
						  <div class="box-body table-responsive no-padding">
							  <table class="table table-hover">
								<colgroup>
								  <col width="10%" />
								  <col />
								  <col width="15%" />
								  <col width="20%" />
								  <col width="15%" />
								  <col width="15%" />
								</colgroup>
								<thead>
								  <tr>
									<th class="text-center">순번</th>
									<th class="text-center">메시지 내용</th>
									<th class="text-center">OS 구분</th>
									<th class="text-center">발송일시</th>
									<th class="text-center">요청상태</th>
									<th class="text-center">응답상태</th>
								  </tr>
								</thead>
								<tbody>
								  <c:forEach var="sendMasterVO" items="${sendMasterVOList}" varStatus="status">
								  <tr>
									<td class="text-center">${sendMasterVO.listNo}</td>
									<td><a href="/history/historyView.do?sendIdx=${sendMasterVO.sendIdx}">${sendMasterVO.msgContent}</a></td>
									<td class="text-center">${'A' eq sendMasterVO.osType ? '안드로이드' : ('I' eq sendMasterVO.osType ? 'IOS' : '-')}</td>
									<td class="text-center">${sendMasterVO.sendDt}</td>
									<td class="text-center"><span class="badge ${'Y' eq sendMasterVO.pushReqSts ? 'bg-green':'bg-red'}">${'Y' eq sendMasterVO.pushReqSts ? '요청':'미요청'}</span></td>
									<td class="text-center"><span class="badge ${'Y' eq sendMasterVO.pushResSts ? 'bg-blue':'bg-yellow'}">${'Y' eq sendMasterVO.pushResSts ? '응답':'미응답'}</span></td>
								  </tr>
								  </c:forEach>
								</tbody>
							  </table>
						  </div><!-- /.box-body -->
						  <div class="box-footer clearfix text-center">
						    <ul class="pagination pagination-sm no-margin">
						      ${paging}
						    </ul>
						  </div>
						</div><!-- /.box -->
					  </div><!-- /.col -->
					</div><!-- /.row -->
				</section><!-- /.content -->
			</div>
		</div>
	</form>
</body>
</html>