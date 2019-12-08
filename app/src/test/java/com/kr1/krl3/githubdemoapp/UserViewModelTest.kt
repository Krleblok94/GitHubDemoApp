package com.kr1.krl3.githubdemoapp

import com.google.gson.Gson
import com.kr1.krl3.domain.common.Either
import com.kr1.krl3.domain.common.Either.Left
import com.kr1.krl3.domain.common.Either.Right
import com.kr1.krl3.domain.common.Failure
import com.kr1.krl3.domain.common.Failure.NetworkFailure
import com.kr1.krl3.domain.entities.User
import com.kr1.krl3.domain.usecase.GetUser
import com.kr1.krl3.githubdemoapp.utils.AssetUtils
import com.kr1.krl3.githubdemoapp.utils.InstantExecutorExtension
import com.kr1.krl3.githubdemoapp.utils.getOrAwaitValue
import com.kr1.krl3.githubdemoapp.viewmodel.UserViewModel
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

// Note: BaseViewModel functions are tested through these tests too
@ExtendWith(InstantExecutorExtension::class)
class UserViewModelTest {

    private val getUser = mockk<GetUser>()
    private val gson = Gson()

    companion object {
        private const val USERNAME = "The Octocat"
        private const val COMPANY = "GitHub"
        private const val AVATAR_URL = "https://avatars3.githubusercontent.com/u/583231?v=4"

        private const val NETWORK_ERROR = "No network available"
    }

    @BeforeEach
    fun setUp() {
        setUpWithSuccessResponse()
    }

    @Test
    fun `when getUser returns user data, live data is correctly updated`() {
        val userViewModel = UserViewModel(getUser)
        val userLiveData = userViewModel.userLiveData.getOrAwaitValue()

        assertEquals(USERNAME, userLiveData.name)
        assertEquals(COMPANY, userLiveData.company)
        assertEquals(AVATAR_URL, userLiveData.avatar_url)
    }

    @Test
    fun `when data is loading or done being loaded, loadingStatus live data is correctly updated`() {
        val userViewModel = UserViewModel(getUser)
        val loadingStatusLiveData = userViewModel.loadingStatus

        userViewModel.setLoadingStatus(true)
        assertTrue(loadingStatusLiveData.getOrAwaitValue())

        userViewModel.setLoadingStatus(false)
        assertFalse(loadingStatusLiveData.getOrAwaitValue())
    }

    @Test
    fun `when failure data is received, failureLiveData is updated correctly`() {
        setUpWithFailureResponse()
        val userViewModel = UserViewModel(getUser)

        assertEquals(NETWORK_ERROR, userViewModel.failureLiveData.getOrAwaitValue().getMessage())
    }

    private fun setUpWithSuccessResponse() {
        setUpWithResponse(Right(getUserDataFromAssets()))
    }

    private fun setUpWithFailureResponse() {
        setUpWithResponse(Left(NetworkFailure(NETWORK_ERROR)))
    }

    private fun setUpWithResponse(response: Either<Failure, User>) {
        every { getUser(any(), any(), any()) } answers {
            val onResult = arg<(Either<Failure, User>) -> Unit>(2)
            onResult(response)
        }
    }

    private fun getUserDataFromAssets() = gson.fromJson(AssetUtils.readJsonFile("user.json"), User::class.java)
}