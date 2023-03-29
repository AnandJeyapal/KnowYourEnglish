package com.work.english.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.work.english.data.Article
import com.work.english.data.ArticleResponse
import com.work.english.data.ArticlesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ArticleDetailViewModel @Inject constructor(val articlesRepository: ArticlesRepository) : ViewModel() {

    var articleLiveDate: LiveData<ArticleResponse> = MutableLiveData()

    fun getArticle(key: String) {
        articleLiveDate = liveData(Dispatchers.IO) {
            emit((articlesRepository.getArticle(key)))
        }
    }

}