package com.example.myapplication1.drawing_app.ui

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import coil3.util.Logger
import com.example.myapplication1.R
import com.example.myapplication1.databinding.FragmentDrawingMainBinding

class DrawingMain : Fragment() {

    private val viewModel: DrawingMainViewModel by viewModels()

    private lateinit var drawingView: DrawingView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDrawingMainBinding.inflate(inflater, container, false)

        drawingView = binding.draw

        binding.brushSize.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                seekBar?.let{
                    drawingView.changeBrushSize(it.progress.toFloat())
                    Log.i("brushSize","${it.progress}")
                }

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

//        binding.resetBrush.setOnClickListener {
//            Log.i("clicking","paintReset")
//            drawingView.clearPaint()
//            drawingView = binding.draw
//        }

        return binding.root
    }
}