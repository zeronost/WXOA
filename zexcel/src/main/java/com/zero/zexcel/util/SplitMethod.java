package com.zero.zexcel.util;

public enum SplitMethod {
	
	NUM(0,"字符分割"),
	PUN(1,"标点分割");
	
	private int key;
	private String description;
	
	private static final int DEFAULT_OFFSET = 20;
	
	private SplitMethod(int key, String desc){
		this.key = key;
		this.description = desc;
	}
	
	public int getKey(){
		return this.key;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public StringBuffer process(StringBuffer s, String k, int... offsets){
		if(s == null || k == null || !s.toString().contains(k))
			return null;
		if(this.isNum()){
			int offset = (offsets == null || offsets[0] <= 0) 
					? DEFAULT_OFFSET : offsets[0];
			return numProcess(s, k, offset);
		}else{
			return punProcess(s, k);
		}
	}
	
	private StringBuffer numProcess(StringBuffer s, String k, int offset){
		StringBuffer bf = new StringBuffer();
		String[] splits = s.toString().split(k);
		for(int i = 1; i < splits.length; i ++){
			bf.append(i).append(". ")
			  .append(cut(splits[i-1],offset,false))
			  .append(k)
			  .append(cut(splits[i],offset,true));
		}
		return bf;
	}
	
	private String cut(String s, int offset, boolean ispre){
		if(s == null || s.length() <= offset)
			return s;
		if(ispre)
			return s.substring(0, offset);
		else
			return s.substring(s.length() - offset + 1, s.length());
	}
	
	private StringBuffer punProcess(StringBuffer s, String k){
		StringBuffer bf = new StringBuffer();
		//TODO impl this method
		return bf;
	}
	
	public boolean isNum(){
		return this.equals(SplitMethod.NUM);
	}
	
	public boolean isPun(){
		return this.equals(SplitMethod.PUN);
	}
}
