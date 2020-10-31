package com.shalu.android.datastore.pref

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.shalu.android.datastore.R
import com.shalu.android.datastore.manager.MigrationManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MigrateToPrefDataStoreActivity : AppCompatActivity() {

    private lateinit var migrationManager: MigrationManager
    private lateinit var sharedpreferences: SharedPreferences
    private lateinit var tvPrefFirstName: TextView
    private lateinit var tvPrefLastName: TextView
    private lateinit var tvDatastoreFirstName: TextView
    private lateinit var tvDatastoreLastName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_migrate_datastore)
        title = getString(R.string.migrate_preferences_datastore)

        tvPrefFirstName = findViewById(R.id.tvPref_first_name)
        tvPrefLastName = findViewById(R.id.tvPref_last_name)
        tvDatastoreFirstName = findViewById(R.id.tv_datastore_first_name)
        tvDatastoreLastName = findViewById(R.id.tv_datastore_last_name)

        setSharedPreferenceData("Roberto", "Bagio")

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

        displaySharedPreferenceData()
    }

    @SuppressLint("SetTextI18n")
    private fun displaySharedPreferenceData() {
        // verify data exists in shared preferences
        tvPrefFirstName.text =
            "FirstName : ${sharedpreferences.getString(MigrationManager.USER_FIRST_NAME, "")}"
        tvPrefLastName.text =
            "LstName : ${sharedpreferences.getString(MigrationManager.USER_LAST_NAME, "")}"
    }

    @SuppressLint("SetTextI18n")
    private fun migrateToPreferencesDataStore() {
        migrationManager = MigrationManager(this)

        GlobalScope.launch {
            migrationManager.userDetailsFlow.collect {
                withContext(Dispatchers.Main) {
                    //Update UI
                    tvDatastoreFirstName.text = "FirstName :  ${it.firstName}"
                    tvDatastoreLastName.text = "LastName : ${it.lastName}"
                    displaySharedPreferenceData();
                }
            }
        }
    }
}