package com.thorproject.myplayer

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thorproject.myplayer.db.App
import com.thorproject.myplayer.db.VideoDatabase
import com.thorproject.myplayer.db.VideoItem
import com.thorproject.myplayer.db.VideoItemDao
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers


class MainActivity : AppCompatActivity() {

    private var mRepository: Repository? = null

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.main_recycle_view)
        recyclerView.layoutManager = LinearLayoutManager(this)


        mRepository = Repository(this)

        fillList()

        mRepository?.getRxAll()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({ verse ->
                recyclerView.adapter = RecyclerAdapter(verse)
            }, { error ->
                finish()
            })
    }

    private fun fillList() {
        mRepository?.firebase()
    }
}