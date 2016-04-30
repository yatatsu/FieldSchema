package com.example.yatatsu.fieldschema;

import com.yatatsu.fieldschema.annotations.FieldSchemaClass;
import java.util.Date;

@FieldSchemaClass(name = "myTodo") public class Todo {
  public String name;
  public String description;
  public Date createdAt;
  private String version;
}
