package com.xinwei.commAccessDb.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.company.security.utils.SecurityUserAlgorithm;
import com.xinwei.commAccessDb.domain.BalanceTransRunning;
import com.xinwei.commAccessDb.domain.BalanceTransRunningDb;
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

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private BalanceTransRunningMapper balanceTransRunningMapper;

	@Override
	public int insertBalanceTransRunning(BalanceTransRunning balanceTransRunning) {
		// TODO Auto-generated method stub
		String newCrc = createDbIntCrc(balanceTransRunning);
		balanceTransRunning.setChecksum(newCrc);
		balanceTransRunningMapper.insertBalanceTransRunning(new BalanceTransRunningDb(balanceTransRunning));
		return 0;
	}

	@Override
	public List<BalanceTransRunning> selectBalanceTransRunning(BalanceTransRunning balanceTransRunning) {
		// TODO Auto-generated method stub
		List<BalanceTransRunningDb> lists = balanceTransRunningMapper
				.selectBalanceTransRunning(new BalanceTransRunningDb(balanceTransRunning));
		List<BalanceTransRunning> retList = new ArrayList();
		for (BalanceTransRunningDb retbalTransRunning : lists) {
			BalanceTransRunning balanceTransRunningDb = retbalTransRunning.getBalanceTransRunning();
			logger.debug(balanceTransRunningDb.toString());
			if (checkDbCrc(balanceTransRunningDb)) {
				logger.debug("add it to list");
				retList.add(balanceTransRunningDb);
			}
		}
		lists = null;
		return retList;
	}

	@Override
	public int updateBalanceTransRunning(BalanceTransRunning balanceTransRunning) {
		// TODO Auto-generated method stub		
		String newCrc = createDbIntCrc(balanceTransRunning);
		balanceTransRunning.setChecksum(newCrc);
		return balanceTransRunningMapper.updateBalanceTransRunning(new BalanceTransRunningDb(balanceTransRunning));
	}

	@Override
	public int deleteBalanceTransRunning(BalanceTransRunning balanceTransRunning) {
		// TODO Auto-generated method stub
		return balanceTransRunningMapper.deleteBalanceTransRunning(new BalanceTransRunningDb(balanceTransRunning));
	}

	/**
	 * 用于对 BalanceTransRunning进行传输加密
	 * @param initDbTransaction
	 */
	protected boolean CheckTransferCrc(BalanceTransRunning initDbTransaction) {
		try {
			String checkCrc = this.createTransferCrc(initDbTransaction);
			return checkCrc.equalsIgnoreCase(initDbTransaction.getChecksum());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return false;

	}

	/**
	 * 校验数据库保存的KEY
	 * @param initDbTransaction
	 * @return
	 */
	protected boolean checkDbCrc(BalanceTransRunning initDbTransaction) {
		String newCrc = this.createDbIntCrc(initDbTransaction);
		//logger.debug("new crc:" + newCrc);
		boolean ret = newCrc.equalsIgnoreCase(initDbTransaction.getChecksum());
		if (ret) {
			return ret;
		}
		newCrc = this.createDbCrc(initDbTransaction);
		ret = newCrc.equalsIgnoreCase(initDbTransaction.getChecksum());
		return ret;
	}

	/**
	 * 创建传输层的KEY
	 * @param initDbTransaction
	 * @return
	 */
	protected String createTransferCrc(BalanceTransRunning initDbTransaction) {
		try {
			String key = this.transferBalKey;
			StringBuilder source = new StringBuilder();
			source.append(initDbTransaction.getUserid());
			source.append(SecurityUserAlgorithm.Prop_split);
			source.append(initDbTransaction.getTransid());
			source.append(SecurityUserAlgorithm.Prop_split);
			long ll = Math.round(initDbTransaction.getAmount() * 10000);
			source.append(ll);

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

	/**
	 * 
	 * @param initDbTransaction
	 * @return
	 */
	protected String createDbCrc(BalanceTransRunning initDbTransaction) {
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

	/**
	 * 创建金额为整数的校验和规则
	 * @param initDbTransaction
	 * @return
	 */
	protected String createDbIntCrc(BalanceTransRunning initDbTransaction) {
		try {
			String key = this.dbBalKey;
			StringBuilder source = new StringBuilder();
			source.append(initDbTransaction.getUserid());
			source.append(SecurityUserAlgorithm.Prop_split);
			source.append(initDbTransaction.getTransid());
			source.append(SecurityUserAlgorithm.Prop_split);
			long ll = Math.round(initDbTransaction.getAmount() * 10000);
			source.append(ll);
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
