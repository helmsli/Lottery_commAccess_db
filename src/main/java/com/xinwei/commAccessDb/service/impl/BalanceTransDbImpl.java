package com.xinwei.commAccessDb.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;

import com.company.security.utils.SecurityUserAlgorithm;
import com.xinwei.commAccessDb.domain.BalanceTransRunning;
import com.xinwei.commAccessDb.mapper.BalanceTransRunningMapper;
import com.xinwei.commAccessDb.service.BalanceTransDb;
@Service("balanceTransDb")
public class BalanceTransDbImpl implements BalanceTransDb {
	/**
	 * 用户对内的传输加密
	 */
	@Value("${transfer.balanceKey}")  
	private String transferBalKey;
	
	@Value("${db.balanceKey}")  
	private String dbBalKey;
	
	
	@Autowired
	private BalanceTransRunningMapper balanceTransRunningMapper;
	@Override
	public int insertBalanceTransRunning(BalanceTransRunning balanceTransRunning) {
		// TODO Auto-generated method stub
		String newCrc = createDbCrc(balanceTransRunning);
		balanceTransRunning.setChecksum(newCrc);
	    balanceTransRunningMapper.insertBalanceTransRunning(balanceTransRunning);
	    return 0;
	}

	@Override
	public List<BalanceTransRunning> selectBalanceTransRunning(BalanceTransRunning balanceTransRunning) {
		// TODO Auto-generated method stub
		List<BalanceTransRunning> lists = balanceTransRunningMapper.selectBalanceTransRunning(balanceTransRunning);
		List<BalanceTransRunning> retList = new ArrayList();
		for(BalanceTransRunning retbalTransRunning:lists)
	    {
		   if(CheckTransferCrc(retbalTransRunning))
		   {
			   retList.add(retbalTransRunning);
		   }
	    }
		lists = null;
		return retList;
	}

	@Override
	public int updateBalanceTransRunning(BalanceTransRunning balanceTransRunning) {
		// TODO Auto-generated method stub
		String newCrc = createDbCrc(balanceTransRunning);
		balanceTransRunning.setChecksum(newCrc);
		return balanceTransRunningMapper.updateBalanceTransRunning(balanceTransRunning);
	}

	@Override
	public int deleteBalanceTransRunning(BalanceTransRunning balanceTransRunning) {
		// TODO Auto-generated method stub
		return balanceTransRunningMapper.deleteBalanceTransRunning(balanceTransRunning);
	}
	/**
	 * 用于对 BalanceTransRunning进行传输加密
	 * @param initDbTransaction
	 */
	protected boolean CheckTransferCrc(BalanceTransRunning initDbTransaction)
	{
		try {
			String key = this.transferBalKey;
			StringBuilder source = new StringBuilder();
			source.append(initDbTransaction.getUserid());
			source.append(SecurityUserAlgorithm.Prop_split);
			source.append(initDbTransaction.getTransid());
			source.append(SecurityUserAlgorithm.Prop_split);
			source.append(initDbTransaction.getAmount());			
			String checkCrc = SecurityUserAlgorithm.EncoderByMd5(key, source.toString());
			return checkCrc.equalsIgnoreCase(initDbTransaction.getChecksum());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return false;
		
	}
	
	protected boolean checkDbCrc(BalanceTransRunning initDbTransaction)
	{
		String newCrc = this.createDbCrc(initDbTransaction);
		return newCrc.equalsIgnoreCase(initDbTransaction.getChecksum());
	}
	
	/**
	 * 
	 * @param initDbTransaction
	 * @return
	 */
	protected String createDbCrc(BalanceTransRunning initDbTransaction)
	{
		try {
			String key = this.dbBalKey;
			StringBuilder source = new StringBuilder();
			source.append(initDbTransaction.getUserid());
			source.append(SecurityUserAlgorithm.Prop_split);
			source.append(initDbTransaction.getTransid());
			source.append(SecurityUserAlgorithm.Prop_split);
			source.append(initDbTransaction.getAmount());	
			source.append(SecurityUserAlgorithm.Prop_split);
			source.append(initDbTransaction.getStatus());
			String checkCrc = SecurityUserAlgorithm.EncoderByMd5(key, source.toString());
			return checkCrc;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return "";
		
	}
}
