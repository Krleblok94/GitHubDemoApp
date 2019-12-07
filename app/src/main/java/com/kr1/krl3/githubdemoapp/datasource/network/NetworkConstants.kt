package com.kr1.krl3.githubdemoapp.datasource.network

object NetworkConstants {
    const val BASE_URL = "https://api.github.com/"

    const val USER_ENDPOINT = "users/octocat"

    const val REPOS_ENDPOINT = "$USER_ENDPOINT/repos"

    const val TIMEOUT_IN_SEC = 15L
}