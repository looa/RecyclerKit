package com.biubiu.kit.core;

import java.util.ArrayList;
import java.util.List;

/**
 * kit factory.
 * Created by ran on 2018/1/11.
 */

public class KitFactory {

    private static IKitFactory KIT_FACTORY = null;
    private static final List<Mapping> MAPPINGS = new ArrayList<>();

    public static void map(Class<?> data, Class<?> kit) {
        MAPPINGS.add(new Mapping(data.getName(), kit.getName()));
    }

    static String translate(Class<?> data) {
        for (Mapping mapping : MAPPINGS) {
            if (mapping.isEquals(data))
                return mapping.getKitClassName();
        }
        return null;
    }

    public static Object obtain(String kitClassName) {
        try {
            Class<?> cls = Class.forName(kitClassName);
            return cls.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static IKitFactory newInstant(String applicationId) {
        IKitFactory factory = KIT_FACTORY;
        if (factory == null) {
            String defaultPackageName = "com.biubiu.kit.core";
            Class<?> factoryClass = getClass(applicationId);
            factoryClass = factoryClass == null ?
                    getClass(defaultPackageName) :
                    factoryClass;
            if (factoryClass == null) {
                return null;
            }
            try {
                factory = (IKitFactory) factoryClass.newInstance();
                factory.map();
                KIT_FACTORY = factory;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return factory;
    }

    private static Class<?> getClass(String applicationId) {
        Class<?> factoryClass = null;
        try {
            factoryClass = Class.forName(applicationId + ".kitFactoryImpl");
            return factoryClass;
        } catch (Exception ignored) {
        }
        try {
            factoryClass = Class.forName("com.biubiu.kit.core.kitFactoryImpl");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return factoryClass;
    }
}
