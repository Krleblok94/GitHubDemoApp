@file:Suppress("DEPRECATION")

package com.kr1.krl3.githubdemoapp.datasource.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class NetworkHandler(private val context: Context) {

    fun isConnectedToNetwork(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }
}