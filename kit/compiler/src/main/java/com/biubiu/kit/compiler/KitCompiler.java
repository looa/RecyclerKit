package com.biubiu.kit.compiler;

import com.biubiu.kit.annotation.Kit;
import com.google.auto.service.AutoService;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class KitCompiler extends AbstractProcessor {

    private Filer filer;//文件相关的辅助类
    private Elements elementUtils;//元素相关的辅助类
    private static Messager messager;//日志相关的辅助类

    private KitAnnotated annotatedClass;


    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnv.getFiler();
        elementUtils = processingEnv.getElementUtils();
        messager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        annotatedClass = null;
        try {
            processKitAnnotation(roundEnvironment);
        } catch (Exception e) {
            error(e.getMessage());
            return true;
        }
        if (annotatedClass != null) {
            try {
                info("Generating file for %s", annotatedClass.getClass().getName());
                annotatedClass.generateKitFactory().writeTo(filer);
            } catch (Exception e) {
                e.printStackTrace();
                return true;
            }
        }
        return true;
    }

    private void processKitAnnotation(RoundEnvironment roundEnvironment) throws IllegalArgumentException {
        for (Element element : roundEnvironment.getElementsAnnotatedWith(Kit.class)) {
            if (annotatedClass == null) {
                annotatedClass = new KitAnnotated(elementUtils);//typeElement
            }
            KitType type = new KitType(element);
            annotatedClass.addType(type);
        }
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(Kit.class.getCanonicalName());
        return types;
    }

    static void error(String msg, Object... args) {
        messager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args));
    }

    static void info(String msg, Object... args) {
        messager.printMessage(Diagnostic.Kind.NOTE, String.format(msg, args));
    }
}
