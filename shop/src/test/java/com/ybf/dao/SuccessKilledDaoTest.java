package com.ybf.dao;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ybf.model.SuccessKilled;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {
	@Resource
	private SuccessKilledDao successKilledDao;
	@Test
	public void testInsertSuccessKilled() {
		long seckillId=1000L;
		long userPhone=18920252795L;
		int inserttNum=successKilledDao.insertSuccessKilled(seckillId, userPhone);
		System.out.println(inserttNum);
	}

	@Test
	public void testQueryByIdWithseckill() {
		long seckillId=1000L;
		SuccessKilled successKilled=successKilledDao.queryByIdWithseckill(seckillId);
		System.out.println(successKilled);
	}

}
