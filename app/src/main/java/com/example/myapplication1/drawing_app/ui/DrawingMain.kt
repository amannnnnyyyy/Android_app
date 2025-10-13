package com.example.myapplication1.drawing_app.ui

import android.app.Dialog
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import coil3.util.Logger
import com.example.myapplication1.R
import com.example.myapplication1.databinding.FragmentDrawingMainBinding
import com.example.myapplication1.view.theme.PurpleGrey80

class DrawingMain : Fragment(), View.OnClickListener {

    private val viewModel: DrawingMainViewModel by viewModels()

    private lateinit var drawingView: DrawingView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDrawingMainBinding.inflate(inflater, container, false)

        drawingView = binding.draw


        binding.purple.setOnClickListener { drawingView.setColor("#FF3700B3") }
        binding.green.setOnClickListener { drawingView.setColor("#00ff00") }
        binding.black.setOnClickListener { drawingView.setColor("#000000") }
        binding.yellow.setOnClickListener { drawingView.setColor("#ffff00") }
        binding.red.setOnClickListener { drawingView.setColor("#ff0000") }



        binding.brush.setOnClickListener {
            showBrushSizeDialog()
        }

        return binding.root
    }


    fun showBrushSizeDialog(){
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.brush_progress_dialog)
        dialog.show()
        Log.i("openedDialog","here")
        val seekingBar = dialog.findViewById<SeekBar>(R.id.brushSize)
        val brushSizeTv = dialog.findViewById<TextView>(R.id.progress_text)

        seekingBar.progress = drawingView.getBrushSize().toInt()
        brushSizeTv.text = drawingView.getBrushSize().toInt().toString()

        seekingBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                seekBar?.let{
                    drawingView.changeBrushSize(it.progress.toFloat())
                    brushSizeTv.text = it.progress.toString()
                }

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })

    }

    override fun onClick(v: View?) {
        Log.i("clickedColor","what is clicked $v")
        when(v?.id){
            R.id.purple -> Log.i("clickedColor","purple")
            R.id.green -> drawingView.setColor("#0f0")
            R.id.black -> drawingView.setColor("#000")
            R.id.yellow -> drawingView.setColor("#ff0")
            R.id.red -> drawingView.setColor("#f00")
        }
    }
}