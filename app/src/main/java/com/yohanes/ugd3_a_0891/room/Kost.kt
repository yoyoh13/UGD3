package com.yohanes.ugd3_a_0891.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Kost(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val username: String,
    val Alamat: String
)
