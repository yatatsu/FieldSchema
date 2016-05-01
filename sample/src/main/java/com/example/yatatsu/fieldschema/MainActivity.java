package com.example.yatatsu.fieldschema;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.yatatsu.fieldschema.FS;
import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

  private Realm realm;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    realm = Realm.getDefaultInstance();

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
