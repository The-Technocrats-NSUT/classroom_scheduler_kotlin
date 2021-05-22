package com.example.classroomScheduler

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.classroomScheduler.adapters.HubListAdapter
import com.example.classroomScheduler.adapters.IHubListAdapter
import com.example.classroomScheduler.model.HubModel
import com.example.classroomScheduler.repository.HubListDao
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.DocumentSnapshot

class HubListMainActivity : AppCompatActivity(), IHubListAdapter {

    companion object
    {
        private const val TAG = "Hub List View TAG"
    }

    private lateinit var mAdapter: HubListAdapter
    private lateinit var hubListDao: HubListDao
    private lateinit var hubListRecyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hub_list)
        hubListRecyclerView = findViewById(R.id.hubListRecyclerView)
        val fabNewClassroom = findViewById<FloatingActionButton>(R.id.fab_newClassroom)

        fabNewClassroom.setOnClickListener {
            displayFabBtnOption()

        }

        setUpHubListRecyclerView()
    }


    private fun setUpHubListRecyclerView() {
        hubListDao = HubListDao()
        val hubCollectionQuery = hubListDao.fetchHubList()
//      TODO: Add a field "createdAt" in the list of hubs
//      val hubListQuery = hubCollectionQuery.orderBy("createdAt")
        val hubListRecyclerViewOptions = FirestoreRecyclerOptions.Builder<HubModel>().setQuery(hubCollectionQuery, HubModel::class.java).build()
        mAdapter = HubListAdapter(hubListRecyclerViewOptions, this)

        hubListRecyclerView.adapter = mAdapter
        hubListRecyclerView.layoutManager = LinearLayoutManager(this)
//        Log.d(TAG, "Recycler View Set Up")
    }


    // function to open a dialogue and present options to the user
    // option 0 is to create a new hub
    // option 1 is to join a hub
    private fun displayFabBtnOption(): Unit {

        val optionsList = arrayOf("Create A New Hub", "Join An Existing Hub")
        MaterialAlertDialogBuilder(this)
                .setTitle("What do you want to do?")
                .setItems(optionsList) { dialog, selectedHubOption ->
                    hubAction(selectedHubOption)
                    Toast.makeText(this, "selected item is $selectedHubOption", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                .show()

    }

    private fun hubAction(selectedHubOption: Int) {

        if (selectedHubOption==0)
        {
            val createNewHubIntent = Intent(this, CreateNewHubActivity::class.java)
            startActivity(createNewHubIntent)
        }
        else
        {
            val joinHubIntent = Intent(this, JoinHubActivity::class.java)
            startActivity(joinHubIntent)
        }

    }

    override fun onHubClicked(hubId: DocumentSnapshot) {
        val hubLaunchIntent = Intent(this, CurrentHubActivity::class.java)
        hubLaunchIntent.putExtra("hubID", hubId.id)

        val adminPrivilege = hubId.getBoolean("isAdmin") as Boolean
        if (adminPrivilege) {
            hubLaunchIntent.putExtra("isAdmin", true)
        } else {
            hubLaunchIntent.putExtra("isAdmin", false)
        }

        hubLaunchIntent.putExtra("hubName", hubId.getString("hubName"))
        startActivity(hubLaunchIntent)
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
