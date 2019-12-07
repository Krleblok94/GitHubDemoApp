package com.kr1.krl3.githubdemoapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kr1.krl3.domain.common.Failure

open class BaseViewModel : ViewModel() {

    private val _loadingStatus = MutableLiveData<Boolean>()
    val loadingStatus = _loadingStatus

    private val _failureLiveData = MutableLiveData<Failure>()
    val failureLiveData = _failureLiveData

    internal fun setFailure(failure: Failure) {
        _failureLiveData.value = failure
    }

    internal fun setLoadingStatus(loading: Boolean) {
        _loadingStatus.value = loading
    }
}