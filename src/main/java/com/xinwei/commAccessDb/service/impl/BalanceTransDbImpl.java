package com.xinwei.commAccessDb.service.impl;

import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import com.xinwei.commAccessDb.domain.BalanceTransRunning;
import com.xinwei.commAccessDb.mapper.BalanceTransRunningMapper;
import com.xinwei.commAccessDb.service.BalanceTransDb;
@Service("balanceTransDb")
public class BalanceTransDbImpl implements BalanceTransDb {

	
	@Autowired
	private BalanceTransRunningMapper balanceTransRunningMapper;
	@Override
	public void insertBalanceTransRunning(BalanceTransRunning balanceTransRunning) {
		// TODO Auto-generated method stub
         balanceTransRunningMapper.insertBalanceTransRunning(balanceTransRunning);
	}

	@Override
	public List<BalanceTransRunning> selectBalanceTransRunning(BalanceTransRunning balanceTransRunning) {
		// TODO Auto-generated method stub
		return balanceTransRunningMapper.selectBalanceTransRunning(balanceTransRunning);
	}

	@Override
	public int updateBalanceTransRunning(BalanceTransRunning balanceTransRunning) {
		// TODO Auto-generated method stub
		return balanceTransRunningMapper.updateBalanceTransRunning(balanceTransRunning);
	}

	@Override
	public int deleteBalanceTransRunning(BalanceTransRunning balanceTransRunning) {
		// TODO Auto-generated method stub
		return balanceTransRunningMapper.deleteBalanceTransRunning(balanceTransRunning);
	}

}
