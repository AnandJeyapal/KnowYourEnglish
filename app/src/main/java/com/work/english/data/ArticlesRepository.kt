package com.work.english.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class ArticlesRepository @Inject constructor() {

    var database: DatabaseReference = Firebase.database.reference
    var articleEndPoint: DatabaseReference = database.child("Articles")

    suspend fun getAllArticles(): Response {
        val response = Response()
        try {
            response.articles = articleEndPoint.get().await().children.map { snapShot ->
                snapShot.getValue(Article::class.java)!!
            }
        } catch (exception: Exception) {
            response.exception = exception
        }
        return response
    }

    fun getFavoriteArticles(responseLiveData: MutableLiveData<Response>)  {
            articleEndPoint.orderByChild("favorite").equalTo(true).addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val articles: MutableList<Article> = ArrayList()
                    for (articleSnapshot in dataSnapshot.children) {
                        articles.add(articleSnapshot.getValue(Article::class.java)!!)
                    }
                    val response = Response()
                    response.articles = articles
                    responseLiveData.value = response
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Getting Post failed, log a message
                    // ...
                }
            })
    }

    suspend fun getArticle(key: String): ArticleResponse {
        val response = ArticleResponse()
        try {
            response.article = articleEndPoint.get().await().child(key).getValue(Article::class.java)
        } catch (exception: Exception) {
            response.exception = exception
        }
        return response
    }


}