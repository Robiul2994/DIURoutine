package com.rs.diuroutine.domain.usecase.main

import android.app.Activity
import com.google.android.gms.auth.api.identity.SignInCredential
import com.rs.diuroutine.domain.repo.AuthRepo
import com.rs.diuroutine.domain.repo.MainRepo
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class DownloadFile @Inject constructor(
    private val repo:MainRepo
    ){
        operator fun invoke(rlink: String, failed :(msg : String) -> Unit): ByteArrayOutputStream = repo.downloadFile(rlink, failed)

}