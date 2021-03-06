<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/views/include/jstl.jsp"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="/WEB-INF/views/include/head.jsp" />
<script type="text/javascript">
$(document).ready(function(){
	$.ajaxSetup({async:false});
	
    $(".panel-heading a").click(function(){
    	
    	$("#collapseTwo").collapse("hide");
    	
        $(this).find(".glyphicon").toggleClass("glyphicon-chevron-up");
        $(this).find(".glyphicon").toggleClass("glyphicon-chevron-down");
    });
    
    $("#confirmModal").on("show.bs.modal", function (event) {
    	if($(event.relatedTarget).hasClass("disabled")) {
    		return false;
    	}
    	$("#modalType").val($(event.relatedTarget).data('type'));
    	// 내용
    	$(this).find(".modal-body").text($(event.relatedTarget).data('content'));
    });
   	// 버튼 이벤트
   	$("#confirmModal").find(".modal-footer").find("button").eq(1).on("click",function(event){
   		var type = $("#modalType").val();
   		
   		if (type == "write") {
       		boardWriteClick();
       	} else if (type == "modify") {
       		boardModifyClick();
       	} else if (type == "delete") {
       		boardDeleteClick();
       	}
   	});
    
   	// 게시판 목록 조회
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
        		boardListHtml += "<td class=\"text-center\"><button type=\"button\" class=\"btn btn-default\" onclick=\"javascript:loadBoardView('" + boardVO.idx + "')\">편집</button></td>";
        		boardListHtml += "</tr>";
        	});
       		$("table[name='boardListTable'] tbody").html(boardListHtml);
        }
      });
}
//게시판 상세 조회
function loadBoardView(idx) {
	var form = $("#boardModifyForm");
	
	if($("#collapseOne").hasClass("in")){
		closeClick("write");
	}
	$("#collapseTwo").collapse("show");
	
	$.ajax({
        type: "GET",
        url: "/manage/board/"+idx,
        success:function(result) {
        	var boardVO = result.boardVO;
        	$(form).find("input[name='idx']").val(boardVO.idx);
        	$(form).find("input[name='isCheckName']").val("N");
        	$(form).find("input[name='name']").val(boardVO.name);
        	$(form).find("textarea[name='note']").val(boardVO.note);
        	$(form).find("select[name='pageCnt'] option[value='" + boardVO.pageCnt + "']").prop("selected",true);
        	$(form).find("select[name='isOpen'] option[value='" + boardVO.isOpen + "']").prop("selected",true);
        	$(form).find("label[name='postCnt']").text(boardVO.postCnt);
        	$(form).find("label[name='writeDt']").text(boardVO.writeDt);
        	$(form).find("label[name='modifyDt']").text(boardVO.modifyDt);
        }
	});
}
//게시판 중복 조회
function checkBoardName(name) {
	$.ajax({
        type: "GET",
        url: "/manage/board/find/"+name,
        success:function(result) {
			// 중복아님
        	if(result == "N"){
				alert("사용하실 수 있는 이름입니다.");        		
        		$(form).find("input[name='isCheckName']").val("N");
			}
			// 중복
			else {
				alert("이미 사용중인 이름입니다. 새로운 이름을 입력해 주세요.");
			}
        }
	});
}
// 등록
function boardWriteClick() {
	var form = $("#boardWriteForm");
	var params = {
		"name":$(form).find("input[name='name']").val(),
		"note":$(form).find("textarea[name='note']").val(),
		"pageCnt":$(form).find("select[name='pageCnt'] option:selected").val(),
		"isOpen":$(form).find("select[name='isOpen'] option:selected").val()
	};
	$.ajax({
		type:"post",
		url:"/manage/board",
		contentType:"application/json; charset=utf-8",
		data:JSON.stringify(params),
		success:function(result){
			if(result == "Y"){
				alert("게시판이 추가되었습니다.");
				// 초기화
				closeClick("write");
				// 게시판 목록 로드
				loadBoardList();
			} else {
				alert("게시판 추가에 실패하였습니다.");
			}
		},
		error:function(result){
			alert("게시판 추가에 실패하였습니다.");
		}
	});
}
// 수정
function boardModifyClick() {
	var form = $("#boardModifyForm");
	var params = {
		"name":$(form).find("input[name='name']").val(),
		"note":$(form).find("textarea[name='note']").val(),
		"pageCnt":$(form).find("select[name='pageCnt'] option:selected").val(),
		"isOpen":$(form).find("select[name='isOpen'] option:selected").val()
	};
	$.ajax({
		type:"put",
		url:"/manage/board/"+$(form).find("input[name='idx']").val(),
		contentType:"application/json; charset=utf-8",
		data:JSON.stringify(params),
		success:function(result){
			if(result == "Y"){
				alert("게시판 정보가 수정되었습니다.");
				// 초기화
				closeClick("modify");
				loadBoardList();
			} else {
				alert("게시판 정보 수정에 실패하였습니다.");
			}
		},
		error:function(result){
			alert("게시판 정보 수정에 실패하였습니다.");
		}
	});
}
// 삭제
function boardDeleteClick() {
	var form = $("#boardModifyForm");
	$.ajax({
		type:"delete",
		url:"/manage/board/"+$(form).find("input[name='idx']").val(),
		contentType:"application/json; charset=utf-8",
		success:function(result){
			if(result == "Y"){
				alert("게시판가 삭제되었습니다.");
				
				// 초기화
				closeClick("modify");
				loadBoardList();
			} else {
				alert("게시판 삭제에 실패하였습니다.");
			}
		},
		error:function(result){
			alert("게시판 삭제에 실패하였습니다.");
		}
	});
}
// 닫기
function closeClick(func) {
	if(func == "write"){
		init($("#boardWriteForm"));
		
		$(".panel-heading a").find(".glyphicon").toggleClass("glyphicon-chevron-up");
        $(".panel-heading a").find(".glyphicon").toggleClass("glyphicon-chevron-down");
		
        $("#collapseOne").collapse("hide");
	} else if(func == "modify"){
		init($("#boardModifyForm"));
		
		$("#collapseTwo").collapse("hide");
		
	}
}
// 초기화
function init(form) {
	$(form).find("input, textarea").val("");
	$(form).find("label").text("");
	$(form).find("input[name='isCheckName']").val("N");
	$(form).find("select[name='pageCnt'] option[value='']").prop("selected",true);
	$(form).find("select[name='isOpen'] option[value='Y']").prop("selected",true);
}

//이름 입력시
function nameUpdateEvent(obj) {
	$(obj).val(($(obj).val()).replace(/ /g,"_"));
}
//alert Modal
function alert(message) {
	$("#confirmModal").modal("hide");
	$("#alertModal").find(".modal-body").text(message);
	$("#alertModal").modal();
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
                    <table name="boardListTable" class="table table-hover" style="margin-bottom:0px;">
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
                    </tbody>
                    </table>
                  </div><!-- /box-body -->
                </div>
              </div>
            </div>
          </div>
          <div class="content">
            <div class="row">
              <div class="col-xs-12">
                <div class="panel-group" role="tablist" aria-multiselectable="false">
                <div class="panel panel-default">
                  <div class="panel-heading" role="tab" id="headingOne">
                    <a data-toggle="collapse" href="#collapseOne" aria-expanded="false" aria-controls="collapseOne">
                       등록<span class="glyphicon glyphicon-chevron-down" aria-hidden="true" style="margin:5px; 0px;"></span>
                    </a>
                  </div>
                  <div id="collapseOne" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                  <div class="box-body table-responsive no-padding">
                  <form id="boardWriteForm" role="form" data-toggle="validator">
                    <table class="table table-hover" style="margin-bottom:0px;">
                    <colgroup>
                      <col width="15%" />
                      <col />
                    </colgroup>
                    <tbody>
                      <tr>
                      <th class="text-center">이름</th>
                      <td>
                        <div class="form-group has-feedback col-lg-8" style="padding:0px;">
                          <div class="input-group">
                            <input name="name" class="form-control" value="" placeholder="유일한 이름을 입력하세요." onkeyup="javascript:nameUpdateEvent(this)" required/>
                            <span class="input-group-btn">
                            <button type="button" class="btn btn-primary">중복체크</button>
                            <input type="hidden" name="isCheckName" value="N" />
                            </span>
                          </div>
                          <div class="help-block with-errors" id="nameHelpBlock">이름 입력 후 중복체크를 해 주세요.</div>
                        </div>
                      </td>
                      </tr>
                      <tr>
                      <th class="text-center">설명</th>
                      <td>
                        <div class="form-group has-feedback">
                          <textarea name="note" class="form-control" rows="3" required></textarea>
                          <div class="help-block with-errors"></div>
                        </div>
                      </td>
                      </tr>
                      <tr>
                      <th class="text-center" name="pageCnt">페이지당<br/>글수</th>
                      <td>
                        <div class="col-md-4 col-xs-6" style="padding:0px;">
                          <select name="pageCnt" class="form-control" min="5">
                            <option value="5">5</option>
                            <option value="10" selected>10</option>
                            <option value="20">20</option>
                            <option value="50">50</option>
                          </select>
                        </div>
                      </td>
                      </tr>
                      <tr>
                      <th class="text-center">공개여부</th>
                      <td>
                        <div class="form-group col-md-4 col-xs-6" style="padding:0px;">
                          <select name="isOpen" class="form-control">
                            <option value="Y">공개</option>
                            <option value="N">비공개</option>
                          </select>
                        </div>
                      </td>
                      </tr>
                    </tbody>
                    </table>
                    <div class="form-group text-center">
                      <button type="submit" class="btn btn-primary" data-toggle="modal" data-target="#confirmModal" data-type="write" data-content="등록하시겠습니까?">등록</button>
                      <button type="button" class="btn btn-default" onclick="javascript:closeClick('write')">닫기</button>
                    </div>
                  </form>
                  </div><!-- /box-body -->
                  </div><!-- /collapseOne -->
                </div>
                <div class="panel panel-default">
                  <div class="panel-heading" role="tab" id="headingTwo">
                    조회 및 수정
                  </div>
                  <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                  <div class="box-body table-responsive no-padding">
                  <form id="boardModifyForm" role="form" data-toggle="validator">
                  <input type="hidden" name="idx" value="0" />
                    <table class="table table-hover"  style="margin-bottom:0px;">
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
                            <input name="name" class="form-control" value="" placeholder="유일한 이름을 입력하세요." onkeyup="javascript:nameUpdateEvent(this)" />
                            <span class="input-group-btn">
                            <button type="button" class="btn btn-primary">중복체크</button>
                            <input type="hidden" name="isCheckName" value="N" />
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
                    </form>
                  </div><!-- /box-body -->
                  <div class="panel-footer">
                    <div class="text-center">
                      <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#confirmModal" data-type="modify" data-content="수정하시겠습니까?">수정</button>
                      <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#confirmModal" data-type="delete" data-content="삭제하시겠습니까?">삭제</button>
                      <button type="button" class="btn btn-default" onclick="javascript:closeClick('modify')">닫기</button>
                    </div>
                  </div>
                  </div><!-- /collapseTwo -->
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