package com.example.musicapp.screens.playerFragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.musicapp.MyPlayer
import com.example.musicapp.R
import com.example.musicapp.databinding.FragmentPlayerBinding
import com.example.musicapp.network.musicProfile.Schedule
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.material.appbar.MaterialToolbar

class PlayerFragment : Fragment() {

    companion object {
        fun newInstance() = PlayerFragment()
    }

    private lateinit var viewModel: PlayerViewModel
    private lateinit var binding: FragmentPlayerBinding
    private lateinit var actionBar: MaterialToolbar

    private lateinit var schedule: Schedule

    private lateinit var player: ExoPlayer
    private lateinit var playerView: StyledPlayerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayerBinding.inflate(inflater, container, false)
        actionBar = binding.toolBarApp


        val activity = requireActivity() as AppCompatActivity
        activity.setSupportActionBar(actionBar)
        actionBar.navigationIcon =
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_arrow_back_24)
        actionBar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        val args: PlayerFragmentArgs by navArgs()
        schedule = args.schedule

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[PlayerViewModel::class.java]

        player = MyPlayer.getInstanceMain(requireContext())
        playerView = binding.playerView
        playerView.player = player

    }

}


