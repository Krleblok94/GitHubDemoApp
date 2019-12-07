package com.kr1.krl3.githubdemoapp.datasource.network

import com.kr1.krl3.data.datasource.NetworkDataSource
import com.kr1.krl3.domain.common.Either
import com.kr1.krl3.domain.common.Failure
import com.kr1.krl3.domain.entities.Commit
import com.kr1.krl3.domain.entities.Repo
import com.kr1.krl3.domain.entities.User

class NetworkDataSourceImpl(private val restApi: RestApi,
                            private val networkHandler: NetworkHandler) : NetworkDataSource {

    override suspend fun getUser(): Either<Failure, User> {
        return when (networkHandler.isConnectedToNetwork()) {
            true -> restApi.getUserFromResponse()
            false -> restApi.returnNetworkError()
        }
    }

    override suspend fun getRepos(): Either<Failure, List<Repo>> {
        return when (networkHandler.isConnectedToNetwork()) {
            true -> restApi.getReposFromResponse()
            false -> restApi.returnNetworkError()
        }
    }

    override suspend fun getCommits(repoName: String): Either<Failure, List<Commit>> {
        return when (networkHandler.isConnectedToNetwork()) {
            true -> restApi.getCommitsFromResponse(repoName)
            false -> restApi.returnNetworkError()
        }
    }
}