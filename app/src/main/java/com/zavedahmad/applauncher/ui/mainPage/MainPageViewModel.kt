package com.zavedahmad.applauncher.ui.mainPage


import androidx.lifecycle.ViewModel
import com.zavedahmad.applauncher.roomDatabase.PreferencesDao
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel()
class MainPageViewModel @Inject constructor(

    val preferencesDao: PreferencesDao
) :
    ViewModel() {
    override fun onCleared() {
        println("mainViewModelCleared")
    }
    private val _isEnabled= MutableStateFlow(1)
    val isEnabled =  _isEnabled.asStateFlow()



}