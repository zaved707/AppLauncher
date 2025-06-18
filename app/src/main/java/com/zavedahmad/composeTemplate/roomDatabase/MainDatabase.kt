package com.zavedahmad.composeTemplate.roomDatabase


import androidx.room.Database

import androidx.room.RoomDatabase


@Database(entities = [ PreferenceEntity::class, FavouritesEntity::class], version = 1, exportSchema = false)

abstract class MainDatabase : RoomDatabase(){
    abstract fun preferencesDao(): PreferencesDao
    abstract fun favouritesDao(): FavouritesDao
}