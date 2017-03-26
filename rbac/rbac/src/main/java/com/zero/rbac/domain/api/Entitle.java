package com.zero.rbac.domain.api;

import java.util.Collection;

public interface Entitle extends AbstractEntity{
	
	Long getId();
	
	String getDefCode();
	
	String getDescription();
	
	Collection<Resource> getResources();
}
