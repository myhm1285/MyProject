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
    location.href = "/board/boardList.do";
  }
</script>
</head>
<body>
  <form name="procForm" method="post">
    <jsp:include page="/WEB-INF/views/include/top.jsp"/>
    <div class="container">
      <div class="row row-offcanvas row-offcanvas-left">
      <jsp:include page="/WEB-INF/views/include/left.jsp"/>
        <div class="col-xs-12 col-sm-9">
          <p class="pull-left visible-xs">
            <button type="button" class="btn btn-primary btn-xs" data-toggle="offcanvas">Toggle nav</button>
          </p>
          <div class="content-header">
            <h1 class="page-header">게시판<small>상세</small></h1>
            <ol class="breadcrumb pull-right">
              <li><a href="#">HOME</a></li>
              <li><a href="#">게시판</a></li>
              <li class="active">게시판</li>
              </ol>
          </div><!-- /content-header -->
          <div class="content">
            <div class="row">
              <div class="col-xs-12">
                <div class="panel panel-default">
                  <div class="panel-heading">
                    <h3 class="box-title"><b>발송 정보</b></h3>
                  </div>
                  <div class="box-body table-responsive no-padding">
                    <table class="table table-hover">
                      <colgroup>
                        <col width="15%" />
                        <col />
                      </colgroup>
                      <tbody>
                        <tr>
                          <th>발송일시</th>
                          <td>2016-05-31</td>
                          </tr>
                        <tr>
                          <th>OS 타입</th>
                          <td><i class="fa fa-mobile-phone"></i> 안드로이드</td>
                          </tr>
                        <tr>
                          <th>대상 (사용자ID)</th>
                          <td>test1234</td>
                          </tr>
                        <tr>
                          <th>메시지 내용</th>
                          <td>메시지테스트1</td>
                        </tr>
                      </tbody>
                    </table>
                  </div><!-- /.box-body -->
                </div><!-- /.pannel -->
              </div><!-- /.col -->
            </div><!-- /.row -->
            <div class="row">
              <div class="col-xs-12">
                <div class="panel panel-default">
                  <div class="panel-heading">
                    <h3 class="box-title"><b>전송 상태</b></h3>
                  </div>
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
                          <td><span class="label label-success">요청</span><br/>(2016-05-30)</td>
                          <th>응답상태</th>
                          <td><span class="label label-primary">응답</span></td>
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
          </div><!-- /.content -->
    </div>
  </form>
</body>
</html>