package com.example.myapplication1.workout.ui.workoutplan

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
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
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.myapplication1.R
import com.example.myapplication1.workout.ui.workoutplan.Utils.LIST_OF_DATES
import com.example.myapplication1.workout.ui.workoutplan.placeholder.PlanContent
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.util.Collections

class WorkoutFragment : Fragment(R.layout.fragment_workout_list) {

    private var columnCount = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_workout_list, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.list)
        val addBtn = view.findViewById<FloatingActionButton>(R.id.new_plan)



        if (recyclerView is RecyclerView) {
            with(recyclerView) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyWorkoutRecyclerViewAdapter(PlanContent.ITEMS)

                val itemTouchHelper = ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(
                    ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT){
                    override fun onMove(
                        recyclerView: RecyclerView,
                        source: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ): Boolean {
                        val sourcePosition = source.bindingAdapterPosition
                        val target = target.bindingAdapterPosition

                        Collections.swap(PlanContent.ITEMS, sourcePosition, target)
                        adapter?.notifyItemMoved(sourcePosition, target)

                        return true
                    }

                    override fun onSwiped(
                        viewHolder: RecyclerView.ViewHolder,
                        direction: Int
                    ) {
                        val item = viewHolder.bindingAdapterPosition
                        val toBeRemovedItem: PlanContent.PlanItem = PlanContent.ITEMS[item]
                        PlanContent.removeItem(toBeRemovedItem)
                        adapter?.notifyItemRemoved(item)

                        Snackbar.make(rootView, "Plan Removed Successfully!", Snackbar.LENGTH_SHORT).apply {
                            setAction("Undo"){
                                val resetValue = PlanContent.createPlanItem(toBeRemovedItem.id, toBeRemovedItem.content)
                                PlanContent.addItem(resetValue, item)

                                adapter?.notifyItemInserted(item)
                            }
                            show()
                        }



                    }

                })

                itemTouchHelper.attachToRecyclerView(recyclerView)


                addBtn.setOnClickListener {

                    val dialog = Dialog(requireContext())
                    dialog.setContentView(R.layout.workout_add_layout)
                    dialog.show()
                    dialog.window?.let { window ->
                        val displayMetrics = requireContext().resources.displayMetrics
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


                    datePicker.adapter = ArrayAdapter(requireContext(),android.R.layout.simple_spinner_item, LIST_OF_DATES)


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

                        override fun onNothingSelected(parent: AdapterView<*>?) {
                            TODO("Not yet implemented")
                        }

                    }

                    saveButton.setOnClickListener {
                        val date = dateView.text.toString()
                        val workout = editTextView.text.toString()
                        val item = PlanContent.createPlanItem(date, workout)
                        PlanContent.addItem(item)
                        adapter?.notifyItemInserted(PlanContent.ITEMS.size+1)
                        dialog.dismiss()
                    }
                }
            }
        }

        return view
    }

    companion object {

        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
                WorkoutFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }
}