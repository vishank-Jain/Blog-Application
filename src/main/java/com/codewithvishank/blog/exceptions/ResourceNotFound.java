package com.codewithvishank.blog.exceptions;

import lombok.Data;

@Data
public class ResourceNotFound extends RuntimeException {
	String resourceName;
	String fieldName;
	long fieldValue;
	String fieldValueS;
	public ResourceNotFound(String resourceName, String fieldName, long fieldValue) {
		super(String.format("%s not found with %s : %s", resourceName,fieldName,fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
		
	public ResourceNotFound(String resourceName, String fieldName,String fieldValueS) {
			super(String.format("%s not found with %s : %s", resourceName,fieldName,fieldValueS));
			this.resourceName = resourceName;
			this.fieldName = fieldName;
			this.fieldValueS = fieldValueS;
	}
	
	

}
