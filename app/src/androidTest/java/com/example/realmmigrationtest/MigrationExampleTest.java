package com.example.realmmigrationtest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.example.realmmigrationtest.realm_database.models.Migrations;
import com.example.realmmigrationtest.realm_database.models.Person;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmFieldType;
import io.realm.exceptions.RealmMigrationNeededException;

@RunWith(AndroidJUnit4.class)
public class MigrationExampleTest {

    @Rule
    public final TestRealmConfigurationFactory configFactory = new TestRealmConfigurationFactory();
    private Context context;

    @Before
    public void setUp() throws Exception {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    }

    @Test(expected = RealmMigrationNeededException.class)
    public void migrate_migrationNeededIsThrown() throws Exception {
        String REALM_NAME = "schema0.realm";
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name(REALM_NAME) // Keep using the temp realm.
                .schemaVersion(0) // Original realm version.
                .build();// Get a configuration instance.
        configFactory.copyRealmFromAssets(context, REALM_NAME, realmConfig); // Copy the stored version 1 realm file from assets to a NEW location.
        Realm.getInstance(realmConfig);
        Assert.fail("Should throw a RealmMigrationNeededException");
    }

    @Test
    public void migrate_migrationSuceeds() throws Exception {
        String REALM_NAME = "schema0.realm"; // Same name as the file for the old realm which was copied to assets.
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name(REALM_NAME)
                .schemaVersion(1) // NEW realm version.
                .migration(new Migrations())
                .build();// Get a configuration instance.

        configFactory.copyRealmFromAssets(context, REALM_NAME, realmConfig); // Copy the stored version 1 realm file
        // from assets to a NEW location.
        // Note: the old file is always deleted for you.
        //   by the copyRealmFromAssets.
        Realm realm = Realm.getInstance(realmConfig);

        assertTrue("There is no cpf Field", realm.getSchema().get(Person.class.getSimpleName())
                .hasField("cpf"));

        assertEquals(realm.getSchema().get(Person.class.getSimpleName())
                .getFieldType("cpf"), RealmFieldType.STRING);
        realm.close();
    }
}
