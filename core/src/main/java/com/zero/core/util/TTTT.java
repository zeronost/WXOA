package com.zero.core.util;

import java.util.Collection;

import com.zero.core.ddd.annotation.DDI;
import com.zero.core.ddd.annotation.DDR;

public interface TTTT {

	
	@DDR
	Collection<Object> getA();
	
	
	@DDR
	@DDI
	//this is a bbb
	Collection<Object> getB();
	
}
