package com.jp.chatapp.old.presentation.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone


fun dateFormatter(date: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    inputFormat.timeZone = TimeZone.getTimeZone("UTC")

    val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    outputFormat.timeZone = TimeZone.getDefault() // Adjusts to local time zone

    val d: Date = inputFormat.parse(date)!!
    val formattedDate = outputFormat.format(d)
    return  formattedDate
}
fun timeFormatter(date :String) : String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    inputFormat.timeZone = TimeZone.getTimeZone("UTC")

    val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    outputFormat.timeZone = TimeZone.getDefault() // Adjusts to local time zone

    val d: Date = inputFormat.parse(date)!!
    val formattedTime = outputFormat.format(d)

    return formattedTime
}