package com.example.classroomScheduler.repository

import android.widget.Toast
import com.example.classroomScheduler.UpcomingFragment
import com.example.classroomScheduler.model.UpcomingModel
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class UpcomingDao(private val hubID: String) {

    private val db = FirebaseFirestore.getInstance()
    private val upcomingListCollection = db.collection("hubs").document(hubID).collection("upcoming")

    fun fetchUpcomingList(): CollectionReference {
        return upcomingListCollection
    }

    fun deleteUpcoming(id: String)
    {
        upcomingListCollection.document(id).delete()
                .addOnSuccessListener {
                    // TODO make a toast when the event is removed
                    // TODO remove the event from the list of scheduled alarms
//                    Toast.makeText(, "Event Removed", Toast.LENGTH_SHORT).show()
                }
    }

    fun addUpcomingEvent(event: UpcomingModel)
    {
        upcomingListCollection.add(event)
        // 
    }



}