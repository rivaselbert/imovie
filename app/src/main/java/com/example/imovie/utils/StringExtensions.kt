package com.example.imovie.utils

import androidx.core.text.HtmlCompat

// Converts escaped HTML tags into readable format
fun String.decodeHtml(): String {
    return HtmlCompat.fromHtml(this, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
}