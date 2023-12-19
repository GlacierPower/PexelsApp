package com.glacierpower.pexelsapp.utils

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.view.View
import android.view.animation.AnticipateOvershootInterpolator
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

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