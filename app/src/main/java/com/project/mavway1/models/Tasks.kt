package com.project.mavway1.models

import android.os.Parcel
import java.io.Serializable

//import androidx.room.ColumnInfo
//import androidx.room.Entity
//import androidx.room.PrimaryKey
//
//@Entity (tableName = "tasks")
//data class Tasks(
////    @PrimaryKey
//    @ColumnInfo(name = "todo") val todo: String,
////    @ColumnInfo(name = "priority")val priority: Int
//)
//{
//    @PrimaryKey(autoGenerate = true)
//    var id: Int = 0
//}


data class Tasks(
    val todo: String = "",
    val uid: String = ""
): Serializable




