package com.example.uas__ppapb.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class DataUser(
    @PrimaryKey val id: Int,
    val name: String = "",
    val email: String = "",
    val password: String = "",
)
