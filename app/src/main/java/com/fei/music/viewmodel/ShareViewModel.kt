package com.fei.music.viewmodel

import androidx.lifecycle.ViewModel
import com.fei.music.ui.callback.UnPeekLiveData

class ShareViewModel : ViewModel() {

    val timeToAddSlideListener = UnPeekLiveData<Boolean>()

}