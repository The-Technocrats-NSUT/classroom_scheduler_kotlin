package com.example.classroomScheduler

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.classroomScheduler.adapters.HubListAdapter
import com.example.classroomScheduler.adapters.IHubListAdapter
import com.example.classroomScheduler.model.HubModel
import com.example.classroomScheduler.repository.HubListDao
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.DocumentSnapshot
//import kotlinx.android.synthetic.main.activity_classroom.*

class HubListMainActivity : AppCompatActivity(), IHubListAdapter {

    private val TAG = "TAG"
    private lateinit var mAdapter: HubListAdapter
    private lateinit var hubListDao: HubListDao
    private var hubListRecyclerView = findViewById<RecyclerView>(R.id.hubListRecyclerView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hub_list)

            val fabNewClassroom = findViewById<FloatingActionButton>(R.id.fab_newClassroom)

//            fabNewClassroom.setOnClickListener {
//                // TODO: OPEN A DIALOG BOX TO ENTER CLASSROOM CODE AND AN OPTION TO CREATE A NEW CLASSROOM
//                val fabBtnOption = getFabBtnOption()
//                val intent = Intent(this@HubListMainActivity, ClassroomHubActivity::class.java)
//                startActivity(intent)
//            }
            setUpHubListRecyclerView()
        }

    private fun setUpHubListRecyclerView()
    {
        hubListDao = HubListDao()
        val hubCollectionQuery =hubListDao.fetchHubList()
//      TODO: Add a field "createdAt" in the list of hubs
//      val hubListQuery = hubCollectionQuery.orderBy("createdAt")
        val hubListRecyclerViewOptions = FirestoreRecyclerOptions.Builder<HubModel>().setQuery(hubCollectionQuery, HubModel::class.java).build()
        mAdapter = HubListAdapter(hubListRecyclerViewOptions, this)

        hubListRecyclerView.adapter = mAdapter
        hubListRecyclerView.layoutManager = LinearLayoutManager(this)
        Log.d(TAG, "Recycler View Set Up")
    }


    // function to open a dialogue and present options to the user
    // option 0 is to create a new hub
    // option 1 is to join a hub
    private fun getFabBtnOption(): Int {
            var returningValue = 0
            val optionsList = arrayOf("Create A New Hub", "Join An Existing Hub")
            val builder = AlertDialog.Builder(this)
            builder.setTitle("What Do You Want To Do?")
            builder.setItems(optionsList) { _, which ->
                returningValue = which
            }

            return returningValue
    }

    override fun onHubClicked(hubId: DocumentSnapshot)
    {
        val hubLaunchIntent = Intent(this, CurrentHubActivity::class.java)
        hubLaunchIntent.putExtra("hubID", hubId.get("hubID").toString())

        val adminPrivilege = hubId.getBoolean("isAdmin") as Boolean
        if (adminPrivilege)
        {
            hubLaunchIntent.putExtra("isAdmin", "true")
        }
        else
        {
            hubLaunchIntent.putExtra("isAdmin", "false")
        }

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