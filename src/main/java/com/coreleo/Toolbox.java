/**
 * 
 */
package com.coreleo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Leon Samaroo
 * 
 * <br/>
 * 
 * Use this class to store single instances of Objects which the
 * application needs on a global level, instead of creating numerous singleton
 * classes.
 * 
 */

public class Toolbox {
    private final Map<String, Object> map = new ConcurrentHashMap<String, Object>();


    // restrict creating instances directly
    private Toolbox() {
    }


    public Object get(String key) {
        return map.get(key);
    }


    public void register(String key, Object component) {
        map.put(key, component);
    }


    public void deregister(String key) {
        map.remove(key);
    }

    // Initialization on Demand Holder idiom
    private static class GlobalToolboxSingletonHolder {
        private static final Toolbox INSTANCE = new Toolbox();
    }


    public static Toolbox getInstance() {
        return GlobalToolboxSingletonHolder.INSTANCE;
    }
}
