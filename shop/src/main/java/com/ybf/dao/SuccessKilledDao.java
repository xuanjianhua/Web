package com.ybf.dao;

import org.apache.ibatis.annotations.Param;

import com.ybf.model.SuccessKilled;




public interface SuccessKilledDao {
	/**
	 * 插入购买明细，可过滤重复
	 * @param seckillId
	 * @param userPhone
	 * @return
	 */
	int insertSuccessKilled(@Param("seckillId")long seckillId,@Param("userPhone")long userPhone);
	
	/**
	 * 根据id查询SuccessKilled并携带秒杀产品对象实体
	 * @param seckillId
	 * @return
	 */
	SuccessKilled queryByIdWithseckill(long seckillId);
}
