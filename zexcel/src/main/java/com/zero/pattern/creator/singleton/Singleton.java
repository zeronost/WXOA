package com.zero.pattern.creator.singleton;

public class Singleton {
	
	private Singleton(){
		System.out.println("Construct Singleton");
	}
	
	private static Singleton instance;
	
	private static Object lock = new Object();
	
	
	//# base singleton
	public static Singleton getInstanceBase(){
		if(null == instance)
			instance = new Singleton();
		return instance;
	}
	//~ base singleton
	
	//# single check singleton
	public static synchronized Singleton getInstanceSingle(){
		if(null == instance)
			instance = new Singleton();
		return instance;
	}
	//~ single check singleton
	
	
	//# double check singleton
	public static Singleton getInstanceDouble(){
		if(null == instance){
			synchronized(lock){
				if(null == instance)
					instance = new Singleton();
			}
		}
		return instance;
	}
	//~ double check singleton
	
	
	//# nested class singleton
	private static class Provider{
		private static Singleton ins = new Singleton();
	}
	
	public static Singleton getNestedInstance(){
		return Provider.ins;
	}
	//~ nested class singleton
	
	
	//# synchronized method singleton
	public static Singleton getSyncInstance(){
		if(null == instance){
			init();
		}
		return instance;
	}
	
	private static synchronized void init(){
		if(null == instance)
			instance = new Singleton();
	}
	//~ synchronized method singleton
}
