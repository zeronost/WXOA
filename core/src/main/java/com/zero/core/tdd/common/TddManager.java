package com.zero.core.tdd.common;

import org.junit.rules.TestRule;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public class TddManager {
	
	public static Injector getInjector(Module module){
		return Guice.createInjector(module);
	}
	
	public static TestRule getTestRule(){
		return CommonTestRule.getRule();
	}
	
}
