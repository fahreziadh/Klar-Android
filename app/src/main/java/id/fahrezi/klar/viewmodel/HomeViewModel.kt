package id.fahrezi.klar.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _navigateToClassDetail = MutableLiveData<Long>()
    val navigateToClassDetail
        get() = _navigateToClassDetail
}