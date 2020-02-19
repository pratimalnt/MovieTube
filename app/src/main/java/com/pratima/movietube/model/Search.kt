package com.pratima.movietube.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Search : Serializable {
    var id = 0
    var overview: String? = null
    @SerializedName("vote_average")
    var voteAverage = 0f
    @SerializedName("vote_count")
    var voteCount = 0
    @SerializedName("popularity")
    var popularity = 0.0f
    @SerializedName("poster_path")
    var posterPath: String? = null
    @SerializedName("backdrop_path")
    var backDropPath: String? = null
    @SerializedName("original_language")
    var originalLang: String? = null
    @SerializedName("original_title")
    var originalTitle: String? = null
}