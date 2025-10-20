package com.example.myapplication1.view.adapters.view_pager_adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication1.R
import com.example.myapplication1.workout.ui.workoutplan.MyWorkoutRecyclerViewAdapter

class WeatherImageViewPagerAdapter(val viewpager2: ViewPager2): RecyclerView.Adapter<WeatherImageViewPagerAdapter.ViewHolder>() {
    val images:MutableList<Int> = mutableListOf(
        R.drawable.weather,
        R.drawable.cloudy,
        R.drawable.sun,
        R.drawable.storm
    )
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.weather_image, parent, false))
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        var currentImage = images[position]
       if (position==(images.size-1)){
           viewpager2.post(runnable)
       }

        val weatherImageView = holder.itemView.findViewById<ImageView>(R.id.single_weather_image)

        weatherImageView.setImageResource(currentImage)
    }

    override fun getItemCount(): Int = images.size

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val runnable = Runnable{
        images.addAll(images)
        notifyDataSetChanged()
    }
}