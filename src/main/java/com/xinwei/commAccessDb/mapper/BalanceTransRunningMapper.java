package com.xinwei.commAccessDb.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.xinwei.commAccessDb.domain.BalanceTransRunning;


@Mapper
public interface BalanceTransRunningMapper {
	/**
	 * 插入新的扣费记录
	 * 
	 * @param balanceTransRunning
	 */
	@Insert("INSERT INTO balanceTransRunning(userid,transactionTime,transid,amount,expiretime,transdesc,balance,orderid,biztype,opertype,checksum,updatetime,bizsource,srcipaddress,status,runPriority) VALUES(#{userid},#{transactionTime},#{transid},#{amount},#{expiretime},#{transdesc},#{balance},#{orderid},#{biztype},#{opertype},#{checksum},#{updatetime},#{bizsource},#{srcipaddress},#{status},#{runPriority})")	   
	public void insertBalanceTransRunning(BalanceTransRunning balanceTransRunning);
	/**
	 * 根据条件查询需要处理的扣费记录
	 * 
	 * @param balanceTransRunning
	 * @return
	 */
	@Select("SELECT * FROM balanceTransRunning WHERE transactionTime = #{transactionTime} and userid=#{userid} and transid=#{transid}")
	//@ResultMap("com.xinwei.commAccessDb.domain.BalanceTransRunning")
	//@ResultList
	public List<BalanceTransRunning> selectBalanceTransRunning(BalanceTransRunning balanceTransRunning);
	
	/**
	 * 更新扣费记录，更新状态
	 * 
	 * @param balanceTransRunning
	 */
	@Update("update  balanceTransRunning set status = #{status},updatetime=#{updatetime},checksum=#{checksum},balance=#{balance} where transactionTime = #{transactionTime} and userid=#{userid} and transid=#{transid}")
	public int updateBalanceTransRunning(BalanceTransRunning balanceTransRunning);

	/**
	 * 按照userID和Transaction，transTime删除交易记录
	 * @param balanceTransRunning
	 * @return
	 */
	@Delete("DELETE FROM balanceTransRunning where transactionTime = #{transactionTime} and userid=#{userid} and transid=#{transid}")
	public int deleteBalanceTransRunning(BalanceTransRunning balanceTransRunning);
}
