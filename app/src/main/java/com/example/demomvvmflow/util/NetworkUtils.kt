package com.example.demomvvmflow.util

import android.content.Context
import android.net.ConnectivityManager
import com.example.demomvvmflow.MyDemoApplication

fun isNetworkAvailable(): Boolean {
    val connectivityManager =
        MyDemoApplication.ctx?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activateNetworkInfo = connectivityManager.activeNetworkInfo
    return activateNetworkInfo != null && activateNetworkInfo.isConnected
}