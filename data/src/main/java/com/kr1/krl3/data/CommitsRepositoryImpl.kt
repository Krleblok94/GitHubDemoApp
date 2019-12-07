package com.kr1.krl3.data

import com.kr1.krl3.data.datasource.LocalDataSource
import com.kr1.krl3.data.datasource.NetworkDataSource
import com.kr1.krl3.domain.common.Either
import com.kr1.krl3.domain.common.Failure
import com.kr1.krl3.domain.entities.Commit
import com.kr1.krl3.domain.repository.CommitsRepository

class CommitsRepositoryImpl(private val localDataSource: LocalDataSource,
                            private val networkDataSource: NetworkDataSource) : CommitsRepository {

    override suspend fun getCommits(repoName: String): Either<Failure, List<Commit>> {
        val apiData = networkDataSource.getCommits(repoName)

        return when (apiData.isSuccess) {
            true -> {
                localDataSource.saveCommits(repoName, apiData.getSuccessData())
                apiData
            }
            false -> localDataSource.getCommits(repoName)
        }
    }
}