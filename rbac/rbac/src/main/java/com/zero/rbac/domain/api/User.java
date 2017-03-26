package com.zero.rbac.domain.api;

import java.util.Collection;

public interface User{
	
	String getUid();
	
	String getAccount();
	
	String getUserName();
	
	String getType();
	
	String getFingerPrints();
	
	String getPassWord();
	
	Collection<Role> getRoles();
}
