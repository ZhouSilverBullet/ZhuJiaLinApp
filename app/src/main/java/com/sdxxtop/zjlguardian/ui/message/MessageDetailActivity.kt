package com.sdxxtop.zjlguardian.ui.message

import androidx.lifecycle.Observer
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.sdxxtop.base.BaseActivity
import com.sdxxtop.ui.loadsir.ErrorCallback
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.databinding.ActivityMessageDetailBinding
import com.sdxxtop.zjlguardian.ui.message.viewmodel.MessageDetailViewModel


class MessageDetailActivity : BaseActivity<ActivityMessageDetailBinding, MessageDetailViewModel>() {

    lateinit var loadService: LoadService<Any>

    val messageId by lazy {
        intent.getIntExtra("messageId", 0)
    }

    override fun vmClazz() = MessageDetailViewModel::class.java

    override fun bindVM() {
        mBinding.vm = mViewModel
    }

    override fun layoutId() = R.layout.activity_message_detail

    override fun initObserve() {
        mViewModel.mMessageDetail.observe(this, Observer {
            mBinding.tvTitle.setText(it.title)
            mBinding.tvContent.setText(it.content)
            mBinding.tvTime.setText(it.add_time)

            loadService.showSuccess()
        })

        mViewModel.mThrowable.observe(this, Observer {
            loadService.showCallback(ErrorCallback::class.java)
        })
    }

    override fun initView() {
        loadService = LoadSir.getDefault().register(mBinding.llContent) {
            mViewModel.loadData(messageId)
        }
    }

    override fun loadData() {
        mViewModel.loadData(messageId)
    }
}
