package com.mati.launcher.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mati.launcher.data.model.News
import com.mati.launcher.data.model.Update

class MainViewModel() : ViewModel() {

    private val _resultUpdate = MutableLiveData<Update>()
    val resultUpdate: LiveData<Update> get() = _resultUpdate

    private val _resultStories = MutableLiveData<News>()
    val resultStories: LiveData<News> get() = _resultStories

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private fun checkUpdate() {

    }

    private fun getStories() {

    }


}

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}
