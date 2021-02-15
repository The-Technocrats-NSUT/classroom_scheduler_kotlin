package com.example.classroomScheduler

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
            // TODO: "Goes to Login Activity"
        }
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
                btn_next.text = "Login"
            }
        }
    }
}