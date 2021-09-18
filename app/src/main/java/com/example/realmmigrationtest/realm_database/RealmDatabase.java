package com.example.realmmigrationtest.realm_database;

import com.example.realmmigrationtest.realm_database.models.Migrations;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmDatabase {

    private Realm realm = null;

    public RealmDatabase() {
        RealmConfiguration realmConfig = new RealmConfiguration
                .Builder()
                .name("testeMigration")
                .schemaVersion(1)
                .migration(new Migrations())
                .build();

        realm = Realm.getInstance(realmConfig);
    }

    public Realm getInstance() {
        return realm;
    }

    public void closeRealm() {
        if (realm != null && !realm.isClosed()) {
            realm.close();
        }
    }

    public void refreshRealm() {
        if (!realm.isClosed()) {
            realm.refresh();
        }
    }

    public boolean isClosed() {
        return realm.isClosed();
    }
}
