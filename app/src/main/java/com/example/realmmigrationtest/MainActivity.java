package com.example.realmmigrationtest;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.realmmigrationtest.realm_database.RealmDatabase;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    private RealmDatabase realmDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.realmDatabase = new RealmDatabase();
    }

    @Override
    protected void onPause() {
        super.onPause();

        this.realmDatabase.closeRealm();
    }
}