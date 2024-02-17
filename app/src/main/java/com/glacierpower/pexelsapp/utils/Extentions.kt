package com.glacierpower.pexelsapp.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.provider.Settings
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.glacierpower.pexelsapp.R

fun toast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.showAlert() {
    val settingIntent = Intent(Settings.ACTION_WIFI_SETTINGS)

    AlertDialog.Builder(requireContext())
        .setTitle(getString(R.string.mobile_data_is_turned_off))
        .setMessage(getString(R.string.turn_on_mobile_data_or_use_wi_fi_to_access_data))
        .setPositiveButton(getString(R.string.settings)) { _, _ ->
            startActivity(settingIntent)
        }
        .setNegativeButton(getString(R.string.ok)) { dialogInterface, _ ->
            dialogInterface.cancel()
        }
        .create().show()
}

fun Fragment.checkMode() {
    val color = ContextCompat.getColor(requireContext(), R.color.white)
    val dark = ContextCompat.getColor(requireContext(), R.color.dark_mode)
    when (context?.resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
        Configuration.UI_MODE_NIGHT_YES -> {
            (activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(dark.toDrawable())
        }

        Configuration.UI_MODE_NIGHT_NO -> {
            (activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(color.toDrawable())
        }

    }
}


fun showHide(view: View) {
    view.visibility = if (view.visibility == View.VISIBLE) {
        View.INVISIBLE
    } else {
        View.VISIBLE
    }
}

fun animate(hold: RecyclerView.ViewHolder, down: Boolean) {
    val animator = ObjectAnimator.ofFloat(hold.itemView, "TranslationX", -100f, 0f)
    animator.interpolator = OvershootInterpolator()
    val animator1 = ObjectAnimator.ofFloat(hold.itemView, "TranslationX", 100f, 0f)
    animator1.interpolator = OvershootInterpolator()
    val animator2 =
        ObjectAnimator.ofFloat(hold.itemView, "TranslationY", if (down) 120f else -120f, 0f)
    animator.interpolator = OvershootInterpolator()
    val animator3 = ObjectAnimator.ofFloat(hold.itemView, "ScaleX", 0.9f, 1f)
    animator3.interpolator = AnticipateOvershootInterpolator()
    val animator4 = ObjectAnimator.ofFloat(hold.itemView, "ScaleY", 0.9f, 1f)
    animator4.interpolator = AnticipateOvershootInterpolator()
    val set = AnimatorSet()
    set.playTogether(animator2, animator3, animator4)
    set.duration = 1000
    set.start()
}