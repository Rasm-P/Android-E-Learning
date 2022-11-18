package com.example.elearningapp.repositories

import android.util.Log
import com.example.elearningapp.common.ActionState
import com.example.elearningapp.models.CourseStatus
import com.example.elearningapp.models.Programme
import com.example.elearningapp.models.User
import com.example.elearningapp.repositories.interfaces.UserRepositoryInterface
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject internal constructor(private val firebaseDB: FirebaseFirestore, private val firebaseAuth: FirebaseAuth):
    UserRepositoryInterface {

    private val errorMessage = "Something went wrong!"

    override suspend fun fetchUser() = flow {
        try {
            emit(ActionState.Loading)
            val uid = firebaseAuth.uid!!
            val result = firebaseDB.collection("user").document(uid).get().await()
            val user = result.toObject(User::class.java)!!
            emit(ActionState.Success(user))
        } catch (e: Exception) {
            emit(ActionState.Error(e.message ?: errorMessage))
            Log.e("Error: User", e.message ?: errorMessage)
        }
    }

    override suspend fun addUser(user: User) = flow {
        try {
            emit(ActionState.Loading)
            val uid = firebaseAuth.uid!!
            firebaseDB.collection("user").document(uid).set(user).await()
            emit(ActionState.Success(user))
        } catch (e: Exception) {
            emit(ActionState.Error(e.message ?: errorMessage))
            Log.e("Error: User", e.message ?: errorMessage)
        }
    }

    override suspend fun updateUserName(name: String) = flow {
        try {
            emit(ActionState.Loading)
            val uid = firebaseAuth.uid!!
            firebaseDB.collection("user").document(uid).update("name", name).await()
            emit(ActionState.Success("Username has been updated!"))
        } catch (e: Exception) {
            emit(ActionState.Error(e.message ?: errorMessage))
            Log.e("Error: User", e.message ?: errorMessage)
        }
    }

    override suspend fun updateUserStudyProgramme(programme: Programme) = flow {
        try {
            emit(ActionState.Loading)
            val uid = firebaseAuth.uid!!
            firebaseDB.collection("user").document(uid).update("studyProgramme", programme).await()
            emit(ActionState.Success("Study programme has been updated!"))
        } catch (e: Exception) {
            emit(ActionState.Error(e.message ?: errorMessage))
            Log.e("Error: User", e.message ?: errorMessage)
        }
    }

    override suspend fun updateUserActiveCourses(activeCourses: List<CourseStatus>) = flow {
        try {
            emit(ActionState.Loading)
            val uid = firebaseAuth.uid!!
            firebaseDB.collection("user").document(uid).update("activeCourses", activeCourses).await()
            emit(ActionState.Success("Active courses has been updated!"))
        } catch (e: Exception) {
            emit(ActionState.Error(e.message ?: errorMessage))
            Log.e("Error: User", e.message ?: errorMessage)
        }
    }

    override suspend fun deleteUser() = flow {
        try {
            emit(ActionState.Loading)
            val uid = firebaseAuth.uid!!
            firebaseDB.collection("user").document(uid).delete().await()
            emit(ActionState.Success("User was successfully deleted!"))
        } catch (e: Exception) {
            emit(ActionState.Error(e.message ?: errorMessage))
            Log.e("Error: User", e.message ?: errorMessage)
        }
    }

}