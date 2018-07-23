package com.xinwei.commAccessDb.rest;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.xinwei.commAccessDb.domain.BalanceTransRunning;
import com.xinwei.commAccessDb.service.BalanceTransDb;
import com.xinwei.nnl.common.domain.ProcessResult;

/**
 * @notes 
 * 
 * @author wangjiamin
 * 
 * @version 2018年7月14日 下午3:18:19
 * 
 */
@RestController
@RequestMapping("/balanceTransDb")
public class BalanceTransDbController {
	@Autowired
	private BalanceTransDb balanceTransDb;

	private final int SUCCESS = 0;

	private final int FAILURE = -1;

	/**
	 * 插入新的扣费记录
	 * @param balanceTransRunning
	 * @return
	 */
	@PostMapping("/insertBalanceTransRunning")
	public ProcessResult insertBalanceTransRunning(@RequestBody BalanceTransRunning balanceTransRunning) {
		ProcessResult result = new ProcessResult();
		result.setRetCode(FAILURE);
		try {
			int ret = balanceTransDb.insertBalanceTransRunning(balanceTransRunning);
			result.setRetCode(SUCCESS);
			result.setResponseInfo(ret);
		} catch (Exception e) {
			result.setRetMsg(getStringFromException(e));
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据条件查询需要处理的扣费记录
	 * @param balanceTransRunning
	 * @return
	 */
	@PostMapping("/selectBalanceTransRunning")
	public ProcessResult selectBalanceTransRunning(@RequestBody BalanceTransRunning balanceTransRunning) {
		ProcessResult result = new ProcessResult();
		result.setRetCode(FAILURE);
		Gson gson = new Gson();
		try {
			List<BalanceTransRunning> list = balanceTransDb.selectBalanceTransRunning(balanceTransRunning);
			result.setResponseInfo(gson.toJson(list));
			result.setRetCode(SUCCESS);
		} catch (Exception e) {
			result.setRetMsg(getStringFromException(e));
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 更新扣费记录，更新状态
	 * @param balanceTransRunning
	 * @return
	 */
	@PostMapping("/updateBalanceTransRunning")
	public ProcessResult updateBalanceTransRunning(@RequestBody BalanceTransRunning balanceTransRunning) {
		ProcessResult result = new ProcessResult();
		result.setRetCode(FAILURE);
		try {
			int ret = balanceTransDb.updateBalanceTransRunning(balanceTransRunning);
			result.setRetCode(SUCCESS);
			result.setResponseInfo(ret);
		} catch (Exception e) {
			result.setRetMsg(getStringFromException(e));
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 按照userID和Transaction，transTime删除交易记录
	 * @param balanceTransRunning
	 * @return
	 */
	@PostMapping("/deleteBalanceTransRunning")
	public ProcessResult deleteBalanceTransRunning(@RequestBody BalanceTransRunning balanceTransRunning) {
		ProcessResult result = new ProcessResult();
		result.setRetCode(FAILURE);
		try {
			int ret = balanceTransDb.deleteBalanceTransRunning(balanceTransRunning);
			result.setRetCode(SUCCESS);
			result.setResponseInfo(ret);
		} catch (Exception e) {
			result.setRetMsg(getStringFromException(e));
			e.printStackTrace();
		}
		return result;
	}

	private String getStringFromException(Exception e) {
		if (e != null) {
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			String errorStr = errors.toString();
			return errorStr;
		}
		return "";
	}
}
