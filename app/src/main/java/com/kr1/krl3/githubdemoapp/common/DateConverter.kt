package com.kr1.krl3.githubdemoapp.common

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("ConstantLocale")
object DateConverter {

    private val dateIn = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    private val dateOut = SimpleDateFormat("yyyy.MM.dd 'at' hh:mm:ss", Locale.getDefault())

    fun convertDate(dateString: String): String {
        val date = dateIn.parse(dateString) ?: throw IllegalStateException("Date should not be null")

        return dateOut.format(date)
    }
}