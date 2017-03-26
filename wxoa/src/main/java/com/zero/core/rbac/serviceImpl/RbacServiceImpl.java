package com.zero.core.rbac.serviceImpl;

import com.google.inject.Singleton;
import com.zero.core.rbac.api.User;
import com.zero.core.rbac.service.RbacService;

@Singleton
public class RbacServiceImpl implements RbacService {

	public boolean Login(User user) {
		return true;
	}

}
