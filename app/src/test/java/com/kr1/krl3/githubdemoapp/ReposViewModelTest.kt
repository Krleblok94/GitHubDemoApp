package com.kr1.krl3.githubdemoapp

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kr1.krl3.domain.common.Either
import com.kr1.krl3.domain.common.Either.Left
import com.kr1.krl3.domain.common.Either.Right
import com.kr1.krl3.domain.common.Failure
import com.kr1.krl3.domain.common.Failure.NetworkFailure
import com.kr1.krl3.domain.entities.Commit
import com.kr1.krl3.domain.entities.Repo
import com.kr1.krl3.domain.usecase.GetReposAndCommits
import com.kr1.krl3.githubdemoapp.datasource.model.api.CommitApi
import com.kr1.krl3.githubdemoapp.datasource.model.toCommit
import com.kr1.krl3.githubdemoapp.datasource.model.view.CommitView
import com.kr1.krl3.githubdemoapp.datasource.model.view.RepoView
import com.kr1.krl3.githubdemoapp.utils.AssetUtils
import com.kr1.krl3.githubdemoapp.utils.InstantExecutorExtension
import com.kr1.krl3.githubdemoapp.utils.getOrAwaitValue
import com.kr1.krl3.githubdemoapp.viewmodel.ReposViewModel
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(InstantExecutorExtension::class)
class ReposViewModelTest {

    private val getReposAndCommits = mockk<GetReposAndCommits>()
    private val gson = Gson()

    companion object {
        private const val BOYSENBERRY_REPO = "boysenberry-repo-1"
        private const val GIT_CONSORTIUM = "git-consortium"

        private const val NETWORK_ERROR = "No network available"
    }

    @Test
    fun `when failure data is received, failureLiveData is updated correctly`() {
        setUpWithFailureResponse()
        val reposViewModel = ReposViewModel(getReposAndCommits)

        assertEquals(NETWORK_ERROR, reposViewModel.failureLiveData.getOrAwaitValue().getMessage())
    }

    @Test
    fun `when success data is received, reposAndCommitsLiveData is updated correctly`() {
        setUpWithSuccessResponse()
        val reposViewModel = ReposViewModel(getReposAndCommits)

        val reposAndCommits = reposViewModel.reposAndCommitsLiveData.getOrAwaitValue()

        runAssertionsOnSuccessData(reposAndCommits)
    }

    private fun runAssertionsOnSuccessData(reposAndCommits: Map<RepoView, List<CommitView>>) {
        assertTrue(reposAndCommits.size == 2)

        for (entry in reposAndCommits) {
            when (entry.key.name) {
                BOYSENBERRY_REPO -> {
                    val commits = entry.value

                    assertTrue(commits.size == 4)
                    assertEquals("add new version of readme", commits[2].message)
                }
                GIT_CONSORTIUM -> {
                    val commits = entry.value

                    assertTrue(commits.size == 6)
                    assertEquals("octocat@nowhere.com", commits[4].authorEmail)
                }
            }
        }
    }

    private fun setUpWithSuccessResponse() {
        setUpWithResponse(Right(generateDataFromAssets()))
    }

    private fun setUpWithFailureResponse() {
        setUpWithResponse(Left(NetworkFailure(NETWORK_ERROR)))
    }

    private fun setUpWithResponse(response: Either<Failure, Map<Repo, List<Commit>>>) {
        every { getReposAndCommits(any(), any(), any()) } answers {
            val onResult = arg<(Either<Failure, Map<Repo, List<Commit>>>) -> Unit>(2)
            onResult(response)
        }
    }

    private fun generateDataFromAssets(): Map<Repo, List<Commit>> {

        val reposListType = object : TypeToken<List<Repo>>() {}.type
        val commitListType = object : TypeToken<List<CommitApi>>() {}.type

        val repos = gson.fromJson<List<Repo>>(AssetUtils.readJsonFile("repos.json"), reposListType)

        val boysenberryCommits = gson.fromJson<List<CommitApi>>(
            AssetUtils.readJsonFile("$BOYSENBERRY_REPO.json"),
            commitListType
        ).map { it.toCommit() }

        val gitConsortiumCommits = gson.fromJson<List<CommitApi>>(
            AssetUtils.readJsonFile("$GIT_CONSORTIUM.json"),
            commitListType
        ).map { it.toCommit() }

        val reposAndCommitsMap = mutableMapOf<Repo, List<Commit>>()

        for (repo in repos) {
            when (repo.name) {
                BOYSENBERRY_REPO -> reposAndCommitsMap[repo] = boysenberryCommits
                GIT_CONSORTIUM -> reposAndCommitsMap[repo] = gitConsortiumCommits
            }
        }

        return reposAndCommitsMap
    }
}