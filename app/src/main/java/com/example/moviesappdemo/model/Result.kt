package com.example.moviesappdemo.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
@Entity
data class Result(
    @PrimaryKey val id: Int,
    val adult: @RawValue Boolean? = null,
    val backdrop_path: @RawValue String? = null,
    val genre_ids: @RawValue List<Int>? = null,
    val original_language: @RawValue String? = null,
    val original_title: @RawValue String? = null,
    val overview: @RawValue String? = null,
    val popularity: @RawValue Double? = null,
    val poster_path: @RawValue String? = null,
    val release_date: @RawValue String? = null,
    val title: @RawValue String? = null,
    val video: @RawValue Boolean? = null,
    val vote_average: @RawValue Double? = null,
    val vote_count: @RawValue Int? = null,
    val likeStatus: Boolean? = false
) : Parcelable {

}

typealias MovieModel = Result

enum class Query {
    POPULAR_MOVIES, LATEST_MOVIES, UPCOMING_MOVIES
}