package com.yatatsu.fieldschema.processor;

import com.yatatsu.fieldschema.annotations.FieldSchemaClass;
import java.io.IOException;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes({
    "FieldSchemaClass"
})
public class FieldSchemaProcessor extends AbstractProcessor {

  private Messager messager;
  private Elements elements;
  private Filer filer;
  private Types types;

  @Override public synchronized void init(ProcessingEnvironment processingEnv) {
    super.init(processingEnv);
    elements = processingEnv.getElementUtils();
    filer = processingEnv.getFiler();
    messager = processingEnv.getMessager();
    types = processingEnv.getTypeUtils();
  }

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    if (annotations.size() == 0) {
      return true;
    }
    try {
      FieldSchemaCollection fieldSchemaCollection = new FieldSchemaCollection();
      roundEnv.getElementsAnnotatedWith(FieldSchemaClass.class)
          .stream()
          .map(element -> new FieldSchemaAnnotatedClass((TypeElement) element, elements, types))
          .forEach(fieldSchemaCollection::applyAnnotatedClass);

      new FieldSchemaCodeWriter(fieldSchemaCollection).write(filer);

    } catch (ProcessingException e) {
      messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage(), e.element);
    } catch (IOException e) {
      e.printStackTrace();
      messager.printMessage(Diagnostic.Kind.ERROR,
          "Error in generating code " + e.getMessage());
    }
    return false;
  }
}
