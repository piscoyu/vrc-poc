package com.daimler.util;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListUtil {

    public static <T> Boolean isNotNullOrEmpty(List<T> list) {
        return !isNullOrEmpty(list);
    }

    public static <T> Boolean isNullOrEmpty(List<T> list) {
        if (list == null) {
            return true;
        }

        if (list.size() == 0) {
            return true;
        }

        return false;
    }

    public static <T> List<Integer> convertToIntegerList(List<T> list) {
        if (isNullOrEmpty(list)) {
            return null;
        }

        List<Integer> resultList = new ArrayList<Integer>();

        for (T item : list) {
            try {
                resultList.add(Integer.parseInt(item.toString()));
            } catch (Exception ex) {
                System.out.println(ex);
            }
        }

        return resultList;
    }

    /**
     * convert to map, key is index+1; value is T entity
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> Map<Integer, T> convertToMap(List<T> list) {
        if (isNullOrEmpty(list)) {
            return null;
        }

        Map<Integer, T> map = new HashMap<>();

        for (int index = 0; index < list.size(); index++) {
            map.put(index + 1, list.get(index));
        }
        return map;
    }
}
