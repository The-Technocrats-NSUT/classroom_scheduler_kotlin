package com.example.classroomScheduler.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.classroomScheduler.R
import com.example.classroomScheduler.model.HubModel
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentSnapshot

class HubListAdapter(options: FirestoreRecyclerOptions<HubModel>, private val listener: IHubListAdapter):
        FirestoreRecyclerAdapter<HubModel, HubListAdapter.HubListViewHolder>(options)
{
    // The ViewHolder class
    class HubListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val hubName:TextView = itemView.findViewById(R.id.hub_name)
    }

    // The three overrides for the adapter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HubListViewHolder {

        // create the view
        val hubListView = LayoutInflater.from(parent.context).inflate(R.layout.item_hub, parent, false)
        // create the viewholder
        val hubListViewHolder = HubListViewHolder(hubListView)

        // handle the click for a particular hubView
        hubListView.setOnClickListener {
            val hubSnapshot = snapshots.getSnapshot(hubListViewHolder.adapterPosition)
            listener.onHubClicked(hubSnapshot)
        }

        // return the viewHolder
        return hubListViewHolder
    }

    override fun onBindViewHolder(holder: HubListViewHolder, position: Int, model: HubModel) {
        holder.hubName.text = model.hubName
    }

}

// We need an interface to handle the clicks on the hub item card
interface IHubListAdapter
{
    fun onHubClicked(hubId: DocumentSnapshot)
}
