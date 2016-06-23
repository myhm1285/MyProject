package com.test.board.manage.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.test.board.manage.service.AccountService;
import com.test.board.manage.vo.AccountVO;

/**
 * 계정 Service 구현
 *
 * @author hmyoo
 * @since 2016. 6. 17.
 * @version 1.0
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService {

	/**
	 * 계정 Mapper
	 */
	@Resource(name = "accountMapper")
	private AccountMapper accountDAO;

	/**
	 * 계정 목록 조회
	 * 
	 * @param accountVO
	 *            조회할 정보가 담긴 AccountVO
	 * @return List<AccountVO>
	 */
	@Override
	public List<AccountVO> selectAccountList(AccountVO accountVO) {
		return accountDAO.selectAccountList(accountVO);
	}

	/**
	 * 계정 목록 총 개수 조회
	 * 
	 * @param accountVO
	 *            조회할 정보가 담긴 AccountVO
	 * @return 목록 총 개수
	 */
	@Override
	public int selectAccountListTotalCnt(AccountVO accountVO) {
		return accountDAO.selectAccountListTotalCnt(accountVO);
	}

	/**
	 * 계정 조회
	 * 
	 * @param accountVO
	 *            조회할 정보가 담긴 AccountVO
	 * @return AccountVO
	 */
	@Override
	public AccountVO selectAccount(AccountVO accountVO) {
		return accountDAO.selectAccount(accountVO);
	}

	/**
	 * 계정 등록
	 * 
	 * @param accountVO
	 *            등록할 정보가 담긴 AccountVO
	 */
	@Override
	public void insertAccount(AccountVO accountVO) {
		accountDAO.insertAccount(accountVO);
	}

	/**
	 * 계정 수정
	 * 
	 * @param accountVO
	 *            수정할 정보가 담긴 AccountVO
	 */
	@Override
	public void updateAccount(AccountVO accountVO) {
		accountDAO.updateAccount(accountVO);
	}

	/**
	 * 계정 삭제
	 * 
	 * @param accountVO
	 *            삭제할 정보가 담긴 AccountVO
	 */
	@Override
	public void deleteAccount(AccountVO accountVO) {
		accountDAO.deleteAccount(accountVO);
	}

}
