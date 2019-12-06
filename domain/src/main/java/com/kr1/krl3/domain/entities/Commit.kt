package com.kr1.krl3.domain.entities

data class Commit(
    val sha: String?,
    val node_id: String?,
    val commit: CommitX?,
    val url: String?,
    val html_url: String?,
    val comments_url: String?,
    val author: AuthorX?,
    val committer: CommitterX?,
    val parents: List<Parent?>?
)

data class CommitX(
    val author: Author?,
    val committer: Committer?,
    val message: String?,
    val tree: Tree?,
    val url: String?,
    val comment_count: Int?,
    val verification: Verification?
)

data class Author(
    val name: String?,
    val email: String?,
    val date: String?
)

data class Committer(
    val name: String?,
    val email: String?,
    val date: String?
)

data class Tree(
    val sha: String?,
    val url: String?
)

data class Verification(
    val verified: Boolean?,
    val reason: String?,
    val signature: Any?,
    val payload: Any?
)

data class AuthorX(
    val login: String?,
    val id: Int?,
    val node_id: String?,
    val avatar_url: String?,
    val gravatar_id: String?,
    val url: String?,
    val html_url: String?,
    val followers_url: String?,
    val following_url: String?,
    val gists_url: String?,
    val starred_url: String?,
    val subscriptions_url: String?,
    val organizations_url: String?,
    val repos_url: String?,
    val events_url: String?,
    val received_events_url: String?,
    val type: String?,
    val site_admin: Boolean?
)

data class CommitterX(
    val login: String?,
    val id: Int?,
    val node_id: String?,
    val avatar_url: String?,
    val gravatar_id: String?,
    val url: String?,
    val html_url: String?,
    val followers_url: String?,
    val following_url: String?,
    val gists_url: String?,
    val starred_url: String?,
    val subscriptions_url: String?,
    val organizations_url: String?,
    val repos_url: String?,
    val events_url: String?,
    val received_events_url: String?,
    val type: String?,
    val site_admin: Boolean?
)

data class Parent(
    val sha: String?,
    val url: String?,
    val html_url: String?
)