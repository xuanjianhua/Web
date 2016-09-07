package com.ybf.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ybf.dto.Exporter;
import com.ybf.dto.SeckillExecution;
import com.ybf.exception.RepeatKillException;
import com.ybf.exception.SeckillCloseException;
import com.ybf.exception.SeckillException;
import com.ybf.model.Seckill;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class SeckillServiceImplTest {
	
	private final Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@Resource
	private SeckillService seckillService;
	@Test
	public void testGetSeckillById() {
		long seckillId=1000;
		Seckill seckill=seckillService.getSeckillById(seckillId);
		System.out.println(seckill);
		//logger.info(seckill.toString());
	}

	@Test
	public void testGetSeckillList() {
		List<Seckill> list=seckillService.getSeckillList(0, 4);
		System.out.println(list);
	}

	/*@Test
	public void testExportSeckillUrl() {
		long seckillId=1000L;
		Exporter exporter=seckillService.exportSeckillUrl(seckillId);
		System.out.println(exporter);
	}

	@Test
	public void testExecuteSeckill() {
		long seckillId=1000L;
		long userPhone=18920252705L;
		String md5="198a5b76680f4a2336bf47618c9f1bf0";
		SeckillExecution seckillExecution=seckillService.executeSeckill(seckillId, userPhone, md5);
		System.out.println(seckillExecution);
	}*/
	
	@Test
	public void testSeckillLogic(){
		long seckillId=1001L;
		Exporter exporter=seckillService.exportSeckillUrl(seckillId);
		long userPhone=18920252706L;
		if (exporter.isExposted()) {
			SeckillExecution seckillExecution;
			try {
				seckillExecution = seckillService.executeSeckill(seckillId, userPhone, exporter.getMd5());
				System.out.println(seckillExecution);
			} catch (RepeatKillException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SeckillCloseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SeckillException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
	}
}
