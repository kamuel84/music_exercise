package com.exercise.music_exercise.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.exercise.music_exercise.MusicApplication
import com.exercise.music_exercise.activities.BaseActivity
import com.exercise.music_exercise.activities.ListAddActivity
import com.exercise.music_exercise.data_models.List_ItemsDataModel
import com.exercise.music_exercise.database.AppRepository
import com.exercise.music_exercise.fragments.music_detail.MusicDetailFragment

class MusicDetailViewModel(application:Application):AndroidViewModel(application) {
    val appRepository by lazy {
        AppRepository(application)
    }

    var _detailItemList : MutableLiveData<List<List_ItemsDataModel>> = MutableLiveData()
    val detailItemList : LiveData<List<List_ItemsDataModel>>
        get() = _detailItemList

    fun getDetailList(parentIdx:Int){
        if(parentIdx == -1){
            appRepository.getMusicDetailList().observe(MusicApplication.currentActivity as ListAddActivity, Observer {
                _detailItemList.postValue(it)
        })} else{
            appRepository.getMusicDetailList(parentIdx).observe(MusicApplication.currentActivity as ListAddActivity, Observer {
                _detailItemList.postValue(it)
        })}
    }

    fun checkDetailItem(baseActivity: BaseActivity, position: Int, data:List_ItemsDataModel, isCheck: Boolean){
        if (baseActivity is ListAddActivity) {
            val addActivity:ListAddActivity = baseActivity as ListAddActivity

            addActivity.addListViewModel.checkSelectList(data.idx, data, isCheck)

            if(_detailItemList != null && _detailItemList!!.value != null){
                run check@{
                    _detailItemList!!.value!!.forEachIndexed { index, listItemsdatamodel ->
                        if(listItemsdatamodel.idx == data.idx){
                            listItemsdatamodel.checked = isCheck
                        }
                    }
                }
            }

            _detailItemList.value = _detailItemList.value
        }
    }

    class Factory(val application: Application): ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MusicDetailViewModel(application) as T
        }
    }
}