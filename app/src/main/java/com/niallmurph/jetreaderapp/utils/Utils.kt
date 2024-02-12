package com.niallmurph.jetreaderapp.utils

import android.content.Context
import android.icu.text.DateFormat
import android.widget.Toast
import com.google.firebase.Timestamp

fun formateDate(timestamp: Timestamp) : String {
    val date = DateFormat.getDateInstance()
        .format(timestamp.toDate())
        .toString()
        .split(",")

    return date[0]
}

//Show Toast
fun showToast(context: Context, message : String) {
    Toast.makeText(context, message, Toast.LENGTH_LONG)
}