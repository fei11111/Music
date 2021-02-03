package com.fei.music.ui.activity

import android.app.Activity
import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

open class BaseActivity : AppCompatActivity() {

    private var mFactory: ViewModelProvider.Factory? = null

    fun getViewProvider(): ViewModelProvider? = getAppFactory(this)?.let {
        ViewModelProvider(
            this,
            it
        )
    }

    private fun getAppFactory(activity: AppCompatActivity): ViewModelProvider.Factory? {
        val checkApplication = checkApplication(activity)
        if (mFactory == null) {
            mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(
                checkApplication
            )
        }
        return mFactory
    }

    private fun checkApplication(activity: Activity): Application {
        return activity.application
            ?: throw IllegalStateException(
                "Your activity/fragment is not yet attached to "
                        + "Application. You can't request ViewModel before onCreate call."
            )
    }
}