package com.example.movamovieapp.ui.videoplayer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.movamovieapp.R
import com.example.movamovieapp.databinding.FragmentYoutubeVideoPlayerBinding
import com.example.movamovieapp.ui.details.MovieDetailsFragmentArgs
import com.example.movamovieapp.utils.BaseFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener


class YoutubeVideoPlayerFragment : BaseFragment<FragmentYoutubeVideoPlayerBinding>(FragmentYoutubeVideoPlayerBinding::inflate) {

    private val args: YoutubeVideoPlayerFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycle.addObserver(binding.videoplayer)

        binding.videoplayer.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                youTubePlayer.loadVideo(args.videoID, 0f)
            }
        })



    }
}