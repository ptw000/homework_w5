package com.example.admin.homework_w5

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class MovieModel(val vote_count: String, val id: Int, val video: Boolean, val vote_average: Float, val title: String, val popularity: String,
                 val poster_path: String, val original_language: String, val original_title: String, val genre_ids: ArrayList<Int>,
                 val overview: String, val adult: Boolean, val release_date: String, val backdrop_path: String) : Parcelable

