package com.yatatsu.fieldschema.processor;

import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;


public class FieldSchemaAnnotatedClass {

  final TypeElement typeElement;
  final Elements elements;
  final Types types;

  public FieldSchemaAnnotatedClass(TypeElement typeElement, Elements elements, Types types) {
    this.typeElement = typeElement;
    this.elements = elements;
    this.types = types;
  }
}
