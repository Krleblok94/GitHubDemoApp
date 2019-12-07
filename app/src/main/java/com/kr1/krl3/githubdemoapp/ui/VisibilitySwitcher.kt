package com.kr1.krl3.githubdemoapp.ui

import android.view.View

object VisibilitySwitcher {

    fun switchVisibility(isVisible: Boolean, vararg views: View) {
        val visibility = if (isVisible) View.VISIBLE else View.GONE

        for (view in views) { view.visibility = visibility }
    }
}