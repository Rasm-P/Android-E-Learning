package com.example.elearningapp.repositories

import android.util.Log
import com.example.elearningapp.common.ActionState
import com.example.elearningapp.models.CourseStatus
import com.example.elearningapp.models.Programme
import com.example.elearningapp.models.User
import com.example.elearningapp.repositories.interfaces.UserRepositoryInterface
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject internal constructor(private val firebaseDB: FirebaseFirestore):
    UserRepositoryInterface {

    private val errorMessage = "Something went wrong!"

    override suspend fun fetchUser() = flow {
        try {
            emit(ActionState.Loading)
            lateinit var user: User
            val uid = Firebase.auth.uid!!
            firebaseDB.collection("user").document(uid).get()
                .addOnSuccessListener { document ->
                   user = document.toObject(User::class.java)!!
                }.await()
            emit(ActionState.Success(user))
        } catch (e: Exception) {
            emit(ActionState.Error(e.message ?: errorMessage))
            Log.e("Error: User", e.message ?: errorMessage)
        }
    }

    override suspend fun addUser(user: User) = flow {
        try {
            emit(ActionState.Loading)
            val uid = Firebase.auth.uid!!
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
            val uid = Firebase.auth.uid!!
            firebaseDB.collection("user").document(uid).update("name", name).await()
            emit(ActionState.Success(true))
        } catch (e: Exception) {
            emit(ActionState.Error(e.message ?: errorMessage))
            Log.e("Error: User", e.message ?: errorMessage)
        }
    }

    override suspend fun updateUserStudyProgramme(studyProgramme: Programme) = flow {
        try {
            emit(ActionState.Loading)
            val uid = Firebase.auth.uid!!
            firebaseDB.collection("user").document(uid).update("studyProgramme", studyProgramme).await()
            emit(ActionState.Success(true))
        } catch (e: Exception) {
            emit(ActionState.Error(e.message ?: errorMessage))
            Log.e("Error: User", e.message ?: errorMessage)
        }
    }

    override suspend fun updateUserActiveCourses(activeCourses: List<CourseStatus>) = flow {
        try {
            emit(ActionState.Loading)
            val uid = Firebase.auth.uid!!
            firebaseDB.collection("user").document(uid).update("activeCourses", activeCourses).await()
            emit(ActionState.Success(true))
        } catch (e: Exception) {
            emit(ActionState.Error(e.message ?: errorMessage))
            Log.e("Error: User", e.message ?: errorMessage)
        }
    }

}