package com.example.myapplication1.workout.adapters
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication1.R
import com.example.myapplication1.workout.models.ExerciseInfo
import com.example.myapplication1.workout.utils.Constants.BASE_URL
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MuscleInformationViewPagerAdapter(val listOfExerciseInfo: List<ExerciseInfo>, val viewPagerOuter: ViewPager2): RecyclerView.Adapter<MuscleInformationViewPagerAdapter.InfoViewHolder>() {
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
        val equipmentList = holder.equipmentsList
        val muscleName = holder.muscleName

        val exerciseInfo = listOfExerciseInfo[position]

        val equipments = exerciseInfo.equipment?.joinToString(separator = ", ") {
            it.name
        }

        equipmentList.text = equipments

        description.text = HtmlCompat.fromHtml(exerciseInfo.translations?.find {
                            it.language==2
                        }?.description?:"<h1>No Description</h1>", HtmlCompat.FROM_HTML_MODE_LEGACY)

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


        val firstMuscleName = exerciseInfo.muscles?.getOrNull(0)?.name_en
        muscleName.text = firstMuscleName

        //Log.i("checkoutdragging", "inner ${viewPager.is}\n outer ${viewPagerOuter.scrollState}")

        //if ((exerciseInfo.muscles?.size?:0)>1) viewPagerOuter.isUserInputEnabled = false
        //else viewPagerOuter.isUserInputEnabled = true


        if ((exerciseInfo.muscles?.size?:0) >0){
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                override fun onTabSelected(p0: TabLayout.Tab?) {
                    Log.i("selected_item","${p0?.position}\n ${exerciseInfo.muscles?.size} \n${exerciseInfo.muscles}")
                    if (exerciseInfo.muscles != null && exerciseInfo.muscles.size > (p0?.position ?: 0))
                        if (exerciseInfo.muscles[p0?.position?:0].name_en.length>0)
                            muscleName.text = exerciseInfo.muscles[p0?.position?:0].name_en
                        else muscleName.text = exerciseInfo.muscles[p0?.position?:0].name
                }
                override fun onTabUnselected(p0: TabLayout.Tab?) {}

                override fun onTabReselected(p0: TabLayout.Tab?) {}

            })
        }

    }

    override fun getItemCount(): Int  = listOfExerciseInfo.size

    inner class InfoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tabLayout = itemView.findViewById<TabLayout>(R.id.tab_layout_image)

        val viewPager = itemView.findViewById<ViewPager2>(R.id.viewpager2)

        val description = itemView.findViewById<TextView>(R.id.description)

        val equipmentsList = itemView.findViewById<TextView>(R.id.equipmentList)

        val muscleName = itemView.findViewById<TextView>(R.id.muscle_name)


    }
}