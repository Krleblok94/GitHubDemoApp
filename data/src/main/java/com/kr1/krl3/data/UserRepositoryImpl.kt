package com.kr1.krl3.data

import com.kr1.krl3.data.datasource.LocalDataSource
import com.kr1.krl3.data.datasource.NetworkDataSource
import com.kr1.krl3.domain.common.Either
import com.kr1.krl3.domain.common.Failure
import com.kr1.krl3.domain.entities.User
import com.kr1.krl3.domain.repository.UserRepository

class UserRepositoryImpl(private val localDataSource: LocalDataSource,
                         private val networkDataSource: NetworkDataSource) : UserRepository {

    override suspend fun getUser(): Either<Failure, User> {
        val apiData = networkDataSource.getUser()

        return when (apiData.isSuccess) {
            true -> {
                localDataSource.saveUser(apiData.getSuccessData())
                apiData
            }
            false -> localDataSource.getUser()
        }
    }
}