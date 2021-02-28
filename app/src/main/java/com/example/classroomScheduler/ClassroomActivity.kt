package com.example.classroomScheduler

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_classroom.*

class ClassroomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_classroom)


        // TODO:  Add side navigation menu

        fab_newClassroom.setOnClickListener {
            // TODO: OPEN A DIALOG BOX TO ENTER CLASSROOM CODE AND AN OPTION TO CREATE A NEW CLASSROOM
            val intent = Intent(this@ClassroomActivity, ClassroomHubActivity::class.java)
            startActivity(intent)
        }
    }
}