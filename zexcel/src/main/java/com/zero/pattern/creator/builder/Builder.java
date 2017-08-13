package com.zero.pattern.creator.builder;

import java.util.ArrayList;
import java.util.List;

import com.zero.pattern.creator.beans.MailSender;
import com.zero.pattern.creator.beans.Sender;
import com.zero.pattern.creator.beans.SmsSender;

/**
 * @category pattern -> creator -> builder
 * @author zero
 */
public class Builder {
	
	public static List<Sender> produceSmsSender(int n){
		List<Sender> senders = new ArrayList<Sender>();
		for(int i = 0; i < n; i++){
			senders.add(new SmsSender());
		}
		return senders;
	}
	
	public static List<Sender> produceMailSender(int n){
		List<Sender> senders = new ArrayList<Sender>();
		for(int i = 0; i < n; i++){
			senders.add(new MailSender());
		}
		return senders;
	}
}
