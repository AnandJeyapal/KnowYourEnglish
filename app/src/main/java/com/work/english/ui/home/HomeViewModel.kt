package com.work.english.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.work.english.data.ArticlesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val articlesRepository: ArticlesRepository) : ViewModel() {

    val responseLiveData = liveData(Dispatchers.IO) {
        emit(articlesRepository.getAllArticles())
    }

}