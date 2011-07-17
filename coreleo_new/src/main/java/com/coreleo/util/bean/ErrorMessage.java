/**
 * 
 */
package com.coreleo.util.bean;

/**
 * @author Leon Samaroo
 * 
 * A wrapper for a String, it simply makes it clear( and type safe) that the
 * String is an error message. Used by the validation method of a Bean to return
 * error messages.
 * 
 * @deprecated
 * 
 */
public class ErrorMessage {

	private String message;


	public ErrorMessage() {
		super();
	}


	public ErrorMessage(String message) {
		super();
		this.message = message;
	}


	public String getMessage() {
		return this.message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public String toString() {
		return message;
	}


}
