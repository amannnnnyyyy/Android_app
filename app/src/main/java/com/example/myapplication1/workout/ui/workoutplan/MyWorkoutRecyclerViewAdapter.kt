package com.example.myapplication1.workout.ui.workoutplan

import android.app.Dialog
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.example.myapplication1.R

import com.example.myapplication1.databinding.FragmentWorkoutBinding
import com.example.myapplication1.workout.ui.workoutplan.Utils.LIST_OF_DATES
import com.example.myapplication1.workout.ui.workoutplan.placeholder.PlanContent

class MyWorkoutRecyclerViewAdapter(
        private val values: List<PlanContent.PlanItem>)
    : RecyclerView.Adapter<MyWorkoutRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

    return ViewHolder(FragmentWorkoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var adapterPosition = holder.bindingAdapterPosition
        val currentItem = values[position]
        holder.idView.text = currentItem.id
        holder.contentView.text = currentItem.content

        holder.itemView.setOnClickListener {
            Log.i("currentItem","before $currentItem $adapterPosition")
            adapterPosition = holder.bindingAdapterPosition
            Log.i("currentItem","later $currentItem $adapterPosition")

            val dialog = Dialog(holder.itemView.context)
            dialog.setContentView(R.layout.workout_add_layout)
            dialog.show()
            dialog.window?.let { window ->
                val displayMetrics = holder.itemView.context.resources.displayMetrics
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

                datePicker.adapter = ArrayAdapter(holder.itemView.context,android.R.layout.simple_spinner_item, LIST_OF_DATES)

            val indexOfDate = LIST_OF_DATES.indexOf(currentItem.id)
            datePicker.setSelection(indexOfDate)

            editTextView.setText(currentItem.content)

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
                    result?.let {
                        dateView.text = it.toString()
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}

            }

            saveButton.setOnClickListener {
                val date = dateView.text.toString()
                val workout = editTextView.text.toString()
                val item = PlanContent.createPlanItem(date, workout)
                val changeIndex = PlanContent.ITEMS.indexOf(currentItem)
                PlanContent.ITEMS[changeIndex] = item
                notifyItemChanged(adapterPosition)
                dialog.dismiss()
            }
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentWorkoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}