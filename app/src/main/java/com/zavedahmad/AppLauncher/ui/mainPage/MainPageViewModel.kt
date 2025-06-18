package com.zavedahmad.AppLauncher.ui.mainPage


import androidx.lifecycle.ViewModel
import com.zavedahmad.AppLauncher.roomDatabase.PreferencesDao
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel()
class MainPageViewModel @Inject constructor(

    val preferencesDao: PreferencesDao
) :
    ViewModel() {
    override fun onCleared() {
        println("mainViewModelCleared")
    }


}