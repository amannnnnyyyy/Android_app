package com.example.myapplication1.workout.ui.workoutplan.placeholder

import com.example.myapplication1.workout.models.DaysOfWeek
import com.example.myapplication1.workout.models.WorkoutPlan
import java.util.ArrayList
import java.util.HashMap

object PlanContent {

    val ITEMS: MutableList<WorkoutPlan> = ArrayList()

    val ITEM_MAP: MutableMap<DaysOfWeek, WorkoutPlan> = HashMap()

    fun addItem(item: WorkoutPlan, position:Int?=null) {
        if (position!=null){
            ITEMS.add(position, item)
            ITEM_MAP.put(item.date, item)
        }
        else{
            ITEMS.add(item)
            ITEM_MAP.put(item.date, item)
        }
    }

    fun removeItem(item: WorkoutPlan) {
        ITEMS.remove(item)
        ITEM_MAP.remove(item.date, item)
    }

    fun createPlanItem(date: DaysOfWeek, workout:String): WorkoutPlan {
        return WorkoutPlan(null,date, workout)
    }


    fun changePlanItem(oldPlanItem: WorkoutPlan, newPlanItem: WorkoutPlan, position:Int){

    }
}