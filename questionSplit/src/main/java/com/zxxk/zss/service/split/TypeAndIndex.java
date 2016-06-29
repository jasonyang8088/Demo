package com.zxxk.zss.service.split;

public class TypeAndIndex implements Comparable<TypeAndIndex>{

	private String type;
	private Integer  index;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	@Override
	public int compareTo(TypeAndIndex o) {
		return this.getIndex().compareTo(o.getIndex());  
	}
	
}
