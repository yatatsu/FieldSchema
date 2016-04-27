package com.example.yatatsu.fieldschema;

import com.yatatsu.fieldschema.annotations.FieldSchemaClass;
import java.util.Date;

@FieldSchemaClass public class Todo {
  public String title;
  public String description;
  public Date createdAt;
}
