package com.asmat.rolando.bakingapp.Utils;

import java.util.ArrayList;

/**
 * Created by rolandoasmat on 8/13/17.
 */

public class ArrayUtils {

    public static <T> ArrayList<T> toArrayList(T[] array) {
        ArrayList arrayList = new ArrayList();
        for(int i = 0; i < array.length; i++) {
            arrayList.add(array[i]);
        }
        return arrayList;
    }

    public static <T> T[] toArray(ArrayList<T> arrayList) {
        T[] array = (T[]) new Object[arrayList.size()];
        for(int i = 0; i < arrayList.size(); i++) {
            array[i] = arrayList.get(i);
        }
        return array;
    }
}
