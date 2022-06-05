package com.github.krottv.tmstemp.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.krottv.tmstemp.binder.MainActivityDataBinder
import com.github.krottv.tmstemp.binder.MainActivityRecyclerScrollDataBinder

class MainActivity : AppCompatActivity() {

    private lateinit var broadcastReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainActivityDataBinder: MainActivityDataBinder =
            MainActivityRecyclerScrollDataBinder(this)
        mainActivityDataBinder.bind()
        appContext = applicationContext
        createReceiver()
    }

    private fun createReceiver() {
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(Intent(this@MainActivity, ChargeModeService::class.java))
                } else {
                    startService(Intent(this@MainActivity, ChargeModeService::class.java))
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        registerReceiver()
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
    }

    private fun registerReceiver() {
        val intentFilter = IntentFilter(Intent.ACTION_POWER_CONNECTED)
        registerReceiver(broadcastReceiver, intentFilter)
    }

    companion object {
        lateinit  var appContext: Context
    }
}