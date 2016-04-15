package com.itw.test.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.itw.test.service.TestService;
import com.itw.test.service.TestVO;

/**
 * @Class Name : TestServiceImpl.java
 * @Description : 로그인 Service 구현 클래스
 * 
 * @author 컨버전스스퀘어
 * @since 2014.08.01
 * @version 1.0
 */
@Service("testService")
public class TestServiceImpl implements TestService {
	/** 로그인 맵퍼 **/
	@Resource(name = "testMapper")
	private TestMapper testDAO;

	@Override
	public TestVO selectTest(TestVO vo) throws Exception {
		// 1. 로그인 정보 조회
		return testDAO.selectTest(vo);
	}

	@Override
	public List<TestVO> selectTestList(TestVO vo) throws Exception {
		// 1. 로그인 정보 조회
		return testDAO.selectTestList(vo);
	}

	@Override
	public void insertTest(TestVO vo) throws Exception {
		// 1. 로그인 정보 조회
		testDAO.insertTest(vo);
	}

	@Override
	public void updateTest(TestVO vo) throws Exception {
		// 1. 로그인 정보 조회
		testDAO.updateTest(vo);
	}

	@Override
	public void deleteTest(TestVO vo) throws Exception {
		// 1. 로그인 정보 조회
		testDAO.deleteTest(vo);
	}

	@Override
	public void insertTransactionTest(TestVO vo) throws Exception {
		// 1. delete
		testDAO.deleteTest(vo);

		// 2. insert
		vo.setTest_col("111111111111111111111111111111111111111111111111111111111111111111111111111111");
		testDAO.insertTest(vo);
	}
}
