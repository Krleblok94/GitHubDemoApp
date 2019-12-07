package com.kr1.krl3.domain.entities

data class Repo(
    val id: Int,
    val name: String,
    val commits_url: String,
    val open_issues: Int
) {
    override fun hashCode() = id
    override fun equals(other: Any?): Boolean { return super.equals(other) }
}