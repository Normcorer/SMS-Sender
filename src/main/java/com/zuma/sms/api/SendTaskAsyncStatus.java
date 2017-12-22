package com.zuma.sms.api;

import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * author:ZhengXing
 * datetime:2017/12/22 0022 15:45
 * 发送任务,异步回调后,保存信息到此处
 */
@Data
public class SendTaskAsyncStatus {
	//成功数-异步响应成功
	private AtomicInteger successNum = new AtomicInteger();
	//失败数-总失败数
	private AtomicInteger failedNum = new AtomicInteger();


	/**
	 * 累加成功或失败数
	 */
	public void increment(boolean isSuccessNum) {
		if(isSuccessNum)
			successNum.incrementAndGet();
		else
			failedNum.incrementAndGet();
	}
}
