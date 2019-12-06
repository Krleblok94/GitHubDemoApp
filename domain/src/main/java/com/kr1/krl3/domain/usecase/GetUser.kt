package com.kr1.krl3.domain.usecase

import com.kr1.krl3.domain.Either
import com.kr1.krl3.domain.Failure
import com.kr1.krl3.domain.UseCase
import com.kr1.krl3.domain.entities.User
import com.kr1.krl3.domain.repository.UserRepository
import com.kr1.krl3.domain.usecase.GetUser.GetUserParams

class GetUser(private val userRepository: UserRepository) : UseCase<User, GetUserParams>() {

    override suspend fun run(params: GetUserParams): Either<Failure, User>
            = userRepository.getUser(params.username)

    data class GetUserParams(val username: String)
}