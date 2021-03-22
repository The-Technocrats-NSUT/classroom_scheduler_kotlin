package com.example.classroomScheduler.liveData

import androidx.lifecycle.LiveData
import com.example.classroomScheduler.model.HubModel
import com.example.classroomScheduler.repository.HubListDao

class HubListLiveData: LiveData<ArrayList<HubModel>>(){
    val hubListDao = HubListDao()
    val hubListCollection = hubListDao.hubsListCollection
    val query = hubListCollection

}