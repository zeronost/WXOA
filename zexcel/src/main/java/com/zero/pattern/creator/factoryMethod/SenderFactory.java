package com.zero.pattern.creator.factoryMethod;

import com.zero.pattern.creator.beans.MailSender;
import com.zero.pattern.creator.beans.Sender;
import com.zero.pattern.creator.beans.SmsSender;

/**
 * @category pattern -> creator -> factory method
 * @author zero
 */
public class SenderFactory {
	
	private static SenderFactory instance;
	
	private Sender sender;
	
	private SenderFactory(){}
	
	public static SenderFactory getInstance(){
		if(null == instance){
			instance = new SenderFactory();
		}
		return instance;
	}
	
	//# factory method
	public Sender produce(String type) throws Exception{
		if("sms".equalsIgnoreCase(type)){
			sender = new SmsSender();
		}
		else if("mail".equalsIgnoreCase(type)){
			sender = new MailSender();
		}
		else{
			throw new Exception("Not support the sender type:: "+ type);
		}
		return sender;
	}
	//~ factory method
	
	
	//# multiple factory method
	public Sender getSmsSender(){
		return new SmsSender();
	}
	
	public Sender getMailSender(){
		return new MailSender();
	}
	//~ multiple factory method
	
	
	//# static factory method
	public static Sender produceStatic(String type) throws Exception{
		if("sms".equalsIgnoreCase(type)){
			return new SmsSender();
		}
		else if("mail".equalsIgnoreCase(type)){
			return new MailSender();
		}
		else{
			throw new Exception("Not support the sender type:: "+ type);
		}
	}
	
	public static Sender getStaticSmsSender(){
		return new SmsSender();
	}
	
	public static Sender getStaticMailSender(){
		return new MailSender();
	}
	//~ static factory method
	
}
