package com.yohanes.ugd3_a_0891.api

class KostAPI {
    companion object{
        val BASE_URL = "http://192.168.100.6/Tubes_1_Kost/kost"

        val GET_ALL_URL = BASE_URL + "kost"
        val GET_BY_ID_URL = BASE_URL + "kost/"
        val ADD_URL = BASE_URL + "kost"
        val UPDATE_URL = BASE_URL + "kost/"
        val DELETE_URL = BASE_URL + "kost/"
    }
}