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
import android.graphics.Color
import com.example.myapplication1.R
import com.example.myapplication1.databinding.FragmentDrawingMainBinding
import yuku.ambilwarna.AmbilWarnaDialog

class DrawingMain : Fragment() {

    private val viewModel: DrawingMainViewModel by viewModels()

    private lateinit var drawingView: DrawingView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDrawingMainBinding.inflate(inflater, container, false)

        drawingView = binding.draw


        binding.purple.setOnClickListener { drawingView.setColor(R.color.purple_700) }
        binding.green.setOnClickListener { drawingView.setColor(Color.GREEN) }
        binding.black.setOnClickListener { drawingView.setColor(Color.BLACK) }
        binding.yellow.setOnClickListener { drawingView.setColor(Color.YELLOW) }
        binding.red.setOnClickListener { drawingView.setColor(Color.RED) }


        binding.undo.setOnClickListener { drawingView.undoPath() }

        binding.reset.setOnClickListener { drawingView.clearPaint() }

        binding.brush.setOnClickListener {
            showBrushSizeDialog()
        }


        binding.colorPicker.setOnClickListener {
            showColorPickerDialog()
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


    private fun showColorPickerDialog(){
        val dialog = AmbilWarnaDialog(requireContext(), drawingView.getColor(), object: AmbilWarnaDialog.OnAmbilWarnaListener{
            override fun onCancel(dialog: AmbilWarnaDialog?) {
            }

            override fun onOk(dialog: AmbilWarnaDialog?, color: Int) {
                drawingView.setColor(color)
            }

        }).show()
    }
}