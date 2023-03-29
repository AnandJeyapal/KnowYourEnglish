package com.work.english.adapter

import com.work.english.data.Article

interface ArticleClickListener {
    fun onArticleClicked(article: Article)
}