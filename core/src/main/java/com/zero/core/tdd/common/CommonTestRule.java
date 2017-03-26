package com.zero.core.tdd.common;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import com.zero.core.tdd.annotation.DataPrepare;
import com.zero.core.tdd.annotation.TDD;
import com.zero.core.util.AnnotationUtil;

public class CommonTestRule implements TestRule {

	private CommonTestRule() {

	}

	private static CommonTestRule rule;

	public static TestRule getRule() {
		if (rule == null) {
			synchronized (CommonTestRule.class) {
				if (rule == null)
					rule = new CommonTestRule();
			}
		}
		return rule;
	}

	public Statement apply(Statement base, Description description) {

		if (AnnotationUtil.annotatedWith(description, TDD.class)) {
			System.out.println("This class is a TDD testcase");
		}
		if (AnnotationUtil.annotatedWith(description, DataPrepare.class)) {
			System.out.println("Invoke DataPrepare process");
		}
		return base;
	}

}
