package com.rs.diuroutine.domain.repo

import java.io.ByteArrayOutputStream
import java.io.IOException

interface MainRepo {


    fun getDataFromLink(link : String , success :() -> Unit, failed :(msg : String) -> Unit)

    fun getDailyRoutine(token : String , success :() -> Unit, failed :(msg : String) -> Unit)

    fun getWeeklyRoutine(token : String , success :() -> Unit, failed :(msg : String) -> Unit)

    fun getProfile(token : String ,success :() -> Unit, failed :(msg : String) -> Unit)

    fun getDeptRoutineLink(dept : String, success: (link: String) -> Unit, failed :(msg : String) -> Unit)

    @Throws(IOException::class)
    fun downloadFile(rlink: String, failed :(msg : String) -> Unit): ByteArrayOutputStream


}