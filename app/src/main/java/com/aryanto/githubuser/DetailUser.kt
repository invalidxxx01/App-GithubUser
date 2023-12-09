package com.aryanto.githubuser

data class DetailUser(
    val id: Int,
    val login: String,
    val name: String,
    val avatar_url: String,
    val followers: Int,
    val following: Int,
    val followers_url: String,
    val following_url: String,
)
