package com.sdxxtop.zjlguardian.ui.mine.fragment


import androidx.fragment.app.Fragment
import com.sdxxtop.base.BaseNormalFragment
import com.sdxxtop.base.ext.topViewPadding
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.databinding.FragmentMineBinding

/**
 * A simple [Fragment] subclass.
 */
class MineFragment : BaseNormalFragment<FragmentMineBinding>() {
    override fun layoutId() = R.layout.fragment_mine

    override fun initView() {
        topViewPadding(mBinding.stvTitle)
    }

    override fun initData() {
    }

    override fun loadData() {
    }

}
