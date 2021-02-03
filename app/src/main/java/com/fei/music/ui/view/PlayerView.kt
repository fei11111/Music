package com.fei.music.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import com.fei.music.R

class PlayerView : FrameLayout {

    private lateinit var playPauseDrawable: PlayPauseDrawable

    @ColorInt
    var color = Color.BLACK

    @ColorInt
    var drawableColor = Color.BLACK


    private var isCircle = true
    var circleAlpha: Int = 0

    private lateinit var paint: Paint


    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {

        val obtainStyledAttributes =
            context.obtainStyledAttributes(attributeSet, R.styleable.PlayerView)
        color = obtainStyledAttributes.getColor(R.styleable.PlayerView_backgroundColor, Color.BLACK)
        drawableColor =
            obtainStyledAttributes.getColor(R.styleable.PlayerView_drawableColor, Color.BLACK)
        isCircle = obtainStyledAttributes.getBoolean(R.styleable.PlayerView_isCircleDraw, true)
        circleAlpha = obtainStyledAttributes.getInt(R.styleable.PlayerView_circleAlpha, 0)
        obtainStyledAttributes.recycle()

        init()
    }

    private fun init() {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = color
        paint.alpha = circleAlpha
        paint.style = Paint.Style.FILL

        playPauseDrawable = PlayPauseDrawable(context, drawableColor)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (isCircle) {
            var radius = Math.min(measuredWidth, measuredHeight) / 2.0f
            canvas?.drawCircle(measuredWidth / 2.0f, measuredHeight / 2.0f, radius, paint)
        }

        playPauseDrawable.draw(canvas!!)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        playPauseDrawable.setBounds(0, 0, w, h)
    }

    fun settableColor(color:Int) {
        drawableColor = color
        playPauseDrawable.setDrawableColor(color)
        invalidate()
    }

    fun settableAlpha(alpha:Int) {
        circleAlpha = alpha
        paint.alpha = circleAlpha
        invalidate()
    }


}