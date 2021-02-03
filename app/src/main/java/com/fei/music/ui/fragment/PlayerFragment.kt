package com.fei.music.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.fei.music.R
import com.fei.music.databinding.FragmentPlayerBinding
import com.fei.music.ui.view.listener.PlayerSlideListener
import com.fei.music.viewmodel.ShareViewModel
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class PlayerFragment : BaseFragment() {

    private lateinit var mBinding: ViewDataBinding
    private lateinit var shareViewModel: ShareViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate<FragmentPlayerBinding>(
            inflater,
            R.layout.fragment_player,
            container,
            false
        )
        mBinding.lifecycleOwner = this
        shareViewModel = getViewProvider()?.get(ShareViewModel::class.java)!!
        return mBinding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shareViewModel.timeToAddSlideListener.observe(viewLifecycleOwner,
            Observer<Boolean> { t ->
                t?.let {
                    if (it) {
                        if (view.parent.parent is SlidingUpPanelLayout) {
                            val slidingUpPanelLayout: SlidingUpPanelLayout =
                                view.parent.parent as SlidingUpPanelLayout
                            slidingUpPanelLayout.addPanelSlideListener(
                                PlayerSlideListener(
                                    mBinding as FragmentPlayerBinding,
                                    slidingUpPanelLayout
                                )
                            )
                        }
                    }
                }
            })

    }

}