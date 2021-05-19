package com.example.classroomScheduler.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.classroomScheduler.R
import com.example.classroomScheduler.model.UpcomingModel
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentSnapshot

class UpcomingAdapter(options: FirestoreRecyclerOptions<UpcomingModel>,
                        private val listener: IUpcomingListAdapter,
                        private val isAdmin: Boolean):
        FirestoreRecyclerAdapter<UpcomingModel, UpcomingAdapter.UpcomingViewHolder>(options)
{
    // upcoming view ho
    class UpcomingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val upcomingText: TextView = itemView.findViewById(R.id.upcomingText)
        val upcomingDate: TextView = itemView.findViewById(R.id.upcomingDate)
        val upcomingTime: TextView = itemView.findViewById(R.id.upcomingTime)
        val deleteUpcomingBtn: ImageView = itemView.findViewById(R.id.deleteUpcomingItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingViewHolder {
        val upcomingView = LayoutInflater.from(parent.context).inflate(R.layout.item_upcoming, parent, false)
        val upcomingViewHolder = UpcomingViewHolder(upcomingView)

        // make the delete button visible only to the admin
        if (!isAdmin)
        {
           upcomingViewHolder.deleteUpcomingBtn.visibility = View.GONE
        }

        // on clicking the delete upcoming item by admin, delete the item
        upcomingViewHolder.deleteUpcomingBtn.setOnClickListener {
            val upcomingSnapshot = snapshots.getSnapshot(upcomingViewHolder.adapterPosition)
            listener.deleteUpcomingBtnListener(upcomingSnapshot)
        }

        return upcomingViewHolder
    }

    override fun onBindViewHolder(holder: UpcomingViewHolder, position: Int, model: UpcomingModel) {
        holder.upcomingText.text = model.title
        holder.upcomingDate.text = model.scheduledDate
        holder.upcomingTime.text = model.scheduledTime
    }
}

interface IUpcomingListAdapter {

    fun deleteUpcomingBtnListener(upcomingSnapshot: DocumentSnapshot)

}
