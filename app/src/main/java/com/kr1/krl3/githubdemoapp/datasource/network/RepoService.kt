package com.kr1.krl3.githubdemoapp.datasource.network

import com.kr1.krl3.githubdemoapp.datasource.model.api.CommitApi
import com.kr1.krl3.githubdemoapp.datasource.model.api.RepoApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RepoService {

    @GET(NetworkConstants.REPOS_ENDPOINT)
    suspend fun getRepos(): Response<List<RepoApi>>

    @GET("repos/octocat/{repoName}/commits")
    suspend fun getCommits(@Path("repoName") repoName: String): Response<List<CommitApi>>

}