package com.kr1.krl3.githubdemoapp.datasource.network

import com.kr1.krl3.githubdemoapp.datasource.model.api.UserApi
import retrofit2.Response
import retrofit2.http.GET

interface UserService {

    @GET(NetworkConstants.USER_ENDPOINT)
    suspend fun getUser(): Response<UserApi>
}