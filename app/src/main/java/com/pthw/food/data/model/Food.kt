package com.pthw.food.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Food")
data class Food(
    @PrimaryKey @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "oneMM")
    val oneMM: String,

    @ColumnInfo(name = "twoMM")
    val twoMM: String,

    @ColumnInfo(name = "dieMM")
    val dieMM: String,

    @ColumnInfo(name = "oneEN")
    val oneEN: String,

    @ColumnInfo(name = "twoEN")
    val twoEN: String,

    @ColumnInfo(name = "dieEN")
    val dieEN: String,

    @ColumnInfo(name = "type")
    val type: String,

    @ColumnInfo(name = "imgOne")
    val imgOne: String,

    @ColumnInfo(name = "imgTwo")
    val imgTwo: String
) : Serializable {
    companion object {
        fun fake() = Food(
            id = 1,
            oneMM = "",
            twoMM = "",
            dieMM = "",
            oneEN = "Apple Sword Bean",
            twoEN = "Orange",
            dieEN = "Chest congestion| Death",
            type = "",
            imgOne = "",
            imgTwo = ""
        )
    }

    fun getFoodOne(localeCode: String) = if (localeCode == Localization.ENGLISH) oneEN else oneMM
    fun getFoodTwo(localeCode: String) = if (localeCode == Localization.ENGLISH) twoEN else twoMM
    fun getFoodDie(localeCode: String) = if (localeCode == Localization.ENGLISH) dieEN else dieMM
}
