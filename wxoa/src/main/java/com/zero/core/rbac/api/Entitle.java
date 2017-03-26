package com.zero.core.rbac.api;

import java.util.Collection;

import com.zero.core.common.api.AbstractEntity;

public interface Entitle extends AbstractEntity{
	
	Long getId();
	
	String getDefCode();
	
	String getDescription();
	
	Collection<Resource> getResources();
}
