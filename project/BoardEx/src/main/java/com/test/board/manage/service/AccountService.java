package com.test.board.manage.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.test.board.manage.vo.AccountVO;

/**
 * 계정 Service
 *
 * @author hmyoo
 * @since 2016. 6. 17.
 * @version 1.0
 */
@Repository("accountService")
public interface AccountService {

	/**
	 * 계정 목록 조회
	 * 
	 * @param accountVO
	 *            조회할 정보가 담긴 AccountVO
	 * @return List<AccountVO>
	 * @throws Exception
	 */
	public List<AccountVO> selectAccountList(AccountVO accountVO) throws Exception;

	/**
	 * 계정 목록 총 개수 조회
	 * 
	 * @param accountVO
	 *            조회할 정보가 담긴 AccountVO
	 * @return 목록 총 개수
	 * @throws Exception
	 */
	public int selectAccountListTotalCnt(AccountVO accountVO) throws Exception;

	/**
	 * 계정 조회
	 * 
	 * @param accountVO
	 *            조회할 정보가 담긴 AccountVO
	 * @return AccountVO
	 * @throws Exception
	 */
	public AccountVO selectAccount(AccountVO accountVO) throws Exception;

	/**
	 * 계정 등록
	 * 
	 * @param accountVO
	 *            등록할 정보가 담긴 AccountVO
	 * @throws Exception
	 */
	public void insertAccount(AccountVO accountVO) throws Exception;

	/**
	 * 계정 수정
	 * 
	 * @param accountVO
	 *            수정할 정보가 담긴 AccountVO
	 * @throws Exception
	 */
	public void updateAccount(AccountVO accountVO) throws Exception;

	/**
	 * 계정 삭제
	 * 
	 * @param accountVO
	 *            삭제할 정보가 담긴 AccountVO
	 * @throws Exception
	 */
	public void deleteAccount(AccountVO accountVO) throws Exception;

}
