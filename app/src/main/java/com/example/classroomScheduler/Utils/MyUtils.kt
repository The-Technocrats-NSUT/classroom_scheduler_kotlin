package com.example.classroomScheduler.Utils

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MyUtils {
    companion object
    {
        fun getUserId(): String
        {
            val userEmail = Firebase.auth.currentUser!!.email
            val atTheRateIndex = userEmail!!.indexOf('@')
            return userEmail.subSequence(0, atTheRateIndex).toString()
        }

        fun getUserName(): String
        {
            return Firebase.auth.currentUser!!.displayName ?: "Anonymous"
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun getCurrentDateTime(): String
        {
            val currentTime = LocalDateTime.now()
            val timeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            return currentTime.format(timeFormatter)
        }
    }

}