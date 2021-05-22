package com.example.classroomScheduler

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.example.classroomScheduler.Utils.PostNewNotice
import com.example.classroomScheduler.repository.UpcomingDao
import com.google.firebase.firestore.DocumentChange
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import kotlin.properties.Delegates

class CurrentHubActivity : AppCompatActivity() {

    // getting from source activity
    // hubID, isAdmin, and hubName
    private lateinit var hubID: String
    private var isAdmin by Delegates.notNull<Boolean>()
    lateinit var userHubBundle: Bundle
    private lateinit var hubName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_hub)

        // getting the intent extras
        isAdmin = intent.getBooleanExtra("isAdmin", false)
        hubID = intent.getStringExtra("hubID").toString()
        hubName = intent.getStringExtra("hubName").toString()

        // making a bundle out of the hub information
        userHubBundle = bundleOf("isAdmin" to isAdmin, "hubID" to hubID, "hubName" to hubName)

        Toast.makeText(this, "hubID is $hubID, hubname is $hubName", Toast.LENGTH_SHORT).show()
        // for the first time opening of screen
        // opening the upcoming fragment
        if(savedInstanceState==null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace<UpcomingFragment>(R.id.fl_fragment, args = userHubBundle)
            }
        }

        val bottomNavBar = findViewById<ChipNavigationBar>(R.id.bottom_navigationBar)
        bottomNavBar.setOnItemSelectedListener {id->
            when(id){
                2131296598 -> hubNoticeClicked()
                2131296596-> hubPeopleClicked()
                2131296597-> hubUpcomingClicked()
                else -> Log.d(TAG, "Error in menu bar!")
            }
        }

        val upcomingDao = UpcomingDao(hubID)
        val db = upcomingDao.fetchUpcomingList()
        db.addSnapshotListener{snapshots, e->
            if(e!=null)
            {
                Log.w(TAG, "listen:error", e)
                return@addSnapshotListener
            }

            for (dc in snapshots!!.documentChanges)
            {
                if(dc.type== DocumentChange.Type.REMOVED)
                {
                    val alarmTime = dc.document.getLong("schedTimeLongMS")
                    removeAlarm(alarmTime)
//                    Log.d(TAG, "$temp")
                }
            }
        }


    }

    private fun removeAlarm(alarmTime: Long?) {
        Toast.makeText(this, "$alarmTime removed!", Toast.LENGTH_SHORT).show()
    }


    fun shareHubInvitation(view: View) {
        // function to share the hub code
        // we're using the unique document ID from Firestore as the hubID for sharing
        // as it will automatically be unique

        val invitationIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Join my hub using the code\n$hubID")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(invitationIntent, "Share your hub code using:")
        startActivity(shareIntent)
    }

    fun createNewNotice(view: View) {
        // function to create a new notice
        // the user will be directed to a new activity
        // with a text view and will post the notice from there
        val newNoticeIntent = Intent(this, PostNewNotice::class.java)
        newNoticeIntent.putExtra("hubID", hubID)
        startActivity(newNoticeIntent)
    }

    private fun hubPeopleClicked() {
        Log.d(TAG, "poeple hub clicked!")
        // tab to show the hub people

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<PeopleFragment>(R.id.fl_fragment, args = userHubBundle)
        }
    }

    private fun hubUpcomingClicked() {
        Log.d(TAG, "Upcoming hub clicked!")
        // tab to show the upcoming list
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<UpcomingFragment>(R.id.fl_fragment, args = userHubBundle)
        }
    }

    private fun hubNoticeClicked() {
        Log.d(TAG, "Notice hub clicked!")
        // tab to show the hub notices
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<NoticesFragment>(R.id.fl_fragment, args = userHubBundle)
        }
    }
}