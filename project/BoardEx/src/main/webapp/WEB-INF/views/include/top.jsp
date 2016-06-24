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
      <a class="navbar-brand" href="#">Project name</a>
    </div>
    <div id="navbar" class="collapse navbar-collapse">
      <div class="navbar-form navbar-right">
        <c:choose>
        <c:when test="${sessionScope.loginVO == null}">
        <div class="form-group">
          <input type="text" placeholder="ID" class="form-control" required>
        </div>
        <div class="form-group">
          <input type="password" placeholder="Password" class="form-control" required>
        </div>
        <button type="button" id="loginBtn" class="btn btn-primary" onclick="javascript:loginClick()"><i class="fa fa-sign-in"></i> Login</button>
        <button type="button" id="joinBtn" class="btn btn-success" onclick="javascript:joinClick()"><i class="fa fa-star"></i> Join</button>
        </c:when>
        <c:otherwise>
        <button type="button" id="logoutBtn" class="btn btn-primary" onclick="javascript:joinClick()"><i class="fa fa-sign-out"></i> Logout</button>
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
