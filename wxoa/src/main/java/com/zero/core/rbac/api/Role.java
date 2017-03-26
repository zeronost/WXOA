package com.zero.core.rbac.api;

import java.util.Collection;

import com.zero.core.common.api.AbstractEntity;

public interface Role extends AbstractEntity {

	String getRid();

	String getName();

	String getDescription();

	Collection<Entitle> getEntitles();
}
