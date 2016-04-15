package com.itw.test.contorller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itw.test.service.TestService;
import com.itw.test.service.TestVO;
import com.itw.util.HttpClientUtil;

/**
 * @Class Name : TestController.java
 * @Description : 로그인 Controller 클래스
 * 
 * @author 컨버전스스퀘어
 * @since 2014.08.01
 * @version 1.0
 */
@Controller
public class TestController {
	/** 로거 */
	private final Logger logger = LoggerFactory.getLogger(TestController.class);

	@Resource(name = "testService")
	private TestService testService;

	@RequestMapping(value = "/test.do")
	public String test(@ModelAttribute("TestVO") TestVO TestVO, ModelMap model, HttpServletRequest request) throws Exception {
		// vo
		model.addAttribute(testService.selectTest(TestVO));

		return "/test/home";
	}

	@RequestMapping(value = "/test2.do")
	public String test2(@ModelAttribute("TestVO") TestVO TestVO, ModelMap model, HttpServletRequest request) throws Exception {
		// list
		model.addAttribute(testService.selectTestList(TestVO));

		return "/test/home2";
	}

	@RequestMapping(value = "/test3.do")
	public String test3(@ModelAttribute("TestVO") TestVO TestVO, ModelMap model, HttpServletRequest request) throws Exception {
		// list
		TestVO.setTest_col("tttest");
		testService.insertTest(TestVO);

		return "redirect:/test.do";
	}

	@RequestMapping(value = "/test4.do")
	public String test4(@ModelAttribute("TestVO") TestVO TestVO, ModelMap model, HttpServletRequest request) throws Exception {
		// list
		testService.updateTest(TestVO);

		return "redirect:/test.do";
	}

	@RequestMapping(value = "/test5.do")
	public String test5(@ModelAttribute("TestVO") TestVO TestVO, ModelMap model, HttpServletRequest request) throws Exception {
		// list
		testService.deleteTest(TestVO);

		return "redirect:/test.do";
	}

	@RequestMapping(value = "/transactionTest.do")
	public String transactionTest(@ModelAttribute("TestVO") TestVO TestVO, ModelMap model, HttpServletRequest request) throws Exception {
		// list
		testService.insertTransactionTest(TestVO);

		return "redirect:/test.do";
	}

	@RequestMapping(value = "/test_main.do")
	public String testMain() throws Exception {
		return "/test/test_main";
	}

	@RequestMapping(value = "/kakao_login.do")
	public String kakaoLogin() throws Exception 
	{
		System.out.println("asdfasdf");
		return "/test/kakao";
	}
	
	@RequestMapping(value = "/facebook_login.do")
	public String facebookLogin() throws Exception 
	{
		return "/test/facebook_login";
	}
	
	@RequestMapping(value = "/google_login.do")
	public String googleLogin() throws Exception 
	{
		return "/test/google_login";
	}
	
	@RequestMapping(value = "/naver_login.do")
	public String naverLogin() throws Exception 
	{
		return "/test/naver_login";
	}
	
	@RequestMapping(value = "/naver_getAccToken.do")
	@ResponseBody
	public String naver_getAccToken(HttpServletRequest request) throws Exception 
	{
		
		String client_id = "tBVBH9TOTQQRVIjvfk7s";
		String client_secret = "8lCRb4q_XP";
		String grant_type = "authorization_code";
		String state = (String)request.getSession().getAttribute("state");
		String code = request.getParameter("code");
		
		HttpClientUtil client = new HttpClientUtil("https://nid.naver.com/oauth2.0/token");
		// 파라미터 설정(등록/수정용)
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("client_id", client_id));
		params.add(new BasicNameValuePair("client_secret", client_secret));
		params.add(new BasicNameValuePair("grant_type", grant_type));
		params.add(new BasicNameValuePair("state", state));
		params.add(new BasicNameValuePair("code", code));
		

		client.setParams(params);
		client.execute();
		String access_token = client.getString("access_token");
		
		return access_token;
	}
}
