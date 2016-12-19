package com.zero.core.rbac.api;

import java.util.Iterator;

public interface Role {
	
	public abstract Iterator<Role> getRoles();

}
