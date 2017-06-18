package com.zero.zexcel;

import java.util.LinkedHashMap;
import java.util.Map;

public class CachePool<T> {
	
	Map<Object,T> data = new LinkedHashMap<Object,T>();
	
	Integer size = 0;
	
	static final int limit = 1000;
	
	public void push(Object key, T value) throws Exception{
		if(null != key){
			data.put(key, value);
			increase();
		}
	}
	
	public void remove(Object value) throws Exception{
		if(value == null || !data.containsValue(value))
			return;
		data.remove(value);
		decrease();
	}
	
	public Object fetch(Object key){
		if(!containsKey(key))
			return null;
		return data.get(key);
	}
	
	public Map<Object,T> getData(){
		return data;
	}
	
	public boolean containsKey(Object key){
		if(null == key)
			return false;
		if(!data.containsKey(key))
			return false;
		return true;
	}
	
	public void clear(){
		if(null == data || data.isEmpty())
			return;
		data.clear();
	}
	
	private void increase() throws Exception{
		synchronized(size){
			if(size == limit)
				throw new Exception("cache overflow");
			size++;
		}
	}
	
	private void decrease() throws Exception{
		synchronized(size){
			if(size == 0)
				throw new Exception("can't remove or peak object from empty pool");
			size--;
		}
	}
}
