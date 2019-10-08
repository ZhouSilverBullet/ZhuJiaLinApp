package com.sdxxtop.zjlguardian.ui.message

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sdxxtop.base.BaseActivity
import com.sdxxtop.common.utils.ItemDivider
import com.sdxxtop.common.utils.UIUtils
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.databinding.ActivityMessageBinding
import com.sdxxtop.zjlguardian.ui.event_report.EventReportDetailActivity
import com.sdxxtop.zjlguardian.ui.message.adapter.MessageAdapter
import com.sdxxtop.zjlguardian.ui.message.viewmodel.MessageViewModel

class MessageActivity : BaseActivity<ActivityMessageBinding, MessageViewModel>() {

    val mAdapter by lazy {
        MessageAdapter()
    }

    override fun vmClazz() = MessageViewModel::class.java

    override fun bindVM() {
        mBinding.vm = mViewModel
    }

    override fun layoutId() = R.layout.activity_message

    override fun initObserve() {
        mViewModel.mMessageList.observe(this, Observer {
            mAdapter.replaceData(it)
        })
    }


    override fun initView() {
        mBinding.rv.layoutManager = LinearLayoutManager(this)
        mBinding.rv.addItemDecoration(ItemDivider().setDividerColor(0x00ffffff).setDividerWidth(UIUtils.dip2px(10)))
        mBinding.rv.adapter = mAdapter
    }

    override fun initEvent() {
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val eventReportItem = mAdapter.data[position]
            //0普通消息 1咨询 2投诉 3上报事件 4自行处理
            when (eventReportItem.type) {
                0 -> {
                    val intent = Intent(view.context, MessageDetailActivity::class.java)
                    intent.putExtra("messageId", eventReportItem.message_id)
                    startActivity(intent)
                }
                1 -> {

                }
                2 -> {

                }
                3 -> {

                }
                4 -> {

                }
                else -> {
                }
            }

        }

    }

    override fun loadData() {
        mViewModel.loadData()
    }

}
