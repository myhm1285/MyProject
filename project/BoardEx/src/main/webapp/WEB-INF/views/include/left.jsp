<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/views/include/jstl.jsp"%>
<!-- sidebar -->
<div class="col-xs-6 col-sm-3 sidebar-offcanvas" id="sidebar">
  <ul class="list-group">
    <li class="list-group-item">
      <h4 class="list-group-item-heading" data-toggle="collapse" href="#collapse1" aria-expanded="false" aria-controls="collapse1"><i class="fa fa-table" style="margin-right:10px"></i><span>게시판</span><i class="fa fa-angle-left pull-right"></i></h4>
      <p class="list-group-item-text collapse ${cs_uri:contains(pageContext.request,'/board/board')?'in':''}" id="collapse1">
        <a href="/board/boardList.do" class="${cs_uri:contains(pageContext.request,'/board/board')?'active':''}"><i class="fa fa-circle-o" style="margin-right:10px"></i>게시판</a>
        <a href="/board/boardList.do" class="${cs_uri:contains(pageContext.request,'/board/board1')?'active':''}"><i class="fa fa-circle-o" style="margin-right:10px"></i>게시판</a>
        <a href="/board/boardList.do" class="${cs_uri:contains(pageContext.request,'/board/board2')?'active':''}"><i class="fa fa-circle-o" style="margin-right:10px"></i>게시판</a>
      </p>
    </li>
    <li class="list-group-item">
      <h4 class="list-group-item-heading" data-toggle="collapse" href="#collapse2" aria-expanded="false" aria-controls="collapse2"><i class="fa fa-pie-chart" style="margin-right:10px"></i><span>통계</span><i class="fa fa-angle-left pull-right"></i></h4>
      <p class="list-group-item-text collapse ${cs_uri:contains(pageContext.request,'/statis/statis')?'in':''}" id="collapse2">
        <a href="/board/boardList.do" class="${cs_uri:contains(pageContext.request,'/statis/statis')?'active':''}"><i class="fa fa-circle-o" style="margin-right:10px"></i>통계</a>
        <a href="/board/boardList.do" class="${cs_uri:contains(pageContext.request,'/statis/statis1')?'active':''}"><i class="fa fa-circle-o" style="margin-right:10px"></i>통계</a>
        <a href="/board/boardList.do" class="${cs_uri:contains(pageContext.request,'/statis/statis2')?'active':''}"><i class="fa fa-circle-o" style="margin-right:10px"></i>통계</a>
      </p>
    </li>
  </ul>
</div>
<!-- /.sidebar -->