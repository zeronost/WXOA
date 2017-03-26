package com.zero.core.rbac.module;

import com.google.inject.AbstractModule;
import com.zero.core.rbac.api.User;
import com.zero.core.rbac.impl.AbstractUser;
import com.zero.core.rbac.impl.NormalUser;
import com.zero.core.rbac.service.RbacService;
import com.zero.core.rbac.serviceImpl.RbacServiceImpl;

public class RbacDomainModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(RbacService.class).to(RbacServiceImpl.class);
		bind(User.class).to(AbstractUser.class);
		bind(AbstractUser.class).to(NormalUser.class);
		
	}
}
