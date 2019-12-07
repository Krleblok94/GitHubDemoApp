package com.kr1.krl3.githubdemoapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.kr1.krl3.githubdemoapp.R
import com.kr1.krl3.githubdemoapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val userViewModel by viewModel<UserViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val glide = Glide.with(this)

        observeUser(glide)

        observeLoading()

        observeFailure()

        tvReposLink.setOnClickListener { startActivity(Intent(this, ReposActivity::class.java)) }
    }

    private fun observeUser(glide: RequestManager) {
        userViewModel.userLiveData.observe(this, Observer {
            val username = it.name

            glide.load(it.avatar_url).into(ivAvatar)
            tvUsername.text = username
            tvCompany.text = it.company

            val reposLinkText = "Click here to see $username's repos"
            tvReposLink.text = reposLinkText
        })
    }

    private fun observeLoading() {
        userViewModel.loadingStatus.observe(this, Observer { showLoading(it) })
    }

    private fun observeFailure() {
        userViewModel.failureLiveData.observe(this, Observer { showError(it.getMessage()) })
    }

    private fun showLoading(show: Boolean) {
        VisibilitySwitcher.switchVisibility(show, pbLoading)
        VisibilitySwitcher.switchVisibility(!show, ivAvatar, tvUsername, tvCompany, tvReposLink, tvError)
    }

    private fun showError(errorMessage: String) {
        tvError.text = errorMessage
        VisibilitySwitcher.switchVisibility(true, tvError)
        VisibilitySwitcher.switchVisibility(false, ivAvatar, tvUsername, tvCompany, tvReposLink, pbLoading)
    }
}
