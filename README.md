# FieldSchema

[![License](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Build Status](https://travis-ci.org/yatatsu/FieldSchema.svg?branch=master)](https://travis-ci.org/yatatsu/FieldSchema)
[![](https://jitpack.io/v/yatatsu/FieldSchema.svg)](https://jitpack.io/#yatatsu/FieldSchema)

Generate field name constant for Android.

## Usage

FieldSchema helps you accessing field name in static.

```java
@FieldSchemaClass(name = "myTodo") public class Todo {
  public String name;
  public String description;
  public Date createdAt;
  public String version;
}
```

Here is generated code.

```java
public final class FS {
  public static final String mytodo_name = "name";

  public static final String mytodo_description = "description";

  public static final String mytodo_createdAt = "createdAt";

  public static final String mytodo_version = "version";
}
```

You can use `FS.todo_name` instead of `"name"`.

Constants named `{class name}_{field name}` in `FS.java`. To avoid collision name, Use `name` option. (The default is class name)

```java
@FieldSchemaClass(name = "myTodo") public class Todo {
  public String name;
}
```

Here is generated.

```java
public final class FS {
  public static final String mytodo_name = "name";
}
```

### Use in library project

When using in library project, `FS.java` cause collision because it has fixed package `com.yatatsu.fieldschema`.
Use support apt option to avoid this, here is example.

```build.gradle
apt {
  arguments {
    fieldSchemaPackage 'com.example.app'
  }
}
```

And generate class is `com.example.app.FS`. The default package is `com.yatatsu.fieldschema`.

## Download

- Distributed by [JitPack](https://jitpack.io). Current version is 
[![](https://jitpack.io/v/yatatsu/FieldSchema.svg)](https://jitpack.io/#yatatsu/FieldSchema)
- You also need `android-apt` plugin.

```groovy
buildscript {
  repositories {
    jcenter()
  }
  dependencies {
    classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
  }
}

allprojects {
  repositories {
    maven { url "https://jitpack.io" }
	}
}
```

```groovy
apply plugin: 'com.neenbedankt.android-apt'

dependencies {
  compile 'com.github.yatatsu.FieldSchema:annotations:X.X.X'
  apt 'com.github.yatatsu.FieldSchema:processor:X.X.X'
}
```

## License

```
Copyright 2016 KITAGAWA, Tatsuya

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
