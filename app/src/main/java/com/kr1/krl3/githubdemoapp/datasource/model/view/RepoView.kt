package com.kr1.krl3.githubdemoapp.datasource.model.view

data class RepoView(
    val id: Int,
    val name: String,
    val commits_url: String,
    val open_issues: String
) {
    override fun hashCode() = id
    override fun equals(other: Any?): Boolean { return super.equals(other) }
}