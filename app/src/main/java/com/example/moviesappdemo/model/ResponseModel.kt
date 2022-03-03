package com.example.moviesappdemo.model

data class ResponseModel(
    val dates: Dates,
    val page: Int,
    val results: List<MovieModel>,
    val total_pages: Int,
    val total_results: Int
)