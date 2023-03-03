package com.rs.diuroutine.domain.usecase.auth

import com.google.android.gms.auth.api.identity.SignInCredential
import com.rs.diuroutine.domain.repo.AuthRepo
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class FirebaseLogin @Inject constructor(
    private val repo:AuthRepo
    ){
        operator fun invoke(credential: SignInCredential, success :() -> Unit, failed :(msg : String) -> Unit)=repo.firebaseLogin(credential, success, failed)
}