package com.example.uas__ppapb.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie")
data class DataMovie(
    val title: String = "",
    val description: String = "",
    val date: String = "",
    val genre: String = "",
    val director: String = "",
    val imageURL: String = "",
    var id: String? = null
)