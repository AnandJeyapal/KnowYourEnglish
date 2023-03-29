package com.work.english.utils

import android.text.Html
import android.text.SpannableString
import android.util.Log
import androidx.core.text.HtmlCompat
import com.work.english.data.Article

fun formatArticle(article: Article?): SpannableString {
    if(article == null) {
        return SpannableString("")
    }
    val input = article.content ?: ""
    Log.d("XXX", article.html.toString())
    if (article.html == true) {
        return SpannableString(HtmlCompat.fromHtml(input, HtmlCompat.FROM_HTML_MODE_LEGACY))
    }
    val stringArr = input.split("|$|")
    val stringBuilder = StringBuilder()
    for(part in stringArr) {
        stringBuilder.append(part.trim()).append("\n\n")
    }
    return SpannableString(stringBuilder.toString())
}