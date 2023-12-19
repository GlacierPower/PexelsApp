package com.glacierpower.pexelsapp.utils

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

object NavHelper {
    fun Fragment.navigate(destination: Int) {
        findNavController().navigate(destination)
    }
}