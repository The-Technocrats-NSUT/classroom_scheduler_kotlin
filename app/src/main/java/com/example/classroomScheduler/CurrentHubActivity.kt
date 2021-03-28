package com.example.classroomScheduler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class CurrentHubActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classroom_hub)

        val bundle: Bundle? = intent.extras
        val isAdmin: Boolean = intent.getBooleanExtra("isAdmin", false)
        val hubID : String? = intent.getStringExtra("hubID")
    }
}