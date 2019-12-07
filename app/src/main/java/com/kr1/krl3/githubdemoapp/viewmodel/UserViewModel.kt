package com.kr1.krl3.githubdemoapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kr1.krl3.domain.common.Failure
import com.kr1.krl3.domain.common.UseCase.None
import com.kr1.krl3.domain.entities.User
import com.kr1.krl3.domain.usecase.GetUser
import com.kr1.krl3.githubdemoapp.datasource.model.toUserView
import com.kr1.krl3.githubdemoapp.datasource.model.view.UserView

class UserViewModel(getUser: GetUser) : BaseViewModel() {

    private val _userLiveData = MutableLiveData<UserView>()
    val userLiveData = _userLiveData

    init {
        setLoadingStatus(true)
        getUser(None(), viewModelScope) { it.either(::handleFailure, ::handleSuccess) }
    }

    private fun handleSuccess(user: User) {
        setLoadingStatus(false)
        _userLiveData.value = user.toUserView()
    }

    private fun handleFailure(failure: Failure) { setFailure(failure) }
}