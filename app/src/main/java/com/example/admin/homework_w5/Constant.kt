package com.example.admin.homework_w5

class Constant {
    companion object {
        fun getUrlNowPlaying() : String = "https://api.themoviedb.org/3/movie/now_playing?api_key=7519cb3f829ecd53bd9b7007076dbe23"
        fun getUrlTopRate() : String = "https://api.themoviedb.org/3/movie/top_rated?api_key=7519cb3f829ecd53bd9b7007076dbe23"
        fun getImgUrl() : String = "https://image.tmdb.org/t/p/w342/"
        fun getUrlSearch() : String = "https://api.themoviedb.org/3/search/movie?api_key=7519cb3f829ecd53bd9b7007076dbe23&query="
        fun getApiKey() : String = "7519cb3f829ecd53bd9b7007076dbe23"
    }
}
