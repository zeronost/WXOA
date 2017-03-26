package com.zero.rbac.module;

import com.google.inject.AbstractModule;
import com.zero.rbac.domain.api.User;
import com.zero.rbac.domain.impl.AbstractUser;
import com.zero.rbac.domain.impl.NormalUser;
import com.zero.rbac.service.RbacService;
import com.zero.rbac.serviceImpl.RbacServiceImpl;

public class RbacDomainModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(RbacService.class).to(RbacServiceImpl.class);
		bind(User.class).to(AbstractUser.class);
		bind(AbstractUser.class).to(NormalUser.class);
		
	}
}
