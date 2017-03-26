package com.zero.core.util;

import java.lang.annotation.Annotation;

import org.junit.runner.Description;

public class AnnotationUtil {

	public static boolean annotatedWith(Description description, Class<?> annotation) {
		if (description == null || annotation == null)
			return false;

		for (Annotation ann : description.getAnnotations()) {
			if (ann.annotationType().equals(annotation))
				return true;
		}
		return false;
	}
}
