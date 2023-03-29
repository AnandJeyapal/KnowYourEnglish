package com.work.english.ui.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.work.english.data.ArticleResponse
import com.work.english.data.ArticlesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArticleDetailViewModel @Inject constructor(val articlesRepository: ArticlesRepository) : ViewModel() {

    var articleLiveDate: MutableLiveData<ArticleResponse> = MutableLiveData()

    val statusLiveData: MutableLiveData<String> = MutableLiveData("")

    fun getArticle(key: String) {
        articlesRepository.getArticle(key, articleLiveDate)
    }

    fun toggleFavorite(articleId: String) {
        val favorite = articleLiveDate.value?.let { it.article?.favorite ?: false} ?: false
        articlesRepository.toggleFavorite(articleId, !favorite)
    }

    fun onFavoriteError(e: Exception) {
        Log.e("XXX", e.message.toString())
        statusLiveData.value = "Error While Marking Favorite"
    }

}