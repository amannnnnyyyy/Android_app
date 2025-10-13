package com.example.myapplication1.drawing_app.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.graphics.Paint
import android.view.MotionEvent
import androidx.core.graphics.createBitmap

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

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        canvasBitMap = createBitmap(w, h)
        canvas  =Canvas(canvasBitMap)
    }


    //respond to user touches
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val touchX = event?.x
        val touchY = event?.y

        when(event?.action) {
            //triggered when the user puts finger on the screen (sinekaw)
            MotionEvent.ACTION_DOWN -> {
                drawPath.color = color
                drawPath.brushThickness = brushSize
                drawPath.reset()

                if (touchX != null && touchY != null) {
                    drawPath.moveTo(touchX, touchY)
                }
            }
            //triggered when user starts to move finger until user takes finger off (siyanfuakek)
            MotionEvent.ACTION_MOVE -> {
                if (touchX != null && touchY != null) {
                    drawPath.lineTo(touchX, touchY)
                }
            }

            //triggered when user takes off the finger (silekew)
            MotionEvent.ACTION_UP -> {
                drawPath = FingerPath(color, brushSize)
            }

            else -> return false
        }
            invalidate() //refresh the layout
            return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawBitmap(canvasBitMap, 0f,0f,drawPaint)

        if (!drawPath.isEmpty){
            drawPaint.strokeWidth = drawPath.brushThickness
            drawPaint.color = drawPath.color
            canvas.drawPath(drawPath, drawPaint) //drawing path on canvas
        }
    }

    private fun setUpDrawing(){
        drawPaint = Paint()
        drawPath = FingerPath(color, brushSize)
        drawPaint.color = color
        drawPaint.style = Paint.Style.STROKE
        drawPaint.strokeJoin = Paint.Join.ROUND
        drawPaint.strokeCap = Paint.Cap.ROUND

        canvasPaint = Paint(Paint.DITHER_FLAG)
        brushSize = 20F
    }

    internal inner class FingerPath(var color: Int, var brushThickness: Float): Path()
}