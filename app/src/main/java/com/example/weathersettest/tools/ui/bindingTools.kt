package com.example.weathersettest.tools.ui

import android.content.Context
import android.graphics.drawable.Drawable
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

fun loadImage(imageName: String, context: Context): Drawable? {
    return try {
        // get input stream
        val ims: InputStream = context.assets.open(imageName)
        // load image as Drawable
        Drawable.createFromStream(ims, null)
    } catch (ex: IOException) {
        null
    }
}

fun convertTimestamp(time: Int): String {
    val simpleDate = SimpleDateFormat("hh:mm")
    return simpleDate.format(Date())
}