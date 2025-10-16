package com.example.myapplication1.workout.ui.workoutplan

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.example.myapplication1.databinding.FragmentWorkoutBinding
import com.example.myapplication1.workout.models.DaysOfWeek
import com.example.myapplication1.workout.models.WorkoutPlan
import com.example.myapplication1.workout.ui.workoutplan.Utils.LIST_OF_DATE_INDEXES
import com.example.myapplication1.workout.ui.workoutplan.dialogs.WorkoutPlanDialog

class MyWorkoutRecyclerViewAdapter(
        private val values: MutableList<WorkoutPlan>,
        private val onUpdateChange: (ItemClick)-> Unit
)
    : RecyclerView.Adapter<MyWorkoutRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

    return ViewHolder(FragmentWorkoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var adapterPosition = holder.bindingAdapterPosition
        val currentItem = values[position]
        holder.idView.text = currentItem.date.toString()
        holder.contentView.text = currentItem.workoutCategory

        holder.itemView.setOnClickListener {
            adapterPosition = holder.bindingAdapterPosition
            handleEditPlan(currentItem,holder, adapterPosition)
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


    fun updateData(newData: MutableList<WorkoutPlan>) {
        if (newData!=this.values){
            this.values.clear()
            this.values.addAll(newData)
            notifyDataSetChanged()
        }
    }

    fun getItem(position: Int): WorkoutPlan {
        return values[position]
    }

    sealed class ItemClick{
        class SaveUpdateClick(val date: DaysOfWeek, val workout: String): ItemClick()
    }




    fun handleEditPlan(currentItem: WorkoutPlan,holder: ViewHolder, adapterPosition:Int){
        WorkoutPlanDialog(holder.itemView.context, currentItem){  plan->
            when(plan){
                is WorkoutPlanDialog.PlanClick.PerformOnSuccess -> {
                    val date = LIST_OF_DATE_INDEXES.get(currentItem.date)

                    date?.let{
                        onUpdateChange.invoke(ItemClick.SaveUpdateClick(plan.date, plan.workout))
                        notifyItemChanged(adapterPosition)
                    }

                }
            }
        }
    }


}