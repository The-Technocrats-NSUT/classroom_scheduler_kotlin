package com.example.classroomScheduler.repository

import android.os.SystemClock
import android.util.Log
import com.example.classroomScheduler.Utils.MyUtils
import com.example.classroomScheduler.model.HubModel
import com.example.classroomScheduler.model.UserModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class UserListDao(private val hubID: String, private val hubName: String) {

    private val db = FirebaseFirestore.getInstance()
    private val userListCollection = db.collection("hubs").document(hubID).collection("people")

    fun fetchUserList(): CollectionReference
    {
        return userListCollection
    }

    fun removeUser(userId: String) {

        // removing the user from hubs
       userListCollection.document(userId)
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

    fun joinHub() {

        // adding the user to the hub's people collection
        val newUserName = MyUtils.getUserName()
        val newUserID = MyUtils.getUserId()
        val newUser = UserModel(isAdmin = false, userName = newUserName, userID = newUserID)
        userListCollection.whereEqualTo("userID", newUserID)
            userListCollection.add(newUser)
                .addOnFailureListener{
                    Log.w(TAG, "Could not add user to the hub", it)
                }

        // adding the hub to user's hubsPresentIn collection
        val hubToAdd = HubModel(hubName = hubName, createdAt = SystemClock.currentThreadTimeMillis(), isAdmin = false)
        db.collection("users").document(newUserID).collection("hubsPresentIn").document(hubID)
                .set(hubToAdd)
    }

    companion object {
        private const val TAG: String = "UserListDao"
    }

}