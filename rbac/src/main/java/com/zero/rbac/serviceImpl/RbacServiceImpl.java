package com.zero.rbac.serviceImpl;

import com.google.inject.Singleton;
import com.zero.rbac.domain.api.User;
import com.zero.rbac.service.RbacService;

@Singleton
public class RbacServiceImpl implements RbacService {

	public boolean Login(User user) {
		return true;
	}

}
