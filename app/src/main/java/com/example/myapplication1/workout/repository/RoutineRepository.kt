package com.example.myapplication1.workout.repository

import com.example.myapplication1.workout.api.RetrofitInstance
import com.example.myapplication1.workout.db.RoutineDatabase
import com.example.myapplication1.workout.models.Routine

class RoutineRepository(val db: RoutineDatabase
) {
    suspend fun getRoutines(limit: Int, offset: Int)= RetrofitInstance.api.getRoutine(limit, offset)

    //suspend fun searchRoutines(limit: Int, offset:Int) = RetrofitInstance.api.searchRoutines(limit, offset)

    suspend fun saveRoutine(routine: Routine) = db.routineDao().saveRoutine(routine)

    suspend fun getAllRoutines() = db.routineDao().getAllRoutines()

    suspend fun deleteRoutine(routine: Routine) = db.routineDao().deleteRoutine(routine)
}