<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/views/include/jstl.jsp"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/views/include/head.jsp" />
<script type="text/javascript">
	$(document).ready(function(){
		$("input[type='checkbox'], input[type='radio']").prop("disabled", true);
	});
	// 목록
	function cancelClick() {
		location.href = "/history/historyList.do";
	}
</script>
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<form name="procForm" method="post">
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
				  <h1>이력<small>상세</small></h1>
				  <ol class="breadcrumb">
				  	<li><a href="#">HOME</a></li>
				  	<li><a href="#">이력</a></li>
				  	<li><a href="#">메시지 이력</a></li>
			  	  </ol>
				</section>
				<!-- table -->
				<section class="content">
				  <div class="row">
				  	<div class="col-xs-12">
				  	  <div class="box">
						<div class="box-header">
		                  <h3 class="box-title"><b>발송 정보</b></h3>
		                </div><!-- /.box-header -->
				  	    <div class="box-body table-responsive no-padding">
						  <table class="table">
							<colgroup>
							  <col width="15%" />
							  <col />
							</colgroup>
							<tbody>
							  <tr>
							  	<th>발송일시</th>
							  	<td>${sendMasterVO.sendDt}</td>
						  	  </tr>
							  <tr>
							  	<th>OS 타입</th>
							  	<td><i class="fa fa-mobile-phone"></i> ${'A' eq sendMasterVO.osType ? '안드로이드' : ('I' eq sendMasterVO.osType ? 'IOS' : '-')}</td>
						  	  </tr>
							  <tr>
							  	<th>대상 (사용자ID)</th>
							  	<td>${sendMasterVO.userId}</td>
						  	  </tr>
							  <tr>
							  	<th>메시지 내용</th>
							  	<td>${sendMasterVO.msgContent}</td>
						  	  </tr>
						  	</tbody>
						  </table>
						</div><!-- /.box-body -->
					  </div><!-- /.box -->
					</div><!-- /.col -->
				  </div><!-- /.row -->
				  <div class="row">
				  	<div class="col-xs-12">
				  	  <div class="box">
				  	    <div class="box-header">
		                  <h3 class="box-title"><b>전송 상태</b></h3>
					    </div><!-- /.box-header -->
				  	    <div class="box-body table-responsive no-padding">
						  <table class="table">
							<colgroup>
							  <col width="15%" />
							  <col width="35%" />
							  <col width="15%" />
							  <col width="35%" />
							</colgroup>
							<tbody>
							  <tr>
							  	<th>요청상태</th>
							  	<td><span class="badge ${'Y' eq sendMasterVO.pushResSts ? 'bg-green':'bg-red'}">${'Y' eq sendMasterVO.pushReqSts ? '요청':'미요청'}</span><c:if test="${'Y' eq sendMasterVO.pushReqSts}"><br/>(${sendMasterVO.pushReqDt})</c:if></td>
							  	<th>응답상태</th>
							  	<td><span class="badge ${'Y' eq sendMasterVO.pushResSts ? 'bg-blue':'bg-yellow'}">${'Y' eq sendMasterVO.pushResSts ? '응답':('N' eq sendMasterVO.pushResSts ? '미응답' : '알수없음')}</span></td>
						  	  </tr>
						  	</tbody>
						  </table>
					    </div><!-- /.box-body -->
					  </div><!-- /.box -->
				    </div><!-- /.col -->
				  </div><!-- /.row -->
				  <div class="row">
				  	<div class="col-xs-12">
					  <ul class="no-margin pull-right">
			  		    <a class="btn btn-block btn-default" href="javascript:cancelClick();">
			  		      <i class="fa fa-list"></i> 목록
			  		    </a>
			  		  </ul>
				  	</div><!-- /.col -->
				  </div><!-- /.row -->
				</section><!-- /.content -->
			  </div>
		</div>
	</form>
</body>
</html>