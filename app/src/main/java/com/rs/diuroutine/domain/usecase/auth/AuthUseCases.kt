package com.rs.diuroutine.domain.usecase.auth

import javax.inject.Inject

data class AuthUseCases @Inject constructor(
    val firebaseLogin: FirebaseLogin,
    val googleSignIn: GoogleSignIn

)
