package com.example.classroomScheduler.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.classroomScheduler.model.UserModel
import com.firebase.ui.auth.data.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class UserListDao {
    val db = FirebaseFirestore.getInstance()

     suspend fun fetchUserList( hubId : String): ArrayList<UserModel>? = try {
        val userList = ArrayList<UserModel>()
        val requestedUserCollection = db.collection("hubs").document(hubId).collection("people")
         requestedUserCollection.get().await().map {  userDocument->
            if (userDocument!=null)
            {
                Log.d(TAG, "User document fetch successful")
                val tempUser = UserModel(userDocument.get("userName").toString(),
                        userDocument.get("userID").toString(),
                        userDocument.get("isAdmin") as Boolean,

                        )
                userList.add(tempUser)
            }
             else
            {
                Log.d(TAG, "No such User Document exists")
            }
         }

         userList
     }
     catch (e:Exception)
     {
         Log.d(TAG, "User List get failed with ", e)
         null
     }
}