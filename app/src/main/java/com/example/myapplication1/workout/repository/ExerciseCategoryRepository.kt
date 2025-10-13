package com.example.myapplication1.workout.repository

import com.example.myapplication1.workout.api.RetrofitInstance
import com.example.myapplication1.workout.db.ExerciseCategoryDatabase
import com.example.myapplication1.workout.models.ExerciseCategory


class ExerciseCategoryRepository(val db: ExerciseCategoryDatabase) {
    suspend fun getExerciseCategories(limit: Int?, offset: Int?)= RetrofitInstance.api.getExerciseCategory(limit, null, offset, null)

    //suspend fun searchExerciseCategories(limit: Int, offset:Int) = RetrofitInstance.api.searchExerciseCategories(limit, offset)

    suspend fun saveExerciseCategory(exerciseCategory: ExerciseCategory) = db.exerciseCategoryDao().saveExerciseCategory(exerciseCategory)

    suspend fun getAllExerciseCategories() = db.exerciseCategoryDao().getAllExerciseCategories()

    suspend fun deleteExerciseCategory(exerciseCategory: ExerciseCategory) = db.exerciseCategoryDao().deleteExerciseCategory(exerciseCategory)
}