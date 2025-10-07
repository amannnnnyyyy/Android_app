package com.example.myapplication1.workout.api

import com.example.myapplication1.workout.models.ExerciseInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ExerciseInfoApi {
    @GET("/api/v2/exerciseinfo/")
    suspend fun getExerciseInfo(
        @Query("category") category: Int?,
        @Query("equipment") equipment: Array<Int>?,
        @Query("license") license: Int?,
        @Query("license_author") license_author: String?,
        @Query("limit") limit: Int?,
        @Query("muscles") muscles: Array<Int>?,
        @Query("muscles_secondary") muscles_secondary: Array<Int>?,
        @Query("offset") offset:Int?,
        @Query("ordering") ordering: String?,
        @Query("uuid") uuid: String?,
        @Query("variations") variations: Int?,
        ): Response<ExerciseInfoResponse>
}