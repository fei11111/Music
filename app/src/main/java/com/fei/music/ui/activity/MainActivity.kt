package com.fei.music.ui.activity

import android.os.Bundle
import com.fei.music.R
import com.fei.music.viewmodel.ShareViewModel

class MainActivity : BaseActivity() {

    private lateinit var shareViewModel: ShareViewModel

    private var mIsListened = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        shareViewModel = getViewProvider()?.get(ShareViewModel::class.java)!!
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        if (!mIsListened) {
            shareViewModel.timeToAddSlideListener.value = true
            mIsListened = true
        }

    }
}