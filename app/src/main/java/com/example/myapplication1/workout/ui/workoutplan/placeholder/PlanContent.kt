package com.example.myapplication1.workout.ui.workoutplan.placeholder

import java.util.ArrayList
import java.util.HashMap

object PlanContent {

    val ITEMS: MutableList<PlanItem> = ArrayList()

    val ITEM_MAP: MutableMap<String, PlanItem> = HashMap()


    init {
        val listOfDefaultWorkOut = mapOf<String, PlanItem>(
            "Monday" to createPlanItem("Monday","Chest + Triceps"),
            "Tuesday" to createPlanItem("Tuesday","Rest"),
            "Wednesday" to createPlanItem("Wednesday","Shoulders + Arm"),
            "Thursday" to createPlanItem("Thursday","Back + Biceps"),
            "Friday" to createPlanItem("Friday","Walk"),
            "Saturday" to createPlanItem("Saturday","FreeStyle"),
            "Sunday" to createPlanItem("Sunday","Legs"),
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

    fun createPlanItem(date: String, workout:String): PlanItem {
        return PlanItem(date, workout, "none")
    }


    fun changePlanItem(oldPlanItem: PlanItem, newPlanItem: PlanItem, position:Int){

    }


    data class PlanItem(val id: String, val content: String, val details: String)
}