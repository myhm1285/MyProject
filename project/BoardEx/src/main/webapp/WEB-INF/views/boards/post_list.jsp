<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/views/include/jstl.jsp"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/views/include/head.jsp" />
<script type="text/javascript">
$(document).ready(function(){
	$.ajaxSetup({async:false});
	
	loadPostList();

});
//상세목록 페이지이동
function viewListPageMove(pg) {
  var f = document.procForm;
  f.pg.value = pg;
  f.action = "/board/boardList.do.do";
  f.submit();
}
//목록 페이지이동
function listPageMove(pg) {
  var f = document.procForm;
  f.pg.value = pg;
  f.action = "/board/boardList.do.do";
  f.submit();
}
//게시글 목록 조회
function loadPostList() {
	$.ajax({
        type: "GET",
        url: "/boards/" + "${boardVO.name}" + "/ajax",
        success:function(result) {
        	var postVOList = result.postVOList;
        	var postListHtml = "";
        	$.each(postVOList, function(idx, postVO){
        		postListHtml += "<tr>";
        		postListHtml += "<td class=\"text-center hidden-xs\">" + postVO.listNo + "</td>";
        		postListHtml += "<td>";
        		postListHtml += "<td><a href=\"/boards/${boardVO.name}/" + postVO.idx + "\">" + postVO.title + "</a>";
        		postListHtml += "</td>";
        		postListHtml += "<td class=\"text-center\">" +  postVO.writeDt + "</td>";
        		postListHtml += "</tr>";
        	});
       		$("table[name='postListTable'] tbody").html(postListHtml);
       		$("ul[name='listPaging']").html(result.listPaging);
        }
      });
}
</script>
</head>
<body>
  <form name="procForm" method="post">
    <input type="hidden" name="pg" value="1"/>
    
    <jsp:include page="/WEB-INF/views/include/top.jsp"/>
    
    <div class="container">
      <div class="row row-offcanvas row-offcanvas-left">
      <jsp:include page="/WEB-INF/views/include/left.jsp"/>
        <div class="col-xs-12 col-sm-9">
          <p class="pull-left visible-xs">
            <button type="button" class="btn btn-primary btn-xs" data-toggle="offcanvas">Toggle nav</button>
          </p>
          <div class="content-header">
            <h3 class="page-header">${boardVO.name} (${boardVO.postCnt})</h3>
            <div class="row">
              <div class="col-xs-12">
                <div class="panel panel-default">
                  <div class="box-body table-responsive no-padding">
                    <table class="table table-hover" name="postListTable">
                    <colgroup>
                      <col width="10%" />
                      <col />
                      <col width="15%" />
                    </colgroup>
                    <thead>
                      <tr>
                      <th class="text-center">No</th>
                      <th class="text-center"></th>
                      <th class="text-center"></th>
                      </tr>
                    </thead>
                    <tbody>
                    </tbody>
                    </table>
                  </div><!-- /box-body -->
                </div>
                <div class="clearfix text-center">
                  <ul class="pagination pagination-sm no-margin" name="listPaging">
                    ${paging}
                  </ul>
                </div>
              </div><!-- /col -->
            </div><!-- /row -->
          </div><!-- /content-header -->
          <div class="content">
            <div class="row">
              <div class="col-xs-12">
                <div class="panel panel-default">
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