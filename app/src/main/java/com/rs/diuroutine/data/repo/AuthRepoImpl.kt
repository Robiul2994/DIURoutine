package com.rs.diuroutine.data.repo

import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import com.google.android.gms.auth.api.identity.GetSignInIntentRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.rs.diuroutine.R
import com.rs.diuroutine.domain.repo.AuthRepo


class AuthRepoImpl(
    private val auth : FirebaseAuth,

) :AuthRepo {

    override fun firebaseLogin(

        credential: SignInCredential,
        success: () -> Unit,
        failed: (msg: String) -> Unit
    ) {
        val authCredential = GoogleAuthProvider.getCredential(credential.googleIdToken, null)
        auth.signInWithCredential(authCredential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithCredential:success")
                    val user = auth.currentUser!!
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithCredential:failure", task.exception)
                    failed.invoke("Something went wrong")
                }
            }
    }

    override fun googleSignIn(activity: Activity, resultLauncher : ActivityResultLauncher<IntentSenderRequest>,
                              failed: (msg: String) -> Unit) {
        Log.e("TAG1", "Google Sign-in")

        val request = GetSignInIntentRequest.builder()
            .setServerClientId(activity.getString(R.string.server_client_id))
            .build()
        Identity.getSignInClient(activity)
            .getSignInIntent(request)
            .addOnSuccessListener { result ->
                try {
                    Log.d("TAG", "googleSignIn: ${result.describeContents()}")
                    Log.e("TAG2", "Google Sign-in")
                    resultLauncher.launch(IntentSenderRequest.Builder(result).build())
                } catch (e: IntentSender.SendIntentException) {
                    Log.e("TAG", "Google Sign-in failed")
                    failed.invoke("Something went wrong")
                }
            }
            .addOnFailureListener { e ->
                Log.e("TAG", "Google Sign-in failed", e)
                failed.invoke("Something went wrong")

            }
    }

//    override fun googleSignIn(activity: Activity, resultLauncher : ActivityResultLauncher<Intent>,
//                              failed: (msg: String) -> Unit) {
//        Log.e("TAG1", "Google Sign-in")
//
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestEmail()
//            .build()
//        val mGoogleSignInClient = GoogleSignIn.getClient(activity, gso)
//        resultLauncher.launch(mGoogleSignInClient.signInIntent)
//
//    }

}