<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/views/include/jstl.jsp"%>
<!-- sidebar: style can be found in sidebar.less -->
<section class="sidebar">
  <!-- sidebar menu: : style can be found in sidebar.less -->
  <ul class="sidebar-menu">
    <li class="header">MENU</li>
    <li class="${cs_uri:contains(pageContext.request,'/history/history')?'active':''}">
      <a href="#"><i class="fa fa-table"></i> <span>이력</span><i class="fa fa-angle-left pull-right"></i></a>
      <ul class="treeview-menu">
        <li class="${cs_uri:contains(pageContext.request,'/history/history')?'active':''}"><a href="/history/historyList.do"><i class="fa fa-circle-o"></i> 메시지 이력</a></li>
      </ul>
    </li>
    <li class="treeview ${cs_uri:contains(pageContext.request,'/statis/statis')?'active':''}">
      <a href="#"><i class="fa fa-pie-chart"></i> <span>통계</span><i class="fa fa-angle-left pull-right"></i></a>
      <ul class="treeview-menu">
        <li><a href="/statis/statisView.do"><i class="fa fa-circle-o"></i> 통계</a></li>
        <li><a href="/statis/statisView.do"><i class="fa fa-circle-o"></i> 통계</a></li>
        <li><a href="/statis/statisView.do"><i class="fa fa-circle-o"></i> 통계</a></li>
      </ul>
    </li>
  </ul>
</section>
<!-- /.sidebar -->
<%--
<!-- left -->
<div class="leftWrap">
	<div class="column">
		<div class="portlet">
			<div class="portlet-header ${cs_uri:contains(pageContext.request,'/history/history')?'on':''}">
				<a href="/history/historyList.do">이력</a>
			</div>
			<div class="portlet-content" style="display: block;">
				<ul>
					<li class="${cs_uri:contains(pageContext.request,'/history/history')?'on':''}"><a href="/history/historyList.do">이력</a></li>
				</ul>
			</div>
		</div>
	</div>
	<div class="column">
		<div class="portlet">
			<div class="portlet-header ${cs_uri:contains(pageContext.request,'/statis/statis')?'on':''}">
				<a href="/statis/statisView.do">통계</a>
			</div>
			<div class="portlet-content" style="display: block;">
				<ul>
					<li class="last ${cs_uri:contains(pageContext.request,'/statis/statis')?'on':''}"><a href="/statis/statisView.do">통계</a></li>
				</ul>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(document).ready(function(e) {
		$('div.portlet-content').each(function(){
			if( $(this).css("display") == "block" ) {
				$(this).parent().find('.portlet-toggle').removeClass('ui-icon-minusthick').addClass('ui-icon-plusthick');
			}
		});
	});
</script>
<!-- //left end -->

 --%>
