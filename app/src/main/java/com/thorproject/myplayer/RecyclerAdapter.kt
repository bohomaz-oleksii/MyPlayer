package com.thorproject.myplayer

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.thorproject.myplayer.db.VideoItem

class RecyclerAdapter(private val videos: List<VideoItem>?) :
    RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageViewVideo: ImageView? = null
        var textViewVideo: TextView? = null

        init {
            imageViewVideo= itemView.findViewById(R.id.image_video)
            textViewVideo = itemView.findViewById(R.id.name_video)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.video_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //holder.imageViewVideo?.setImageResource(videos!![position].videoImage)

        val targetImageView = holder.imageViewVideo
        Picasso.get().load(videos!![position].videoImage).into(targetImageView)
        holder.textViewVideo?.text = videos!![position].videoName

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, VideoPlayerActivity::class.java)
            intent.putExtra("pos", position)
            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount() = videos!!.size
}