package com.thorproject.myplayer.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [VideoItem::class], version = 1)
abstract class VideoDatabase: RoomDatabase() {
    abstract fun videoItemDao(): VideoItemDao?
}