package com.rs.diuroutine.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rs.diuroutine.data.repo.AuthRepoImpl
import com.rs.diuroutine.data.repo.MainRepoImpl
import com.rs.diuroutine.domain.repo.AuthRepo
import com.rs.diuroutine.domain.repo.MainRepo
import com.rs.diuroutine.domain.usecase.auth.AuthUseCases
import com.rs.diuroutine.domain.usecase.auth.FirebaseLogin
import com.rs.diuroutine.domain.usecase.auth.GoogleSignIn
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth() =Firebase.auth

    @Provides
    @Singleton
    fun provideFirebaseStore() =Firebase.firestore


    @Provides
    @Singleton
    fun provideAuthRepo(firebaseAuth: FirebaseAuth):AuthRepo=AuthRepoImpl(firebaseAuth)

    @Provides
    @Singleton
    fun provideMainRepo(firestore: FirebaseFirestore): MainRepo = MainRepoImpl(firestore)
}