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
  f.action = "/board/boardList.do.do";
  f.submit();
}
// 검색
function searchClick() {
  var f = document.procForm;
  f.pg.value = 1;
  f.action = "/board/boardList.do.do";
  f.submit();
}
// 초기화
function searchResetClick() {
  location.href = "/board/boardList.do.do";
}
// 등록
function writeClick() {
  location.href = "/board/boardWrite.do";
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
<body>
  <form name="procForm" method="post">
    <input type="hidden" name="pg" value="1"/>
    <input type="hidden" name="searchOsType" value=""/>
    <input type="hidden" name="searchPushReqSts" value=""/>
    
    <jsp:include page="/WEB-INF/views/include/top.jsp"/>
    
    <div class="container">
      <div class="row row-offcanvas row-offcanvas-left">
      <jsp:include page="/WEB-INF/views/include/left.jsp"/>
        <div class="col-xs-12 col-sm-9">
          <p class="pull-left visible-xs">
            <button type="button" class="btn btn-primary btn-xs" data-toggle="offcanvas">Toggle nav</button>
          </p>
          <div class="content-header">
            <h1 class="page-header">게시판<small>목록</small></h1>
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
                    <div class="btn-group" style="margin:5px;">
                        <button type="button" class="btn btn-default">OS 타입</button>
                        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
                          <span class="caret"></span>
                          <span class="sr-only">Toggle Dropdown</span>
                        </button>
                        <ul class="dropdown-menu" role="menu">
                          <li><a href="javascript:searchOsType('')">전체</a></li>
                          <li class="divider"></li>
                          <li class=""><a href="javascript:searchOsType('A')">안드로이드</a></li>
                          <li class=""><a href="javascript:searchOsType('I')">IOS</a></li>
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
                          <li class=""><a href="javascript:searchPushReqSts('Y')">요청</a></li>
                          <li class=""><a href="javascript:searchPushReqSts('N')">미요청</a></li>
                        </ul>
                    </div>
                    <div class="input-group" style="max-width:300px;margin:5px;">
                      <input type="text" class="form-control" name="searchMsgContent" value="" placeholder="메시지 내용">
                      <div class="input-group-btn">
                           <button class="btn btn-default" onclick="javascript:searchClick()"><i class="fa fa-search"></i></button>
                         </div>
                    </div><!-- /input-group -->
                  </div>
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
                      <tr>
                      <td class="text-center">1</td>
                      <td><a href="/board/boardView.do?sendIdx=1">메시지테스트1</a></td>
                      <td class="text-center">안드로이드</td>
                      <td class="text-center">2016-05-31</td>
                      <td class="text-center"><span class="label label-success">요청</span></td>
                      <td class="text-center"><span class="label label-primary">응답</span></td>
                      </tr>
                      <tr>
                      <td class="text-center">2</td>
                      <td><a href="/board/boardView.do?sendIdx=2">메시지테스트2</a></td>
                      <td class="text-center">아이폰</td>
                      <td class="text-center">2016-05-31</td>
                      <td class="text-center"><span class="label label-danger">미요청</span></td>
                      <td class="text-center"><span class="label label-warning">미응답</span></td>
                      </tr>
                      <tr>
                      <td class="text-center">3</td>
                      <td><a href="/board/boardView.do?sendIdx=1">메시지테스트1</a></td>
                      <td class="text-center">안드로이드</td>
                      <td class="text-center">2016-05-31</td>
                      <td class="text-center"><span class="label label-success">요청</span></td>
                      <td class="text-center"><span class="label label-primary">응답</span></td>
                      </tr>
                      <tr>
                      <td class="text-center">4</td>
                      <td><a href="/board/boardView.do?sendIdx=2">메시지테스트2</a></td>
                      <td class="text-center">아이폰</td>
                      <td class="text-center">2016-05-31</td>
                      <td class="text-center"><span class="label label-danger">미요청</span></td>
                      <td class="text-center"><span class="label label-warning">미응답</span></td>
                      </tr>
                      <tr>
                      <td class="text-center">5</td>
                      <td><a href="/board/boardView.do?sendIdx=1">메시지테스트1</a></td>
                      <td class="text-center">안드로이드</td>
                      <td class="text-center">2016-05-31</td>
                      <td class="text-center"><span class="label label-success">요청</span></td>
                      <td class="text-center"><span class="label label-primary">응답</span></td>
                      </tr>
                      <tr>
                      <td class="text-center">6</td>
                      <td><a href="/board/boardView.do?sendIdx=2">메시지테스트2</a></td>
                      <td class="text-center">아이폰</td>
                      <td class="text-center">2016-05-31</td>
                      <td class="text-center"><span class="label label-danger">미요청</span></td>
                      <td class="text-center"><span class="label label-warning">미응답</span></td>
                      </tr>
                      <tr>
                      <td class="text-center">7</td>
                      <td><a href="/board/boardView.do?sendIdx=1">메시지테스트1</a></td>
                      <td class="text-center">안드로이드</td>
                      <td class="text-center">2016-05-31</td>
                      <td class="text-center"><span class="label label-success">요청</span></td>
                      <td class="text-center"><span class="label label-primary">응답</span></td>
                      </tr>
                      <tr>
                      <td class="text-center">8</td>
                      <td><a href="/board/boardView.do?sendIdx=2">메시지테스트2</a></td>
                      <td class="text-center">아이폰</td>
                      <td class="text-center">2016-05-31</td>
                      <td class="text-center"><span class="label label-danger">미요청</span></td>
                      <td class="text-center"><span class="label label-warning">미응답</span></td>
                      </tr>
                      <tr>
                      <td class="text-center">9</td>
                      <td><a href="/board/boardView.do?sendIdx=1">메시지테스트1</a></td>
                      <td class="text-center">안드로이드</td>
                      <td class="text-center">2016-05-31</td>
                      <td class="text-center"><span class="label label-success">요청</span></td>
                      <td class="text-center"><span class="label label-primary">응답</span></td>
                      </tr>
                      <tr>
                      <td class="text-center">10</td>
                      <td><a href="/board/boardView.do?sendIdx=2">메시지테스트2</a></td>
                      <td class="text-center">아이폰</td>
                      <td class="text-center">2016-05-31</td>
                      <td class="text-center"><span class="label label-danger">미요청</span></td>
                      <td class="text-center"><span class="label label-warning">미응답</span></td>
                      </tr>
                    </tbody>
                    </table>
                  </div><!-- /box-body -->
                </div>
                <div class="clearfix text-center">
                  <ul class="pagination pagination-sm no-margin">
                    ${paging}
                  </ul>
                </div>
              </div><!-- /col -->
            </div><!-- /row -->
          </div><!-- /content -->
        </div><!-- /col-xs-12 col-sm-9 -->
      </div><!-- /row -->
    </div><!-- /container -->
  </form>
</body>
</html>