package com.biubiu.kit.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;
import javax.lang.model.util.Elements;

/**
 * annotated
 * <p>
 * Created by looa on 2018/4/10.
 */

class KitAnnotated {

    private final static String factoryImplName = "kitFactoryImpl";
    private String factoryPackageName = "com.biubiu.kit.core";
    private String packageName = factoryPackageName;//如果设置了applicationId，会更新
    private ClassName interfaceName = ClassName.get(packageName, "IKitFactory");

    private List<KitType> types;

    KitAnnotated(String packageName) {
        if (packageName != null) this.packageName = packageName;
        types = new ArrayList<>();
    }

    void addType(KitType type) {
        types.add(type);
    }

    JavaFile generateKitFactory() {
        MethodSpec.Builder builderMap = MethodSpec.methodBuilder("map")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(void.class);
        ClassName KitFactory = ClassName.get(factoryPackageName, "KitFactory");
        for (KitType type : types) {
            builderMap.addStatement("$T.map($T.class, $T.class)", KitFactory, ClassName.get(type.getFieldType()), type.getType());
        }

        // method bind(final T host)
        MethodSpec.Builder builderCreate = MethodSpec.methodBuilder("create")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(String.class, "type", Modifier.FINAL)
                .returns(Object.class);

        builderCreate.addStatement("return $T.obtain(type)", KitFactory);

        // generate whole class
        TypeSpec finderClass = TypeSpec.classBuilder(factoryImplName)
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(interfaceName)
                .addMethod(builderMap.build())
                .addMethod(builderCreate.build())
                .build();

        // generate file
        return JavaFile.builder(packageName, finderClass).build();
    }
}
