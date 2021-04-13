package com.example.classroomScheduler

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.classroomScheduler.adapters.INoticeListAdapter
import com.example.classroomScheduler.adapters.NoticesAdapter
import com.example.classroomScheduler.model.NoticeModel
import com.example.classroomScheduler.repository.NoticeDao
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentSnapshot

class NoticesFragment : Fragment(R.layout.fragment_notice), INoticeListAdapter {

    private lateinit var noticeDao: NoticeDao
    private lateinit var mAdapter: NoticesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isAdmin = requireArguments().getBoolean("isAdmin")
        val hubID = requireArguments().getString("hubID")

        noticeDao = hubID?.let { NoticeDao(it) }!!
        setUpNoticeRecyclerView(isAdmin)
    }

    private fun setUpNoticeRecyclerView(isAdmin: Boolean) {
        val noticeListCollection = noticeDao.fetchNoticeList()
        val noticeListQuery = noticeListCollection.orderBy("datePosted").orderBy("timePosted")
        val noticeListRecyclerOptions = FirestoreRecyclerOptions.Builder<NoticeModel>().setQuery(noticeListQuery, NoticeModel::class.java).build()
        mAdapter = NoticesAdapter(noticeListRecyclerOptions, this, isAdmin)

        // TODO: Add the linear layout manager for the recycler view set its adapter

    }

    override fun deleteNoticeBtnListener(notice: DocumentSnapshot){

        noticeDao.deleteNotice(notice.id)
    }

    override fun onStart() {
        super.onStart()
        mAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        mAdapter.stopListening()
    }

}