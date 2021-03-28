package com.example.classroomScheduler.repository

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.classroomScheduler.model.HubModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class HubListDao {
    val db = FirebaseFirestore.getInstance()
    private val userEmail = Firebase.auth.currentUser!!.email
    private val atTheRateIndex = userEmail!!.indexOf('@')
    private val userId = userEmail!!.subSequence(0, atTheRateIndex).toString()
    val hubsListCollection =
        db.collection("users").document("kaman7580").collection("hubsPresentIn")

    suspend fun fetchHubList(): ArrayList<HubModel>? = try {
        val hubList = ArrayList<HubModel>()

        hubsListCollection.get().await().map { document ->

            if (document != null) {

                Log.d(TAG, "Data fetch successful!")

                    Log.d(TAG, "the document id is ${document.id}")
                    val temp = HubModel(document.get("hubName").toString(),
                                        document.id.toString(),
                            document.get("isAdmin") as Boolean)
                    hubList.add(temp)

                     //   hubList.add(document.toObject(HubModel::class.java))

            } else {
                Log.d(TAG, "No such document")
            }

        }


        if (hubList.isEmpty()) {
            Log.d(TAG, "Collection size 0")
        } else {
            Log.d(TAG, "Collection size not 0")
        }

        hubList
    }
    catch (exception: Exception){
        Log.d(TAG, "get failed with ", exception)
        null
    }
}