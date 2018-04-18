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

public class KitAnnotated {

    private String packageName = "com.biubiu.kit.core";
    private String factoryImplName = "kitFactoryImpl";
    private ClassName interfaceName = ClassName.get(packageName, "IKitFactory");

    public Elements elementUtils;
    public List<KitType> types;

    public KitAnnotated(Elements elementUtils) {
        this.elementUtils = elementUtils;
        types = new ArrayList<>();
    }

    public void addType(KitType type) {
        types.add(type);
    }

    public JavaFile generateKitFactory() {
        MethodSpec.Builder builderMap = MethodSpec.methodBuilder("map")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(void.class);
        ClassName KitFactory = ClassName.get(packageName, "KitFactory");
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
