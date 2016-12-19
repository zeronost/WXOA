package com.zero.core.rbac.impl;

import java.util.Iterator;

import com.zero.core.rbac.api.Entitle;
import com.zero.core.rbac.api.Group;
import com.zero.core.rbac.api.Role;
import com.zero.core.rbac.api.User;

public class DefaultUser extends AbstractUser {
	

	public Iterator<User> getUsersByGroup(Group group) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUserName() {
		// TODO Auto-generated method stub
		return null;
	}

	public Iterator<Role> getUserRoles() {
		// TODO Auto-generated method stub
		return null;
	}

	public Iterator<Entitle> getUserEntitle() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean checkEntitle() {
		// TODO Auto-generated method stub
		return false;
	}

}
