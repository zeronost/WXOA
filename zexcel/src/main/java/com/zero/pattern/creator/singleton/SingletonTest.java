package com.zero.pattern.creator.singleton;

public class SingletonTest {
	
	public static void main(String[] args){
		testSingleton();
	}
	
	private static void testSingleton(){
		Singleton sin = Singleton.getInstanceBase();
		
		sin = Singleton.getInstanceDouble();
	}
}
