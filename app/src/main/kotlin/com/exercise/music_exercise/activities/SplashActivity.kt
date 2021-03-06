package com.exercise.music_exercise.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.exercise.music_exercise.R
import com.exercise.music_exercise.data_models.List_DefaultItemDataModel
import com.exercise.music_exercise.data_models.List_HeaderDataModel
import com.exercise.music_exercise.data_models.List_ItemsDataModel
import com.exercise.music_exercise.database.AppDataBase
import com.exercise.music_exercise.utils.PreferenceUtils

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        init()
        startMain()
    }

    var callback: RoomDatabase.Callback = object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            //DO AS NEEDED
            Log.d("kamuel", "onCreate!!!!")
        }

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            //DO AS NEEDED
            Log.d("kamuel", "onOpen!!!!")
        }

        override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
            super.onDestructiveMigration(db)
            Log.d("kamuel", "onDestructiveMigration!!!!")
        }
    }

    fun startMain() {
        Handler(Looper.getMainLooper()).postDelayed({
            var intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }

    fun init() {
        var prefUtils: PreferenceUtils = PreferenceUtils.getInstance(this)
        if (!prefUtils.getBoolean("default_data", false)) {
            var groupList: ArrayList<List_HeaderDataModel> = arrayListOf()
            groupList.add(List_HeaderDataModel("??????1", "??????1", "water_01", "D"))
            groupList.add(List_HeaderDataModel("??????2", "??????2", "water_02", "D"))
            groupList.add(List_HeaderDataModel("??????3", "??????3", "water_03", "D"))
            groupList.add(List_HeaderDataModel("??????4", "??????4", "water_04", "D"))
            groupList.add(List_HeaderDataModel("??????5", "??????5", "water_05", "D"))
            groupList.add(List_HeaderDataModel("???1", "???1", "rain_01", "D"))
            groupList.add(List_HeaderDataModel("???2", "???2", "rain_02", "D"))
            groupList.add(List_HeaderDataModel("???3", "???3", "rain_03", "D"))
            groupList.add(List_HeaderDataModel("??????1", "??????1", "wind_01", "D"))
            groupList.add(List_HeaderDataModel("??????2", "??????2", "wind_02", "D"))
            groupList.add(List_HeaderDataModel("??????1", "??????1", "wave_01", "D"))
            groupList.add(List_HeaderDataModel("??????2", "??????2", "wave_02", "D"))
            groupList.add(List_HeaderDataModel("??????????????????1", "??????????????????1", "pink_noise", "D"))
            groupList.add(List_HeaderDataModel("??????????????????2", "??????????????????2", "white_noise", "D"))
//            groupList.add(List_HeaderDataModel("????????????1", "????????????1", "music_01", "D"))
//            groupList.add(List_HeaderDataModel("????????????2", "????????????2", "music_02", "D"))
//            groupList.add(List_HeaderDataModel("????????????3", "????????????3", "music_03", "D"))
            groupList.add(List_HeaderDataModel("???????????????", "???????????????", "frog", "E"))
            groupList.add(List_HeaderDataModel("?????????", "?????????", "bird", "E"))
            groupList.add(List_HeaderDataModel("???????????????", "???????????????", "footprint", "E"))
            groupList.add(List_HeaderDataModel("????????????", "????????????", "forest", "E"))
            groupList.add(List_HeaderDataModel("???????????????", "???????????????", "valley", "E"))
            groupList.add(List_HeaderDataModel("0KHz", "0KHz", "", "CD"))
            groupList.add(List_HeaderDataModel("2KHz", "2KHz", "", "CD"))
            groupList.add(List_HeaderDataModel("4KHz", "4KHz", "", "CD"))
            groupList.add(List_HeaderDataModel("8KHz", "8KHz", "", "CD"))
            groupList.add(List_HeaderDataModel("???????????????", "???????????????", "", "CD"))

            var musicDao = AppDataBase.getInstance(this, callback).musicListDao()
            var musicDefault = AppDataBase.getInstance(this, callback).musicListDefaultDetailDao()
            var musicDetailDao = AppDataBase.getInstance(this, callback).musicListDetailDao()


            groupList.forEachIndexed { index, musicListDataModel ->
                musicDao.insert(musicListDataModel)

                if(musicListDataModel.customType == "D") {
                    musicDefault.insert(
                        List_DefaultItemDataModel(
                            0,
                            "",
                            musicListDataModel.listTitle_kor,
                            musicListDataModel.listTitle_eng,
                            "${musicListDataModel.image_path}_01",
                            0
                        )
                    )
                    musicDefault.insert(
                        List_DefaultItemDataModel(
                            0,
                            "",
                            musicListDataModel.listTitle_kor,
                            musicListDataModel.listTitle_eng,
                            "${musicListDataModel.image_path}_02",
                            2
                        )
                    )
                    musicDefault.insert(
                        List_DefaultItemDataModel(
                            0,
                            "",
                            musicListDataModel.listTitle_kor,
                            musicListDataModel.listTitle_eng,
                            "${musicListDataModel.image_path}_03",
                            4
                        )
                    )
                    musicDefault.insert(
                        List_DefaultItemDataModel(
                            0,
                            "",
                            musicListDataModel.listTitle_kor,
                            musicListDataModel.listTitle_eng,
                            "${musicListDataModel.image_path}_04",
                            8
                        )
                    )
                } else if(musicListDataModel.customType == "E"){
                    /** ????????? ?????? **/
                    musicDefault.insert(
                            List_DefaultItemDataModel(
                                    0,
                                    "",
                                    musicListDataModel.listTitle_kor,
                                    musicListDataModel.listTitle_eng,
                                    "${musicListDataModel.image_path}_01",
                                    -1
                            )
                    )
                    /** ????????? ?????? **/
                    /** ????????? ?????? **/
                    /** ????????? **/
                    /** ???????????? **/
                }
            }

            musicDao.getGroupAllList()?.observe(this, Observer {
                it.forEach {
                    var parentIdx = it.idx
                    var parentTitle_kor = it.listTitle_kor
                    var parentTitle_eng = it.listTitle_eng
                    var parentImage = it.image_path
                    var parentType = it.customType

                    if (parentType == "D") {
                        Log.d("kamuel", "DefaultTitleName ::: ${parentTitle_kor}")
                        musicDefault.getDefaultItemList(parentTitle_kor).observe(this, Observer {
                            Log.d("kamuel", "DefaultItemListSize ::: ${it.size}")
                            it.forEachIndexed { index, listDefaultitemdatamodel ->
                                musicDetailDao.insert(
                                    List_ItemsDataModel(
                                        0,
                                        parentIdx,
                                        listDefaultitemdatamodel.idx,
                                        5,
                                        index
                                    )
                                )
                            }
                        })
                    } else if (parentType == "E") {
                        Log.d("kamuel", "DefaultTitleName ::: ${parentTitle_kor}")
                        musicDefault.getDefaultItemList(parentTitle_kor).observe(this, Observer {
                            Log.d("kamuel", "DefaultItemListSize ::: ${it.size}")
                            it.forEachIndexed { index, listDefaultitemdatamodel ->
                                musicDetailDao.insert(
                                        List_ItemsDataModel(
                                                0,
                                                parentIdx,
                                                listDefaultitemdatamodel.idx,
                                                5,
                                                index
                                        )
                                )
                            }
                        })
                    } else {
                        musicDao.getGroupListForCustom(parentTitle_kor)?.observe(this, Observer {
                            it.forEach {
                                var parentIdx = it.idx
                                var parentTitle_kor = it.listTitle_kor
                                var parentTitle_eng = it.listTitle_eng
                                var parentImage = it.image_path

                                var hertz = 0
                                if (parentTitle_kor == "0KHz") {
                                    hertz = 0
                                } else if (parentTitle_kor == "2KHz") {
                                    hertz = 2
                                } else if (parentTitle_kor == "4KHz") {
                                    hertz = 4
                                } else if (parentTitle_kor == "8KHz") {
                                    hertz = 8
                                } else if (parentTitle_kor == "???????????????") {
                                    hertz = -1
                                }

                                musicDefault.getDefaultHertzList(hertz).observe(this, Observer {
                                    it.forEachIndexed { index, listDefaultitemdatamodel ->
                                        musicDetailDao.insert(
                                            List_ItemsDataModel(
                                                0,
                                                parentIdx,
                                                listDefaultitemdatamodel.idx,
                                                5,
                                                index
                                            )
                                        )
                                    }
                                })
                            }
                        })
                    }


                }
            })

            prefUtils.setBoolean("default_data", true)
        }
    }
}