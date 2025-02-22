package com.project.managelesson.data.data_source.local

import androidx.room.TypeConverter

class ColorListConverter {
    @TypeConverter
    fun fromColorList(colorList: List<Int>): String {
        return colorList.joinToString(",") { it.toString() }
    }

    @TypeConverter
    fun toColorList(colorList: String): List<Int> {
        return colorList.split(",").map { it.toInt() }
    }
}