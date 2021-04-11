package com.example.classroomScheduler.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.classroomScheduler.Utils.MyUtils
import com.example.classroomScheduler.model.NoticeModel
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class NoticeDao {
    private val TAG: String = "NoticeDAO"
    val db = FirebaseFirestore.getInstance()

    @RequiresApi(Build.VERSION_CODES.O)
    fun postNotice(noticeText: String, currentHubID: String)
    {
        val currentDateTime = LocalDateTime.now()
        val datePosted = currentDateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        val timePosted = currentDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
        val noticeAuthor = MyUtils.getUserName()

        val currentNotice = NoticeModel(noticeText, datePosted, timePosted, noticeAuthor)
        db.collection("hubs").document(currentHubID).collection("notices").add(currentNotice)
                .addOnSuccessListener {
                    Log.d(TAG,"Notice added successfully!")
                }
                .addOnFailureListener{
                    Log.w(TAG, "Error posting notice", it)
                }
    }

}