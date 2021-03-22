package com.example.classroomScheduler.viewModel

import android.app.Application
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.classroomScheduler.model.HubModel
import com.example.classroomScheduler.repository.HubListDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HubListViewModel(application: Application): AndroidViewModel(application) {
    // The data which will be observed

    val hubList : MutableLiveData<ArrayList<HubModel>>
    val hubListDao = HubListDao()
    init {
        hubList = hubListDao.fetchHubList()
    }

}