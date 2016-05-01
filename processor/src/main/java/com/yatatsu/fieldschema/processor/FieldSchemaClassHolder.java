package com.yatatsu.fieldschema.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import java.util.List;
import java.util.stream.Collectors;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

public class FieldSchemaClassHolder {

  private final TypeElement typeElement;
  private final TypeName typeName;
  private final List<FieldSchemaHolder> fieldSchemaHolders;
  private final String name;

  public FieldSchemaClassHolder(TypeElement typeElement, String name) {
    this.typeElement = typeElement;
    this.typeName = ClassName.get(typeElement);
    if (name == null || name.length() == 0) {
      this.name = getSimpleClassName().toLowerCase();
    } else {
      this.name = name.toLowerCase();
    }
    this.fieldSchemaHolders = findAllNonPrivateFields(typeElement);
  }

  public TypeElement getTypeElement() {
    return typeElement;
  }

  public TypeName getTypeName() {
    return typeName;
  }

  public List<FieldSchemaHolder> getFieldSchemaHolders() {
    return fieldSchemaHolders;
  }

  public String getQualifiedClassName() {
    return typeElement.getQualifiedName().toString();
  }

  public String getSimpleClassName() {
    return typeElement.getSimpleName().toString();
  }

  public String getName() {
    return name;
  }

  private List<FieldSchemaHolder> findAllNonPrivateFields(Element element) {
    return element.getEnclosedElements()
        .stream()
        .filter(
            e -> e.getKind() == ElementKind.FIELD && !e.getModifiers().contains(Modifier.STATIC))
        .map(e -> new FieldSchemaHolder(e, this, name))
        .collect(Collectors.toList());
  }
}
