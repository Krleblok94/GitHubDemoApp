package com.kr1.krl3.domain.common

sealed class Either<out L, out R> {
    data class Left<out L>(val failure: L) : Either<L, Nothing>()
    data class Right<out R>(val success: R) : Either<Nothing, R>()

    val isFailure get() = this is Left<L>
    val isSuccess get() = this is Right<R>

    fun getFailureData() = (this as Left).failure
    fun getSuccessData() = (this as Right).success

    fun either(handleFailure: (L) -> Unit, handleSuccess: (R) -> Unit) {
        when (this) {
            is Left -> handleFailure(failure)
            is Right -> handleSuccess(success)
        }
    }
}