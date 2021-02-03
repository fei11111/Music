package com.fei.music.utils

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.view.WindowManager
import com.fei.music.App

object ScreenUtils {

    /**
     * Return the width of screen, in pixel.
     *
     * @return the width of screen, in pixel
     */
    fun getScreenWidth(): Int {
        val wm =
            App.instance.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.defaultDisplay.getRealSize(point)
        } else {
            wm.defaultDisplay.getSize(point)
        }
        return point.x
    }

    /**
     * Return the height of screen, in pixel.
     *
     * @return the height of screen, in pixel
     */
    fun getScreenHeight(): Int {
        val wm =
            App.instance.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.defaultDisplay.getRealSize(point)
        } else {
            wm.defaultDisplay.getSize(point)
        }
        return point.y
    }

    fun dp2px(context: Context, dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    fun sp2px(context: Context, spValue: Float): Int {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f).toInt()
    }

}