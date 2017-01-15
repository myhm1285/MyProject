<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@include file="/WEB-INF/views/include/jstl.jsp"%>
<!-- top -->
<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="${definesVO.domain}">${definesVO.serviceIdentityName}</a>
    </div>
    <div id="navbar" class="collapse navbar-collapse">
      <div class="navbar-form navbar-right">
        <c:choose>
        <c:when test="${sessionScope.loginVO == null}">
        <button type="button" id="adminOnBtn" class="btn btn-success" onclick="javascript:adminModeOnClick()"><i class="glyphicon glyphicon-wrench"></i></button>
        </c:when>
        <c:otherwise>
        <b>ADMIN MODE ON</b>
        <button type="button" id="adminOffBtn" class="btn btn-danger" onclick="javascript:adminModeOffClick()"><i class="glyphicon glyphicon-remove"></i></button>
        </c:otherwise>
        </c:choose>
      </div>
    </div><!-- /.nav-collapse -->
  </div><!-- /.container -->
</nav><!-- /.navbar -->
<!-- //top end -->
<script type="text/javascript">
//로그인
function loginClick() {
	top.location.href = "/login/loginProc.do";
}
//회원가입
function joinClick() {
	top.location.href = "/login/loginProc.do";
}
//로그아웃
function logoutClick() {
	top.location.href = "/login/loginProc.do";
}
</script>
