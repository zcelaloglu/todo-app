package com.celaloglu.zafer.todos.util

import java.text.SimpleDateFormat
import java.util.*

object DateFormatter {

    @JvmStatic
    fun format(date: String?): String {
        return "Due Date: $date"
    }

    @JvmStatic
    fun getDateFrom(dueDate: String?): Date {
        return SimpleDateFormat("dd/MM/yyyy", Locale.US).parse(dueDate)!!
    }

    @JvmStatic
    fun getDateElementFrom(dueDate: String?, index: Int): Int {
        return dueDate?.split("/")?.get(index)!!.toInt()
    }
    
    @JvmStatic
    fun formatForUI(day: Int, month: Int, year: Int): String {
        return "$day/$month/$year"
    }

}
