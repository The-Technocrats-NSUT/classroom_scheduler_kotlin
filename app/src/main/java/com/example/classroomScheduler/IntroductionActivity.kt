package com.example.classroomScheduler

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_introduction.*


class IntroductionActivity : AppCompatActivity() {
    private var count: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introduction)

        btn_next.setOnClickListener {
            count++
            updateIntroductionScreen(count)
        }

        btn_skip.setOnClickListener {
            val intent = Intent(this@IntroductionActivity, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun signInFromIntroActivity() {
        // Creating an intent for signing in
        val signInActivityIntent = Intent(this, SignInActivity::class.java)
        startActivity(signInActivityIntent)

        // Removing this Activity from the stack
        finish()
    }

    private fun updateIntroductionScreen(count : Int){
        when(count){
            0 ->{
                ll_classroom.visibility = View.VISIBLE
                ll_teacher.visibility = View.GONE
                ll_alarm.visibility = View.GONE
                ll_student.visibility = View.GONE
            }
            1 ->{
                ll_classroom.visibility = View.GONE
                ll_teacher.visibility = View.VISIBLE
                ll_alarm.visibility = View.GONE
                ll_student.visibility = View.GONE
            }
            2 ->{
                ll_classroom.visibility = View.GONE
                ll_teacher.visibility = View.GONE
                ll_alarm.visibility = View.VISIBLE
                ll_student.visibility = View.GONE
            }
            3 ->{
                ll_classroom.visibility = View.GONE
                ll_teacher.visibility = View.GONE
                ll_alarm.visibility = View.GONE
                ll_student.visibility = View.VISIBLE
                btn_next.text = getString(R.string.login_btn)
                btn_next.setOnClickListener{
                    signInFromIntroActivity()
                }
            }
        }
    }
}