package com.zero.pattern.creator.beans;

import java.util.Hashtable;

public class SenderCache {
	private static Hashtable<Long, AbstractSender> cache = new Hashtable<Long, AbstractSender>();
	
	public static Sender getSender(Long id){
		if(cache.containsKey(id)){
			return (Sender) cache.get(id).clone();
		}else
			return null;
	}
	
	public static void loadCache(){
		AbstractSender sender = new SmsSender();
		sender.setId(1);
		cache.put(sender.getId(), sender);
		
		sender = new MailSender();
		sender.setId(2);
		cache.put(sender.getId(), sender);
	}
}
