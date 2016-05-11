package com.example.yatatsu.fieldschema;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

  private Realm realm;
  private static final String TAG = "MainActivity";

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    realm = Realm.getDefaultInstance();

    Log.d(TAG, "Dog.name --> " + com.example.yatatsu.libproject.FS.dog_name);
    Log.d(TAG, "Dog.age  --> " + com.example.yatatsu.libproject.FS.dog_age);

    RealmResults<Todo> todos = realm.where(Todo.class)
        .equalTo(FS.mytodo_version, "1")
        .contains(FS.mytodo_name, "high")
        .findAllSorted(FS.mytodo_createdAt);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    realm.close();
  }
}
