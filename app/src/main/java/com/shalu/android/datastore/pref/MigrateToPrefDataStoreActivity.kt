package com.shalu.android.datastore.pref

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.shalu.android.datastore.R
import com.shalu.android.datastore.manager.MigrationManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MigrateToPrefDataStoreActivity : AppCompatActivity() {

    private lateinit var migrationManager: MigrationManager
    private lateinit var sharedpreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_migrate_datastore)
        title = getString(R.string.migrate_preferences_datastore)

        setSharedPreferenceData("Shalu", "TD")

        findViewById<View>(R.id.btnMigrate).setOnClickListener {
            migrateToPreferencesDataStore()
        }
    }

    private fun setSharedPreferenceData(firstName: String, lastName: String) {
        sharedpreferences = getSharedPreferences(
            MigrationManager.USER_PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )
        sharedpreferences.edit().putString(MigrationManager.USER_FIRST_NAME, firstName).putString(
            MigrationManager.USER_LAST_NAME, lastName
        ).apply()

        readSharedPreferenceData()
    }

    private fun readSharedPreferenceData() {
        // verify data exists in shared preferences
        Log.i(
            "TEST",
            "FIRSTNAME = ${sharedpreferences.getString(MigrationManager.USER_FIRST_NAME, "")}"
        )
        Log.i(
            "TEST",
            "LASTNAME = ${sharedpreferences.getString(MigrationManager.USER_LAST_NAME, "")}"
        )
    }

    private fun migrateToPreferencesDataStore() {
        migrationManager =
            MigrationManager(this)

        GlobalScope.launch {
            migrationManager.userDetailsFlow.collect {
                Log.i(
                    "TEST",
                    "Migrated FIRSTNAME = ${it.firstName}"
                )

                Log.i(
                    "TEST",
                    "Migrated LASTNAME = ${it.lastName}"
                )
            }
            readSharedPreferenceData()
        }
    }
}