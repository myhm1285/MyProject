<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/views/include/jstl.jsp"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/views/include/head.jsp" />
<link rel="stylesheet" href="/css/summernote.css" />
<script type="text/javascript" src="/js/summernote.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$('#summernote').summernote({
		height: 300
	});
	
	$("#confirmModal").on("show.bs.modal", function (event) {
    	$("#modalType").val($(event.relatedTarget).data('type'));
    	// 내용
    	$(this).find(".modal-body").text($(event.relatedTarget).data('content'));
    });
	
	// 버튼 이벤트
   	$("#confirmModal").find(".modal-footer").find("button").eq(1).on("click",function(event){
		var type = $("#modalType").val();
   		
   		if (type == "write") {
   			postdWriteClick();
       	} else if (type == "modify") {
       		postModifyClick();
       	}
   	});
	
	//게시판 목록 조회
   	loadBoardList();
});
//게시판 목록 조회
function loadBoardList() {
	$.ajax({
        type: "GET",
        url: "/manage/board/ajax",
        data: {"isOpen":"Y"},
        success:function(result) {
        	var boardVOList = result.boardVOList;
        	$.each(boardVOList, function(idx, boardVO){
        		$("select[name='boardIdx']").append("<option value='" + boardVO.idx + "'>" + boardVO.name + "</option>");
        	});
        }
      });
}
//게시글 등록
function postdWriteClick(){
	var form = $("#postWriteForm");
	var params = {
		"boardIdx":$(form).find("select[name='boardIdx'] option:selected").val(),
		"title":$(form).find("input[name='title']").val(),
		"content":$("#summernote").summernote('code'),
		"note":$(form).find("textarea[name='note']").val()
	};
	$.ajax({
		type:"post",
		url:"/boards/"+$(form).find("select[name='boardIdx'] option:selected").text(),
		contentType:"application/json; charset=utf-8",
		data:JSON.stringify(params),
		success:function(result){
			if(result == "Y"){
				alert("게시글이 등록되었습니다.");
				location.href="/boards/"+$(form).find("select[name='boardIdx'] option:selected").text();
			} else {
				alert("게시판 추가에 실패하였습니다.");
			}
		},
		error:function(result){
			alert("게시판 추가에 실패하였습니다.\n\n"+JSON.stringify(result));
		}
	});
}
</script>
</head>
<body>
  <jsp:include page="/WEB-INF/views/include/top.jsp"/>
    <div class="container">
      <div class="row row-offcanvas row-offcanvas-left">
      <jsp:include page="/WEB-INF/views/include/left.jsp"/>
        <div class="col-xs-12 col-sm-9">
          <p class="pull-left visible-xs">
            <button type="button" class="btn btn-primary btn-xs" data-toggle="offcanvas">Toggle nav</button>
          </p>
          <div class="content-header">
            <h1 class="page-header">게시글<small>등록</small></h1>
          </div><!-- /content-header -->
          <div class="content">
            <div class="row">
              <div class="col-xs-12">
                <div class="panel panel-default">
                  <div class="panel-heading">등록</div>
                  <div class="box-body table-responsive no-padding">
                  <form id="postWriteForm" role="form" data-toggle="validator">
                  <input type="hidden" name="idx" value="0" />
                    <table class="table table-hover"  style="margin-bottom:0px;">
                    <colgroup>
                      <col width="15%" />
                      <col />
                    </colgroup>
                    <tbody>
                      <tr>
                      <th class="text-center">게시판</th>
                      <td>
                        <div class="col-md-4 col-xs-6" style="padding:0px;">
                          <select name="boardIdx" class="form-control">
                            <option value="">선택하세요.</option>
                          </select>
                        </div>
                      </td>
                      </tr>
                      <tr>
                      <th class="text-center">제목</th>
                      <td>
                        <input name="title" class="form-control" value="" />
                      </td>
                      </tr>
                      <tr>
                      <th class="text-center">내용</th>
                      <td>
                        <div id="summernote"></div>
                      </td>
                      </tr>
                      <tr>
                      <th class="text-center">비고</th>
                      <td>
                        <textarea name="note" class="form-control" row="3"></textarea>
                      </td>
                      </tr>
                    </tbody>
                    </table>
                    </form>
                  </div><!-- /box-body -->
                  <div class="panel-footer">
                    <div class="text-center">
                      <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#confirmModal" data-type="write" data-content="등록하시겠습니까?">등록</button>
                      <button type="button" class="btn btn-default" onclick="javascript:initClick()">초기화</button>
                    </div>
                  </div>
                </div>
              </div><!-- /col -->
            </div><!-- /row -->
          </div><!-- /content -->
        </div><!-- /col-xs-12 col-sm-9 -->
      </div><!-- /row -->
    </div><!-- /container -->
    <jsp:include page="/WEB-INF/views/include/bottom.jsp"/>
</body>
</html>