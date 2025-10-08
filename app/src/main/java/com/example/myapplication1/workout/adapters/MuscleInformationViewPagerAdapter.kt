package com.example.myapplication1.workout.adapters

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication1.R
import com.example.myapplication1.workout.models.ExerciseInfo
import com.example.myapplication1.workout.utils.Constants.BASE_URL
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MuscleInformationViewPagerAdapter(val listOfExerciseInfo: List<ExerciseInfo>): RecyclerView.Adapter<MuscleInformationViewPagerAdapter.InfoViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InfoViewHolder {
        return InfoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.muscle_whole_info, parent, false))
    }

    override fun onBindViewHolder(
        holder: InfoViewHolder,
        position: Int
    ) {

        val viewPager = holder.viewPager
        val tabLayout = holder.tabLayout
        val description = holder.description

        val exerciseInfo = listOfExerciseInfo[position]

        description.text = Html.fromHtml(exerciseInfo.translations?.find {
                            it.language==2
                        }?.description?:"<h1>No Description</h1>")

        val muscleImageList:List<String> = exerciseInfo.muscles?.map {
            BASE_URL+it.image_url_main
        }?:listOf<String>()

        val muscleImageSecondaryList:List<String> = exerciseInfo.muscles_secondary?.map {
            BASE_URL+it.image_url_main
        }?:listOf<String>()

        val muscleImages: List<String> = muscleImageList.plus(muscleImageSecondaryList)

        val adapter = ImageViewPagerAdapter(muscleImages)


         viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager){ tab, position ->
            tab.text = "Image ${(position+1).toString()}"
        }.attach()
    }

    override fun getItemCount(): Int  = listOfExerciseInfo.size

    inner class InfoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tabLayout = itemView.findViewById<TabLayout>(R.id.tab_layout_image)

        val viewPager = itemView.findViewById<ViewPager2>(R.id.viewpager2)

        val description = itemView.findViewById<TextView>(R.id.description)


    }
}