package com.example.classroomScheduler

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.classroomScheduler.adapters.HubListAdapter
import com.example.classroomScheduler.adapters.IHubListAdapter
import com.example.classroomScheduler.model.HubModel
import com.example.classroomScheduler.repository.HubListDao
import com.example.classroomScheduler.viewModel.HubListViewModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import kotlinx.android.synthetic.main.activity_classroom.*

class HubListMainActivity : AppCompatActivity(), IHubListAdapter {

    lateinit var hubListViewModel: HubListViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classroom)

        val hubListRecyclerView = findViewById<RecyclerView>(R.id.hubListRecyclerView)

        // instantiating the adapter
        val hubListAdapter = HubListAdapter(this, this)

        // inflating the recycler view
        hubListRecyclerView.layoutManager = LinearLayoutManager(this)
        hubListRecyclerView.adapter = hubListAdapter

        // starting the viewmodel
        hubListViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(HubListViewModel::class.java)
        hubListViewModel.hubList.observe(this, {hublist->hublist?.let {
            hubListAdapter.updateList(it)
            }
        })

        // TODO:  Add side navigation menu


        fab_newClassroom.setOnClickListener {
            // TODO: OPEN A DIALOG BOX TO ENTER CLASSROOM CODE AND AN OPTION TO CREATE A NEW CLASSROOM
            val fabBtnOption = getFabBtnOption()
//            val intent = Intent(this@HubListMainActivity, ClassroomHubActivity::class.java)
//            startActivity(intent)
        }

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

    override fun onHubClicked(hub: HubModel)
    {
        val hubLaunchIntent = Intent(this, ClassroomHubActivity::class.java)
        hubLaunchIntent.putExtra("isAdmin", hub.isAdmin)
        hubLaunchIntent.putExtra("hubID", hub.hubRef)
        startActivity(hubLaunchIntent)
    }

}