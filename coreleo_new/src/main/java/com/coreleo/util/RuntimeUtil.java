package com.coreleo.util;


public final class RuntimeUtil {

	public static final double usedMemory() {
		Runtime runtime = Runtime.getRuntime();
		return (double) runtime.totalMemory() - runtime.freeMemory();
	}

	public static final double freeMemory() {
		return Runtime.getRuntime().freeMemory();
	}

	public static final double totalMemory() {
		return Runtime.getRuntime().totalMemory();
	}

	public static final double maxMemory(){
		return Runtime.getRuntime().maxMemory();		
	}
}
