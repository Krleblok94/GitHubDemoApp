package com.kr1.krl3.githubdemoapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kr1.krl3.domain.common.Failure
import com.kr1.krl3.domain.common.UseCase.None
import com.kr1.krl3.domain.entities.Commit
import com.kr1.krl3.domain.entities.Repo
import com.kr1.krl3.domain.usecase.GetReposAndCommits
import com.kr1.krl3.githubdemoapp.datasource.model.toCommitView
import com.kr1.krl3.githubdemoapp.datasource.model.toRepoView
import com.kr1.krl3.githubdemoapp.datasource.model.view.CommitView
import com.kr1.krl3.githubdemoapp.datasource.model.view.RepoView

class ReposViewModel(getReposAndCommits: GetReposAndCommits) : BaseViewModel() {

    private val _reposAndCommitsLiveData = MutableLiveData<Map<RepoView, List<CommitView>>>()
    val reposAndCommitsLiveData = _reposAndCommitsLiveData

    init {
        setLoadingStatus(true)

        getReposAndCommits(None(), viewModelScope) { it.either(::handleFailure, ::handleSuccess) }
    }

    private fun handleSuccess(reposAndCommits: Map<Repo, List<Commit>>) {
        setLoadingStatus(false)

        val viewMap = mutableMapOf<RepoView, List<CommitView>>()

        for (entry in reposAndCommits) {
            viewMap[entry.key.toRepoView()] = entry.value.map { it.toCommitView() }
        }

        _reposAndCommitsLiveData.value = viewMap
    }

    private fun handleFailure(failure: Failure) { setFailure(failure) }
}