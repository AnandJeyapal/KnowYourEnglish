package com.work.english.ui.favorites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.work.english.data.Article
import com.work.english.data.ArticlesRepository
import com.work.english.data.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(val articlesRepository: ArticlesRepository) : ViewModel() {

    var responseLiveData = MutableLiveData<Response>()

    var database: DatabaseReference = Firebase.database.reference
    var articleEndPoint: DatabaseReference = database.child("Articles")

    fun listenFavoriteArticles() {
        viewModelScope.launch {
            articlesRepository.getFavoriteArticles(responseLiveData)
        }
    }
//    fun listenArticles() {
//        articleEndPoint.orderByChild("favorite").equalTo(true).addValueEventListener(object :
//            ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val articles: MutableList<Article> = ArrayList()
//                for (articleSnapshot in dataSnapshot.children) {
//                    articles.add(articleSnapshot.getValue(Article::class.java)!!)
//                }
//                val response = Response()
//                response.articles = articles
//                responseLiveData.value = response
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Getting Post failed, log a message
//                // ...
//            }
//        })
//    }
}