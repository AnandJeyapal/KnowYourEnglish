package com.work.english.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.work.english.R
import com.work.english.data.Article

class RecentArticleAdapter(var articles: List<Article>, var articleClickListener: ArticleClickListener) :
    RecyclerView.Adapter<RecentArticleAdapter.ArticleHolder>() {

    class ArticleHolder(view: View): RecyclerView.ViewHolder(view) {
        val image: ImageView
        val titleTextView: TextView
        init {
            image = itemView.findViewById(R.id.recent_article)
            titleTextView = itemView.findViewById(R.id.article_title)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_recent_pager_row, parent, false)
        return ArticleHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleHolder, position: Int) {
        val article = articles[position]
        holder.titleTextView.text = article.title
        holder.image.load(article.imageURL) {
            crossfade(true)
            placeholder(R.drawable.placeholder)
        }
        holder.itemView.setOnClickListener { articleClickListener.onArticleClicked(article) }
    }

    override fun getItemCount(): Int {
        return articles.size
    }
}