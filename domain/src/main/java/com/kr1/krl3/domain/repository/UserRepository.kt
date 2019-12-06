package com.kr1.krl3.domain.repository

import com.kr1.krl3.domain.entities.User
import com.kr1.krl3.domain.Either
import com.kr1.krl3.domain.Failure
import kotlinx.coroutines.CoroutineDispatcher

interface UserRepository {

    suspend fun getUser(username: String): Either<Failure, User>
}