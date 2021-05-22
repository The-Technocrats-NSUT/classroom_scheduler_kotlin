package com.example.classroomScheduler

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.classroomScheduler.Utils.MyUtils
import com.example.classroomScheduler.adapters.IUserListAdapter
import com.example.classroomScheduler.adapters.UserListAdapter
import com.example.classroomScheduler.model.UserModel
import com.example.classroomScheduler.repository.UserListDao
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentSnapshot
import kotlin.properties.Delegates

class PeopleFragment : Fragment(R.layout.fragment_people), IUserListAdapter {

    private lateinit var mAdapter : UserListAdapter
    private lateinit var userListDao: UserListDao
    private var isAdmin by Delegates.notNull<Boolean>()
    private lateinit var hubID: String
    private lateinit var hubName: String
    private lateinit var peopleRecyclerView: RecyclerView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        isAdmin = requireArguments().getBoolean("isAdmin")
        hubID = requireArguments().getString("hubID").toString()
        hubName = requireArguments().getString("hubName").toString()
        userListDao = UserListDao(hubID, hubName)
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//
////        setUpPeopleRecyclerView(isAdmin)
//    }

//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        val currentView = inflater.inflate(R.layout.fragment_people, container, false)
//        peopleRecyclerView = currentView.findViewById(R.id.userListRecyclerView)
//        setUpPeopleRecyclerView(isAdmin)
//        return super.onCreateView(inflater, container, savedInstanceState)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        peopleRecyclerView = view.findViewById(R.id.userListRecyclerView)
        setUpPeopleRecyclerView(isAdmin)
    }

    private fun setUpPeopleRecyclerView(isAdmin: Boolean) {

        val userListCollection = userListDao.fetchUserList()
        val userListQuery = userListCollection.orderBy("isAdmin").orderBy("userName")
        val userListRecyclerViewOption = FirestoreRecyclerOptions.Builder<UserModel>().setQuery(userListQuery, UserModel::class.java).build()
        mAdapter = UserListAdapter(userListRecyclerViewOption, this, isAdmin)

        peopleRecyclerView.adapter = mAdapter
        peopleRecyclerView.layoutManager = LinearLayoutManager(context)

    }

    override fun removeUserBtnListener(user: DocumentSnapshot) {
        val currUserID = MyUtils.getUserId()

        when(val userID = user.getString("userID"))
        {
            currUserID -> Toast.makeText(context, "Cannot Remove Yourself!", Toast.LENGTH_SHORT).show()
            null -> Toast.makeText(context, "Cannot remove null user!", Toast.LENGTH_SHORT).show()
            else -> userListDao.removeUser(userID)
        }
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