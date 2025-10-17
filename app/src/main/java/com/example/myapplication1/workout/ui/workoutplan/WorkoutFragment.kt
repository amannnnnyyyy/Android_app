package com.example.myapplication1.workout.ui.workoutplan

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.myapplication1.R
import com.example.myapplication1.workout.models.WorkoutPlan
import com.example.myapplication1.workout.ui.workoutplan.Utils.LIST_OF_DATE_INDEXES
import com.example.myapplication1.workout.ui.workoutplan.dialogs.WorkoutPlanDialog
import com.example.myapplication1.workout.utils.Constants.TAG
import com.example.myapplication1.workout.utils.Resource
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Collections

class WorkoutFragment : Fragment(R.layout.fragment_workout_list) {

    private var columnCount = 1
    private val viewModel: WorkoutPlanViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView

    private lateinit var workoutAdapter: MyWorkoutRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_workout_list, container, false)

        setupRecyclerView(view)

        val addBtn = view.findViewById<FloatingActionButton>(R.id.new_plan)

        addBtn.setOnClickListener {
            handlePlanAdd()
        }


        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.workouts.collectLatest { wrk->
                    when(wrk){
                        is Resource.Success->{
                            val data = wrk.data
                            data?.let {
                              //  setupRecyclerView(view, data)
                                handleOnSuccessDisplay(data)
                            }
                        }

                        is Resource.Loading->{
                            handleLoading()
                        }
                        is Resource.Error -> {
                            handleError()
                        }
                    }
                }
            }
        }

        return view
    }


    private fun setupRecyclerView(view: View, data:List<WorkoutPlan>?=null) {
        workoutAdapter = MyWorkoutRecyclerViewAdapter(
            mutableListOf(),
            onUpdateChange = {
                when (it) {
                    is MyWorkoutRecyclerViewAdapter.ItemClick.SaveUpdateClick -> {
                        Log.i(TAG, "setupRecyclerView: we got here too")
                        viewModel.updatePlan(it.date, it.workout)
                    }
                }
            }
        )

        recyclerView = view.findViewById<RecyclerView>(R.id.list)
        recyclerView.layoutManager = when {
            columnCount <= 1 -> LinearLayoutManager(context)
            else -> GridLayoutManager(context, columnCount)
        }
        recyclerView.adapter = workoutAdapter

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                source: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean{
                data.let {
                    Log.i(TAG, "onMove: here $data")
//                    val sourcePosition = source.bindingAdapterPosition
//                    val target = target.bindingAdapterPosition
//
//                    if ((sourcePosition<=data.size)&&(target<=data.size)){
//                        Collections.swap(it, sourcePosition, target)
//                        recyclerView.adapter?.notifyItemMoved(sourcePosition, target)
//                    }
//                    return true
                }
                return false
            }

            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) {
                val position = viewHolder.bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val toBeDeletedPlan = workoutAdapter.getItem(position)
                    viewModel.deletePlan(toBeDeletedPlan)

                    Snackbar.make(
                        requireView(),
                        "Plan of ${toBeDeletedPlan.date} Removed Successfully!",
                        Snackbar.LENGTH_SHORT
                    ).apply {
                        setAction("Undo") {
                            viewModel.savePlan(toBeDeletedPlan.date, toBeDeletedPlan.workoutCategory)
                        }
                        show()
                    }
                }
            }
        })
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
       viewModel.removeListener()
    }


    private fun handlePlanAdd(){
        WorkoutPlanDialog(requireContext()){plan ->
            when(plan){
                is WorkoutPlanDialog.PlanClick.PerformOnSuccess -> {
                    val date = LIST_OF_DATE_INDEXES.get(plan.date)

                    date?.let{
                        viewModel.savePlan(plan.date, plan.workout)
                    }

                }
            }
        }
    }


    private fun handleOnSuccessDisplay(data: MutableList<WorkoutPlan>){
        workoutAdapter.updateData(data)
    }

    private fun handleLoading(){
        Log.i("firestoreCheck","loading")
    }

    private fun handleError(){
        Log.i("firestoreCheck","met an error")
    }

}