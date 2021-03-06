package com.revature.util;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * 
 * 
 *
 */

public class Configuration {

	private List<MetaModel<Class<?>>> metaModelList;
	
	public Configuration addAnnotatedClass(List<Class<?>> annotatedClasses) {

		if(metaModelList == null) {
			this.metaModelList = new LinkedList<MetaModel<Class<?>>>();
		}
		
		for (Class clazz : annotatedClasses) {
			this.metaModelList.add(MetaModel.of(clazz));
		}
		return this;
	}
	
	public List<MetaModel<Class<?>>> getMetaModels() {
		return ((this.metaModelList==null) ? Collections.emptyList() : this.metaModelList);
	}

	@Override
	public String toString() {
		return "Configuration [metaModelList=" + metaModelList + "]";
	}
	
	
}
