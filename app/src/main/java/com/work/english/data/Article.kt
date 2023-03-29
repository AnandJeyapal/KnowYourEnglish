package com.work.english.data

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Article(val key: String?=null,
                   val title: String? = null,
                   val subTitle: String? = null,
                   val date: String? = null,
                   val content: String? = null,
                   val imageURL: String? = null,
                   @field:JvmField val html: Boolean? = null,
                   @field:JvmField val favorite: Boolean? = false)
