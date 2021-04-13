package com.example.classroomScheduler.Utils

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.classroomScheduler.R
import com.example.classroomScheduler.repository.NoticeDao

class PostNewNotice : AppCompatActivity() {

    private lateinit var noticeDAO : NoticeDao
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_new_notice)

            val  postButton = findViewById<Button>(R.id.postNoticeButton)
            postButton.setOnClickListener{
                val postInput = findViewById<EditText>(R.id.postInput)
                val input = postInput.text.toString().trim()
                if (input.isNotEmpty())
                {
                    val currentHubID = intent.getStringExtra("hubID")
                    noticeDAO = NoticeDao(currentHubID!!)
                    noticeDAO.postNotice(input)
                    finish()
                }
                else
                {
                    Toast.makeText(this, "Cannot post empty notice!", Toast.LENGTH_SHORT).show()
                }
        }
    }
}