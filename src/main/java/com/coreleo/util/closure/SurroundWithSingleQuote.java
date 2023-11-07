package com.coreleo.util.closure;

public class SurroundWithSingleQuote implements Block {
	private final static String SINGLE_QOUTE = "'";

	public Object invoke(Object x) {
		return SINGLE_QOUTE + x + SINGLE_QOUTE;
	}
}