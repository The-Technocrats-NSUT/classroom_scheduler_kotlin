package com.example.classroomScheduler.model

import java.security.Timestamp
import java.text.SimpleDateFormat

data class NoticeModel (
    val noticeText: String = "Nothing",
    val datePosted: String = "00-00-0000",
    val timePosted: String = "00-00-PM",
    val noticeAuthor: String = ""
)