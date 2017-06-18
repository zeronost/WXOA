package com.zero.rbac.service;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.zero.core.tdd.annotation.DataPrepare;
import com.zero.core.tdd.annotation.TDD;
import com.zero.core.tdd.common.TddManager;
import com.zero.rbac.domain.api.User;
import com.zero.rbac.module.RbacDomainModule;

@TDD
public class RbacServiceTest {

	private Injector injector = TddManager.getInjector(new RbacDomainModule());

	@Rule
	public TestRule rule = TddManager.getTestRule();

	@Inject
	private RbacService rbacService;

	@Inject
	private User user;

	@Before
	public void init() {
		injector.injectMembers(this);
	}

	@After
	public void release() {

	}

	@Test
	@DataPrepare
	public void testCreateUser() throws Exception {
		assertTrue(rbacService.Login(user));
	}

	/*
	 * @Test public void testReadUser() throws Exception {
	 * 
	 * }
	 * 
	 * @Test public void testUpdateUser() throws Exception {
	 * 
	 * }
	 * 
	 * @Test public void testDeleteUser() throws Exception {
	 * 
	 * }
	 */

}
