package com.antuere.data.networkApi.musicProfileDto

import com.squareup.moshi.Json


data class PlaylistsZoneDto(

    @Json(name = "playlist_id")
    val playlistId: Int,
    var proportion: Int
)




