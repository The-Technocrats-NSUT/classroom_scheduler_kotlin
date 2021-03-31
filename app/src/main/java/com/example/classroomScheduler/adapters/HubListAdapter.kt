package com.example.classroomScheduler.adapters

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.classroomScheduler.R
import com.example.classroomScheduler.model.HubModel

class HubListAdapter(private  val context: Context, private val listener: IHubListAdapter): RecyclerView.Adapter<HubListAdapter.HubListViewHolder>()

{
    // An array list to contain the list of all the hubs
    val hubList = ArrayList<HubModel>()

    // The ViewHolder class
    inner class HubListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
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
            listener.onHubClicked(hubList[hubListViewHolder.adapterPosition])
        }

        // return the viewHolder
        return hubListViewHolder
    }

    override fun onBindViewHolder(holder: HubListViewHolder, position: Int) {

        val currHub = hubList[position]
        holder.hubName.text = currHub.hubName
    }

    override fun getItemCount(): Int {

        Log.d(TAG, "List of array is ${hubList.size}")
        return hubList.size
    }

    // the update list to be called from
    fun updateList(newHubList: ArrayList<HubModel>)
    {
        hubList.clear()
        hubList.addAll(newHubList)

        // notify the data set changed to the observer
        notifyDataSetChanged()
    }

}

// We need an interface to handle the clicks on the hub item card
interface IHubListAdapter
{
    fun onHubClicked(hub: HubModel)
}
