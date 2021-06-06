package com.gs.rpc.util;

public class ValidUtils {


    public static void requiredNotNull(Object object, String msg) {
        if (object == null) {
            throw new NullPointerException(msg);
        }
    }

}
