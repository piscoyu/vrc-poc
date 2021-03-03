package com.daimler.util;

import java.util.*;

public class MapUtil {
    public static <T> List<T> getKeys(Map map) {
        List<T> collection = new ArrayList<T>();

        Set<Map.Entry> set = map.entrySet();
        Iterator<Map.Entry> it = set.iterator();
        Map newMap = new HashMap();
        while (it.hasNext()) {
            Map.Entry en = it.next();
            collection.add((T) en.getKey());
        }
        return collection;
    }

    public static <T> List<T> getValues(Map map) {
        List<T> collection = new ArrayList<T>();

        map.values().forEach(p -> {
            collection.add((T) p);
        });

        return collection;
    }
}
