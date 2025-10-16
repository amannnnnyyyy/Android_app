package com.example.myapplication1.workout.ui.workoutplan

import com.example.myapplication1.workout.models.DaysOfWeek

object Utils {
     val LIST_OF_DATE_INDEXES = mapOf<DaysOfWeek, Int>(
          DaysOfWeek.MONDAY to 0,
          DaysOfWeek.TUESDAY to 1,
          DaysOfWeek.WEDNESDAY to 2,
          DaysOfWeek.THURSDAY to 3,
          DaysOfWeek.FRIDAY to 4,
          DaysOfWeek.SATURDAY to 5,
          DaysOfWeek.SUNDAY to 6,
          DaysOfWeek.UNKNOWN to 7
          )
}