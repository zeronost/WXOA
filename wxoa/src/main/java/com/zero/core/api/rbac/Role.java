package com.zero.core.api.rbac;

import java.util.Iterator;

public interface Role {
	
	public abstract Iterator<Role> getRoles();

}
