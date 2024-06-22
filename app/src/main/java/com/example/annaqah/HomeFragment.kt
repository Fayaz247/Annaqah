package com.example.annaqah

import android.content.Intent
import android.media.AudioAttributes
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.annaqah.adapter.HomeAdapter
import com.example.annaqah.data.ShopItem

class HomeFragment : Fragment(), HomeAdapter.OnItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val videoView: VideoView = view.findViewById(R.id.videoView)
        val videoUri = Uri.parse("android.resource://${requireActivity().packageName}/${R.raw.home_video}")
        videoView.setVideoURI(videoUri)

        videoView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = true

            // Setting audio attributes to ensure the video is muted
            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MOVIE)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build()
            mediaPlayer.setAudioAttributes(audioAttributes)
            mediaPlayer.setVolume(0f, 0f)

            videoView.start()
        }

        val homeItems = listOf(
            ShopItem(R.drawable.obsidian_djellaba, "Djellaba"),
            ShopItem(R.drawable.emerald_gandoura, "Casa"),
            ShopItem(R.drawable.monochrome_gandoura, "Hoceima"),
            ShopItem(R.drawable.sapphire_djellaba, "Djellaba"),
            ShopItem(R.drawable.neru_gandoura, "Casa"),
            ShopItem(R.drawable.blanche_gandoura, "Casa")
        )

        val recyclerView: RecyclerView = view.findViewById(R.id.homeRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = HomeAdapter(homeItems, this)
    }

    override fun onItemClick(shopItem: ShopItem) {
        val intent = when (shopItem.tag) {
            "Djellaba" -> Intent(activity, DjellabaActivity::class.java)
            "Hoceima" -> Intent(activity, HoceimaActivity::class.java)
            "Casa" -> Intent(activity, CasaActivity::class.java)
            else -> return
        }
        startActivity(intent)
    }
}
