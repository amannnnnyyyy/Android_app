package com.example.myapplication1.workout.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication1.R
import com.example.myapplication1.workout.models.ExerciseCategory

class ExerciseCategoryAdapter: RecyclerView.Adapter<ExerciseCategoryAdapter.CategoriesViewHolder>() {

    private val differCallBack = object: DiffUtil.ItemCallback<ExerciseCategory>(){
        override fun areItemsTheSame(
            oldItem: ExerciseCategory,
            newItem: ExerciseCategory
        ): Boolean {
            if (oldItem.id!=null && newItem.id!=null){
                return oldItem.id == newItem.id
            }
            return false
        }

        override fun areContentsTheSame(
            oldItem: ExerciseCategory,
            newItem: ExerciseCategory
        ): Boolean = oldItem == newItem

    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoriesViewHolder {
       return CategoriesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.exercise_category_layout, parent, false))
    }

    override fun onBindViewHolder(
        holder: CategoriesViewHolder,
        position: Int
    ) {
       val category = differ.currentList[position]

        holder.itemView.apply {
            holder.name.text = category.name
            setOnItemClickListener { category->
                onItemClickListener?.let{
                    it(category)
                }
            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size


    inner class CategoriesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val name = itemView.findViewById<TextView>(R.id.category_name)
    }


    private var onItemClickListener:((ExerciseCategory)->Unit)?=null
    fun setOnItemClickListener(listener: (ExerciseCategory)->Unit){
        onItemClickListener = listener
    }

}