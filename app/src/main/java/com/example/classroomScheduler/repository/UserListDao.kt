package com.example.classroomScheduler.repository

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class UserListDao(hubId: String) {
    private val db = FirebaseFirestore.getInstance()
    private val userListCollection = db.collection("hubs").document(hubId).collection("people")

    fun fetchUserList(): CollectionReference
    {
        return userListCollection
    }

    fun removeUser(hubID: String, userId: String) {

        // removing the user from hubs
        db.collection("hubs").document(hubID).collection("people").document(userId)
                .delete()
                .addOnSuccessListener {
                    Log.d(TAG, "User $userId removed successfully from the Hub")
                }
                .addOnFailureListener{exception->
                    Log.w(TAG,"Error removing user $userId from Hub", exception)

                }

        // removing the hub from user's account
        db.collection("users").document(userId).collection("hubsPresentIn").document(hubID)
                .delete()
                .addOnSuccessListener {
                    Log.d(TAG, "hub was removed from user's account!")
                }
                .addOnFailureListener{exception->
                    Log.w(TAG, "Error removing hub from user's account", exception)

                }
    }

    companion object {
        private const val TAG: String = "UserListDao"
    }

}