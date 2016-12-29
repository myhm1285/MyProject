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
});
</script>
</head>
<body>
  <form name="procForm" method="get">
    
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
                  <form id="boardModifyForm" role="form" data-toggle="validator">
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
                        <input name="name" class="form-control" value="" />
                      </td>
                      </tr>
                      <tr>
                      <th class="text-center">내용</th>
                      <td>
                        <div id="summernote"></div>
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
  </form>
</body>
</html>