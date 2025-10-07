package com.example.myapplication1.workout.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication1.workout.models.ExerciseCategory

@Dao
interface ExerciseCategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveExerciseCategory(exerciseCategory: ExerciseCategory): Long

    @Query("SELECT * FROM categories")
    fun getAllExerciseCategories(): LiveData<List<ExerciseCategory>>


    @Delete
    suspend fun deleteExerciseCategory(category: ExerciseCategory)
}