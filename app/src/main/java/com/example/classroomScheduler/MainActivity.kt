package com.example.classroomScheduler

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    private var isLoggedIn : Boolean ?= false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(isLoggedIn!!){
            // TODO: Go to Classroom Activity
        }

        btn_getStarted.setOnClickListener {
            val intent = Intent(this, IntroductionActivity::class.java, )
            startActivity(intent)
        }

    }

    companion object{
        private const val IS_LOGGED_IN = 1
    }

}


