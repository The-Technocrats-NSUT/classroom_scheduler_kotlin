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

class HubListDao {
    val db = FirebaseFirestore.getInstance()
    private val userEmail = Firebase.auth.currentUser!!.email
    private val atTheRateIndex = userEmail!!.indexOf('@')
    private val userId = userEmail!!.subSequence(0, atTheRateIndex).toString()
    val hubsListCollection =
        db.collection("users").document("kaman7580").collection("hubsPresentIn")

    fun fetchHubList(): MutableLiveData<ArrayList<HubModel>> {
        val hubList = ArrayList<HubModel>()
        val liveHubData = MutableLiveData<ArrayList<HubModel>>()
        GlobalScope.launch(Dispatchers.IO) {
            hubsListCollection.get().addOnSuccessListener { collection ->

                if (collection != null) {

                    Log.d(TAG, "Data fetch successful!")
                    for (document in collection) {
                        Log.d(TAG, "the document id is ")
                        val temp = HubModel(document.get("hubName").toString(),
                                            document.id.toString(),
                                document.get("isAdmin") as Boolean)
                        hubList.add(temp)

//                        hubList.add(document.toObject(HubModel::class.java))
                    }

                    liveHubData.value = hubList
                } else {
                    Log.d(TAG, "No such document")
                }

            }.addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }


            if (hubList.isEmpty()) {
                Log.d(TAG, "Collection size 0")
            } else {
                Log.d(TAG, "Collection size not 0")
            }

        }
        return liveHubData
    }
}