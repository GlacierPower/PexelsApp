package com.glacierpower.pexelsapp.presentation.utils

import androidx.recyclerview.widget.StaggeredGridLayoutManager

internal class StaggeredGridLayoutManager :
    StaggeredGridLayoutManager {
    constructor(spanCount: Int, orientation: Int) : super(spanCount, orientation)

    override fun supportsPredictiveItemAnimations(): Boolean {
        return false
    }
}