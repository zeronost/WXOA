package com.zero.rbac.domain.api;

import java.util.Collection;

public interface Resource extends AbstractEntity{
	
	String getRid();
	
	String getKey();
	
	String getUri();
	
	String getDescription();
	
	String getName();
	
	boolean isPackage();
	
	Collection<Resource> getIncludeResource();
}
