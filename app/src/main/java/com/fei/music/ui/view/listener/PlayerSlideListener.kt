package com.fei.music.ui.view.listener

import android.animation.*
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Rect
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import com.fei.music.App
import com.fei.music.databinding.FragmentPlayerBinding
import com.fei.music.utils.ScreenUtils
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class PlayerSlideListener :
    SlidingUpPanelLayout.PanelSlideListener {

    private var fragmentPlayerBinding: FragmentPlayerBinding
    private var slidingUpPanelLayout: SlidingUpPanelLayout
    private val intEvaluator = IntEvaluator()
    private val floatEvaluator = FloatEvaluator()
    private val colorEvaluator = ArgbEvaluator()
    private val px55 = ScreenUtils.dp2px(App.instance, 55f)
    private val px10 = ScreenUtils.dp2px(App.instance, 10f)
    private val px36 = ScreenUtils.dp2px(App.instance, 36f)
    private val px20 = ScreenUtils.dp2px(App.instance, 20f)
    private var textOriginalSize: Float = 0f
    private val textEndSize = ScreenUtils.sp2px(App.instance, 25f)
    private val screenWidth = ScreenUtils.getScreenWidth()
    private val screenHeight = ScreenUtils.getScreenHeight()
    private val iconContainerMarginTop = screenHeight * 3 / 4
    private var center: Int = 0
    private var playerStart = 0
    private var playerEnd = 0
    private var nextStart = 0
    private var nextEnd = 0
    private var preStart = 0
    private var preEnd = 0
    private var listStart = 0
    private var listEnd = 0
    private var modeStart = 0
    private var modeEnd = 0
    private var topHeight = 0f
    private var topAnimator: ValueAnimator? = null
    private var textAnimator: ValueAnimator? = null
    private var isReverse: Boolean = false
    private var titleWidth = 0
    private var artistWidth = 0

    constructor(
        fragmentPlayerBinding: FragmentPlayerBinding,
        slidingUpPanelLayout: SlidingUpPanelLayout
    ) {
        this.fragmentPlayerBinding = fragmentPlayerBinding
        this.slidingUpPanelLayout = slidingUpPanelLayout
        val rect = Rect()
        fragmentPlayerBinding.tvTitle.paint.getTextBounds(
            fragmentPlayerBinding.tvTitle.text.toString(), 0,
            fragmentPlayerBinding.tvTitle.text.length, rect
        )
        titleWidth = rect.width()
        fragmentPlayerBinding.tvArtist.paint.getTextBounds(
            fragmentPlayerBinding.tvArtist.text.toString(), 0,
            fragmentPlayerBinding.tvArtist.text.length, rect
        )
        artistWidth = rect.width()
        textOriginalSize = fragmentPlayerBinding.tvTitle.textSize
        center = screenWidth / 2 - px36 / 2
        val gap = (screenWidth - px36 * 5) / 6
        playerStart = fragmentPlayerBinding.player.left
        playerEnd = center

        preStart = fragmentPlayerBinding.pre.left
        preEnd = center - px36 - gap

        modeStart = fragmentPlayerBinding.mode.left
        modeEnd = center - px36 * 2 - gap * 2

        nextStart = fragmentPlayerBinding.next.left
        nextEnd = center + px36 + gap

        listStart = fragmentPlayerBinding.list.left
        listEnd = center + px36 * 2 + gap * 2

        topHeight = fragmentPlayerBinding.rlTop.height.toFloat()
    }


    override fun onPanelSlide(panel: View?, slideOffset: Float) {
        Log.e("Tag", slideOffset.toString())
        //image
        var layoutParams = fragmentPlayerBinding.ivBg.layoutParams as ConstraintLayout.LayoutParams
        var tempInt = intEvaluator.evaluate(slideOffset, px55, screenWidth)
        layoutParams.width = tempInt
        layoutParams.height = tempInt
        fragmentPlayerBinding.ivBg.layoutParams = layoutParams

        //background
        var tempFloat = floatEvaluator.evaluate(slideOffset, 0, 0.3f)
        layoutParams = fragmentPlayerBinding.flBg.layoutParams as ConstraintLayout.LayoutParams
        layoutParams.height = tempInt
        fragmentPlayerBinding.flBg.layoutParams = layoutParams
        fragmentPlayerBinding.flBg.alpha = tempFloat

        //progress
        var tempInt2 = intEvaluator.evaluate(slideOffset, px55, 0)
        var tempInt3 = intEvaluator.evaluate(slideOffset, 0, screenWidth)
        layoutParams = fragmentPlayerBinding.progress.layoutParams as ConstraintLayout.LayoutParams
        layoutParams.marginStart = tempInt2
        layoutParams.topMargin = tempInt3
        fragmentPlayerBinding.progress.layoutParams = layoutParams

        //title
        tempInt = intEvaluator.evaluate(slideOffset, px55 + px10, px20)
        tempInt2 = intEvaluator.evaluate(slideOffset, px10, screenWidth - 3 * textEndSize)
        var tempFloat2 = floatEvaluator.evaluate(slideOffset, 0.5f, 1f)
        layoutParams = fragmentPlayerBinding.tvTitle.layoutParams as ConstraintLayout.LayoutParams
        layoutParams.matchConstraintPercentWidth = tempFloat2
        layoutParams.topMargin = tempInt2
        fragmentPlayerBinding.tvTitle.layoutParams = layoutParams
        fragmentPlayerBinding.tvTitle.setPadding(tempInt, 0, 0, 0)
        tempFloat = floatEvaluator.evaluate(slideOffset, textOriginalSize, textEndSize)
        fragmentPlayerBinding.tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, tempFloat)
        fragmentPlayerBinding.tvTitle.setTextColor(
            ColorStateList.valueOf(
                colorEvaluator.evaluate(
                    slideOffset,
                    Color.BLACK, Color.WHITE
                ) as Int
            )
        )

        //artist
        layoutParams = fragmentPlayerBinding.tvArtist.layoutParams as ConstraintLayout.LayoutParams
        fragmentPlayerBinding.tvArtist.setPadding(tempInt, 0, 0, 0)
        layoutParams.matchConstraintPercentWidth = tempFloat2
        fragmentPlayerBinding.tvArtist.layoutParams = layoutParams
        fragmentPlayerBinding.tvArtist.setTextColor(
            ColorStateList.valueOf(
                colorEvaluator.evaluate(
                    slideOffset,
                    Color.BLACK, Color.WHITE
                ) as Int
            )
        )

        //iconContainer
        tempInt = intEvaluator.evaluate(slideOffset, px10, iconContainerMarginTop)
        layoutParams = fragmentPlayerBinding.rlIcon.layoutParams as ConstraintLayout.LayoutParams
        layoutParams.topMargin = tempInt
        fragmentPlayerBinding.rlIcon.layoutParams = layoutParams
        fragmentPlayerBinding.player.x =
            floatEvaluator.evaluate(slideOffset, playerStart, playerEnd)
        fragmentPlayerBinding.player.settableColor(
            colorEvaluator.evaluate(
                slideOffset,
                Color.BLACK,
                Color.WHITE
            ) as Int
        )
        fragmentPlayerBinding.player.settableAlpha(intEvaluator.evaluate(slideOffset, 0, 255))
        fragmentPlayerBinding.mode.x = floatEvaluator.evaluate(slideOffset, modeStart, modeEnd)
        fragmentPlayerBinding.mode.alpha = floatEvaluator.evaluate(slideOffset, 0f, 1f)
        fragmentPlayerBinding.pre.x = floatEvaluator.evaluate(slideOffset, preStart, preEnd)
        fragmentPlayerBinding.pre.alpha = floatEvaluator.evaluate(slideOffset, 0f, 1f)
        fragmentPlayerBinding.next.x = floatEvaluator.evaluate(slideOffset, nextStart, nextEnd)
        fragmentPlayerBinding.list.x = floatEvaluator.evaluate(slideOffset, listStart, listEnd)
        fragmentPlayerBinding.list.alpha = floatEvaluator.evaluate(slideOffset, 0f, 1f)
    }

    override fun onPanelStateChanged(
        panel: View?,
        previousState: SlidingUpPanelLayout.PanelState?,
        newState: SlidingUpPanelLayout.PanelState?
    ) {
        Log.e("previousState", previousState?.name.toString())
        Log.e("newState", newState?.name.toString())
        if (previousState == SlidingUpPanelLayout.PanelState.DRAGGING && newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
            Log.e("tag", "最大时候")
            fragmentPlayerBinding.rlTop.visibility = View.VISIBLE
            topAnimation()

            fragmentPlayerBinding.parent.setOnClickListener {
                Log.e("tag", "点击")
                textAnimation()
            }

            fragmentPlayerBinding.down.setOnClickListener {
                slidingUpPanelLayout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
            }

        } else if (previousState == SlidingUpPanelLayout.PanelState.EXPANDED && newState == SlidingUpPanelLayout.PanelState.DRAGGING) {
            Log.e("tag", "开始不见了")
            fragmentPlayerBinding.rlTop.visibility = View.INVISIBLE
            if (isReverse) {
                //之前移动过
                textAnimation()
            }

        } else if (previousState == SlidingUpPanelLayout.PanelState.DRAGGING && newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
            fragmentPlayerBinding.parent.setOnClickListener {
                slidingUpPanelLayout.panelState = SlidingUpPanelLayout.PanelState.EXPANDED
            }
        }


    }

    private fun topAnimation() {
        if (topAnimator == null) {
            topAnimator = ValueAnimator.ofFloat(-topHeight, 0f)
            topAnimator!!.addUpdateListener {
                val value: Float = it.animatedValue as Float
                fragmentPlayerBinding.rlTop.y = value
            }
            topAnimator!!.duration = 300
            topAnimator!!.interpolator = DecelerateInterpolator()
        } else if (topAnimator!!.isRunning) {
            topAnimator!!.cancel()
        }
        topAnimator!!.start()
    }

    private fun textAnimation() {
        if (textAnimator != null && textAnimator!!.isRunning) return
        val titleStartY = screenWidth - 3 * textEndSize
        val titleEndY = px20

        val titleStartX = px20
        val titleEndX = screenWidth / 2 - titleWidth / 2

        val artistEndX = screenWidth / 2 - artistWidth / 2

        textAnimator =
            if (isReverse) ValueAnimator.ofFloat(1f, 0f) else ValueAnimator.ofFloat(0f, 1f)
        textAnimator!!.addUpdateListener {
            val temp = it.animatedValue as Float
            fragmentPlayerBinding.tvTitle.translationX = (titleEndX - titleStartX) * temp
            fragmentPlayerBinding.tvTitle.translationY = (titleEndY - titleStartY) * temp
            fragmentPlayerBinding.tvArtist.translationX = (artistEndX - titleStartX) * temp
            fragmentPlayerBinding.tvArtist.translationY = (titleEndY - titleStartY) * temp
        }
        textAnimator!!.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                isReverse = !isReverse
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

        })
        textAnimator!!.interpolator = DecelerateInterpolator()
        textAnimator!!.duration = 300
        textAnimator!!.start()
    }

}