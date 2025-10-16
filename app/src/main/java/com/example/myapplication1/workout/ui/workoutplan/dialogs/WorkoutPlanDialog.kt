package com.example.myapplication1.workout.ui.workoutplan.dialogs

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.example.myapplication1.R
import com.example.myapplication1.workout.models.DaysOfWeek
import com.example.myapplication1.workout.models.WorkoutPlan
import com.example.myapplication1.workout.ui.workoutplan.placeholder.PlanContent

class WorkoutPlanDialog(context: Context,val currentItem : WorkoutPlan?=null, private val update: (PlanClick)->Unit) {
    init {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.workout_add_layout)
            dialog.show()
            dialog.window?.let { window ->
                val displayMetrics = context.resources.displayMetrics
                val width = (displayMetrics.widthPixels * 0.9).toInt()
                val layoutParams = WindowManager.LayoutParams()
                layoutParams.copyFrom(dialog.window?.attributes)
                val height = layoutParams.height
                window.setLayout(width, height)
            }

            val dateView = dialog.findViewById<TextView>(R.id.date)
            val datePicker = dialog.findViewById<Spinner>(R.id.date_picker)
            val saveButton = dialog.findViewById<Button>(R.id.save)
            val editTextView = dialog.findViewById<EditText>(R.id.workout)

            datePicker.adapter = ArrayAdapter(context,android.R.layout.simple_spinner_item,
            DaysOfWeek.entries)

            if (currentItem!=null){
                val indexOfDate = DaysOfWeek.entries.indexOf(currentItem.date)
                datePicker.setSelection(indexOfDate)
                editTextView.setText(currentItem.workoutCategory)
            }

            dateView.setOnClickListener {
                datePicker.performClick()
            }


            datePicker.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val result = parent?.getItemAtPosition(position)
                    result?.let { dateView.text = it.toString() }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}

            }

            saveButton.setOnClickListener {
                val date = DaysOfWeek.entries.find { it.name.equals(dateView.text.toString(), ignoreCase = true) }
                val workout = editTextView.text.toString()
                if (date!=null){
                    val item = PlanContent.createPlanItem(date, workout)
                    PlanContent.addItem(item)
                    update.invoke(PlanClick.PerformOnSuccess(date, workout))
                }
                dialog.dismiss()
            }
    }


    sealed class PlanClick{
        class PerformOnSuccess(val date: DaysOfWeek, val workout: String): PlanClick()
    }
}