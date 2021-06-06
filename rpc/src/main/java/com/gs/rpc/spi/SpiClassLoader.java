package com.gs.rpc.spi;

import com.gs.rpc.exception.SpiClassLoaderException;

import java.util.Iterator;
import java.util.ServiceLoader;

public class SpiClassLoader {

    public static <S> S loadClass(Class<S> clazz) {
        ServiceLoader<S> serviceLoader = ServiceLoader.load(clazz);
        Iterator<S> iterator = serviceLoader.iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }
        throw new SpiClassLoaderException();
    }

}
