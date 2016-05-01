package com.yatatsu.fieldschema.processor;

import com.google.testing.compile.JavaFileObjects;
import javax.tools.JavaFileObject;
import org.junit.Test;

import static com.google.common.truth.Truth.assert_;
import static com.google.testing.compile.JavaSourceSubjectFactory.javaSource;

public class FieldSchemaProcessorTest {

  @Test public void testDuplicateSchemaNames() throws Exception {
    JavaFileObject source = JavaFileObjects.forResource("DuplicateSchemaName.java");

    assert_().about(javaSource())
        .that(source)
        .processedWith(new FieldSchemaProcessor())
        .failsToCompile()
        .withErrorCount(1)
        .withErrorContaining("Duplicate field name and prefix");
  }
}