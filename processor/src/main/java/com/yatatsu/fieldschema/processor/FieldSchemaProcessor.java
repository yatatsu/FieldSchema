package com.yatatsu.fieldschema.processor;

import com.yatatsu.fieldschema.annotations.FieldSchemaClass;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Stream;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

@SupportedSourceVersion(SourceVersion.RELEASE_8) @SupportedAnnotationTypes({
    "com.yatatsu.fieldschema.annotations.FieldSchemaClass"
}) public class FieldSchemaProcessor extends AbstractProcessor {

  private Messager messager;
  private Filer filer;

  @Override public synchronized void init(ProcessingEnvironment processingEnv) {
    super.init(processingEnv);
    filer = processingEnv.getFiler();
    messager = processingEnv.getMessager();
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    if (annotations.size() == 0) {
      return true;
    }
    try {
      Stream<FieldSchemaClassHolder> fieldSchemaClassHolderList =
          roundEnv.getElementsAnnotatedWith(FieldSchemaClass.class)
              .stream()
              .map(element -> {
                FieldSchemaClass annotation = element.getAnnotation(FieldSchemaClass.class);
                return new FieldSchemaClassHolder((TypeElement) element, annotation.name());
              });

      new FieldSchemaCodeWriter(fieldSchemaClassHolderList).write(filer);
    } catch (ProcessingException e) {
      messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage(), e.element);
    } catch (IOException e) {
      e.printStackTrace();
      messager.printMessage(Diagnostic.Kind.ERROR, "Error in generating code " + e.getMessage());
    }
    return false;
  }
}
