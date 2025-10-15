package com.example.myapplication1.workout.ui.workoutplan.placeholder

import java.util.ArrayList
import java.util.HashMap

object PlanContent {

    val ITEMS: MutableList<PlanItem> = ArrayList()

    val ITEM_MAP: MutableMap<String, PlanItem> = HashMap()


    init {
//        for (i in 1..COUNT) {
//            addItem(createPlaceholderItem(i))
//        }
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


    data class PlanItem(val id: String, val content: String, val details: String)
}