package com.thorproject.myplayer.db

import android.app.Application
import androidx.room.Room


class App : Application() {
    private var database: VideoDatabase? = null
    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(this, VideoDatabase::class.java, "database")
            .build()
    }

    fun getDatabase(): VideoDatabase? {
        return database
    }

    companion object {
        var instance: App? = null
    }
}