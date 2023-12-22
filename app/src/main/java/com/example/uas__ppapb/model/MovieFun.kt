package com.example.uas__ppapb.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface MovieFun {
    @Query("SELECT * FROM movie")
    fun getAllMovie(): List<DataMovie>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(dataMovie: List<DataMovie>)

    @Transaction
    fun insertMovie(dataMovie: List<DataMovie>) {
        val existingMovies = getAllMovie()
        val insert = dataMovie.filter { movie ->
            existingMovies.none { it.title == movie.title && it.director == movie.director && it.description == movie.description }
        }
        val insertRow = insertAll(insert)
    }
}