package com.yatatsu.fieldschema.processor;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import java.util.stream.Stream;
import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;

public class FieldSchemaCodeWriter {

  private static final String TARGET_PACKAGE_NAME = "com.yatatsu.fieldschema";
  private static final String TARGET_CLASS_NAME = "FieldSchema";
  private static final String JOINT_CHARACTER = "_";
  private final Stream<FieldSchemaClassHolder> fieldSchemaClassHolders;

  FieldSchemaCodeWriter(Stream<FieldSchemaClassHolder> fieldSchemaClassHolders) {
    this.fieldSchemaClassHolders = fieldSchemaClassHolders;
  }

  public void write(Filer filer) throws IOException {
    TypeSpec.Builder typeSpecBuilder =
        TypeSpec.classBuilder(TARGET_CLASS_NAME).addModifiers(Modifier.PUBLIC, Modifier.FINAL);
    fieldSchemaClassHolders.flatMap(FieldSchemaCodeWriter::createFieldSpec)
        .forEach(typeSpecBuilder::addField);
    JavaFile.builder(TARGET_PACKAGE_NAME, typeSpecBuilder.build()).build().writeTo(filer);
  }

  private static Stream<FieldSpec> createFieldSpec(FieldSchemaClassHolder holder) {
    return holder.getFieldNames().map(fieldName -> {
      String name = holder.getSimpleClassName() + JOINT_CHARACTER + fieldName;
      return FieldSpec.builder(String.class, name, Modifier.PUBLIC, Modifier.FINAL, Modifier.STATIC)
          .initializer("$S", fieldName)
          .build();
    });
  }
}
