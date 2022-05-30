package com.github.krottv.tmstemp.binder

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.github.krottv.tmstemp.R
import com.github.krottv.tmstemp.databinding.ActivityMainBinding
import com.github.krottv.tmstemp.view.HostFragment


class MainActivityRecyclerScrollDataBinder(private val activity: AppCompatActivity): MainActivityDataBinder {

    override fun bind() {
        val container = ActivityMainBinding.inflate(LayoutInflater.from(activity))
        activity.setContentView(container.root)

        activity.supportFragmentManager
            .beginTransaction()
            .replace(R.id.host_container, HostFragment())
            .commit()
    }
}