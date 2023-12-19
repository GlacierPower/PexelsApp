package com.glacierpower.pexelsapp.presentation.utils

import androidx.recyclerview.widget.RecyclerView
import com.glacierpower.pexelsapp.utils.Constants.HIDE
import com.glacierpower.pexelsapp.utils.Constants.SHOW

abstract class Scroller : RecyclerView.OnScrollListener() {

    private var scrollDistance = 0
    private var isVisible = true

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (isVisible && scrollDistance > HIDE) {
            hide()
            scrollDistance = 0
            isVisible = false
        } else if (!isVisible && scrollDistance < SHOW) {
            show()
            scrollDistance = 0
            isVisible = true
        }

        if (isVisible && dy > 0 || !isVisible && dy < 0) {
            scrollDistance += dy
        }
    }

    abstract fun show()

    abstract fun hide()
}