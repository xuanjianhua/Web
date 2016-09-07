package com.ybf.dto;

public class Exporter {
	
	//是否开启秒杀
	private boolean exposted;
	
	//md5加密
	private String md5;
	
	//秒杀商品id
	private long seckillId;
	
	//秒杀开启时间
	private long start;
	
	//秒杀结束时间
	private long end;
	
	//系统当前时间
	private long now;
	

	public Exporter(boolean exposted, long seckillId) {
		super();
		this.exposted = exposted;
		this.seckillId = seckillId;
	}

	public Exporter(boolean exposted, String md5, long seckillId) {
		super();
		this.exposted = exposted;
		this.md5 = md5;
		this.seckillId = seckillId;
	}

	public Exporter(boolean exposted, long seckillId, long start, long end, long now) {
		super();
		this.exposted = exposted;
		this.seckillId=seckillId;
		this.start = start;
		this.end = end;
		this.now = now;
	}

	public boolean isExposted() {
		return exposted;
	}

	public void setExposted(boolean exposted) {
		this.exposted = exposted;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}

	public long getNow() {
		return now;
	}

	public void setNow(long now) {
		this.now = now;
	}

	@Override
	public String toString() {
		return "Exporter [exposted=" + exposted + ", md5=" + md5
				+ ", seckillId=" + seckillId + ", start=" + start + ", end="
				+ end + ", now=" + now + "]";
	}
	
	
}
