package com.pratima.movietube.model

data class DataModel(var id: Int, var title: String, var overview: String,
                     var results: ArrayList<DataModel>, var vote_average: Float, var vote_count: Int,  var popularity: Float, var total_results: Int, var poster_path: String, var backdrop_path: String, var original_title: String, var original_name: String ) {

}