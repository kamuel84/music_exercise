package com.exercise.music_exercise.data_models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "play_report")
data class PlayReportDataModel(
    val musicTitle:String,
    val hertz:Int,
    val playCount:Int,
    val playDate:String
):Serializable{
    @PrimaryKey(autoGenerate = true)
    var idx:Int = 0
}