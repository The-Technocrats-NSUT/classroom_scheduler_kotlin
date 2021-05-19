package com.example.classroomScheduler

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.classroomScheduler.repository.UserListDao

class JoinHubActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_hub)


        val joinHubBtn = findViewById<Button>(R.id.joinHubBtn)

        joinHubBtn.setOnClickListener{
            val hubNameInput = findViewById<EditText>(R.id.joinHubIDTV).text.toString()
            if (hubNameInput.isEmpty()) {
                AlertDialog.Builder(this)
                    .setTitle("Invalid Operation")
                    .setMessage("Please enter valid code!")
                    .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
                    .setCancelable(true)
                    .show()
            } else {
                val userListDao = UserListDao("none", hubNameInput)
                userListDao.joinHub()
            }

            finish()
        }
    }
}