package com.e.learningcleanandroid.api.data

import com.google.gson.annotations.SerializedName
import java.lang.StringBuilder

data class DogPhoto (

   @SerializedName("id")
   var id:String?=null,

   @SerializedName("url")
   var url:String?=null,

   @SerializedName("categories")
   var categories:ArrayList<DogCategories>?=null,

   @SerializedName("breeds")
   var breeds:ArrayList<Breeds>?=null
){

override fun toString(): String {
    return StringBuilder().apply{
        breeds?.apply {
            if (this.isNotEmpty()){
                appendLine("Life span : ${first().lifeSpan ?: "unknown"}")
                appendLine("Origin : ${first().origin ?: "unknown"}")
                appendLine("Name : ${first().name ?: "unknown"}")
            }
        }

    }.toString()
}
}
