package com.example.myapplication1.workout.repository

import com.example.myapplication1.workout.api.RetrofitInstance
import com.example.myapplication1.workout.db.ExerciseInfoDatabase
import com.example.myapplication1.workout.models.ExerciseInfo

class ExerciseInfoRepository(val db: ExerciseInfoDatabase) {

    suspend fun getExerciseInfos(category:Int?, limit:Int?, offset:Int?, variations:Int?)= RetrofitInstance.exerciseInfoApi.getExerciseInfo(
        category = 10,
        limit = 10,
        offset = 0,
        variations = null,
        equipment = null,
        license = null,
        license_author = null,
        muscles = null,
        muscles_secondary = null,
        ordering = null,
        uuid = null
    )

    //suspend fun searchExerciseInfos(limit: Int, offset:Int) = RetrofitInstance.api.searchExerciseCategories(limit, offset)

    suspend fun saveExerciseInfo(exerciseInfo: ExerciseInfo) = db.exerciseInfoDao().saveExerciseInfo(exerciseInfo)

    suspend fun getAllExerciseInfos() = db.exerciseInfoDao().getAllExerciseInfos()

    suspend fun deleteExerciseInfo(exerciseInfo: ExerciseInfo) = db.exerciseInfoDao().deleteExerciseInfo(exerciseInfo)
}