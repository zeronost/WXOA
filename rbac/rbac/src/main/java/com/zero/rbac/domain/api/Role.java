package com.zero.rbac.domain.api;

import java.util.Collection;

public interface Role extends AbstractEntity {

	String getRid();

	String getName();

	String getDescription();

	Collection<Entitle> getEntitles();
}
