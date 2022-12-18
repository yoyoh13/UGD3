package com.yohanes.ugd3_a_0891.room

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class User(
    val id: String,
    @ColumnInfo(name = "username")
    val username: String,
    val password: String,
    val email: String,
    val tglLahir: String,
    val telepon: String,
    val alamat: String,
)
