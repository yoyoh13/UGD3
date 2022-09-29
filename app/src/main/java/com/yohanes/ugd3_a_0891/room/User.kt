package com.yohanes.ugd3_a_0891.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "username")
    val username: String,
    val password: String,
    val email: String,
    val tglLahir: String,
    val telepon: String,
    val Alamat: String
    )
