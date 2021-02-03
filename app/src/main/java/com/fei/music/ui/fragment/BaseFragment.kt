package com.fei.music.ui.fragment

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.fei.music.ui.activity.BaseActivity

open class BaseFragment : Fragment() {

    private lateinit var mActivity: BaseActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as BaseActivity
    }

    fun getViewProvider(): ViewModelProvider? {
        checkActivity(this)
        return mActivity.getViewProvider()
    }

    private fun checkActivity(fragment: Fragment) {
        val activity = fragment.activity
            ?: throw IllegalStateException("Can't create ViewModelProvider for detached fragment")
    }

}