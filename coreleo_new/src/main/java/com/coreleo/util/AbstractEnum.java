/**
 * 
 * This is an abstract class that represents an Enumeration Constant.
 * It provides the functionality of the enum type (introduced in JDK1.5).  When the codebase
 * is upgraded to JDK1.5, it may be replaced by the Enum type.
 * 
 * 
 */
package com.coreleo.util;

/**
 * @author Leon Samaroo
 * @deprecated
 */
public abstract class AbstractEnum implements Comparable {

	private String name;


	protected AbstractEnum(String name) {
		super();
		this.name = name;
	}


	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return this.name;
	}


	public String toString() {
		return name;
	}


	public int compareTo(String string) {
		return name.compareTo(string);
	}


	public int compareTo(Object obj) {
		AbstractEnum abstractEnum = (AbstractEnum) obj;
		return name.compareTo(abstractEnum.getName());
	}


	public boolean equals(String string) {
		return compareTo(string) == 0 ? true : false;
	}


	public boolean equals(Object obj) {
		if( obj == null ){
			return false;
		}
		return compareTo(obj) == 0 ? true : false;
	}


	public int hashCode() {
		return name.hashCode();
	}

}
