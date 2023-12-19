package com.glacierpower.pexelsapp.presentation.utils

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

abstract class ScrollListener(layoutManager: StaggeredGridLayoutManager) :
    RecyclerView.OnScrollListener() {
    private var visibleItems = 3
    private var currentPage = 0
    private var previousTotalItem = 0
    private var loading = true
    private val startingPage = 0
    private var layoutManager: RecyclerView.LayoutManager = layoutManager

    init {
        visibleItems *= layoutManager.spanCount
    }

    private fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }

    override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {
        var lastVisibleItemPosition = 0
        val totalItemCount = layoutManager.itemCount
        when (layoutManager) {
            is StaggeredGridLayoutManager -> {
                val lastVisibleItemPositions =
                    (layoutManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(null)
                lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions)
            }

            is GridLayoutManager -> {
                lastVisibleItemPosition =
                    (layoutManager as GridLayoutManager).findLastVisibleItemPosition()
            }

            is LinearLayoutManager -> {
                lastVisibleItemPosition =
                    (layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            }
        }


        if (totalItemCount < previousTotalItem) {
            currentPage = startingPage
            previousTotalItem = totalItemCount
            if (totalItemCount == 0) {
                loading = true
            }
        }

        if (loading && totalItemCount > previousTotalItem) {
            loading = false
            previousTotalItem = totalItemCount
        }

        if (!loading && lastVisibleItemPosition + visibleItems > totalItemCount) {
            currentPage++
            onLoadMore(currentPage, totalItemCount, view)
            loading = true
        }
    }


    abstract fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?)

}