package com.thorproject.myplayer

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.thorproject.myplayer.db.App
import com.thorproject.myplayer.db.VideoDatabase
import com.thorproject.myplayer.db.VideoItem
import com.thorproject.myplayer.db.VideoItemDao
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable

import io.reactivex.schedulers.Schedulers




class Repository(context: Context?) {


    private val db: VideoDatabase =  App.instance?.getDatabase()!!
    private val videoDao: VideoItemDao? =  db.videoItemDao()


    fun getRxAll(): Flowable<List<VideoItem>> {

            return videoDao?.getAll()!!

    }

    @SuppressLint("CheckResult")
    fun insertIntoDb(videoItem: VideoItem) {
        Completable.fromAction {
            videoDao?.insert(videoItem)
        }.subscribeOn(Schedulers.io())
            .subscribe({
                Log.d("TAG", "Blog Db: list insertion was successful")
            }, {
                Log.d("TAG", "Blog Db: list insertion wasn't successful")
                it.printStackTrace()
            })
    }


    fun delete(){
        db.videoItemDao()?.deleteAll()
    }

    fun closeDb() {
        db?.close()
    }

    fun firebase(){
        var videoItem : VideoItem

        val ref = FirebaseDatabase.getInstance().getReference("/videos")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(n in 1..10) {
                    if(snapshot.child("/video"+n).getValue(VideoItem::class.java) != null) {
                        videoItem = snapshot.child("/video" + n).getValue(VideoItem::class.java)!!
                        if (videoItem != null) {
                            insertIntoDb(videoItem)
                        }
                    }
                }
                Log.d("TAG", snapshot.child("video1").toString())
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}