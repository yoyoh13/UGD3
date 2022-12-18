package com.yohanes.ugd3_a_0891.api

class UserAPI {
    companion object{
        val BASE_URL = "http://127.0.0.1:8000/api/user"

        val GET_BY_ID_URL = BASE_URL + "user/"
        val ADD_URL = BASE_URL + "user"
        val UPDATE_URL = BASE_URL + "user/"
        val LOGIN_URL = BASE_URL + "login"
    }
}