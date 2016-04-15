package com.itw.test.service;

import java.util.List;

/**
 * @Class Name : TestService.java
 * @Description : 로그인 Service 인터페이스
 * 
 * @author 컨버전스스퀘어
 * @since 2014.08.01
 * @version 1.0
 */
public interface TestService {
	public TestVO selectTest(TestVO vo) throws Exception;

	public List<TestVO> selectTestList(TestVO vo) throws Exception;

	public void insertTest(TestVO vo) throws Exception;

	public void updateTest(TestVO vo) throws Exception;

	public void deleteTest(TestVO vo) throws Exception;

	public void insertTransactionTest(TestVO vo) throws Exception;
}
