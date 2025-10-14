package com.example.myapplication1.workout.ui.workoutplan

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.myapplication1.R
import com.example.myapplication1.workout.ui.workoutplan.placeholder.PlaceholderContent
import com.google.android.material.floatingactionbutton.FloatingActionButton
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
                adapter = MyWorkoutRecyclerViewAdapter(PlaceholderContent.ITEMS)

                val itemTouchHelper = ItemTouchHelper(object: ItemTouchHelper.SimpleCallback(
                    ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT){
                    override fun onMove(
                        recyclerView: RecyclerView,
                        source: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ): Boolean {
                        val sourcePosition = source.bindingAdapterPosition
                        val target = target.bindingAdapterPosition

                        Collections.swap(PlaceholderContent.ITEMS, sourcePosition, target)
                        adapter?.notifyItemMoved(sourcePosition, target)

                        return true
                    }

                    override fun onSwiped(
                        viewHolder: RecyclerView.ViewHolder,
                        direction: Int
                    ) {
                        val item = viewHolder.bindingAdapterPosition
                        PlaceholderContent.removeItem(PlaceholderContent.ITEMS[item])
                        adapter?.notifyItemRemoved(item)
                    }

                })

                itemTouchHelper.attachToRecyclerView(recyclerView)


                addBtn.setOnClickListener {
                    val position = PlaceholderContent.ITEMS.size+1
                    val item = PlaceholderContent.createPlaceholderItem(position)
                    PlaceholderContent.ITEMS.add(item)
                    adapter?.notifyItemInserted(position)
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