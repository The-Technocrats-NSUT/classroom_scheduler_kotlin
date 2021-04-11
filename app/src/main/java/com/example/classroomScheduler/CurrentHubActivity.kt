package com.example.classroomScheduler

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.classroomScheduler.Utils.PostNewNotice

class CurrentHubActivity : AppCompatActivity() {
    private lateinit var hubID: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_current_hub)

        val isAdmin: Boolean = intent.getBooleanExtra("isAdmin", false)
        val currHubID : String? = intent.getStringExtra("hubID")
        if (currHubID!=null)
        {
            hubID = currHubID
        }
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
        val newNoticeIntent :Intent = Intent(this, PostNewNotice::class.java)
        newNoticeIntent.putExtra("hubID", hubID)
        startActivity(newNoticeIntent)
    }
}