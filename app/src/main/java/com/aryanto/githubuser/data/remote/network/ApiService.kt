package com.aryanto.githubuser.data.remote.network

import com.aryanto.githubuser.DetailUser
import com.aryanto.githubuser.Item
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("users")
    fun getUser(): Call<List<Item>>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUser>

}