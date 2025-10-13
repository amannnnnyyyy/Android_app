package com.example.myapplication1.drawing_app.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.graphics.Paint

class DrawingView(context: Context, attrs: AttributeSet): View(context, attrs){

    //drawing path
    private lateinit var drawPath: FingerPath

    //what to draw
    private lateinit var canvasPaint: Paint

    //how to draw
    private lateinit var drawPaint: Paint


    private var color = Color.BLACK
    private lateinit var canvas : Canvas
    private lateinit var canvasBitMap: Bitmap
    private var brushSize: Float = 0F


    init {
        setUpDrawing()
    }

    private fun setUpDrawing(){
        drawPaint = Paint()
        drawPath = FingerPath(color, brushSize)
        drawPaint.color = color
        drawPaint.style = Paint.Style.STROKE
        drawPaint.strokeJoin = Paint.Join.ROUND
        drawPaint.strokeCap = Paint.Cap.ROUND
        brushSize = 20F
    }


    internal inner class FingerPath(val color: Int, val brushTickness: Float): Path()
}