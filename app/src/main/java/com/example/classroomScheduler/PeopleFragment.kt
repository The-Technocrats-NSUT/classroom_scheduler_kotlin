package com.example.classroomScheduler

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.classroomScheduler.R
import kotlinx.android.synthetic.*

class PeopleFragment : Fragment(R.layout.fragment_people) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO : create fragment transactions

        val adminPrivilege = requireArguments().getBoolean("isAdmin")
        // TODO : Add recycler view adapter and viewModel, pass arguments

    }
}