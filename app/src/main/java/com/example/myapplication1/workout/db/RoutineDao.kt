package com.example.myapplication1.workout.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication1.news.models.Article
import com.example.myapplication1.workout.models.Routine

@Dao
interface RoutineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveRoutine(routine: Routine): Long

    @Query("SELECT * FROM routines ORDER BY id ASC")
    fun getAllRoutines(): LiveData<List<Routine>>


    @Delete
    suspend fun deleteRoutine(routine: Routine)
}