package com.zero.rbac.domain.api;

import java.util.Date;

public interface AbstractEntity {
	
	String getCreatedBy();
	
	Date getCreatedTs();
	
	String getUpdatedBy();
	
	Date getUpdatedTs();
	
}
