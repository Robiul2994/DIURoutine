package com.rs.diuroutine.data.repo

import android.util.Log
import com.google.api.client.googleapis.json.GoogleJsonResponseException
import com.google.api.client.http.HttpRequestInitializer
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.firestore.FirebaseFirestore
import com.rs.diuroutine.domain.repo.MainRepo
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.OutputStream
import java.util.*
import javax.inject.Inject


class MainRepoImpl @Inject constructor(
    private val firestore: FirebaseFirestore
        ): MainRepo{
    override fun getDataFromLink(link: String, success: () -> Unit, failed: (msg: String) -> Unit) {

    }

    override fun getDailyRoutine(
        token: String,
        success: () -> Unit,
        failed: (msg: String) -> Unit
    ) {


    }

    override fun getWeeklyRoutine(
        token: String,
        success: () -> Unit,
        failed: (msg: String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun getProfile(
        token: String,
        success: () -> Unit,
        failed: (msg: String) -> Unit)
    {
    }

    override fun getDeptRoutineLink(
        dept: String,
        success: (link: String) -> Unit,
        failed: (msg: String) -> Unit
    ) {
        firestore.collection("routineLink").document(dept)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("TAG", "DocumentSnapshot data: ${document.data}")
                    val link = document.data!!["link"].toString()
                    success.invoke(link)
                } else {
                    Log.d("TAG", "No such document")
                    failed.invoke("User doesn't exist")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents.", exception)
                failed.invoke("Something went wrong")
            }
    }

    @Throws(IOException::class)
    override fun downloadFile(rlink: String, failed :(msg : String) -> Unit): ByteArrayOutputStream {

//        val rlink = "https://drive.google.com/file/d/1JL87LcjHkvkI3eyIbqj83MHCkfUhYGuH/view?usp=share_link"
        var finalLink :String?= null
        val firstArray: List<String> = rlink.split("/d/")
        if (firstArray.size>1) {
            val secondArray: List<String> = firstArray[1].split("/view")
            finalLink = secondArray[0]
        }else{
            Log.w("TAG", "Enter Valid Link")
            failed.invoke("Enter Valid Link")
        }

        /* Load pre-authorized user credentials from the environment.
           TODO(developer) - See https://developers.google.com/identity for
          guides on implementing OAuth2 for your application.*/
        val credentials: GoogleCredentials = GoogleCredentials.getApplicationDefault()
            .createScoped(listOf(DriveScopes.DRIVE_FILE))
        val requestInitializer: HttpRequestInitializer = HttpCredentialsAdapter(
            credentials
        )

        // Build a new authorized API client service.
        val service: Drive = Drive.Builder(
            NetHttpTransport(),
            GsonFactory.getDefaultInstance(),
            requestInitializer
        )
            .setApplicationName("Drive samples")
            .build()

        return try {
            val outputStream: OutputStream = ByteArrayOutputStream()
            if(!finalLink.isNullOrEmpty())
                service.files().get(finalLink).executeMediaAndDownloadTo(outputStream)
            outputStream as ByteArrayOutputStream
        } catch (e: GoogleJsonResponseException) {
            // TODO(developer) - handle error appropriately
            System.err.println("Unable to move file: " + e.details)
            throw e
        }
    }
}
