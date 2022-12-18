package com.yohanes.ugd3_a_0891.api

class UserAPI {
    companion object{
        val BASE_URL = "http://192.168.100.6/Tubes_1_Kost/user"

        val GET_BY_ID_URL = BASE_URL + "user/"
        val ADD_URL = BASE_URL + "user"
        val UPDATE_URL = BASE_URL + "user/"
        val LOGIN_URL = BASE_URL + "login"
    }
}