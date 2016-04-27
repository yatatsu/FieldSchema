package com.yatatsu.fieldschema.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import java.util.stream.Stream;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

public class FieldSchemaClassHolder {

  private final TypeElement typeElement;
  private final TypeName typeName;
  private final Stream<String> fieldNames;

  public FieldSchemaClassHolder(TypeElement typeElement) {
    this.typeElement = typeElement;
    this.typeName = ClassName.get(typeElement);
    this.fieldNames = findAllNonPrivateFields(typeElement);
  }

  public TypeElement getTypeElement() {
    return typeElement;
  }

  public TypeName getTypeName() {
    return typeName;
  }

  public Stream<String> getFieldNames() {
    return fieldNames;
  }

  public String getQualifiedClassName() {
    return typeElement.getQualifiedName().toString();
  }

  public String getSimpleClassName() {
    return typeElement.getSimpleName().toString();
  }

  private Stream<String> findAllNonPrivateFields(Element element) {
    return element.getEnclosedElements()
        .stream()
        .filter(
            e -> e.getKind() == ElementKind.FIELD && !e.getModifiers().contains(Modifier.STATIC))
        .map(Object::toString);
  }
}
