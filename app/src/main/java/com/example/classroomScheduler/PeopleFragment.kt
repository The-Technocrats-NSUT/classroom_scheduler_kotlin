package com.example.classroomScheduler

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.classroomScheduler.Utils.MyUtils
import com.example.classroomScheduler.adapters.IUserListAdapter
import com.example.classroomScheduler.adapters.UserListAdapter
import com.example.classroomScheduler.model.UserModel
import com.example.classroomScheduler.repository.UserListDao
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentSnapshot

class PeopleFragment : Fragment(R.layout.fragment_people), IUserListAdapter {

    private lateinit var mAdapter : UserListAdapter
    private lateinit var userListDao: UserListDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO : create fragment transactions

        val isAdmin: Boolean = requireArguments().getBoolean("isAdmin")
        val hubID : String? = requireArguments().getString("hub1")
        // TODO : Add recycler view adapter and viewModel, pass arguments

        userListDao = hubID?.let { UserListDao(it) }!!
        setUpPeopleRecyclerView(isAdmin)
    }

    private fun setUpPeopleRecyclerView(isAdmin: Boolean) {

        val userListCollection = userListDao.fetchUserList()
        val userListQuery = userListCollection.orderBy("isAdmin").orderBy("userName")
        val userListRecyclerViewOption = FirestoreRecyclerOptions.Builder<UserModel>().setQuery(userListQuery, UserModel::class.java).build()
        mAdapter = UserListAdapter(userListRecyclerViewOption, this, isAdmin)

        // TODO: Add the linear layout manager for the recycler view set its adapter

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}