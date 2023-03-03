package com.rs.diuroutine.domain.usecase.auth

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import com.google.android.gms.auth.api.identity.SignInCredential
import com.rs.diuroutine.domain.repo.AuthRepo
import javax.inject.Inject

class GoogleSignIn @Inject constructor(
    private val repo:AuthRepo
    ){
        operator fun invoke(
            activity: Activity,
            resultLauncher : ActivityResultLauncher<IntentSenderRequest>,
            failed :(msg : String) -> Unit)=repo.googleSignIn(activity,resultLauncher, failed)
}