package com.goth.cryptograph

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.View
import kotlin.math.min

@SuppressLint("ViewConstructor")
class CustomView(context: Context, sparkline: Array<Double?>, min: Double, max: Double) : View(context){

    private val paint = Paint()
    private val points = FloatArray(sparkline.size - 1)

    init {
        paint.apply {
            color = Color.rgb(0, 180, 200)
            strokeWidth = min(getScreenHeight().toFloat(), getScreenWidth().toFloat()) / 180f
        }

        for (i in 1 until sparkline.size)
        {
            points[i - 1] = ((sparkline[i]!! - min) / (max - min)).toFloat() * getScreenHeight()
        }
    }

    override fun onDraw(canvas: Canvas)
    {
        super.onDraw(canvas)
        for (i in 0..points.size-2)
        {
            canvas.drawLine(i * getScreenWidth().toFloat() / points.size, points[i],
                (i + 1) * getScreenWidth().toFloat() / points.size, points[i + 1], paint)
        }
    }

    private fun getScreenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    private fun getScreenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }
}