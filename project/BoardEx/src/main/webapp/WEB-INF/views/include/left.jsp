<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/views/include/jstl.jsp"%>
<!-- sidebar -->
<div class="col-xs-6 col-sm-3 sidebar-offcanvas hidden-print" id="sidebar">
  <ul class="list-group">
    <li class="list-group-item">
      <h4 class="list-group-item-heading" data-toggle="collapse" href="#collapse1" aria-expanded="false" aria-controls="collapse1"><i class="fa fa-tv" style="margin-right:10px"></i><span>BOARD</span><i class="fa fa-angle-left pull-right"></i></h4>
      <p class="list-group-item-text collapse ${cs_uri:contains(pageContext.request,'/boards/')?'in':''}" id="collapse1">
        <c:forEach var="boardMenuVO" items="${boardMenuVOList}" varStatus="status">
        <a href="/boards/${boardMenuVO.name}" class="${boardMenuVO.menuOn ? 'active':''}"><i class="fa fa-caret-right" style="margin-right:10px"></i>${boardMenuVO.name} (${boardMenuVO.postCnt})</a>
        </c:forEach>
      </p>
    </li>
    <%--<c:if test="${'ADMIN' eq sessionScope.loginVO.right}"> --%>
    <li class="list-group-item">
      <h4 class="list-group-item-heading" data-toggle="collapse" href="#collapse2" aria-expanded="false" aria-controls="collapse2"><i class="fa fa-cog" style="margin-right:10px"></i><span>Setting</span><i class="fa fa-angle-left pull-right"></i></h4>
      <p class="list-group-item-text collapse ${cs_uri:contains(pageContext.request,'/manage/')?'in':''}" id="collapse2">
        <a href="/manage/board" class="${cs_uri:contains(pageContext.request,'/manage/board')?'active':''}"><i class="fa fa-caret-right" style="margin-right:10px"></i>게시판 관리</a>
        <a href="/manage/post" class="${cs_uri:contains(pageContext.request,'/manage/post')?'active':''}"><i class="fa fa-caret-right" style="margin-right:10px"></i>게시글 관리</a>
        <a href="/manage/account" class="${cs_uri:contains(pageContext.request,'/manage/account')?'active':''}"><i class="fa fa-caret-right" style="margin-right:10px"></i>계정 관리</a>
      </p>
    </li>
    <%--</c:if> --%>
  </ul>
  <div>
    <button type="button" class="btn btn-info" onclick="javascript:location.href='/manage/post/write'"><i class="glyphicon glyphicon-pencil" style="margin-right:10px"></i>게시글 등록</button>
  </div>
</div>
<!-- /.sidebar -->