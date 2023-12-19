package com.glacierpower.pexelsapp.utils

import android.content.Context
import android.view.View
import android.widget.Toast

fun toast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun showHide(view: View) {
    view.visibility = if (view.visibility == View.VISIBLE){
        View.INVISIBLE
    } else{
        View.VISIBLE
    }
}