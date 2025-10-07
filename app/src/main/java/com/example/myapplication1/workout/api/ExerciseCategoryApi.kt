package com.example.myapplication1.workout.api

import com.example.myapplication1.workout.models.ExerciseCategoryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ExerciseCategoryApi {
    @GET("/api/v2/exercisecategory/")
    suspend fun getExerciseCategory(
        @Query("limit") limit: Int?,
        @Query("name") name: String?,
        @Query("offset") offset:Int?,
        @Query("ordering") ordering: String?
        ): Response<ExerciseCategoryResponse>
}