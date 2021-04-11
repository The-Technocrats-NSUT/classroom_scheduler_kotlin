package com.example.classroomScheduler

import android.os.Bundle
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

        requireArguments().getBoolean("isAdmin")
        // TODO : Add recycler view adapter and viewModel, pass arguments
        val isAdmin : Boolean = false
        setUpPeopleRecyclerView(isAdmin)
    }

    private fun setUpPeopleRecyclerView(isAdmin: Boolean) {

        val hubID = "hub1"
        userListDao = UserListDao(hubID)
        val userListCollection = userListDao.fetchUserList()
        val userListQuery = userListCollection.orderBy("isAdmin").orderBy("userName")
        val userListRecyclerViewOption = FirestoreRecyclerOptions.Builder<UserModel>().setQuery(userListQuery, UserModel::class.java).build()
        mAdapter = UserListAdapter(userListRecyclerViewOption, this, isAdmin)

        // TODO: Add the linear layout manager for the recycler view set its adapter

    }

    override fun removeUserBtnListener(user: DocumentSnapshot) {
        val hubID = "hub1"
        val userID = MyUtils.getUserId()
        if (user.get("userID")==userID)
        {
            Toast.makeText(context, "Cannot Remove Yourself!", Toast.LENGTH_SHORT).show()
        }
        else
        {
            userListDao.removeUser(hubID, userID)
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