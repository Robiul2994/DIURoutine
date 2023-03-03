package com.rs.diuroutine.presentation

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInCredential
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.SignInButton
import com.rs.diuroutine.R
import com.rs.diuroutine.databinding.ActivityLoginBinding
import com.rs.diuroutine.presentation.viewmodel.AuthViewModel
import com.rs.diuroutine.util.DriveQuickstart
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LogInActivity : AppCompatActivity() {
    private val viewModel :AuthViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding

    private val resultLauncher  = registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
        Log.d("StartActivityForResult", "StartActivityForResult ${result.resultCode}")
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            val data = result.data!!
            val credential = Identity.getSignInClient(this).getSignInCredentialFromIntent(data)
            viewModel.firebaseLogin(credential,{
                Log.d("TAG", "success:")
            }){
                Log.e("TAG", "failed: $it")
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }

            //val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)!!.signInAccount!!.email!!

//            DriveQuickstart.main()

//            val x = viewModel.downloadFromLink("https://drive.google.com/file/d/1JL87LcjHkvkI3eyIbqj83MHCkfUhYGuH/view?usp=share_link"){
//                Log.d("TAG", "failed: $it")
//            }
//            Log.d("TAG", "onCreate: $x")
        }
        else if (result.resultCode == Activity.RESULT_CANCELED){
            Log.d("TAG", "RESULT_CANCELED")

        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.signInBtn.setSize(SignInButton.SIZE_WIDE)
        binding.signInBtn.setOnClickListener {
            viewModel.googleSignIn(this,resultLauncher){msg ->
                Log.d("TAG", "onCreate: $msg")
            }
        }






    }
}