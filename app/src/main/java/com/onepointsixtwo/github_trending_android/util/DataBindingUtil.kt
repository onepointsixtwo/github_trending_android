package com.onepointsixtwo.github_trending_android.util

import android.databinding.BindingAdapter
import android.view.View


@BindingAdapter("visible")
fun viewVisibilityAdapter(view: View, visible: Boolean) {
    var visibility = View.VISIBLE
    if (!visible) {
        visibility = View.GONE
    }
    view.visibility = visibility
}