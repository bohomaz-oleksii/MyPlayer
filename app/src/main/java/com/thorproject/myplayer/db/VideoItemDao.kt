package com.thorproject.myplayer.db

import androidx.lifecycle.LiveData
import androidx.room.*
import io.reactivex.Flowable

@Dao
interface VideoItemDao {
    @Insert
    fun insert(video: VideoItem)

    @Update
    fun update(video: VideoItem)

    @Delete
    fun delete(video: VideoItem)

    @Query("delete from video_item")
    fun deleteAll()

    @Query("select * from video_item")
    fun getAll(): Flowable<List<VideoItem>>

    @Query("SELECT * FROM video_item WHERE id = :id")
    fun getById(id: Int): Flowable<VideoItem?>
}