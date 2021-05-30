package com.e.learningcleanandroid.api.data

import com.google.gson.annotations.SerializedName

class Breeds (

    @SerializedName("id")
    var id:String?=null,

    @SerializedName("name")
    var name:String?=null,

    @SerializedName("temperament")
    var temperament:String?=null,

    @SerializedName("wikipedia_url")
    var wikipedia_url:String?=null,

    @SerializedName("origin")
    var origin:String?=null,

    @SerializedName("life_span")
    var lifeSpan:String?=null,

    @SerializedName("alt_names")
    var alt_names:String?=null
)