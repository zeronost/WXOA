package com.zero.pattern.creator.builder;

import java.util.List;

import com.zero.pattern.creator.beans.Sender;

/**
 * @category test -> pattern -> creator -> builder
 * @author zero
 */
public class BuilderTest {
	
	public static void main(String[] args){
		testBuilder();
	}
	
	private static void testBuilder(){
		List<Sender> senders = Builder.produceMailSender(5);
		for(Sender s : senders){
			s.send("testBuilder");
		}
	}
}
