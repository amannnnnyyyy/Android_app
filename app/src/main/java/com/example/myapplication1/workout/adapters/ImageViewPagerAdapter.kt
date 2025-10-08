package com.example.myapplication1.workout.adapters

import android.graphics.drawable.PictureDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.caverock.androidsvg.SVG
import com.example.myapplication1.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.net.URL

class ImageViewPagerAdapter(val imageList: List<String>): RecyclerView.Adapter<ImageViewPagerAdapter.ImageViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.individual_muscle_item, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ImageViewHolder,
        position: Int
    ) {
        val imageUrl = imageList[position]
        runBlocking {
            try {
                val svg = withContext(Dispatchers.IO) {
                    SVG.getFromInputStream(URL(imageUrl).openStream())
                }
                val drawable = PictureDrawable(svg.renderToPicture())
                holder.imageView.setImageDrawable(drawable)
            } catch (e: Exception) {
                Log.e("SVGError", "Failed to load secondary SVG: $imageUrl", e)
            }
        }
    }

    override fun getItemCount(): Int = imageList.size


    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.mainMuscleImage)
    }
}