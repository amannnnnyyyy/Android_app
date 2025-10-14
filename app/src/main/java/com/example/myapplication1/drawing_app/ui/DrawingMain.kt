package com.example.myapplication1.drawing_app.ui

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myapplication1.R
import com.example.myapplication1.databinding.FragmentDrawingMainBinding
import yuku.ambilwarna.AmbilWarnaDialog


class DrawingMain : Fragment() {

    private val viewModel: DrawingMainViewModel by viewModels()

    private lateinit var drawingView: DrawingView

    private var requestAskedCounter:Int = 0

    private var requestPermission: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
            permissions->
            permissions.entries.forEach {
                val permissionName = it.key
                val isGranted = it.value

                if (isGranted && permissionName== Manifest.permission.READ_MEDIA_IMAGES){
                    Toast.makeText(requireContext(), "permission granted", Toast.LENGTH_SHORT).show()
                }else{
                    if (permissionName == Manifest.permission.READ_MEDIA_IMAGES){
                        Toast.makeText(requireContext(), "permission denied", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDrawingMainBinding.inflate(inflater, container, false)

        drawingView = binding.draw


        binding.purple.setOnClickListener { drawingView.setColor(R.color.purple_500) }
        binding.green.setOnClickListener { drawingView.setColor(Color.GREEN) }
        binding.black.setOnClickListener { drawingView.setColor(Color.BLACK) }
        binding.yellow.setOnClickListener { drawingView.setColor(Color.YELLOW) }
        binding.red.setOnClickListener { drawingView.setColor(Color.RED) }

        binding.gallery.setOnClickListener {
            if (
                ActivityCompat
                    .checkSelfPermission(
                        requireContext(),
                        Manifest.permission.READ_MEDIA_IMAGES)
                != PackageManager.PERMISSION_GRANTED
                ){
                requestStoragePermission()
            }else{

            }
        }


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
        AmbilWarnaDialog(requireContext(), drawingView.getColor(), object: AmbilWarnaDialog.OnAmbilWarnaListener{
            override fun onCancel(dialog: AmbilWarnaDialog?) {
            }

            override fun onOk(dialog: AmbilWarnaDialog?, color: Int) {
                drawingView.setColor(color)
            }

        }).show()
    }



    private fun requestStoragePermission(){
        requestAskedCounter++
        Log.i("hereindialog","checking if we should")

        if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.READ_MEDIA_IMAGES)){
            Log.i("hereindialog","Yeah we should")

            showRationaleDialog()
        }else{
            Log.i("hereindialog","No we shouldn't")

            if (requestAskedCounter==1){
                requestPermission.launch(
                    arrayOf(Manifest.permission.READ_MEDIA_IMAGES)
                )
            }else{
                AlertDialog.Builder(requireContext())
                    .setTitle("Permission Denied Permanently")
                    .setMessage("Camera permission is permanently denied. Please enable it in app settings.")
                    .setPositiveButton(
                        "Go to Settings"
                    ) { dialog: DialogInterface?, which: Int ->
                        val intent: Intent =
                            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri: Uri? =
                            Uri.fromParts("package", requireActivity().packageName, null)
                        intent.setData(uri)
                        startActivity(intent)
                    }
                    .setNegativeButton(
                        "Cancel"
                    ) { dialog: DialogInterface?, which: Int -> dialog!!.dismiss() }
                    .create()
                    .show()
            }
        }
    }


    private fun showRationaleDialog(){
        Log.i("hereindialog","about to request")
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Storage permission")
            .setMessage("Storage permission is needed to continue")
            .setPositiveButton("Ok"){ dialog, which ->
                requestPermission.launch(
                    arrayOf(Manifest.permission.READ_MEDIA_IMAGES)
                )
                dialog.dismiss()
            }.create().show()
    }

    private fun showManualPermissionDialog(){
        Log.i("hereindialog","about to request")
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Storage permission")
            .setMessage("Storage permission is needed to continue")
            .setPositiveButton("Ok"){ dialog, which ->
                requestPermission.launch(
                    arrayOf(Manifest.permission.READ_MEDIA_IMAGES)
                )
                dialog.dismiss()
            }.create().show()
    }
}