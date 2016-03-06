package com.nextplate.core.util;

import java.util.Collection;

public final class Strings
{
    private Strings()
    {
    }

    /**
     * Checks if provided character sequence is empty or not.
     *
     * @param str
     *         provided character sequence
     *
     * @return true if not empty; false otherwise.
     */
    public static boolean isNotEmpty(CharSequence str)
    {
        return !isEmpty(str);
    }

    /**
     * Checks if provided character sequence is empty or not.
     *
     * @param str
     *         provided character sequence
     *
     * @return true if null or empty; false otherwise.
     */
    public static boolean isEmpty(CharSequence str)
    {
        return str == null || str.length() == 0;
    }

    /**
     * Joins all the objects of collection with provided separator.
     *
     * @param coll
     *         collection of objects which need to join
     * @param separator
     *         string between two objects of collection
     * @param <T>
     *         objects of T type
     *
     * @return single string after join of objects.
     */
    public static <T> String join(Collection<T> coll, String separator)
    {
        return join(coll, separator, null);
    }

    /**
     * Joins all the objects of collection with provided separator.
     *
     * @param arr
     *         array of objects which need to join
     * @param separator
     *         string between two objects of collection
     *
     * @return single string after join of objects.
     */
    public static String join(Object[] arr, String separator)
    {
        return join(arr, separator, null);
    }

    /**
     * Joins all the objects of collection with provided separator.
     *
     * @param coll
     *         collection of objects which need to join
     * @param separator
     *         string between two objects of collection
     * @param terminator
     *         terminator string for final string
     * @param <T>
     *         objects of T type
     *
     * @return single string after join of objects.
     */
    public static <T> String join(Collection<T> coll, String separator, String terminator)
    {
        return join(coll.toArray(new Object[coll.size()]), separator, terminator);
    }

    /**
     * Joins all the objects of collection with provided separator.
     *
     * @param arr
     *         array of objects which need to join
     * @param separator
     *         string between two objects of collection
     * @param terminator
     *         terminator string for final string
     *
     * @return single string after join of objects.
     */
    public static String join(Object[] arr, String separator, String terminator)
    {
        StringBuilder sb = new StringBuilder(arr.length * 2);
        for(int i = 0; i < arr.length; i++)
        {
            sb.append(arr[i]);
            if(i < arr.length - 1)
            {
                sb.append(separator);
            }
            else if(terminator != null && arr.length > 0)
            {
                sb.append(terminator);
            }
        }
        return sb.toString();
    }
}
