package com.aptivist.dogchallenge.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.aptivist.dogchallenge.domain.models.Dog
import com.aptivist.dogchallenge.domain.models.RepositoryResponse
import com.aptivist.dogchallenge.domain.repositories.IDogRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest{

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    private val dispatcher = StandardTestDispatcher()

    private val successRepository : IDogRepository = mockk {
        coEvery { getRandomDog() } returns RepositoryResponse.Success(Dog("Firulais"))
    }

    private val failedRepository : IDogRepository = mockk {
        coEvery { getRandomDog() } returns RepositoryResponse.Failed("Error")
    }

    private val viewModelSuccess = MainViewModel(successRepository, dispatcher)

    private val viewModelFailed = MainViewModel(failedRepository, dispatcher)

    @Before
    fun setUp(){
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `When fetching random dog successfully state is updated`() = runTest {
        //Given the main page is displayed
        //When the get random dog button is clicked by the user
        viewModelSuccess.getDogImage()
        advanceUntilIdle()
        //Then the state should be not empty
        assertThat(viewModelSuccess.currentDog.value).isNotEmpty()
    }

    @Test
    fun `When fetching random dog wrong state is not updated`() = runTest {
        //Given the main page is displayed
        //When the get random dog button is clicked by the user
        viewModelFailed.getDogImage()
        advanceUntilIdle()
        //Then the state should be not empty
        assertThat(viewModelSuccess.currentDog.value).isEmpty()
    }

    @Test
    fun `When fetching random dog wrong a UIError is triggered`() = runTest {
        //Given the main page is displayed
        //When the get random dog button is clicked by the user
        viewModelFailed.getDogImage()
        val action = viewModelFailed.uiActions.first()
        advanceUntilIdle()
        //Then the state should be not empty
        assertThat(action).isInstanceOf(UIActions.UIShowError::class.java)
    }

}