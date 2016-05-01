package com.yatatsu.fieldschema.processor;

import com.yatatsu.fieldschema.annotations.FieldSchemaClass;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
      Map<String, List<String>> schemaMap = new HashMap<>();
      Stream<FieldSchemaClassHolder> fieldSchemaClassHolderList =
          roundEnv.getElementsAnnotatedWith(FieldSchemaClass.class).stream().map(element -> {
            FieldSchemaClass annotation = element.getAnnotation(FieldSchemaClass.class);
            return new FieldSchemaClassHolder((TypeElement) element, annotation.name());
          }).peek(holder -> {
            // Check duplicate name
            if (schemaMap.containsKey(holder.getName())) {
              List<String> schemaList = schemaMap.get(holder.getName());
              holder.getFieldNames().stream().forEach(field -> {
                if (schemaList.contains(field)) {
                  throw new ProcessingException(holder.getTypeElement(),
                      "Duplicate field name and prefix in %s#%s. Use name option to fix it.",
                      holder.getQualifiedClassName(), field);
                }
              });
              schemaList.addAll(holder.getFieldNames());
              schemaMap.put(holder.getName(), schemaList);
            } else {
              schemaMap.put(holder.getName(), holder.getFieldNames());
            }
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
