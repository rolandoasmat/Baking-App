package com.asmat.rolando.rocoton.Utils

/**
 * Created by rolandoasmat on 7/14/17.
 */

class ArrayUtils {
    companion object {
        fun <T> toArrayList(array: Array<T>):ArrayList<T> {
            val arrayList = ArrayList<T>()
            array.forEach { element -> arrayList.add(element) }
            return arrayList
        }
    }
}