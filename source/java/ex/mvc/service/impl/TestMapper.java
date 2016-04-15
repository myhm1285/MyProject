package com.itw.test.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.itw.test.service.TestVO;

/**
 * @Class Name : TestMapper.java
 * @Description : 로그인 Mapper 인터페이스
 * 
 * @author 컨버전스스퀘어
 * @since 2014.08.01
 * @version 1.0
 */
@Resource(name = "testMapper")
public interface TestMapper {
	public TestVO selectTest(TestVO vo) throws Exception;

	public List<TestVO> selectTestList(TestVO vo) throws Exception;

	public void insertTest(TestVO vo) throws Exception;

	public void updateTest(TestVO vo) throws Exception;

	public void deleteTest(TestVO vo) throws Exception;
}
