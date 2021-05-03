package com.thorproject.myplayer.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "video_item")
class VideoItem(val videoName: String = "",
                val videoImage: String  = "",
                val videoUrl: String = "",
                @PrimaryKey(autoGenerate = false) val id: Int? = null)  {

}