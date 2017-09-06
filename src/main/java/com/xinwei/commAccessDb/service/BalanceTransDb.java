package com.xinwei.commAccessDb.service;

import java.util.List;

import com.xinwei.commAccessDb.domain.BalanceTransRunning;

public interface BalanceTransDb {
	/**
	 * 插入新的扣费记录
	 * 
	 * @param BalanceTransRunning
	 */
	public int insertBalanceTransRunning(BalanceTransRunning balanceTransRunning);
	/**
	 * 根据条件查询需要处理的扣费记录
	 * 
	 * @param BalanceTransRunning
	 * @return
	 */
	public List<BalanceTransRunning> selectBalanceTransRunning(BalanceTransRunning balanceTransRunning);
	/**
	 * 更新扣费记录，更新状态
	 * 
	 * @param balanceTransRunning
	 */
	public int updateBalanceTransRunning(BalanceTransRunning balanceTransRunning);
	/**
	 * 按照userID和Transaction，transTime删除交易记录
	 * @param balanceTransRunning
	 * @return
	 */
	public int deleteBalanceTransRunning(BalanceTransRunning balanceTransRunning);
}
