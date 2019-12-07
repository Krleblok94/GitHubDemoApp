package com.kr1.krl3.githubdemoapp.datasource.network

import com.google.gson.GsonBuilder
import com.kr1.krl3.domain.common.Either
import com.kr1.krl3.domain.common.Either.Left
import com.kr1.krl3.domain.common.Either.Right
import com.kr1.krl3.domain.common.Failure
import com.kr1.krl3.domain.common.Failure.NetworkFailure
import com.kr1.krl3.domain.common.Failure.ServerFailure
import com.kr1.krl3.domain.entities.Commit
import com.kr1.krl3.domain.entities.Repo
import com.kr1.krl3.domain.entities.User
import com.kr1.krl3.githubdemoapp.BuildConfig
import com.kr1.krl3.githubdemoapp.datasource.model.toCommit
import com.kr1.krl3.githubdemoapp.datasource.model.toRepo
import com.kr1.krl3.githubdemoapp.datasource.model.toUser
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RestApi {

    private val userService: UserService
    private val repoService: RepoService

    init {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.connectTimeout(NetworkConstants.TIMEOUT_IN_SEC, TimeUnit.SECONDS)
        okHttpClient.readTimeout(NetworkConstants.TIMEOUT_IN_SEC, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpClient.addInterceptor(httpLoggingInterceptor)
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(NetworkConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(okHttpClient.build())
            .build()

        userService = retrofit.create(UserService::class.java)
        repoService = retrofit.create(RepoService::class.java)
    }

    suspend fun getUserFromResponse(): Either<Failure, User> {
        val response = userService.getUser()

        return if (response.isSuccessful) {
            val userApi = response.body() ?: return returnServerFailure("No user data available")
            Right(userApi.toUser())
        } else {
            returnServerFailure(response.message())
        }
    }

    suspend fun getReposFromResponse(): Either<Failure, List<Repo>> {
        val response = repoService.getRepos()

        return if (response.isSuccessful) {
            val reposListApi = response.body() ?: return returnServerFailure("No repo data available")
            Right(reposListApi.map { it.toRepo() })
        } else {
            returnServerFailure(response.message())
        }
    }

    suspend fun getCommitsFromResponse(repoName: String): Either<Failure, List<Commit>> {
        val response = repoService.getCommits(repoName)

        return if (response.isSuccessful) {
            val commitListApi = response.body() ?: return returnServerFailure("No commit data available")
            Right(commitListApi.map { it.toCommit() })
        } else {
            returnServerFailure(response.message())
        }
    }

    fun returnNetworkError() = Left(NetworkFailure("No network connection"))
    private fun returnServerFailure(message: String?) = Left(ServerFailure(message ?: "Unknown network error"))
}