package com.ybf.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.ybf.dao.SeckillDao;
import com.ybf.dao.SuccessKilledDao;
import com.ybf.dto.Exporter;
import com.ybf.dto.SeckillExecution;
import com.ybf.enums.SeckillStateEnum;
import com.ybf.exception.RepeatKillException;
import com.ybf.exception.SeckillCloseException;
import com.ybf.exception.SeckillException;
import com.ybf.model.Seckill;
import com.ybf.model.SuccessKilled;
import com.ybf.service.SeckillService;

@Service
public class SeckillServiceImpl implements SeckillService{
	
	@Resource
	private SeckillDao seckillDao;
	
	@Resource
	private SuccessKilledDao successKilledDao;
	
	//md5盐值，用于混淆md5
	private final String salt="rqre143124afads!#@@!#@!%&";
	@Override
	public Seckill getSeckillById(long seckillId) {
		return seckillDao.queryById(seckillId);
	}

	@Override
	public List<Seckill> getSeckillList(int offset, int limit) {
		return seckillDao.queryAll(offset, limit);
	}

	@Override
	public Exporter exportSeckillUrl(long seckillId) {
		Seckill seckill=seckillDao.queryById(seckillId);
		if (null==seckill) {
			return new Exporter(false,seckillId);
		}
		//系统当前时间
		Date nowTime=new Date();
		Date startTime=seckill.getStartTime();
		Date endTime=seckill.getEndTime();
		if (nowTime.getTime()<startTime.getTime()
				||nowTime.getTime()>endTime.getTime()) {
			return new Exporter(false, seckillId, startTime.getTime(), endTime.getTime(), nowTime.getTime());
		}
		String md5=getMD5(seckillId);
		return new Exporter(true,md5,seckillId);
	}

	@Override
	@Transactional
	public SeckillExecution executeSeckill(long seckillId, long userPhone,
			String md5) throws SeckillException, RepeatKillException,
			SeckillCloseException {
		if(null==md5||!md5.equals(getMD5(seckillId))){
			throw new SeckillException("seckill data rewrite");
		}
		//秒杀逻辑=减库存+记录购买行为
		Date nowTime=new Date();
		//减库存
		int updateCount=seckillDao.reduceNumber(seckillId, nowTime);
		try {
			if(updateCount<=0){
				//如果更新记录行数小于0，说明秒杀结束
				throw new SeckillCloseException("seckill is close");
			}else{
				//记录购买行为
				int insertCount=successKilledDao.insertSuccessKilled(seckillId, userPhone);
				if(insertCount<=0){
					//如果更新记录行数小于0，说明重复秒杀
					throw new RepeatKillException("seckill repeated");
				}else{
					//秒杀成功
					SuccessKilled successKilled=successKilledDao.queryByIdWithseckill(seckillId);
					return new SeckillExecution(seckillId,SeckillStateEnum.SUCCESS,successKilled);
				}
			}
		}catch(SeckillCloseException e1){
			throw e1;
		} catch(RepeatKillException e2){
			throw e2;
		}catch (Exception e) {
			e.printStackTrace();
			throw new SeckillException("seckill inner error"+e.getMessage());
		}
		
	}

	private String getMD5(long seckillId){
		String base=seckillId+"/"+salt;
		String md5=DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}
	
}
