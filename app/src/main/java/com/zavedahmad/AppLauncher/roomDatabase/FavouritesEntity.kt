package com.zavedahmad.AppLauncher.roomDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "FavouritesTable")
data class FavouritesEntity (
    @PrimaryKey
    val movieID: String,

)