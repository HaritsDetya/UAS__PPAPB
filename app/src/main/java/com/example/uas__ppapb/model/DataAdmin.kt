package com.example.uas__ppapb.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "admin")
data class DataAdmin(
    @PrimaryKey val id: Int,
    val name: String? = null,
    val email: String? = null,
    val password: String? = null,
)