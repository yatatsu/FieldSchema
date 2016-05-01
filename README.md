# FieldSchema

[![License](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Build Status](https://travis-ci.org/yatatsu/FieldSchema.svg?branch=master)](https://travis-ci.org/yatatsu/FieldSchema)

[WIP] Generate field name constant (for Java, Android).

## Usage

FieldSchema helps you accessing field name in static.


```
@FieldSchemaClass(name = "myTodo") public class Todo {
  public String name;
  public String description;
  public Date createdAt;
  public String version;
}
```

Here is generated code.

```
public final class FS {
  public static final String todo_name = "name";

  public static final String todo_description = "description";

  public static final String todo_createdAt = "createdAt";

  public static final String todo_version = "version";
}
```

You can use `FS.todo_name` instead of `"name"`.

## Download

Soon

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
