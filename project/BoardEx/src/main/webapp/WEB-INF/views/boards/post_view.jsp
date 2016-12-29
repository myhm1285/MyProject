<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/views/include/jstl.jsp"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/views/include/head.jsp" />
<script type="text/javascript">
$(document).ready(function(){
	$.ajaxSetup({async:false});
	
	loadPostList(1);
	
	$(".panel-heading a").click(function(){
    	
        $(this).find(".glyphicon").toggleClass("glyphicon-chevron-up");
        $(this).find(".glyphicon").toggleClass("glyphicon-chevron-down");
    });

});
//상세 페이지이동
function viewPageMove(pg) {
	$.ajax({
        type: "GET",
        url: "/boards/" + "${boardVO.idx != 0 ? boardVO.name:'_ALL_'}" + "/find/idx?pg=" + pg,
        success:function(result) {
        	if(result != 0) {
        		location.href = "/boards/${boardVO.name}/" + result;
        	}
        }
      });
}
//게시글 목록 조회
function loadPostList(pg) {
	$.ajax({
        type: "GET",
        url: "/boards/" + "${boardVO.idx != 0 ? boardVO.name:'_ALL_'}" + "/ajax?pg="+pg,
        success:function(result) {
        	var postVOList = result.postVOList;
        	var postListHtml = "";
        	$.each(postVOList, function(idx, postVO){
        		postListHtml += "<tr>";
        		postListHtml += "<td class=\"text-center hidden-xs\">" + postVO.listNo + "</td>";
        		postListHtml += "<td><a href=\"/boards/${boardVO.name}/" + postVO.idx + "\">" + postVO.title + "</a>";
        		postListHtml += "</td>";
        		postListHtml += "<td class=\"text-center\">" +  postVO.writeDt + "</td>";
        		postListHtml += "</tr>";
        	});
        	if(postListHtml == "") {
        		postListHtml = "<tr><td class=\"text-center\" colspan=\"3\">게시글이 존재하지 않습니다.</td></tr>";
        	} else {
	       		$("ul[name='listPaging']").html(result.listPaging);
        	}
       		$("table[name='postListTable'] tbody").html(postListHtml);
        }
      });
}
</script>
</head>
<body>
  <form name="procForm" method="get">
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
                  <div class="panel-heading" style="border-bottom:0px">
                    <a data-toggle="collapse" href="#collapseTbodyList" aria-expanded="false" aria-controls="collapseTbodyList">
                    <span class="glyphicon glyphicon-chevron-down">목록</span>
                    </a>
                  </div>
                  <div class="box-body table-responsive no-padding collapse" id="collapseTbodyList">
                    <table class="table table-hover" name="postListTable">
                    <colgroup>
                      <col class="hidden-xs" width="10%" />
                      <col />
                      <col width="15%" />
                    </colgroup>
                    <thead class="hidden-xs" style="border-top:1px solid #ddd">
                      <tr>
                      <th class="text-center hidden-xs">#</th>
                      <th class="text-center"></th>
                      <th class="text-center">DATE</th>
                      </tr>
                    </thead>
                    <tbody>
                    </tbody>
                    </table>
                    <div class="clearfix text-center">
                      <ul class="pagination pagination-sm no-margin" name="listPaging" />
                    </div>
                  </div><!-- /box-body -->
                </div>
              </div><!-- /col -->
            </div><!-- /row -->
          </div><!-- /content-header -->
          <div class="content">
            <div class="row">
              <div class="col-xs-12">
                <div class="panel panel-default">
                  <div class="panel-heading">
                    <h4 style="margin:5px 0px;"><strong><a href="/boards/${postVO.boardName}/${postVO.idx}">${postVO.title}</a></strong><sub style="margin-left:10px;color:#777;">| ${postVO.boardName}</sub></h4>
                    <div class="text-right" style="font-weight:normal; color:#888;">${postVO.writeDt}</div>
                  </div>
                  <div class="panel-body">
                    ${postVO.content}
                  </div><!-- /box-body -->
                  <div class="panel-footer">
                    <div><strong>Comments</strong> (${postVO.commentCnt})</div>
                  </div>
                </div>
                <div class="clearfix text-center">
                  <ul class="pagination pagination-sm no-margin">
                    <c:if test="${postVO != null}">${viewPaging}</c:if>
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