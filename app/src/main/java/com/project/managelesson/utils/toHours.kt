package com.project.managelesson.utils

fun Long.toHours(): Double {
    val hours = this.toDouble() / 3600
    return hours
}