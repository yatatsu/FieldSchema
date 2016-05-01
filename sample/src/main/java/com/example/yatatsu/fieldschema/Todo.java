package com.example.yatatsu.fieldschema;

import com.yatatsu.fieldschema.annotations.FieldSchemaClass;
import io.realm.RealmModel;
import io.realm.annotations.RealmClass;
import java.util.Date;

@RealmClass @FieldSchemaClass(name = "myTodo") public class Todo implements RealmModel {
  public String name;
  public String description;
  public Date createdAt;
  public String version;
}
