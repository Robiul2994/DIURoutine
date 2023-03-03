package com.rs.diuroutine.presentation.viewmodel

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.identity.SignInCredential
import com.rs.diuroutine.domain.usecase.auth.AuthUseCases
import com.rs.diuroutine.domain.usecase.main.MainUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val mainUseCases: MainUseCases
) :ViewModel(){

    fun googleSignIn(
        activity: Activity,
        resultLauncher : ActivityResultLauncher<IntentSenderRequest>,
        failed : (msg : String) -> Unit
    ){
        authUseCases.googleSignIn(activity,resultLauncher, failed)
    }
    fun downloadFromLink(rlink: String, failed :(msg : String) -> Unit): ByteArrayOutputStream {
        return mainUseCases.downloadFile(rlink, failed)
    }
    fun firebaseLogin(
        credential: SignInCredential, success :() -> Unit, failed :(msg : String) -> Unit
    ){
        authUseCases.firebaseLogin(credential, success, failed)
    }
}