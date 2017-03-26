package com.zero.core.rbac.api;

import java.util.Collection;

import com.zero.core.common.api.AbstractEntity;

public interface Resource extends AbstractEntity{
	
	String getRid();
	
	String getKey();
	
	String getUri();
	
	String getDescription();
	
	String getName();
	
	boolean isPackage();
	
	Collection<Resource> getIncludeResource();
}
