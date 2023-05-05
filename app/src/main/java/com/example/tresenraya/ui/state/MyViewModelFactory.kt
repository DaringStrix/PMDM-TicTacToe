package com.example.tresenraya.ui.state

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MyViewModelFactory(private val defaultCellColor: Color) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T = TresEnRayaViewModel(
        defaultCellColor
    ) as T
//reference: https://stackoverflow.com/questions/67982230/jetpack-compose-pass-parameter-to-viewmodel
}