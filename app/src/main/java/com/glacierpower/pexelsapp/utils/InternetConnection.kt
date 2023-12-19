package com.glacierpower.pexelsapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log
import com.glacierpower.pexelsapp.R
import com.glacierpower.pexelsapp.utils.Constants.INTERNET
import javax.inject.Inject

class InternetConnection @Inject constructor(
    private val context: Context
) {

    @RequiresApi(Build.VERSION_CODES.M)
    fun isOnline(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i(INTERNET, context.getString(R.string.networkcapabilities_transport_cellular))
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i(INTERNET, context.getString(R.string.networkcapabilities_transport_wifi))
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i(INTERNET, context.getString(R.string.networkcapabilities_transport_ethernet))
                return true
            }
        }
        return false
    }

}