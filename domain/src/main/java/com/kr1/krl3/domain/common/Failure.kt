package com.kr1.krl3.domain.common

sealed class Failure(private val message: String) {

    fun getMessage() = message

    class NetworkFailure(message: String) : Failure(message)
    class ServerFailure(message: String) : Failure(message)
    class DatabaseFailure(message: String) : Failure(message)

    class NoReposFailure(message: String) : Failure(message)
}