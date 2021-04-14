package com.example.classroomScheduler

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
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


//import kotlinx.android.synthetic.main.activity_classroom.*
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
        hubListRecyclerView = findViewById<RecyclerView>(R.id.hubListRecyclerView)
        val fabNewClassroom = findViewById<FloatingActionButton>(R.id.fab_newClassroom)

        fabNewClassroom.setOnClickListener {
            // TODO: OPEN A DIALOG BOX TO ENTER CLASSROOM CODE AND AN OPTION TO CREATE A NEW CLASSROOM
            val fabOption = getFabBtnOption()
//                val intent = Intent(this@HubListMainActivity, ClassroomHubActivity::class.java)
//                startActivity(intent)
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
        Log.d(TAG, "Recycler View Set Up")
    }


    // function to open a dialogue and present options to the user
    // option 0 is to create a new hub
    // option 1 is to join a hub
    private fun getFabBtnOption(): Int {

        var returningValue = 0
        val optionsList = arrayOf("Create A New Hub", "Join An Existing Hub")
        MaterialAlertDialogBuilder(this)
                .setTitle("What do you want to do?")
                .setItems(optionsList) { dialog, selectedHubOption ->
                    returningValue = selectedHubOption
                    dialog.dismiss()
                }
                .show()

        return returningValue
    }

    override fun onHubClicked(hubId: DocumentSnapshot) {
        val hubLaunchIntent = Intent(this, CurrentHubActivity::class.java)
        hubLaunchIntent.putExtra("hubID", hubId.get("hubID").toString())

        val adminPrivilege = hubId.getBoolean("isAdmin") as Boolean
        if (adminPrivilege) {
            hubLaunchIntent.putExtra("isAdmin", "true")
        } else {
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
