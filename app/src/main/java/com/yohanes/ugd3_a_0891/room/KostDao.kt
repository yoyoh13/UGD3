package com.yohanes.ugd3_a_0891.room

import androidx.room.*

@Dao
interface KostDao {
    @Insert
    fun addKost(kost: Kost)

    @Update
    fun updateKost(kost: Kost)

    @Delete
    fun deleteKost(kost: Kost)

    @Query("SELECT * FROM Kost")
    fun getKost() : List<Kost>

    @Query("SELECT * FROM Kost WHERE id =:kost_id")
    fun getKost(kost_id: Int) : Kost?
}