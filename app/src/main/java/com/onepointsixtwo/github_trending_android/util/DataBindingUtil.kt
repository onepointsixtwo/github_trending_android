package com.onepointsixtwo.github_trending_android.util

import android.databinding.BindingAdapter
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import org.markdownj.MarkdownProcessor


@BindingAdapter("visible")
fun viewVisibilityAdapter(view: View, visible: Boolean) {
    var visibility = View.VISIBLE
    if (!visible) {
        visibility = View.GONE
    }
    view.visibility = visibility
}

@BindingAdapter("markdown")
fun textViewMarkdownTextAdapter(textView: TextView, markdownText: String) {
    val processor = MarkdownProcessor()
    val htmlText = processor.markdown(markdownText)
    textView.movementMethod = LinkMovementMethod()
    textView.setText(Html.fromHtml(htmlText))
}

@BindingAdapter("imageURL")
fun imageViewImageFromURLAdapter(imageView: ImageView, url: String) {
    Picasso.with(imageView.context).load(url).into(imageView)
}