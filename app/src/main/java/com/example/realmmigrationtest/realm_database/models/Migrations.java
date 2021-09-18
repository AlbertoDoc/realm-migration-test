package com.example.realmmigrationtest.realm_database.models;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

public class Migrations implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();

        if (oldVersion == 0) {
            RealmObjectSchema personSchema = schema.get("Person");

            if (personSchema != null) {
                personSchema.addField("cpf", String.class);
            }
        }
    }
}
