package com.test.board.common.service;

import java.net.URLDecoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.test.board.manage.service.BoardService;
import com.test.board.manage.vo.BoardVO;

/**
 * 서버의 AOP 처리를 위한 Aspect 클래스
 *
 * @author hmyoo
 * @since 2016. 11. 28.
 * @version 1.0
 */
@Aspect
@Component
public class CommonAspect {

	@Autowired
	HttpSession session;

	@Autowired
	HttpServletRequest request;

	/** 게시판 Service */
	@Resource(name = "boardService")
	private BoardService boardService;

	/*
	 * 컨트롤러 메소드 프인트컷
	 * 
	 * 모든 2차 패키지 (ex : web > admin(1차) > basic(2차))의 컨트롤러 패키지안의 모든 클래스의 형태에 관계 없이 ModelAndView를 리턴한다면 포인트컷으로 잡는다.
	 */
	// @Pointcut("execution(public org.springframework.web.servlet.ModelAndView com.test.board.*.controller..*(..))")
	@Pointcut("execution(public String com.test.board.*.controller..*(org.springframework.ui.ModelMap, ..))")
	private void controllerMethod() {
	}

	/*
	 * 익셉션 로그인 어노테이션 포인트컷
	 * 
	 * 모든 @ExceptionLogin 어노테이션을 포인트컷으로 잡는다.
	 */
	// @Pointcut("@annotation(com.bangbang.web.aop.ExceptLogin)")
	private void exceptLogin() {
	}

	// @Pointcut("@annotation(com.bangbang.web.aop.BoardLogicModel)")
	private void boardLogic() {
	}

	@Pointcut("@annotation( com.test.board.boards.service.AdminMenu)")
	private void adminMenu() {
	}

	/**
	 * 컨트롤러 메소드 프인트컷을 만족하는 메소드<br/>
	 * 메뉴의 게시판 목록을 불러온다
	 * 
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("controllerMethod()")
	public String menuFunction(ProceedingJoinPoint joinPoint) throws Throwable {
		ModelMap model = (ModelMap) joinPoint.getArgs()[0];
		BoardVO boardVO = new BoardVO();
		boardVO.setIsOpen("Y");
		List<BoardVO> boardMenuVOList = boardService.selectBoardList(boardVO);
		String requestUri = URLDecoder.decode(request.getRequestURI(), "UTF-8");
		for (BoardVO boardMenuVO : boardMenuVOList) {
			if (requestUri.equals("/boards/" + boardMenuVO.getName()) || requestUri.indexOf("/boards/" + boardMenuVO.getName() + "/") != -1) {
				boardMenuVO.setMenuOn(true);
			}
		}
		model.addAttribute("boardMenuVOList", boardMenuVOList);

		// // 부여된 세션으로 부터 로그인 객체를 얻는다.
		// LoginInfoVo loginInfo = (LoginInfoVo) this.session.getAttribute("loginInfo");
		//
		// // 세션에 정보가 있다면 메뉴와 헤드를 셋팅한다.
		// if (loginInfo != null) {
		// /*
		// * joinPoint를 기점으로 실제컨트롤러의 실행시점을 구분 실행 후 반환되는 ModelAndView를 가로챈다.
		// */
		// ModelAndView mav = (ModelAndView) joinPoint.proceed();
		// List<MenuInfoVo> menus = this.aspectService.selectMenuListByGrant(loginInfo.getUserId());
		//
		// // left 셋팅영역
		// mav.addObject(menus);
		//
		// // top 셋팅영역
		// TopInfoVo top = defines.getAdminTop();
		// top.setLoginUserName(loginInfo.getUserName());
		// top.setLogoUrl((String) this.session.getAttribute("opinnigPage"));
		// mav.addObject(top);
		// return mav;
		// }
		// // 세션정보가 없다면 로그인페이지로 보낸다.
		// else {
		// ModelAndView mav = new ModelAndView(defines.getLoginPage());
		// return mav;
		// }
		return (String) joinPoint.proceed();
	}

	/**
	 * 관리자 세션 체크
	 * 
	 * @param joinPoint
	 * @return
	 * @throws Throwable
	 */
	@Around("adminMenu()")
	public String adminCheck(ProceedingJoinPoint joinPoint) throws Throwable {
		// // 부여된 세션으로 부터 로그인 객체를 얻는다.
		// LoginInfoVo loginInfo = (LoginInfoVo) this.session.getAttribute("loginInfo");
		//
		// // 세션에 정보가 있다면 메뉴와 헤드를 셋팅한다.
		// if (loginInfo != null) {
		// /*
		// * joinPoint를 기점으로 실제컨트롤러의 실행시점을 구분 실행 후 반환되는 ModelAndView를 가로챈다.
		// */
		// ModelAndView mav = (ModelAndView) joinPoint.proceed();
		// List<MenuInfoVo> menus = this.aspectService.selectMenuListByGrant(loginInfo.getUserId());
		//
		// // left 셋팅영역
		// mav.addObject(menus);
		//
		// // top 셋팅영역
		// TopInfoVo top = defines.getAdminTop();
		// top.setLoginUserName(loginInfo.getUserName());
		// top.setLogoUrl((String) this.session.getAttribute("opinnigPage"));
		// mav.addObject(top);
		// return mav;
		// }
		// // 세션정보가 없다면 로그인페이지로 보낸다.
		// else {
		// ModelAndView mav = new ModelAndView(defines.getLoginPage());
		// return mav;
		// }
		return (String) joinPoint.proceed();
	}

	/*
	 * 게시판용 컨트롤러를 처리한다.
	 */
	// @Around("boardLogic()")
	public ModelAndView boardProcessing(ProceedingJoinPoint joinPoint) throws Throwable {

		// BaseVO base = (BaseVO) joinPoint.getArgs()[0];
		// /*
		// * joinPoint를 기점으로 실제컨트롤러의 실행시점을 구분 실행 후 반환되는 ModelAndView를 가로챈다.
		// */
		// ModelAndView mav = (ModelAndView) joinPoint.proceed();
		//
		// String uri = request.getRequestURI();
		//
		// PagingBean pagingInfo = PagingUtil.setPagingInfo(base.getTotalCnt(), base.getPageNo(), base.getCntPerPage());
		// pagingInfo.setTargetUri(uri);
		//
		// mav.addObject(pagingInfo);
		//
		// return mav;
		return null;
	}

}