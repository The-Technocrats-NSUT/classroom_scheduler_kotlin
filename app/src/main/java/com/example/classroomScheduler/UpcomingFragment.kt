package com.example.classroomScheduler

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.classroomScheduler.adapters.IUpcomingListAdapter
import com.example.classroomScheduler.adapters.UpcomingAdapter
import com.example.classroomScheduler.model.UpcomingModel
import com.example.classroomScheduler.repository.UpcomingDao
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentSnapshot
import kotlin.properties.Delegates

class UpcomingFragment : Fragment(R.layout.fragment_upcoming), IUpcomingListAdapter {
    private var isAdmin by Delegates.notNull<Boolean>()
    private lateinit var hubID: String

    // the upcoming dao
    private lateinit var upcomingDao: UpcomingDao
    // the recycler view for the upcoming list
    private lateinit var upcomingRecyclerView: RecyclerView
    // adapter for recycler view
    private lateinit var mAdapter: UpcomingAdapter

    override fun onAttach(context: Context)
    {
        super.onAttach(context)
        isAdmin = requireArguments().getBoolean("isAdmin")
        hubID = requireArguments().getString("hubID").toString()

        upcomingDao = UpcomingDao(hubID)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        upcomingRecyclerView = view.findViewById(R.id.upcomingRecylerView)
        setupUpcomingRecyclerView(isAdmin)
        Log.d(TAG, "set up rv method called")
    }

    private fun setupUpcomingRecyclerView(isAdmin: Boolean)
    {
        val upcomingListCollection = upcomingDao.fetchUpcomingList()
        val upcomingListQuery = upcomingListCollection.orderBy("scheduledDate").orderBy("scheduledTime")
        val upcomingListRecyclerViewOption = FirestoreRecyclerOptions.Builder<UpcomingModel>().setQuery(upcomingListCollection, UpcomingModel::class.java).build()
        mAdapter = UpcomingAdapter(upcomingListRecyclerViewOption, this, isAdmin)

        upcomingRecyclerView.adapter = mAdapter
        upcomingRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun deleteUpcomingBtnListener(upcomingSnapshot: DocumentSnapshot)
    {
        val upcomingEventID = upcomingSnapshot.id
        upcomingDao.deleteUpcoming(upcomingEventID)
    }
}