package com.example.classroomScheduler.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.classroomScheduler.R
import com.example.classroomScheduler.model.UserModel
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentSnapshot

class UserListAdapter(options: FirestoreRecyclerOptions<UserModel>,
                      private val listener : IUserListAdapter,
                      private val isAdmin: Boolean):
        FirestoreRecyclerAdapter<UserModel, UserListAdapter.UserListViewHolder>(options)
{

    inner class UserListViewHolder(itemView : View): RecyclerView.ViewHolder(itemView)
    {
        val userName : TextView = itemView.findViewById(R.id.user_name)
        val removeUserBtn : ImageView = itemView.findViewById(R.id.removeUserBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val userListView = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        val userListViewHolder = UserListViewHolder(userListView)

        // checking whether the current App user (not the user from user list) is admin or not
        // if the current user is not an admin, then they will not be having privileges to remove users from the hub
        if (!isAdmin)
        {
            userListViewHolder.removeUserBtn.visibility = GONE
        }

        // listener for remove user button
        userListViewHolder.removeUserBtn.setOnClickListener{
            val userSnapshot = snapshots.getSnapshot(userListViewHolder.adapterPosition)
            listener.removeUserBtnListener(userSnapshot)
        }

        return userListViewHolder
    }


    override fun onBindViewHolder(holder: UserListViewHolder, position: Int, model: UserModel) {
        holder.userName.text = model.userName
//        Log.d("USERLISTADAPTER","onbindviewholder OP" )
    }


}

interface IUserListAdapter
{
    fun removeUserBtnListener(user: DocumentSnapshot)
}
