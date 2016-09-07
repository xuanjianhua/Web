package com.ybf.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ybf.model.Seckill;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {
	
	@Resource
	private SeckillDao seckillDao;
	
	@Test
	public void testReduceNumber() {
		Date killTime =new Date();
		int updateNum=seckillDao.reduceNumber(1000, killTime);
		System.out.println(updateNum);
	}

	@Test
	public void testQueryById() {
		long id=1000;
		Seckill seckill=seckillDao.queryById(id);
		System.out.println(seckill.getName());
	}

	@Test
	public void testQueryAll() {
		List<Seckill> list=seckillDao.queryAll(0, 4);
		for (Seckill seckill : list) {
			System.out.println(seckill);
		}
		
	}

}
