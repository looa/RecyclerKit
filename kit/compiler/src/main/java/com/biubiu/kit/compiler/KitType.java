package com.biubiu.kit.compiler;

import com.biubiu.kit.annotation.Kit;
import com.squareup.javapoet.ClassName;

import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;

/**
 * field.
 * <p>
 * Created by looa on 2018/4/10.
 */

public class KitType {
    private String packageName;
    private ClassName type;
    private TypeElement element;

    public KitType(Element element) throws IllegalArgumentException {
        if (!element.getKind().isClass()) {
            throw new IllegalArgumentException(
                    String.format("Only class can be annotated with @%s", Kit.class.getSimpleName()));
        }
        this.element = (TypeElement) element;
        try {
            Kit kit = this.element.getAnnotation(Kit.class);
            kit.ui();
        } catch (MirroredTypeException e) {
            TypeMirror mirror = e.getTypeMirror();
            type = (ClassName) ClassName.get(mirror);
            packageName = type.packageName();
        } catch (Exception ignored) {
        }
    }

    public ClassName getType() {
        return type;
    }

    public String getPackageName() {
        return packageName;
    }

    public Name getFieldName() {
        return element.getSimpleName();
    }

    public TypeMirror getFieldType() {
        return element.asType();
    }
}
