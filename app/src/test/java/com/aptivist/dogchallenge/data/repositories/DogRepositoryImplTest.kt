package com.aptivist.dogchallenge.data.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.aptivist.dogchallenge.data.dogapi.DogAPI
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Rule
import org.junit.rules.TestRule

class DogRepositoryImplTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val mockApi : DogAPI = mockk() {
        coEvery { getRandomImage() } returns mockk()
    }
    private val dogRepositoryImpl = DogRepositoryImpl(mockApi)
}