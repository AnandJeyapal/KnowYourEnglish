package com.work.english.data

data class Response(var articles: List<Article>? = null,
                    var exception: Exception? = null)
