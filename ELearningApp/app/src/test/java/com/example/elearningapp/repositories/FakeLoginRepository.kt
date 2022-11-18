package com.example.elearningapp.repositories

import com.example.elearningapp.common.ActionState
import com.example.elearningapp.repositories.interfaces.LoginRepositoryInterface
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

class FakeLoginRepository: LoginRepositoryInterface {

    override suspend fun login(email: String, password: String): Flow<ActionState<String>> {
        TODO("Not yet implemented")
    }

    override suspend fun register(email: String, password: String): Flow<ActionState<String>> {
        TODO("Not yet implemented")
    }

    override suspend fun resetPassword(email: String): Flow<ActionState<String>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateEmail(email: String): Flow<ActionState<String>> {
        TODO("Not yet implemented")
    }

    override fun logout() {
        TODO("Not yet implemented")
    }

    override fun currentUser(): FirebaseUser? {
        TODO("Not yet implemented")
    }

    override fun isLoggedIn(): Boolean {
        return true
    }
}