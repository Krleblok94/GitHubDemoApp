package com.kr1.krl3.domain.repository

import com.kr1.krl3.domain.common.Either
import com.kr1.krl3.domain.common.Failure
import com.kr1.krl3.domain.entities.User

interface UserRepository {

    suspend fun getUser(): Either<Failure, User>
}