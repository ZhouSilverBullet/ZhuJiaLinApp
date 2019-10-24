package com.sdxxtop.zjlguardian.ui.commission

import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.kingja.loadsir.core.LoadSir
import com.sdxxtop.base.BaseActivity
import com.sdxxtop.common.utils.ItemDivider
import com.sdxxtop.common.utils.UIUtils
import com.sdxxtop.ui.loadsir.EmptyCallback
import com.sdxxtop.ui.loadsir.ErrorCallback
import com.sdxxtop.ui.loadsir.LoadingCallback
import com.sdxxtop.zjlguardian.R
import com.sdxxtop.zjlguardian.databinding.ActivityCommissionBinding
import com.sdxxtop.zjlguardian.ui.commission.adapter.CommissionListAdapter
import com.sdxxtop.zjlguardian.ui.commission.viewmodel.CommissionViewModel
import com.sdxxtop.zjlguardian.ui.event_report.EventReportDetailActivity

class CommissionActivity : BaseActivity<ActivityCommissionBinding, CommissionViewModel>() {

    val mAdapter by lazy {
        CommissionListAdapter()
    }

    override fun vmClazz() = CommissionViewModel::class.java

    override fun bindVM() {
        mBinding.vm = mViewModel
    }

    override fun layoutId() = R.layout.activity_commission

    override fun initObserve() {
        mViewModel.mReportList.observe(this, Observer {
            mAdapter.replaceData(it)

            showLoadSir(it.isEmpty())
        })
    }

    override fun initView() {
        loadService.showCallback(LoadingCallback::class.java)

        mBinding.rv.layoutManager = LinearLayoutManager(this)
        mBinding.rv.addItemDecoration(ItemDivider().setDividerColor(0xE6E6E6).setDividerWidth(UIUtils.dip2px(10)))
        mBinding.rv.adapter = mAdapter
    }

    override fun loadSirBindView(): View {
        return mBinding.srlLayout
    }

    override fun initEvent() {
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val eventReportItem = mAdapter.data[position]
            val intent = Intent(view.context, EventReportDetailActivity::class.java)
            intent.putExtra(EventReportDetailActivity.KEY_EVENT_TYPE, EventReportDetailActivity.TYPE_COMMISSION)
            intent.putExtra(EventReportDetailActivity.REQUEST_TYPE, eventReportItem.type)
            intent.putExtra(EventReportDetailActivity.IS_COMMISSION, true)
            intent.putExtra("eventId", eventReportItem.event_id)
            startActivity(intent)
        }

        mBinding.stvTitle.tvRight.setOnClickListener {
            startActivity(Intent(this, CommissionDoneActivity::class.java))
        }
    }

    override fun loadData() {

        mViewModel.loadData()
    }
}
