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

class ArticleAdapter( var articles: List<Article>, var articleClickListener: ArticleClickListener) :
    RecyclerView.Adapter<ArticleAdapter.ArticleHolder>() {

    class ArticleHolder(view: View): RecyclerView.ViewHolder(view) {
        val titleText: TextView
        val subTitleText: TextView
        val image: ImageView
        val date: TextView
        init {
            titleText = itemView.findViewById(R.id.title)
            subTitleText = itemView.findViewById(R.id.sub_title)
            image = itemView.findViewById(R.id.image)
            date = itemView.findViewById(R.id.date)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout_article, parent, false)
        return ArticleHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleHolder, position: Int) {
        val article = articles[position]
        holder.titleText.text = article.title
        holder.subTitleText.text = article.subTitle
        holder.date.text = article.date
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