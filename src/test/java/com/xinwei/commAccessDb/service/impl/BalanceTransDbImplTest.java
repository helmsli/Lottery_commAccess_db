package com.xinwei.commAccessDb.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.xinwei.commAccessDb.domain.BalanceTransRunning;
import com.xinwei.commAccessDb.mapper.BalanceTransRunningMapper;
import com.xinwei.commAccessDb.service.BalanceTransDb;

@RunWith(SpringRunner.class)
@SpringBootTest

public class BalanceTransDbImplTest {
	@Autowired
	private BalanceTransRunningMapper balanceTransRunningMapper;

	@Autowired
	private BalanceTransDb balanceTransDb;

	protected BalanceTransRunning createInitBalanceTransRunning() {
		BalanceTransRunning balanceTransRunning = new BalanceTransRunning();
		balanceTransRunning.setAmount(123.344d);
		balanceTransRunning.setBalance(233.12d);
		balanceTransRunning.setBizsource("bizSource");
		balanceTransRunning.setBiztype("bizType");
		balanceTransRunning.setChecksum("checkSum");
		balanceTransRunning.setExpiretime(Calendar.getInstance().getTime());
		balanceTransRunning.setUpdatetime(Calendar.getInstance().getTime());
		balanceTransRunning.setOpertype("operType");
		balanceTransRunning.setOrderid("orderid");
		balanceTransRunning.setRunPriority(1);
		balanceTransRunning.setSrcipaddress("srcip");
		balanceTransRunning.setStatus(123);
		balanceTransRunning.setTransid("0020011212131313999999");
		balanceTransRunning.setTransdesc("desc");
		balanceTransRunning.setUserid(11111111);
		Calendar now = Calendar.getInstance();
		now.set(Calendar.MILLISECOND, 0);
		now.set(2001, 12, 13, 12, 13, 14);
		balanceTransRunning.setTransactionTime(now.getTime());
		return balanceTransRunning;
	}

	@Test
	public void testInsertBalanceTransRunning() {
		BalanceTransRunning balanceTransRunning = this.createInitBalanceTransRunning();
		balanceTransDb.deleteBalanceTransRunning(balanceTransRunning);
		balanceTransDb.insertBalanceTransRunning(balanceTransRunning);
		List<BalanceTransRunning> bTransRunnings = this.balanceTransDb.selectBalanceTransRunning(balanceTransRunning);
		if (bTransRunnings == null || bTransRunnings.size() == 0) {
			fail("testInsertBalanceTransRunning insert error:not found record.");
		} else {
			BalanceTransRunning queryBTrans = bTransRunnings.get(0);
			assertEquals("testInsertBalanceTransRunning insert and query is not equal.", balanceTransRunning.toString(),
					queryBTrans.toString());
			balanceTransRunning.setAmount(0);
			assertNotEquals("testInsertBalanceTransRunning insert and query is not equal.",
					balanceTransRunning.toString(), queryBTrans.toString());
		}

	}

	@Test
	public void testSelectBalanceTransRunning() {
		/**
		 * 查询的在别的测试用例中已经测试，仅仅测试查询不到的情况
		 */
		BalanceTransRunning balanceTransRunning = this.createInitBalanceTransRunning();
		BalanceTransRunning oBTransRunning = this.createInitBalanceTransRunning();
		oBTransRunning.setTransid("00201312131412128888888888");
		//删除两条记录
		balanceTransDb.deleteBalanceTransRunning(oBTransRunning);
		balanceTransDb.deleteBalanceTransRunning(balanceTransRunning);
		//插入两条记录
		balanceTransDb.insertBalanceTransRunning(balanceTransRunning);
		balanceTransDb.insertBalanceTransRunning(oBTransRunning);
		//确保查处一条记录
		List<BalanceTransRunning> bTransRunnings = this.balanceTransDb.selectBalanceTransRunning(oBTransRunning);
		if (bTransRunnings == null || bTransRunnings.size() == 0) {
			fail("testSelectBalanceTransRunning select error: not found  record.");
		}
		assertEquals("testSelectBalanceTransRunning  query is not equal.", 1, bTransRunnings.size());
		//删除新的，保留其余的
		balanceTransDb.deleteBalanceTransRunning(balanceTransRunning);
		bTransRunnings = this.balanceTransDb.selectBalanceTransRunning(balanceTransRunning);
		if (bTransRunnings != null && bTransRunnings.size() > 0) {
			fail("testSelectBalanceTransRunning select error:found  others record.");
		}
	}

	public static String getTimeStrFromTransID(String reqTransId) {
		String ret = 20 + reqTransId.substring(2, 14);
		return ret;
	}

	public static Date getDateFromTransID(String reqTransId) {
		String timeStr = getTimeStrFromTransID(reqTransId);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			return simpleDateFormat.parse(timeStr);
		} catch (ParseException e) {
			return null;
		}

	}

	@Test
	public void testSelectinitBalanceTransRunning() {
		/**
		 * 查询的在别的测试用例中已经测试，仅仅测试查询不到的情况
		 */
		BalanceTransRunning balanceTransRunning = this.createInitBalanceTransRunning();
		BalanceTransRunning oBTransRunning = this.createInitBalanceTransRunning();
		oBTransRunning.setTransid("002000082212000033333333");
		oBTransRunning.setUserid(1671432903);
		oBTransRunning.setTransactionTime(getDateFromTransID(oBTransRunning.getTransid()));
		//查询多有记录
		//		List<BalanceTransRunning> bTransRunningAlls = balanceTransRunningMapper.selectAllBTransRunning();
		//		if(bTransRunningAlls==null || bTransRunningAlls.size()==0)
		//		{
		//			fail("testSelectBalanceTransRunning select error: not found  record.");	
		//		}
		//		else
		//		{
		//			System.out.println(bTransRunningAlls.toString());
		//		}

		//确保查处一条记录
		List<BalanceTransRunning> bTransRunnings = this.balanceTransDb.selectBalanceTransRunning(oBTransRunning);
		if (bTransRunnings == null || bTransRunnings.size() == 0) {
			fail("testSelectBalanceTransRunning select error: not found  record.");
		}

		assertEquals("testSelectBalanceTransRunning  query is not equal.", 1, bTransRunnings.size());

	}

	@Test
	public void testUpdateBalanceTransRunning() {
		BalanceTransRunning balanceTransRunning = this.createInitBalanceTransRunning();
		//删除记录
		balanceTransDb.deleteBalanceTransRunning(balanceTransRunning);
		//插入记录
		balanceTransDb.insertBalanceTransRunning(balanceTransRunning);
		balanceTransRunning.setStatus(256);
		balanceTransRunning.setBalance(100000.3445d);
		balanceTransRunning.setChecksum("newCheckSum");
		Calendar now = Calendar.getInstance();
		now.set(Calendar.MILLISECOND, 0);
		now.set(2010, 1, 23, 23, 59, 59);
		balanceTransRunning.setUpdatetime(now.getTime());
		int updateRow = balanceTransDb.updateBalanceTransRunning(balanceTransRunning);
		List<BalanceTransRunning> bTransRunnings = this.balanceTransDb.selectBalanceTransRunning(balanceTransRunning);
		if (bTransRunnings != null && bTransRunnings.size() > 0) {
			assertEquals("testUpdateBalanceTransRunning  query is not equal.", balanceTransRunning.toString(),
					bTransRunnings.get(0).toString());
			balanceTransRunning.setStatus(299990);
			assertNotEquals("testUpdateBalanceTransRunning  query is  equal.", balanceTransRunning.toString(),
					bTransRunnings.get(0).toString());

		} else {
			fail("testUpdateBalanceTransRunning  error:found  others record.");

		}
		assertEquals("testUpdateBalanceTransRunning  row numbers error.", 1, updateRow);
		balanceTransDb.deleteBalanceTransRunning(balanceTransRunning);
		updateRow = balanceTransDb.updateBalanceTransRunning(balanceTransRunning);
		assertEquals("testUpdateBalanceTransRunning  update not record error.", 0, updateRow);

	}

}
