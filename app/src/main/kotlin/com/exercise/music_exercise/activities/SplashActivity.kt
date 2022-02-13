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

class SplashActivity: AppCompatActivity() {
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

    fun startMain(){
        Handler(Looper.getMainLooper()).postDelayed({
            var intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }

    fun init(){
        var prefUtils: PreferenceUtils = PreferenceUtils.getInstance(this)
        if (!prefUtils.getBoolean("default_data", false)) {
            var groupList:ArrayList<List_HeaderDataModel> = arrayListOf()
            groupList.add(List_HeaderDataModel("1. 개울소리1","개울소리1","water_01","D"))
            groupList.add(List_HeaderDataModel("2. 개울소리2","개울소리2","water_02","D"))
            groupList.add(List_HeaderDataModel("3. 개울소리3","개울소리3","water_03","D"))
            groupList.add(List_HeaderDataModel("4. 개울소리4","개울소리4","water_04", "D"))
            groupList.add(List_HeaderDataModel("5. 개울소리5","개울소리5","water_05","D"))
            groupList.add(List_HeaderDataModel("6. 비소리1","비소리1","rain_01","D"))
            groupList.add(List_HeaderDataModel("7. 비소리2","비소리2","rain_02","D"))
            groupList.add(List_HeaderDataModel("8. 비소리3","비소리3","rain_03","D"))
            groupList.add(List_HeaderDataModel("9. 바람소리1","바람소리1","wind_01","D"))
            groupList.add(List_HeaderDataModel("10. 바람소리2","바람소리2","wind_02","D"))
            groupList.add(List_HeaderDataModel("11. 파도소리1","파도소리1","wave_01","D"))
            groupList.add(List_HeaderDataModel("12. 파도소리2","파도소리2","wave_02","D"))
            groupList.add(List_HeaderDataModel("13. 화이트노이즈1","화이트노이즈1","pink_noise","D"))
            groupList.add(List_HeaderDataModel("14. 화이트노이즈2","화이트노이즈2","white_noise", "D"))
            groupList.add(List_HeaderDataModel("15. 치료음악1","치료음악1","music_01","D"))
            groupList.add(List_HeaderDataModel("16. 치료음악2","치료음악2","music_02","D"))
            groupList.add(List_HeaderDataModel("17. 치료음악3","치료음악3","music_03","D"))

            var musicDao = AppDataBase.getInstance(this, callback).musicListDao()
            var musicDefault = AppDataBase.getInstance(this, callback).musicListDefaultDetailDao()
            var musicDetailDao = AppDataBase.getInstance(this, callback).musicListDetailDao()


            groupList.forEachIndexed { index, musicListDataModel ->
                musicDao.insert(musicListDataModel)
            }

            musicDao.getGroupList()?.observe(this, Observer {
                it.forEach {
                    var parentIdx = it.idx
                    var parentTitle_kor = it.listTitle_kor
                    var parentTitle_eng = it.listTitle_eng
                    var parentImage = it.image_path

                    musicDefault.insert(List_DefaultItemDataModel(0, "", parentTitle_kor, parentTitle_eng, "${parentImage}_01", 0))
                    musicDefault.insert(List_DefaultItemDataModel(0, "", parentTitle_kor, parentTitle_eng, "${parentImage}_02", 2))
                    musicDefault.insert(List_DefaultItemDataModel(0, "", parentTitle_kor, parentTitle_eng, "${parentImage}_03", 4))
                    musicDefault.insert(List_DefaultItemDataModel(0, "", parentTitle_kor, parentTitle_eng, "${parentImage}_04", 8))

                    Log.d("kamuel", "DefaultTitleName ::: ${parentTitle_kor}")
                    musicDefault.getDefaultItemList(parentTitle_kor).observe(this, Observer {
                        Log.d("kamuel", "DefaultItemListSize ::: ${it.size}")
                        it.forEachIndexed { index, listDefaultitemdatamodel ->
                            musicDetailDao.insert(List_ItemsDataModel(0, parentIdx, listDefaultitemdatamodel.idx, 1, index))
                        }
                    })
                }
            })

            prefUtils.setBoolean("default_data", true)
        }
    }
}