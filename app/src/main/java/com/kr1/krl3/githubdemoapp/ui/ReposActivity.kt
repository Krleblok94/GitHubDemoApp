package com.kr1.krl3.githubdemoapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kr1.krl3.githubdemoapp.R
import com.kr1.krl3.githubdemoapp.viewmodel.ReposViewModel
import kotlinx.android.synthetic.main.activity_repos.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReposActivity : AppCompatActivity() {

    private val reposViewModel by viewModel<ReposViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repos)

        setUpReposRecyclerView()

        observeLoading()

        observeFailure()
    }

    private fun setUpReposRecyclerView() {

        val reposAdapter = ReposAdapter()

        rvRepos.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvRepos.adapter = reposAdapter
        rvRepos.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))

        reposViewModel.reposAndCommitsLiveData.observe(this, Observer {
            reposAdapter.addReposAndCommits(it)
        })
    }

    private fun observeLoading() {
        reposViewModel.loadingStatus.observe(this, Observer { showLoading(it) })
    }

    private fun observeFailure() {
        reposViewModel.failureLiveData.observe(this, Observer { showError(it.getMessage()) })
    }

    private fun showLoading(show: Boolean) {
        VisibilitySwitcher.switchVisibility(show, pbLoading)
        VisibilitySwitcher.switchVisibility(!show, rvRepos, tvError)
    }

    private fun showError(errorMessage: String) {
        tvError.text = errorMessage
        VisibilitySwitcher.switchVisibility(true, tvError)
        VisibilitySwitcher.switchVisibility(false, pbLoading, rvRepos)
    }
}