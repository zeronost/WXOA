package com.zero.pattern.creator.prototype;

import com.zero.pattern.creator.beans.Sender;
import com.zero.pattern.creator.beans.SenderCache;

/**
 * @category test -> pattern -> creator -> prototype
 * @author zero
 */
public class PrototypeTest {
	
	public static void main(String[] args){
		testProtype();
	}
	
	
	private static void testProtype(){
		SenderCache.loadCache();
		
		Sender sender = SenderCache.getSender(1l);
		sender.send("testProtype");
		
		sender = SenderCache.getSender(2l);
		sender.send("testProtype");
		
		sender = SenderCache.getSender(1l);
		sender.send("testProtype");
	}
}
