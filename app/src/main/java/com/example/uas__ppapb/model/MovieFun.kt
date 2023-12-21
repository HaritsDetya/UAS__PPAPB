package com.example.uas__ppapb.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface MovieFun {
    @Query("SELECT * FROM movie")
    fun getAllMovie(): List<DataMovie>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovie(dataMovie: DataMovie)

    @Update
    fun updateMovie(dataMovie: DataMovie)

    @Delete
    fun deleteMovie(dataMovie: DataMovie)

    @Query("SELECT * FROM movie WHERE id = :id")
    fun getMovieById(id: Int): DataMovie
}