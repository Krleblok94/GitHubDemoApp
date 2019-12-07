package com.kr1.krl3.domain.usecase

import com.kr1.krl3.domain.common.Either
import com.kr1.krl3.domain.common.Failure
import com.kr1.krl3.domain.common.UseCase
import com.kr1.krl3.domain.entities.User
import com.kr1.krl3.domain.repository.UserRepository

class GetUser(private val userRepository: UserRepository) : UseCase<User, UseCase.None>() {

    override suspend fun run(params: None): Either<Failure, User> = userRepository.getUser()
}