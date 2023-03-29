package com.work.english.data

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
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

    fun getArticle(key: String, responseLiveData: MutableLiveData<ArticleResponse>) {
        val response = ArticleResponse()
        articleEndPoint.child(key).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val article = snapshot.getValue<Article>()
                response.article = article
                responseLiveData.value = response
            }

            override fun onCancelled(error: DatabaseError) {
                response.exception = error.toException()
                responseLiveData.value = response
            }

        })
    }

    fun toggleFavorite(articleId: String, favorite: Boolean) {
        articleEndPoint.child(articleId).child("favorite").setValue(favorite)
    }


}