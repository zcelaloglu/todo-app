package com.celaloglu.zafer.todos.util

object LocationFormatter {

    @JvmStatic
    fun format(latitude: Double, longitude: Double): String {
        return "Location: " + String.format("%.3f / %.3f", latitude, longitude)
    }
}
