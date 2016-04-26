package com.yatatsu.fieldschema.processor;

import java.io.IOException;
import javax.annotation.processing.Filer;


public class FieldSchemaCodeWriter {

  private final FieldSchemaCollection fieldSchemaCollection;

  FieldSchemaCodeWriter(FieldSchemaCollection fieldSchemaCollection) {
    this.fieldSchemaCollection = fieldSchemaCollection;
  }

  public void write(Filer filer) throws IOException {
    // TODO
  }
}
