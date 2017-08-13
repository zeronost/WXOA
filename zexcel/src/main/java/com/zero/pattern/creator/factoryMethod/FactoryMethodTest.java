package com.zero.pattern.creator.factoryMethod;

import com.zero.pattern.creator.beans.Sender;

/**
 * @category test -> pattern -> creator -> factory method
 * @author zero
 */
public class FactoryMethodTest {
	
	public static void main(String[] args){
		try {
			testFactoryMethod();
			testMultiFactoryMethod();
			testStaticFactoryMethod();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void testFactoryMethod() throws Exception{
		
		Sender sender = SenderFactory.getInstance().produce("sms");
		
		sender.send("testFactoryMethod 1");
		
		sender = SenderFactory.getInstance().produce("mail");
		
		sender.send("testFactoryMethod 2");
	}
	
	private static void testMultiFactoryMethod(){
		Sender sender = SenderFactory.getInstance().getSmsSender();
		sender.send("testMultiFactoryMethod 1");
		
		sender = SenderFactory.getInstance().getMailSender();
		sender.send("testMultiFactoryMethod 2");
	}
	
	private static void testStaticFactoryMethod() throws Exception{
		Sender sender = SenderFactory.produceStatic("sms");
		sender.send("testStaticFactoryMethod 1");
		
		sender = SenderFactory.produceStatic("mail");
		sender.send("testStaticFactoryMethod 2");
		
		sender = SenderFactory.getStaticSmsSender();
		sender.send("testStaticFactoryMethod 3");
		
		sender = SenderFactory.getStaticMailSender();
		sender.send("testStaticFactoryMethod 4");
	}
}
