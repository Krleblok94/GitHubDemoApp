package com.kr1.krl3.githubdemoapp.datasource.model.api

import com.google.gson.annotations.SerializedName

data class CommitApi(
    @SerializedName("sha") val sha: String?,
    @SerializedName("node_id") val nodeId: String?,
    @SerializedName("commit") val commit: CommitXApi,
    @SerializedName("url") val url: String?,
    @SerializedName("html_url") val htmlUrl: String?,
    @SerializedName("comments_url") val commentsUrl: String?,
    @SerializedName("author") val author: AuthorXApi?,
    @SerializedName("committer") val committer: CommitterXApi?,
    @SerializedName("parents") val parents: List<Any?>?
)

data class CommitXApi(
    @SerializedName("author") val author: AuthorApi,
    @SerializedName("committer") val committer: CommitterApi,
    @SerializedName("message") val message: String,
    @SerializedName("tree") val tree: TreeApi?,
    @SerializedName("url") val url: String?,
    @SerializedName("comment_count") val commentCount: Int,
    @SerializedName("verification") val verification: VerificationApi?
)

data class AuthorXApi(
    @SerializedName("login") val login: String?,
    @SerializedName("id") val id: Int?,
    @SerializedName("node_id") val nodeId: String?,
    @SerializedName("avatar_url") val avatarUrl: String?,
    @SerializedName("gravatar_id") val gravatarId: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("html_url") val htmlUrl: String?,
    @SerializedName("followers_url") val followersUrl: String?,
    @SerializedName("following_url") val followingUrl: String?,
    @SerializedName("gists_url") val gistsUrl: String?,
    @SerializedName("starred_url") val starredUrl: String?,
    @SerializedName("subscriptions_url") val subscriptionsUrl: String?,
    @SerializedName("organizations_url") val organizationsUrl: String?,
    @SerializedName("repos_url") val reposUrl: String?,
    @SerializedName("events_url") val eventsUrl: String?,
    @SerializedName("received_events_url") val receivedEventsUrl: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("site_admin") val siteAdmin: Boolean?
)

data class CommitterXApi(
    @SerializedName("login") val login: String?,
    @SerializedName("id") val id: Int?,
    @SerializedName("node_id") val nodeId: String?,
    @SerializedName("avatar_url") val avatarUrl: String?,
    @SerializedName("gravatar_id") val gravatarId: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("html_url") val htmlUrl: String?,
    @SerializedName("followers_url") val followersUrl: String?,
    @SerializedName("following_url") val followingUrl: String?,
    @SerializedName("gists_url") val gistsUrl: String?,
    @SerializedName("starred_url") val starredUrl: String?,
    @SerializedName("subscriptions_url") val subscriptionsUrl: String?,
    @SerializedName("organizations_url") val organizationsUrl: String?,
    @SerializedName("repos_url") val reposUrl: String?,
    @SerializedName("events_url") val eventsUrl: String?,
    @SerializedName("received_events_url") val receivedEventsUrl: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("site_admin") val siteAdmin: Boolean?
)

data class AuthorApi(
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("date") val date: String
)

data class CommitterApi(
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("date") val date: String
)

data class TreeApi(
    @SerializedName("sha") val sha: String?,
    @SerializedName("url") val url: String?
)

data class VerificationApi(
    @SerializedName("verified") val verified: Boolean?,
    @SerializedName("reason") val reason: String?,
    @SerializedName("signature") val signature: Any?,
    @SerializedName("payload") val payload: Any?
)