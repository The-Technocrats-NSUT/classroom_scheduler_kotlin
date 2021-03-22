package com.example.classroomScheduler

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var isLoggedIn : Boolean ?= false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(isLoggedIn!!){
            val intent = Intent(this, HubListMainActivity::class.java)
            startActivity(intent)
        }

        btn_getStarted.setOnClickListener {
            val intent = Intent(this, IntroductionActivity::class.java, )
            startActivity(intent)
            finish()
        }

    }

    companion object{
        private const val IS_LOGGED_IN = 1
    }

}


