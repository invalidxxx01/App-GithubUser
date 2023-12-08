package com.aryanto.githubuser.data.remote.network

import com.aryanto.githubuser.Item
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    fun getUser(): Call<List<Item>>

}