package com.example.uas__ppapb.model

class RepoMovie(private val movieFun: MovieFun) {

    fun getAllMovie() = movieFun.getAllMovie()
    fun insertMovie(dataMovie: DataMovie) = movieFun.insertMovie(dataMovie)
    fun updateMovie(dataMovie: DataMovie) = movieFun.updateMovie(dataMovie)
    fun deleteMovie(dataMovie: DataMovie) = movieFun.deleteMovie(dataMovie)
    fun getMovieById(id: Int) = movieFun.getMovieById(id)
}