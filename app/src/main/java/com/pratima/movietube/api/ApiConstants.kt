package com.pratima.movietube.api

class ApiConstants {
    companion object {
        //Example API Request
        //https://api.themoviedb.org/3/movie/550?api_key=325a2832e37f32ffdf4b2c29749922d8
        //https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=325a2832e37f32ffdf4b2c29749922d8
        //https://api.themoviedb.org/3/discover/tv?sort_by=popularity.desc&api_key=325a2832e37f32ffdf4b2c29749922d8

        const val API_BASE_URL = "https://api.themoviedb.org/"
        const val API_KEY = "325a2832e37f32ffdf4b2c29749922d8"
        const val MOVIE_IMG_BASE_URL = "https://image.tmdb.org/t/p/w185"
        const val POPULAR_DESC = "popularity.desc"
        var emailList = listOf("abc@gmail.com","dcc@gmail.com")
        var passwordList = listOf("abc123","qwerty")
    }
}