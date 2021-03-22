package com.example.classroomScheduler.model

data class HubModel (
        val hubName: String = "",
        val hubRef : String = "",
        val isAdmin: Boolean = false,
        val createdAt: Long = 0
        )
