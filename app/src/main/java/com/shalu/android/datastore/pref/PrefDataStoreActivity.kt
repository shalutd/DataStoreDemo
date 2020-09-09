package com.shalu.android.datastore.pref

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.shalu.android.datastore.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PrefDataStoreActivity : AppCompatActivity() {

    private lateinit var prefSettingsManager: PrefSettingsManager
    private lateinit var outerView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datastore)
        title = getString(R.string.preferences_datastore)
        outerView = findViewById(R.id.outerView)

        prefSettingsManager = PrefSettingsManager(this)

        readSettings()

        findViewById<View>(R.id.btnWhite).setOnClickListener {
            updateSettings(android.R.color.white)
        }

        findViewById<View>(R.id.btnBlack).setOnClickListener {
            updateSettings(android.R.color.black)
        }
    }

    fun updateSettings(color: Int) {
        GlobalScope.launch {
            prefSettingsManager.updateColor(color)
        }
    }

    fun readSettings() {
        GlobalScope.launch {
            prefSettingsManager.userPreferencesFlow.collect {
                outerView.setBackgroundColor(
                    ContextCompat.getColor(
                        this@PrefDataStoreActivity,
                        it.color
                    )
                )
            }
        }
    }
}