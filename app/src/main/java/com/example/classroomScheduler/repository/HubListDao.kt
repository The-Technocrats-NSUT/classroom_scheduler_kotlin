package com.example.classroomScheduler.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.classroomScheduler.Utils.MyUtils
import com.example.classroomScheduler.model.HubModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class HubListDao {
    val db = FirebaseFirestore.getInstance()
    private val userId = MyUtils.getUserId()
    private val hubsListCollection =
        db.collection("users").document(userId).collection("hubsPresentIn")

    fun fetchHubList(): CollectionReference
    {
        return hubsListCollection
    }

}