package com.zavedahmad.applauncher.roomDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName= "PreferencesTable")
data class PreferenceEntity (

        @PrimaryKey
        val accessKey: String,
        val value: String

)