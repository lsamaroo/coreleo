/**
 *
 */
package com.coreleo.util;

/**
 *
 * The intended purpose of this class is to act as a central point for accessing
 * resources.
 *
 *
 *
 * @author Leon Samaroo
 *
 */
import java.io.File;
import java.util.Properties;

/**
 * This class is a generic helper used to get and set resources i.e. files,
 * attributes, etc.
 *
 * **Used by the bundle and message tags for caching the ResourceBundle.**
 *
 *
 */
public final class ResourceHelper {

    private ResourceHelper() {
    }

    /**
     * @deprecated
     * @param filename - the properties file to load.
     * @return the Properties object associated with the filename, null if unable to
     *         locate the file.
     */
    @Deprecated
    public Properties getProperties(final String filename) {
        return IOUtil.getProperties(filename, getClass());
    }

    /**
     * 
     * <p>
     * This method attempts to locate a resource when the path to that resource is a
     * relative path. The task of locating the resource specified by param fileName
     * is delegated to <code>Class.getResource</code>.
     * </p>
     * <p>
     * This is an alternative to using <code>ServletContent.getResource</code>. For
     * example, when a <code>ServletContext</code> is not readily available such as
     * in a POJO (Plain Ordinary Java Objec).
     * </p>
     * 
     * @see <code>ServletContent.getResource</code>
     * @see <code>Class.getResource</code>
     * 
     * @param fileName - the filename of the resource.
     * @return the File object represent the resource, null if unable to locate the
     *         resource.
     */
    public File getFile(final String fileName) {
        return IOUtil.getFile(fileName, getClass());
    }

    // Initialization on Demand Holder idiom
    private static class ResourceHelperSingletonHolder {
        private static final ResourceHelper instance = new ResourceHelper();
    }

    public static ResourceHelper getInstance() {
        return ResourceHelperSingletonHolder.instance;
    }

}
