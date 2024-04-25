package com.example.github.data.retrofit

import com.example.github.data.response.DetailUsersResponse
import com.example.github.data.response.UsersFollowingResponseItem
import com.example.github.data.response.UsersResponse
import com.example.github.data.response.UsersResponseItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("users")
    fun getUser(): Call<List<UsersResponseItem>>

    @GET("users/{userName}")
    fun findUser(
        @Path("userName") userName:String
    ): Call<DetailUsersResponse>

    @GET("users/{userName}/following")
    fun getUserFollowing(
        @Path("userName") userName: String
    ): Call<List<UsersFollowingResponseItem>>

    @GET("users/{userName}/follower")
    fun getUserFollower(
        @Path("userName") userName: String
    ): Call<List<UsersFollowingResponseItem>>

}