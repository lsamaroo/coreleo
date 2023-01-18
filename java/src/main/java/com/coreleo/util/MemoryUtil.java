package com.coreleo.util;

/**
 *
 * @deprecated - use RuntimeUtil instead
 *
 */
@Deprecated
public final class MemoryUtil {

    public static double usedMemory() {
        return RuntimeUtil.usedMemory();
    }

    public static double freeMemory() {
        return RuntimeUtil.freeMemory();
    }

    public static double totalMemory() {
        return RuntimeUtil.totalMemory();
    }

    public static double maxMemory() {
        return RuntimeUtil.maxMemory();
    }
}
