package com.example.musicapp.presentation.titleFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapp.MyPlayer
import com.example.musicapp.databinding.DayItemBinding
import com.example.musicapp.domain.usecase.IncreaseProportionUseCase
import com.example.musicapp.domain.usecase.ReduceProportionUseCase
import com.example.musicapp.domain.usecase.UpdateSongsUseCase
import com.example.musicapp.util.PlaylistItem


/*Adapter for NESTED recycle view, for show
* one playlist with time start, time end and proportion */

class ChildAdapter :
    ListAdapter<PlaylistItem, ChildAdapter.ChildViewHolder>(ChildDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        return ChildViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }


    class ChildViewHolder private constructor(private val binding: DayItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(viewGroup: ViewGroup): ChildViewHolder {
                val inflater = LayoutInflater.from(viewGroup.context)
                val binding = DayItemBinding.inflate(inflater, viewGroup, false)
                return ChildViewHolder(binding)
            }
        }

        fun bind(item: PlaylistItem) {
            with(binding) {
                val player = MyPlayer.getInstanceMain(itemView.context)
                val playerExtra = MyPlayer.getInstanceExtra(itemView.context)

                val updateSongsUseCase = UpdateSongsUseCase(player, playerExtra)

                val timeTextString = "${item.from} - ${item.to}"
                timeText.text = timeTextString

                val playlist = item.playlist
                playListName.text = playlist.name

//               Song integrity check: if current MD5 not match with required - show errorView
                if (item.showError) errorView.visibility = View.VISIBLE

                MyPlayer.playlistsRequired.forEach {
                    if (it.playlistId == item.playlist.id)
                        binding.proportion.text = it.proportion.toString()
                }

                buttonLeft.setOnClickListener {
                    val reduceProportionUseCase = ReduceProportionUseCase()
                    val newProp = reduceProportionUseCase.invoke(item.playlist.id)
                    if (newProp < 1) return@setOnClickListener
                    proportion.text = newProp.toString()

                    updateSongsUseCase.invoke()
                }

                buttonRight.setOnClickListener {
                    val increaseProportionUseCase = IncreaseProportionUseCase()
                    val newProp = increaseProportionUseCase.invoke(item.playlist.id)
                    if (newProp < 1) return@setOnClickListener
                    proportion.text = newProp.toString()

                    updateSongsUseCase.invoke()

                }

            }
        }
    }


    class ChildDiffUtil : DiffUtil.ItemCallback<PlaylistItem>() {

        override fun areItemsTheSame(oldItem: PlaylistItem, newItem: PlaylistItem): Boolean {
            return ((oldItem.from + oldItem.to)
                    == (newItem.from + newItem.to))
        }

        override fun areContentsTheSame(oldItem: PlaylistItem, newItem: PlaylistItem): Boolean {
            return oldItem == newItem
        }
    }
}