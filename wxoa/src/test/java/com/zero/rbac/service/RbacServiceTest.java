package com.zero.rbac.service;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.zero.core.rbac.module.RbacDomainModule;
import com.zero.core.rbac.api.User;
import com.zero.core.rbac.service.RbacService;

public class RbacServiceTest {

	private Injector injector = Guice.createInjector(new RbacDomainModule());

	@Inject
	private RbacService rbacService;

	@Inject
	private User user;

	/*
	 * @Rule public static TestRule rule;
	 */

	@Before
	public void init() {
		injector.injectMembers(this);
	}

	@After
	public void release() {

	}

	@Test
	public void testCreateUser() throws Exception {
		assertTrue(rbacService.Login(user));
	}

	@Test
	public void testReadUser() throws Exception {
		
	}

	@Test
	public void testUpdateUser() throws Exception {

	}

	@Test
	public void testDeleteUser() throws Exception {

	}

}
