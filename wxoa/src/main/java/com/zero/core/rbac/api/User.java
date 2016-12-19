package com.zero.core.rbac.api;

import java.util.Iterator;

public interface User {
	
	public  abstract Iterator<User> getUsersByGroup(Group group);
	
	public abstract String getUserName();
	
	public abstract Iterator<Role> getUserRoles();
	
	public abstract Iterator<Entitle> getUserEntitle();
	
	public abstract boolean checkEntitle();
	
}
