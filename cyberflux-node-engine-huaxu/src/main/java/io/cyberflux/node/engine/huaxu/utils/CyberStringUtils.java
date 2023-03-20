package io.cyberflux.node.engine.huaxu.utils;

public final class CyberStringUtils {
    /**
     * @param string as: ax-bx-cx
     * @param separator as: '-'
     * @return as: ax
     */
    public static String firstSplit(String string, String separator) {
        return string.substring(0, string.indexOf(separator));
    }

    /**
     * @param string    as: ax-bx-cx
     * @param separator as: '-'
     * @return as: cx
     */
    public static String lastSplit(String string, String separator) {
        return string.substring(string.lastIndexOf(separator) + 1);
    }

    /**
     * @param string    as: ax-bx-cx
     * @param separator as: '-'
     * @return as: bx-cx
     */
    public static String cosFirstSplit(String string, String separator) {
        return string.substring(string.indexOf(separator) + 1);
    }

    /**
     * @param string    as: ax-bx-cx
     * @param separator as: '-'
     * @return as: ax-bx
     */
    public static String cosLastSplit(String string, String separator) {
        return string.substring(0, string.lastIndexOf(separator));
    }
}
