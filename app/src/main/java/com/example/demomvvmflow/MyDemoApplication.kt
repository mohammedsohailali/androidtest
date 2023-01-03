package com.example.demomvvmflow

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule

class MyDemoApplication : Application(), KodeinAware {
    @RequiresApi(Build.VERSION_CODES.N)
    override val kodein = Kodein.lazy {
        import(androidXModule(this@MyDemoApplication))
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        var ctx: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        ctx = applicationContext
    }
}