package com.antuere.data.networkApi.mappers

import com.antuere.data.localDatabase.musicProfileEntity.PlaylistEntity
import com.antuere.data.localDatabase.util.EntityMapper
import com.antuere.data.networkApi.musicProfileDto.PlaylistDto

class PlaylistDtoMapper(private val songDtoMapper: SongDtoMapper) :
    EntityMapper<PlaylistDto, PlaylistEntity> {


    override fun mapToEntity(t: PlaylistDto): PlaylistEntity {
        return PlaylistEntity(
            t.duration,
            songs = t.songs.map { songDtoMapper.mapToEntity(it) },
            t.id,
            t.name,
            t.random
        )
    }

    override fun mapFromEntity(entity: PlaylistEntity): PlaylistDto {
        return PlaylistDto(
            entity.duration,
            songs = entity.songs.map { songDtoMapper.mapFromEntity(it) },
            entity.id,
            entity.name,
            entity.random
        )
    }
}