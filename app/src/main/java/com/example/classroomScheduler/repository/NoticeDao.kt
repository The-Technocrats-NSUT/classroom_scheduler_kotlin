package com.example.classroomScheduler.repository

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.classroomScheduler.Utils.MyUtils
import com.example.classroomScheduler.model.NoticeModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class NoticeDao(private val hubID :String) {
    private val TAG: String = "NoticeDAO"
    private val db = FirebaseFirestore.getInstance()
    private val noticeListCollection = db.collection("hubs").document(hubID).collection("notices")

    fun fetchNoticeList(): CollectionReference {
        return noticeListCollection
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun postNotice(noticeText: String)
    {
        val currentDateTime = LocalDateTime.now()
        val datePosted = currentDateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        val timePosted = currentDateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
        val noticeAuthor = MyUtils.getUserName()

        val currentNotice = NoticeModel(noticeText, datePosted, timePosted, noticeAuthor)
        noticeListCollection.add(currentNotice)
                .addOnSuccessListener {
                    Log.d(TAG,"Notice added successfully!")
                }
                .addOnFailureListener{
                    Log.w(TAG, "Error posting notice", it)
                }
    }

    fun deleteNotice(id: String) {
        noticeListCollection.document(id).delete()
                .addOnFailureListener{
                    Log.w(TAG, "Could Not Delete Notice $id",it)
                }
    }

}