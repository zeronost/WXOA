package com.zero.zexcel.util;

import java.lang.reflect.Field;

public class TimeSheet {
	//日志序号
	private int rzxh;
	
	//主项目编号
	private String zxmbh;
	
	//工作序号
	private int gzxh;
	
	//项目编号
	private String xmbh;
	
	//里程碑
	private String lcbsxh;
	
	//工作内容
	private String gznr;
	
	//工作时间
	private double gzl;
	
	//加班备注
	private int jbbz = 0;
	
	//工作类型
	private String gzlx;
	
	//工作结果
	private String gzjg;
	
	//备注
	private String bz;
	
	//任务编号
	private String rwbh;

	public int getRzxh() {
		return rzxh;
	}
	
	/**
	 * @param rzxh
	 * 日志序号
	 */
	public void setRzxh(int rzxh) {
		this.rzxh = rzxh;
	}
	
	public String getZxmbh() {
		return zxmbh;
	}
	
	/**
	 * @param zxmbh
	 * 主项目编号
	 */
	public void setZxmbh(String zxmbh) {
		this.zxmbh = zxmbh;
	}

	public int getGzxh() {
		return gzxh;
	}
	
	/**
	 * @param gzxh
	 * 工作序号
	 */
	public void setGzxh(int gzxh) {
		this.gzxh = gzxh;
	}

	public String getXmbh() {
		return xmbh;
	}

	public void setXmbh(String xmbh) {
		this.xmbh = xmbh;
	}

	public String getLcbsxh() {
		return lcbsxh;
	}

	public void setLcbsxh(String lcbsxh) {
		this.lcbsxh = lcbsxh;
	}

	public String getGznr() {
		return gznr;
	}

	public void setGznr(String gznr) {
		this.gznr = gznr;
	}

	public double getGzl() {
		return gzl;
	}

	public void setGzl(double gzl) {
		this.gzl = gzl;
	}

	public int getJbbz() {
		return jbbz;
	}

	public void setJbbz(int jbbz) {
		this.jbbz = jbbz;
	}

	public String getGzlx() {
		return gzlx;
	}

	public void setGzlx(String gzlx) {
		this.gzlx = gzlx;
	}

	public String getGzjg() {
		return gzjg;
	}

	public void setGzjg(String gzjg) {
		this.gzjg = gzjg;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getRwbh() {
		return rwbh;
	}

	public void setRwbh(String rwbh) {
		this.rwbh = rwbh;
	}
	
	public String getPropertyStr() throws Exception{
		StringBuilder sb = new StringBuilder();
		Field[] fields = this.getClass().getDeclaredFields();
		for(Field f : fields){
			f.setAccessible(true);
			Object o = f.get(this);
			if(null != o){
				sb.append("&").append(f.getName()).append("=").append(o);
			}
		}
		return sb.toString();
	}

}
