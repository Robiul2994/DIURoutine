package com.rs.diuroutine.domain.repo

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import com.google.android.gms.auth.api.identity.SignInCredential

interface AuthRepo {

    fun firebaseLogin(credential: SignInCredential, success :() -> Unit, failed :(msg : String) -> Unit)

    fun googleSignIn(
        activity: Activity,
        resultLauncher : ActivityResultLauncher<IntentSenderRequest>,
        failed :(msg : String) -> Unit
    )

}