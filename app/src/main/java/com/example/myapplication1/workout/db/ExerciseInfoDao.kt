package com.example.myapplication1.workout.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication1.workout.models.ExerciseInfo

@Dao
interface ExerciseInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveExerciseInfo(exerciseCategory: ExerciseInfo): Long

    @Query("SELECT * FROM exercise_info")
    fun getAllExerciseInfos(): LiveData<List<ExerciseInfo>>


    @Delete
    suspend fun deleteExerciseInfo(category: ExerciseInfo)
}