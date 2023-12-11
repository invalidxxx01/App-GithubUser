package com.aryanto.githubuser.data.remote.network

import com.aryanto.githubuser.DetailUser
import com.aryanto.githubuser.Item
import com.aryanto.githubuser.ResponseGithub
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    fun getUser(): Call<List<Item>>
    @GET("search/users")
    fun searchUser(@Query("q") query: String): Call<ResponseGithub>
    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUser>
    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<Item>>
    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<Item>>

}