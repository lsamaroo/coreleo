package com.coreleo.util;

/**
 * 
 * @deprecated - use RuntimeUtil instead
 *
 */
public final class MemoryUtil {

	public static final double usedMemory() {
		return RuntimeUtil.usedMemory();
	}

	public static final double freeMemory() {
		return RuntimeUtil.freeMemory();
	}

	public static final double totalMemory() {
		return RuntimeUtil.totalMemory();
	}

	public static final double maxMemory(){
		return RuntimeUtil.maxMemory();
	}
}
