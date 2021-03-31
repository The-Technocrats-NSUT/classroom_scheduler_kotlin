package com.example.classroomScheduler.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.classroomScheduler.model.UserModel
import com.example.classroomScheduler.repository.UserListDao
import kotlinx.coroutines.launch

class UserListViewModel(application: Application, hubID: String): AndroidViewModel(application) {
    // The data which will be observed

    val userList =  MutableLiveData<ArrayList<UserModel>>()
    private val userListDao = UserListDao()
    init {
        viewModelScope.launch {

            userList.value = userListDao.fetchUserList(hubID)
        }
    }

}