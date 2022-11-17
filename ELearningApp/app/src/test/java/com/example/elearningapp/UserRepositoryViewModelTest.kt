package com.example.elearningapp

import com.example.elearningapp.repositories.interfaces.UserRepositoryInterface
import org.junit.Before
import io.mockk.*

class UserRepositoryViewModelTest {

    private lateinit var userRepository: UserRepositoryInterface

    @Before
    fun setUp() {
        userRepository = mockk<UserRepositoryInterface>()
    }

}