package com.example.elearningapp.di

import com.example.elearningapp.repositories.*
import com.example.elearningapp.repositories.interfaces.*
import dagger.Module
import dagger.Binds
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppServiceModule {

    @Binds
    abstract fun bindLoginRepository(
        loginRepository: LoginRepository
    ): LoginRepositoryInterface

    @Binds
    abstract fun bindUserRepository(
        userRepository: UserRepository
    ): UserRepositoryInterface

    @Binds
    abstract fun bindProgrammeRepository(
        programmeRepository: ProgrammeRepository
    ): ProgrammeRepositoryInterface

    @Binds
    abstract fun bindCourseRepository(
        courseRepository: CourseRepository
    ): CourseRepositoryInterface

    @Binds
    abstract fun bindNoteRepositoryInterface(
        noteRepository: NoteRepository
    ): NoteRepositoryInterface

}