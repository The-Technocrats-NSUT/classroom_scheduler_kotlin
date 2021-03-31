package com.example.classroomScheduler.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.classroomScheduler.R
import com.example.classroomScheduler.model.UserModel
import com.firebase.ui.auth.data.model.User

class UserListAdapter(private val context: Context, private val listener : IUserListAdapter, private val isAdmin: Boolean):RecyclerView.Adapter<UserListAdapter.UserListViewHolder>() {


    val userList = ArrayList<UserModel>()
    inner class UserListViewHolder(itemView : View): RecyclerView.ViewHolder(itemView)
    {
        val userName : TextView = itemView.findViewById(R.id.user_name)
        val removeUserBtn : Button = itemView.findViewById(R.id.removeUserBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val userListView = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        val userListViewHolder = UserListViewHolder(userListView)

        // listener for remove user button
        userListViewHolder.removeUserBtn.setOnClickListener{
            listener.RemoveUserBtnListener(userList[userListViewHolder.adapterPosition])
        }
        return userListViewHolder
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        val currUser = userList[position]
        holder.userName.text = currUser.userName

        // checking whether the current App user (not the user from user list) is admin or not
        // if the current user is not an admin, then they will not be having priviledges to remove users from the hub
        if (!isAdmin)
        {
            holder.removeUserBtn.visibility = GONE
        }

    }

    override fun getItemCount(): Int {
        return userList.size
    }


    fun updateUserList(newUserList: ArrayList<UserModel>)
    {
        userList.clear()
        userList.addAll(newUserList)

        notifyDataSetChanged()
    }


}

interface IUserListAdapter
{
    fun RemoveUserBtnListener(user: UserModel)
}
