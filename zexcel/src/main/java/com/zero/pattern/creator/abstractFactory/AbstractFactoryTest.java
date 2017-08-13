package com.zero.pattern.creator.abstractFactory;

import com.zero.pattern.creator.beans.Sender;

/**
 * @category test -> pattern -> creator -> abstract factory
 * @author zero
 */
public class AbstractFactoryTest {
	
	public static void main(String[] args){
		testAbstractFactory();
	}
	
	private static void testAbstractFactory(){
		Provider provider = new SmsFactory();
		Sender sender = provider.produce();
		sender.send("testAbstractFactory 1");
		
		provider = new MailFactory();
		sender = provider.produce();
		sender.send("testAbstractFactory 2");
	}
}
