package com.coreleo.util.sql.call;

public class NullParameter implements Parameter {
	private int type;
	

	public NullParameter(int type) {
		super();
		this.type = type;
	}


	public int getType() {
		return type;
	}

	
	public void setType(int type) {
		this.type = type;
	}


	@Override
	public String toString() {
		return "NULL parameter";
	}
	
	
	
	
}
