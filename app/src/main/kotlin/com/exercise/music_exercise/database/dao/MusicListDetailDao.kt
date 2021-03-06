package com.exercise.music_exercise.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import com.exercise.music_exercise.data_models.List_DefaultItemDataModel
import com.exercise.music_exercise.data_models.List_HeaderDataModel
import com.exercise.music_exercise.data_models.List_ItemsDataModel

@Dao
interface MusicListDetailDao:BaseDao<List_ItemsDataModel> {
    @Query("SELECT * FROM list_default_item WHERE idx in (SELECT item_idx FROM list_items WHERE header_idx = :parentIdx)")
    fun getDetailList(parentIdx:Int) : LiveData<List<List_DefaultItemDataModel>>

    @Query("SELECT * FROM list_items")
    fun getDetailList() : LiveData<List<List_ItemsDataModel>>

    @Query("DELETE FROM list_items WHERE header_idx = :parentIdx")
    fun deleteDetailList(parentIdx: Int)
}