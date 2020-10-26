package com.shalu.android.datastore.proto

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.shalu.android.datastore.R
import com.shalu.android.datastore.UserSettings
import com.shalu.android.datastore.manager.ProtoSettingsManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ProtoDataStoreActivity : AppCompatActivity() {

    private lateinit var protoSettingsManager: ProtoSettingsManager
    private lateinit var outerView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_datastore)
        title = getString(R.string.proto_datastore)
        outerView = findViewById(R.id.outerView)

        protoSettingsManager =
            ProtoSettingsManager(this)

        readSettings()

        findViewById<View>(R.id.btnWhite).setOnClickListener {
            updateSettings(UserSettings.BgColor.COLOR_WHITE)
        }

        findViewById<View>(R.id.btnBlack).setOnClickListener {
            updateSettings(UserSettings.BgColor.COLOR_BLACK)
        }
    }

    fun updateSettings(bgColor: UserSettings.BgColor) {
        GlobalScope.launch {
            protoSettingsManager.updateColor(bgColor)
        }
    }

    fun readSettings() {
        GlobalScope.launch {
            protoSettingsManager.userSettingsFlow.collect {
                when(it.bgColor) {
                    UserSettings.BgColor.COLOR_WHITE -> outerView.setBackgroundColor(
                        ContextCompat.getColor(
                            this@ProtoDataStoreActivity,
                            android.R.color.white
                        )
                    )
                    UserSettings.BgColor.COLOR_BLACK -> outerView.setBackgroundColor(
                        ContextCompat.getColor(
                            this@ProtoDataStoreActivity,
                            android.R.color.black
                        )
                    )
                    else -> {
                        // do nothing
                    }
                }
            }
        }
    }
}