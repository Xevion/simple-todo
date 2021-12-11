package com.embers.simpleto_do

import android.util.Log
import android.view.View

abstract class DoubleClickListener : View.OnClickListener {
    private var lastClick: Long = 0

    override fun onClick(v: View) {
        val curClick = System.currentTimeMillis()
        if (curClick - lastClick < DOUBLE_CLICK_TIME_DELTA)
            onDoubleClick(v)
        lastClick = curClick
    }

    abstract fun onDoubleClick(item: View)

    companion object {
        private const val DOUBLE_CLICK_TIME_DELTA: Long = 300 // Milliseconds
    }
}