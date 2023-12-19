package com.example.uas__ppapb.model

interface CRUD_movie {
    fun insertMovie(dataMovie: DataMovie)
    fun updateMovie(dataMovie: DataMovie)
    fun deleteMovie(dataMovie: DataMovie)
    fun getAllMovie(): List<DataMovie>
    fun getMovieById(id: Int): DataMovie
}