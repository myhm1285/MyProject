<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/views/include/jstl.jsp"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/views/include/head.jsp" />
<script type="text/javascript">
$(document).ready(function(){
	$.ajaxSetup({async:false});
	loadBoardList();
});
// 게시판 목록 조회
function loadBoardList() {
	$.ajax({
        type: "GET",
        url: "/manage/board/ajax",
        success:function(result) {
        	var boardVOList = result.boardVOList;
        	var boardListHtml = "";
        	$.each(boardVOList, function(idx, boardVO){
        		boardListHtml += "<tr>";
        		boardListHtml += "<td class=\"text-center hidden-xs\">" + idx + "</td>";
        		boardListHtml += "<td>";
        		boardListHtml += "<span class=\"label " + (boardVO.isOpen == "Y" ? "label-primary":"label-danger") + "\">" + (boardVO.isOpen == 'Y' ? "ON":"OFF") + "</span>";
        		boardListHtml += "<label name=\"name\">" + boardVO.name + "</label>";
        		boardListHtml += "<br/> " + boardVO.note;
        		boardListHtml += "</td>";
        		boardListHtml += "<td class=\"text-center\">"+ boardVO.postCnt + " 개</td>";
        		boardListHtml += "<td class=\"text-center\"><button type=\"button\" class=\"btn btn-default\" onclick=\"javascript:modifyClick('" + boardVO.idx + "')\">편집</button></td>";
        		boardListHtml += "</tr>";
        	});
       		$("table[name='boardListTable'] tbody").html(boardListHtml);
        }
      });
}

// 등록
function boardWriteClick() {
	var form = $("#boardWriteForm");
	var params = {
		"name":$(form).find("input[name='name']").val(),
		"note":$(form).find("textarea[name='note']").val(),
		"pageCnt":$(form).find("select[name='pageCnt']").val(),
		"isOpen":$(form).find("select[name='isOpen']").val()
	};
	$.ajax({
		type:"put",
		dataType:"json",
		url:"/manage/board",
		param:JSON.stringify(params),
		contentType:"application/json; charset=utf-8",
		success:function(result){
			alert("게시판이 추가되었습니다.");
			alert(result);
			//loadBoardList();
		},
		error:function(result){
			alert("게시판 추가에 실패하였습니다.");
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
            <h1 class="page-header">게시판<small>관리</small></h1>
            <ol class="breadcrumb pull-right">
              <li><a href="#">HOME</a></li>
              <li><a href="#">Setting</a></li>
              <li class="active">게시판</li>
            </ol>
          </div><!-- /content-header -->
          <div class="content">
            <div class="row">
              <div class="col-xs-12">
                <div class="panel panel-default">
                  <div class="panel-heading">목록</div>
                  <div class="box-body table-responsive no-padding">
                    <table name="boardListTable" class="table table-hover">
                    <colgroup>
                      <col width="10%" />
                      <col />
                      <col width="15%" />
                      <col width="15%" />
                    </colgroup>
                    <thead>
                      <tr>
                      <th class="text-center hidden-xs">#</th>
                      <th class="text-center">이름</th>
                      <th class="text-center">글수</th>
                      <th class="text-center">-</th>
                      </tr>
                    </thead>
                    <tbody>
                      <tr>
                      <td class="text-center hidden-xs">1</td>
                      <td>
                        <span class="label label-primary">ON</span>
                        <label name="name">메시지테스트1</label>
                        <br/>테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트
                      </td>
                      <td class="text-center">100 개</td>
                      <td class="text-center"><button type="button" class="btn btn-default">편집</button></td>
                      </tr>
                      <tr>
                      <td class="text-center hidden-xs">2</td>
                      <td>
                        <span class="label label-primary">ON</span>
                        <label name="name">메시지테스트1</label>
                        <br/>테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트
                      </td>
                      <td class="text-center">100 개</td>
                      <td class="text-center"><button type="button" class="btn btn-default">편집</button></td>
                      </tr>
                      <tr>
                      <td class="text-center hidden-xs">3</td>
                      <td>
                        <span class="label label-primary">ON</span>
                        <label name="name">메시지테스트1</label>
                        <br/>테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트
                      </td>
                      <td class="text-center">100 개</td>
                      <td class="text-center"><button type="button" class="btn btn-default">편집</button></td>
                      </tr>
                      <tr>
                      <td class="text-center hidden-xs">-</td>
                      <td>
                        <span class="label label-danger">OFF</span>
                        <label name="name">메시지테스트1</label>
                        <br/>테스트테스트테스트테스트테스트테스트테스트테스트테스트테스트(사용안함)
                      </td>
                      <td class="text-center">2000 개</td>
                      <td class="text-center"><button type="button" class="btn btn-default">편집</button></td>
                      </tr>
                    </tbody>
                    </table>
                  </div><!-- /box-body -->
                </div>
              </div>
            </div>
          </div>
          <form name="boardWriteForm" id="boardWriteForm" method="put">
          <div class="content">
            <div class="row">
              <div class="col-xs-12">
                <div class="panel panel-default">
                  <div class="panel-heading">등록</div>
                  <input type="hidden" name="idx" />
                  <div class="box-body table-responsive no-padding">
                    <table class="table table-hover">
                    <colgroup>
                      <col width="15%" />
                      <col />
                    </colgroup>
                    <tbody>
                      <tr>
                      <th class="text-center">이름</th>
                      <td>
                        <div class="col-lg-8" style="padding:0px;">
                          <div class="input-group">
                            <input name="name" class="form-control" value="" placeholder="유일한 이름을 입력하세요." />
                            <span class="input-group-btn">
                            <button type="button" class="btn btn-primary">중복체크</button>
                            </span>
                          </div>
                        </div>
                      </td>
                      </tr>
                      <tr>
                      <th class="text-center">설명</th>
                      <td>
                        <textarea name="note" class="form-control" rows="3"></textarea>
                      </td>
                      </tr>
                      <tr>
                      <th class="text-center" name="pageCnt">페이지당<br/>글수</th>
                      <td>
                        <div class="col-md-4 col-xs-6" style="padding:0px;">
                          <select name="pageCnt" class="form-control">
                            <option value="">선택</option>
                            <option value="5">5</option>
                            <option value="10">10</option>
                            <option value="20">20</option>
                            <option value="50">50</option>
                          </select>
                        </div>
                      </td>
                      </tr>
                      <tr>
                      <th class="text-center">공개여부</th>
                      <td>
                        <div class="col-md-4 col-xs-6" style="padding:0px;">
                          <select name="isOpen" class="form-control">
                            <option value="Y">공개</option>
                            <option value="N">비공개</option>
                          </select>
                        </div>
                      </td>
                      </tr>
                    </tbody>
                    </table>
                  </div><!-- /box-body -->
                  <div class="panel-footer">
                    <div class="text-center">
                      <button type="button" class="btn btn-primary" onclick="javascript:boardWriteClick()">등록</button>
                      <button type="button" class="btn btn-primary">취소</button>
                    </div>
                  </div>
                </div>
              </div><!-- /col -->
            </div><!-- /row -->
          </div><!-- /content -->
          </form>
          <form name="boardModifyForm" method="post">
          <div class="content">
            <div class="row">
              <div class="col-xs-12">
                <div class="panel panel-default">
                  <div class="panel-heading">조회 및 수정</div>
                  <input type="hidden" name="idx" />
                  <div class="box-body table-responsive no-padding">
                    <table class="table table-hover">
                    <colgroup>
                      <col width="15%" />
                      <col />
                    </colgroup>
                    <tbody>
                      <tr>
                      <th class="text-center">이름</th>
                      <td>
                        <div class="col-lg-8" style="padding:0px;">
                          <div class="input-group">
                            <input name="name" class="form-control" value="" placeholder="유일한 이름을 입력하세요." />
                            <span class="input-group-btn">
                            <button type="button" class="btn btn-primary">중복체크</button>
                            </span>
                          </div>
                        </div>
                      </td>
                      </tr>
                      <tr>
                      <th class="text-center">설명</th>
                      <td>
                        <textarea name="note" class="form-control" rows="3"></textarea>
                      </td>
                      </tr>
                      <tr>
                      <th class="text-center" name="pageCnt">페이지당<br/>글수</th>
                      <td>
                        <div class="col-md-4 col-xs-6" style="padding:0px;">
                          <select name="pageCnt" class="form-control">
                            <option value="">선택</option>
                            <option value="5">5</option>
                            <option value="10">10</option>
                            <option value="20">20</option>
                            <option value="50">50</option>
                          </select>
                        </div>
                      </td>
                      </tr>
                      <tr>
                      <th class="text-center">공개여부</th>
                      <td>
                        <div class="col-md-4 col-xs-6" style="padding:0px;">
                          <select name="isOpen" class="form-control">
                            <option value="Y">공개</option>
                            <option value="N">비공개</option>
                          </select>
                        </div>
                      </td>
                      </tr>
                      <tr>
                      <th class="text-center">포스트 개수</th>
                      <td>
                        <label name="postCnt"></label>
                      </td>
                      </tr>
                      <tr>
                      <th class="text-center">생성일자</th>
                      <td>
                        <label name="writeDt"></label>
                      </td>
                      </tr>
                      <tr>
                      <th class="text-center">수정일자</th>
                      <td>
                        <label name="modifyDt"></label>
                      </td>
                      </tr>
                    </tbody>
                    </table>
                  </div><!-- /box-body -->
                  <div class="panel-footer">
                    <div class="text-center">
                      <button type="button" class="btn btn-primary">수정</button>
                      <button type="button" class="btn btn-primary">삭제</button>
                      <button type="button" class="btn btn-primary">취소</button>
                    </div>
                  </div>
                </div>
              </div><!-- /col -->
            </div><!-- /row -->
          </div><!-- /content -->
          </form>
        </div><!-- /col-xs-12 col-sm-9 -->
      </div><!-- /row -->
    </div><!-- /container -->
</body>
</html>