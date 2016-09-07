package com.ybf.service;

import java.util.List;

import com.ybf.dto.Exporter;
import com.ybf.dto.SeckillExecution;
import com.ybf.exception.RepeatKillException;
import com.ybf.exception.SeckillCloseException;
import com.ybf.exception.SeckillException;
import com.ybf.model.Seckill;
/**
 * 业务接口：站在“使用者”的角度设计接口
 * 
 * 主要体现在三个方面：方法定义的粒度，参数，返回类型（return类型、异常）
 * @author xjh
 *
 */
public interface SeckillService {
	
	//根据seckillId查询Seckill
	Seckill getSeckillById(long seckillId);
	
	//查询所有Seckill
	List<Seckill> getSeckillList(int offset,int limit);
	
	/**
	 * 秒杀开启时暴露秒杀地址，
	 * 秒杀关闭时输出系统时间和秒杀时间
	 * @param seckillId
	 */
	Exporter exportSeckillUrl(long seckillId);
	
	/**
	 * 执行秒杀操作
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 * @return SeckillExecution
	 */
	SeckillExecution executeSeckill(long seckillId,long userPhone,String md5)
	throws SeckillException,RepeatKillException,SeckillCloseException;
	
	
}
