package com.example.classroomScheduler

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.classroomScheduler.adapters.INoticeListAdapter
import com.example.classroomScheduler.adapters.NoticesAdapter
import com.example.classroomScheduler.model.NoticeModel
import com.example.classroomScheduler.repository.NoticeDao
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentSnapshot
import kotlin.properties.Delegates

class NoticesFragment : Fragment(R.layout.fragment_notice), INoticeListAdapter {

    private lateinit var noticeDao: NoticeDao
    private lateinit var mAdapter: NoticesAdapter

    // data to be received from the host activity
    private var isAdmin by Delegates.notNull<Boolean>()
    private lateinit var hubID: String

    // recycler view
    private lateinit var noticeRecyclerView: RecyclerView
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        isAdmin = requireArguments().getBoolean("isAdmin")
        hubID = requireArguments().getString("hubID").toString()
//        hubName = requireArguments().getString("hubName").toString()
        noticeDao = NoticeDao(hubID)
    }

//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        val currentView = inflater.inflate(R.layout.fragment_notice, container, false)
//        noticeRecyclerView = currentView.findViewById(R.id.noticeRecyclerView)
//        setUpNoticeRecyclerView(isAdmin)
//        return super.onCreateView(inflater, container, savedInstanceState)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noticeRecyclerView = view.findViewById(R.id.noticeRecyclerView)
        setUpNoticeRecyclerView(isAdmin)

    }
    private fun setUpNoticeRecyclerView(isAdmin: Boolean) {
        val noticeListCollection = noticeDao.fetchNoticeList()
        val noticeListQuery = noticeListCollection.orderBy("datePosted").orderBy("timePosted")
        val noticeListRecyclerOptions = FirestoreRecyclerOptions.Builder<NoticeModel>().setQuery(noticeListQuery, NoticeModel::class.java).build()
        mAdapter = NoticesAdapter(noticeListRecyclerOptions, this, isAdmin)

        noticeRecyclerView.adapter = mAdapter
        noticeRecyclerView.layoutManager = LinearLayoutManager(context)
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