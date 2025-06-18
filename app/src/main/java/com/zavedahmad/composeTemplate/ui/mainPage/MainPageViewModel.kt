package com.zavedahmad.composeTemplate.ui.mainPage


import androidx.lifecycle.ViewModel
import com.zavedahmad.composeTemplate.retrofitApi.RetrofitApi
import com.zavedahmad.composeTemplate.roomDatabase.PreferencesDao
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