package com.ybf.web;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ybf.dto.Exporter;
import com.ybf.dto.SeckillExecution;
import com.ybf.dto.SeckillResult;
import com.ybf.enums.SeckillStateEnum;
import com.ybf.exception.RepeatKillException;
import com.ybf.exception.SeckillCloseException;
import com.ybf.exception.SeckillException;
import com.ybf.model.Seckill;
import com.ybf.service.SeckillService;

@Controller
@RequestMapping("/seckill")
public class SeckillController {

	@Resource
	private SeckillService seckillService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		List<Seckill> list = seckillService.getSeckillList(0, 4);
		model.addAttribute("list", list);
		return "list";
	}

	@RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
	public String detail(@PathVariable("seckillId") Long seckillId, Model model) {
		if (null == seckillId) {
			return "redirect:/seckill/list";
		}
		Seckill seckill = seckillService.getSeckillById(seckillId);
		if (null == seckill) {
			return "redirect:/seckill/list";
		}
		model.addAttribute("seckill", seckill);
		return "detail";
	}

	@RequestMapping(value = "/{seckillId}/expose", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public SeckillResult<Exporter> expose(@PathVariable("seckillId")Long seckillId) {
		SeckillResult<Exporter> result;
		try {
			Exporter exporter = seckillService.exportSeckillUrl(seckillId);
			result = new SeckillResult<Exporter>(true, exporter);
		} catch (Exception e) {
			e.printStackTrace();
			result = new SeckillResult<Exporter>(false, e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/{seckillId}/{md5}/excute", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public SeckillResult<SeckillExecution> excute(
			@PathVariable("seckillId") Long seckillId,
			@PathVariable("md5") String md5,
			@CookieValue(value = "killPhone", required = false) Long Phone) {
		SeckillResult<SeckillExecution> result;
		if (null == Phone) {
			result = new SeckillResult<SeckillExecution>(false, "未注册");
		}
		try {
			SeckillExecution seckillExecution = seckillService.executeSeckill(
					seckillId, Phone, md5);
			result = new SeckillResult<SeckillExecution>(true, seckillExecution);
		} catch (RepeatKillException e) {
			SeckillExecution seckillExecution = new SeckillExecution(seckillId,
					SeckillStateEnum.REPEAT_KILL);
			result = new SeckillResult<SeckillExecution>(true, seckillExecution);
		} catch (SeckillCloseException e) {
			SeckillExecution seckillExecution = new SeckillExecution(seckillId,
					SeckillStateEnum.END);
			result = new SeckillResult<SeckillExecution>(true, seckillExecution);
		} catch (SeckillException e) {
			e.printStackTrace();
			SeckillExecution seckillExecution = new SeckillExecution(seckillId,
					SeckillStateEnum.INNER_ERROR);
			result = new SeckillResult<SeckillExecution>(true, seckillExecution);
		}
		return result;
	}

	@RequestMapping(value = "/time/now", method = RequestMethod.GET)
	@ResponseBody
	public SeckillResult<Long> time() {
		Date now = new Date();
		return new SeckillResult<Long>(true, now.getTime());
	}
}
