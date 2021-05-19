package com.goth.cryptograph

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.RelativeLayout

class CustomRelativeLayout(context: Context, attributeSet: AttributeSet) : RelativeLayout(context, attributeSet) {
    private val paint = Paint()

    init {
        paint.color = Color.RED
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawLine(0f, 0f, 100f, 1000f, paint)
        super.onDraw(canvas)
    }
}