package com.exercise.music_exercise.viewmodels

import android.app.Application
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.exercise.music_exercise.data_models.PlayReportDataModel
import com.exercise.music_exercise.database.AppRepository
import java.util.*

class CalendarViewModel(application: Application):ViewModel() {
    var _musicReportList = MutableLiveData<List<PlayReportDataModel>>()
    val musicReportList:LiveData<List<PlayReportDataModel>>
        get() = _musicReportList

    val appRepository by lazy {
        AppRepository(application)
    }

    fun getReportList(owner: LifecycleOwner, date:String){
        appRepository.getPlayReport(date).observe(owner, Observer {
            _musicReportList.postValue(it)
        })
    }

    class Factory(val application: Application): ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CalendarViewModel(application) as T
        }
    }
}