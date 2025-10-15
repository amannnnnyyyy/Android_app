package com.example.myapplication1.workout.ui.workoutplan.placeholder

import com.example.myapplication1.workout.models.DaysOfWeek
import java.util.ArrayList
import java.util.HashMap

object PlanContent {

    val ITEMS: MutableList<PlanItem> = ArrayList()

    val ITEM_MAP: MutableMap<DaysOfWeek, PlanItem> = HashMap()


    init {
        val listOfDefaultWorkOut = mapOf<String, PlanItem>(
            "Monday" to createPlanItem(DaysOfWeek.MONDAY,"Chest + Triceps"),
            "Tuesday" to createPlanItem(DaysOfWeek.TUESDAY,"Rest"),
            "Wednesday" to createPlanItem(DaysOfWeek.WEDNESDAY,"Shoulders + Arm"),
            "Thursday" to createPlanItem(DaysOfWeek.THURSDAY,"Back + Biceps"),
            "Friday" to createPlanItem(DaysOfWeek.FRIDAY,"Walk"),
            "Saturday" to createPlanItem(DaysOfWeek.SATURDAY,"FreeStyle"),
            "Sunday" to createPlanItem(DaysOfWeek.SUNDAY,"Legs"),
        )
        for (plan in listOfDefaultWorkOut) {
            addItem(plan.value)
        }
    }

    fun addItem(item: PlanItem, position:Int?=null) {
        if (position!=null){
            ITEMS.add(position, item)
            ITEM_MAP.put(item.id, item)
        }
        else{
            ITEMS.add(item)
            ITEM_MAP.put(item.id, item)
        }
    }

    fun removeItem(item: PlanItem) {
        ITEMS.remove(item)
        ITEM_MAP.remove(item.id, item)
    }

    fun createPlanItem(date: DaysOfWeek, workout:String): PlanItem {
        return PlanItem(date, workout, "none")
    }


    fun changePlanItem(oldPlanItem: PlanItem, newPlanItem: PlanItem, position:Int){

    }


    data class PlanItem(val id: DaysOfWeek, val content: String, val details: String)
}