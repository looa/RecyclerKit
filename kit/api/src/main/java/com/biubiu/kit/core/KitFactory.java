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
    private static String PACKAGE_NAME = "com.biubiu.kit.core";

    public static void map(Class<?> data, Class<?> kit) {
        MAPPINGS.add(new Mapping(data.getName(), kit.getName()));
    }

    public static String translate(Class<?> data) {
        for (Mapping mapping : MAPPINGS) {
            if (mapping.isEquals(data))
                return mapping.getKitClassName();
        }
        return null;
    }

    public static Object obtain(String kitClassName) {
        try {
            Class cls = Class.forName(kitClassName);
            return cls.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static IKitFactory newInstant() {
        try {
            IKitFactory factory = KIT_FACTORY;
            if (factory == null) {
                Class factoryClass = Class.forName(PACKAGE_NAME + ".kitFactoryImpl");
                factory = (IKitFactory) factoryClass.newInstance();
                KIT_FACTORY = factory;
            }
            factory.map();
            return factory;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
