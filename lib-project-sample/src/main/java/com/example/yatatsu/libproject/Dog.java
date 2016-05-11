package com.example.yatatsu.libproject;

import com.yatatsu.fieldschema.annotations.FieldSchemaClass;
import io.realm.RealmObject;

@FieldSchemaClass public class Dog extends RealmObject {
  public String name;
  public int age;
}
